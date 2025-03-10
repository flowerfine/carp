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
package cn.sliew.carp.module.http.sync.framework.util;

import org.apache.pekko.japi.Pair;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public enum SyncOffsetHelper {
    ;

    public static boolean supportSplit(LocalDateTime startTime, LocalDateTime endTime, Duration gradient) {
        LocalDateTime nextStart = startTime.plus(gradient);
        return nextStart.isBefore(endTime);
    }

    public static List<Pair<LocalDateTime, LocalDateTime>> split(LocalDateTime startTime, LocalDateTime endTime, Duration gradient, int total) {
        List<Pair<LocalDateTime, LocalDateTime>> pairs = new LinkedList<>();
        LocalDateTime nextStart = startTime.plus(gradient);
        for (int i = 0; i < total; i++) {
            if (nextStart.isAfter(endTime)) {
                break;
            }
            pairs.add(new Pair<>(startTime, nextStart));
            startTime = nextStart;
            nextStart = startTime.plus(gradient);
        }
        return pairs;
    }
}
