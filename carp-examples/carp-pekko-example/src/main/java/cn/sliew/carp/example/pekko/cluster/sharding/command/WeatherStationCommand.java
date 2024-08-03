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

package cn.sliew.carp.example.pekko.cluster.sharding.command;

import cn.sliew.carp.framework.pekko.serialization.CborSerializable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.pekko.actor.typed.ActorRef;

import java.util.List;

public interface WeatherStationCommand extends CborSerializable {

    final class Record implements WeatherStationCommand {
        public final Data data;
        public final long processingTimestamp;
        public final ActorRef<DataRecorded> replyTo;
        public Record(Data data, long processingTimestamp, ActorRef<DataRecorded> replyTo) {
            this.data = data;
            this.processingTimestamp = processingTimestamp;
            this.replyTo = replyTo;
        }
    }

    final class Query implements WeatherStationCommand {
        public final DataType dataType;
        public final Function func;
        public final ActorRef<QueryResult> replyTo;
        public Query(DataType dataType, Function func, ActorRef<QueryResult> replyTo) {
            this.dataType = dataType;
            this.func = func;
            this.replyTo = replyTo;
        }
    }

    final class DataRecorded implements CborSerializable {
        public final String wsid;
        @JsonCreator
        public DataRecorded(String wsid) {
            this.wsid = wsid;
        }

        @Override
        public String toString() {
            return "DataRecorded{" +
                    "wsid='" + wsid + '\'' +
                    '}';
        }
    }

    final class QueryResult implements CborSerializable {
        public final String wsid;
        public final DataType dataType;
        public final Function function;
        public final int readings;
        public final List<TimeWindow> value;
        @JsonCreator
        public QueryResult(String wsid, DataType dataType, Function function, int readings, List<TimeWindow> value) {
            this.wsid = wsid;
            this.dataType = dataType;
            this.function = function;
            this.readings = readings;
            this.value = value;
        }
    }

    enum Function {
        @JsonProperty("highlow")
        HighLow,
        @JsonProperty("average")
        Average,
        @JsonProperty("current")
        Current
    }
    enum DataType {
        @JsonProperty("temperature")
        Temperature,
        @JsonProperty("dewpoint")
        DewPoint,
        @JsonProperty("pressure")
        Pressure
    }


    class Data {
        /**
         * unix timestamp when collected
         */
        public final long eventTime;
        public final DataType dataType;
        /**
         * Air temperature (degrees Celsius)
         */
        public final double value;

        @JsonCreator
        public Data(long eventTime, DataType dataType, double value) {
            this.eventTime = eventTime;
            this.dataType = dataType;
            this.value = value;
        }
    }

    final class TimeWindow {
        public final long start;
        public final long end;
        public final double value;
        public TimeWindow(long start, long end, double value) {
            this.start = start;
            this.end = end;
            this.value = value;
        }
    }
}
