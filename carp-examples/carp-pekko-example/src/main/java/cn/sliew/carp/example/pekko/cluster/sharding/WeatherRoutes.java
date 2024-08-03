/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.carp.example.pekko.cluster.sharding;

import cn.sliew.carp.example.pekko.cluster.sharding.command.WeatherStationCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.cluster.sharding.typed.javadsl.ClusterSharding;
import org.apache.pekko.cluster.sharding.typed.javadsl.EntityRef;
import org.apache.pekko.http.javadsl.marshallers.jackson.Jackson;
import org.apache.pekko.http.javadsl.model.HttpEntity;
import org.apache.pekko.http.javadsl.model.StatusCodes;
import org.apache.pekko.http.javadsl.server.Route;
import org.apache.pekko.http.javadsl.unmarshalling.Unmarshaller;
import org.apache.pekko.serialization.jackson.JacksonObjectMapperProvider;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static org.apache.pekko.http.javadsl.server.Directives.*;
import static org.apache.pekko.http.javadsl.server.PathMatchers.*;

public class WeatherRoutes {

    private final ClusterSharding sharding;
    private final Duration timeout;
    private final ObjectMapper objectMapper;
    private final Unmarshaller<HttpEntity, WeatherStationCommand.Data> dataUnmarshaller;

    public WeatherRoutes(ActorSystem<?> system) {
        sharding = ClusterSharding.get(system);
        timeout = system.settings().config().getDuration("killrweather.routes.ask-timeout");
        // use a pre-configured object mapper from pekko-jackson also for HTTP JSON
        // this lets us use the -parameters compiler argument to skip annotating field names on immutable classes
        objectMapper = JacksonObjectMapperProvider.get(system).getOrCreate("jackson-json", Optional.empty());
        dataUnmarshaller = Jackson.unmarshaller(objectMapper, WeatherStationCommand.Data.class);
    }

    private CompletionStage<WeatherStationCommand.DataRecorded> recordData(long wsid, WeatherStationCommand.Data data) {
        EntityRef<WeatherStationCommand> ref = sharding.entityRefFor(WeatherStation.TypeKey, Long.toString(wsid));
        return ref.ask(replyTo -> new WeatherStationCommand.Record(data, System.currentTimeMillis(), replyTo), timeout);
    }

    private CompletionStage<WeatherStationCommand.QueryResult> query(long wsid, WeatherStationCommand.DataType dataType, WeatherStationCommand.Function function) {
        EntityRef<WeatherStationCommand> ref = sharding.entityRefFor(WeatherStation.TypeKey, Long.toString(wsid));
        return ref.ask(replyTo -> new WeatherStationCommand.Query(dataType, function, replyTo), timeout);
    }

    // unmarshallers for the query parameters
    private final Unmarshaller<String, WeatherStationCommand.Function> functionUnmarshaller = Unmarshaller.sync(text -> {
        String lcText = text.toLowerCase();
        switch (lcText) {
            case "current":
                return WeatherStationCommand.Function.Current;
            case "highlow":
                return WeatherStationCommand.Function.HighLow;
            case "average":
                return WeatherStationCommand.Function.Average;
            default:
                throw new IllegalArgumentException("Unknown function " + lcText);
        }
    });
    private final Unmarshaller<String, WeatherStationCommand.DataType> dataTypeUnmarshaller = Unmarshaller.sync(text -> {
        String lcText = text.toLowerCase();
        switch (lcText) {
            case "temperature":
                return WeatherStationCommand.DataType.Temperature;
            case "dewpoint":
                return WeatherStationCommand.DataType.DewPoint;
            case "pressure":
                return WeatherStationCommand.DataType.Pressure;
            default:
                throw new IllegalArgumentException("Unknown data type " + lcText);
        }
    });


    public Route weather() {
        return path(segment("weather").slash().concat(longSegment()), wsid ->
                concat(
                        get(() ->
                                parameter(dataTypeUnmarshaller, "type", (dataType ->
                                        parameter(functionUnmarshaller, "function", (function ->
                                                completeOKWithFuture(query(wsid, dataType, function), Jackson.marshaller())
                                        ))
                                ))
                        ),
                        post(() ->
                                entity(dataUnmarshaller, data ->
                                        onSuccess(recordData(wsid, data), performed ->
                                                complete(StatusCodes.ACCEPTED, performed + " from event time: " + data.eventTime)
                                        )
                                )
                        )
                )
        );
    }

}
