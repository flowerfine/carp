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
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.PostStop;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;
import org.apache.pekko.cluster.sharding.typed.javadsl.ClusterSharding;
import org.apache.pekko.cluster.sharding.typed.javadsl.Entity;
import org.apache.pekko.cluster.sharding.typed.javadsl.EntityTypeKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherStation extends AbstractBehavior<WeatherStationCommand> {

    // setup for using WeatherStations through Apache Pekko Cluster Sharding
    // these could also live elsewhere and the WeatherStation class be completely
    // oblivious to being used in sharding
    public static final EntityTypeKey<WeatherStationCommand> TypeKey =
            EntityTypeKey.create(WeatherStationCommand.class, "WeatherStation");

    public static void initSharding(ActorSystem<?> system) {
        ClusterSharding.get(system).init(Entity.of(TypeKey, entityContext ->
                WeatherStation.create(entityContext.getEntityId())
        ));
    }

    public static Behavior<WeatherStationCommand> create(String wsid) {
        return Behaviors.setup(context -> new WeatherStation(context, wsid));
    }

    private static double average(List<Double> values) {
        return values.stream().mapToDouble(i -> i).average().getAsDouble();
    }

    private final String wsid;
    private final List<WeatherStationCommand.Data> values = new ArrayList<>();

    public WeatherStation(ActorContext<WeatherStationCommand> context, String wsid) {
        super(context);
        this.wsid = wsid;
    }

    @Override
    public Receive<WeatherStationCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(WeatherStationCommand.Record.class, this::onRecord)
                .onMessage(WeatherStationCommand.Query.class, this::onQuery)
                .onSignalEquals(PostStop.instance(), this::postStop)
                .build();
    }

    private Behavior<WeatherStationCommand> onRecord(WeatherStationCommand.Record record) {
        values.add(record.data);
        if (getContext().getLog().isDebugEnabled()) {
            List<Double> dataForSameType = values.stream()
                    .filter(d -> d.dataType == record.data.dataType)
                    .map(d -> d.value)
                    .collect(Collectors.toList());
            double averageForSameType = average(dataForSameType);
            getContext().getLog().debug("{} total readings from station {}, type {}, average {}, diff: processingTime - eventTime: {} ms",
                    values.size(),
                    wsid,
                    record.data.dataType,
                    averageForSameType,
                    record.processingTimestamp - record.data.eventTime
            );
        }
        record.replyTo.tell(new WeatherStationCommand.DataRecorded(wsid));
        return this;
    }

    private Behavior<WeatherStationCommand> onQuery(WeatherStationCommand.Query query) {
        List<WeatherStationCommand.Data> dataForType = values.stream().filter(d -> d.dataType == query.dataType).collect(Collectors.toList());
        final List<WeatherStationCommand.TimeWindow> queryResult;
        if (dataForType.isEmpty()) {
            queryResult = Collections.emptyList();
        } else {
            switch (query.func) {
                case Average:
                    long start = dataForType.stream().findFirst().map(d -> d.eventTime).orElse(0L);
                    long end = dataForType.isEmpty() ? 0 : dataForType.get(dataForType.size() - 1).eventTime;
                    List<Double> valuesForType = dataForType.stream().map(d -> d.value).collect(Collectors.toList());
                    queryResult = Collections.singletonList(new WeatherStationCommand.TimeWindow(start, end, average(valuesForType)));
                    break;
                case HighLow:
                    WeatherStationCommand.Data min = dataForType.stream().reduce((a, b) -> a.value < b.value ? a : b).get();
                    WeatherStationCommand.Data max = dataForType.stream().reduce((a, b) -> a.value > b.value ? a : b).get();
                    queryResult = Arrays.asList(
                            new WeatherStationCommand.TimeWindow(min.eventTime, max.eventTime, min.value),
                            new WeatherStationCommand.TimeWindow(min.eventTime, max.eventTime, max.value));
                    break;
                case Current:
                    // we know it is not empty from up above
                    WeatherStationCommand.Data current = dataForType.get(dataForType.size() - 1);
                    queryResult = Collections.singletonList(new WeatherStationCommand.TimeWindow(current.eventTime, current.eventTime, current.value));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown operation " + query.func);
            }
        }
        query.replyTo.tell(new WeatherStationCommand.QueryResult(wsid, query.dataType, query.func, dataForType.size(), queryResult));
        return this;
    }

    private Behavior<WeatherStationCommand> postStop() {
        getContext().getLog().info("Stopping, losing all recorded state for station {}", wsid);
        return this;
    }
}
