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
package cn.sliew.carp.module.queue.redis.test1;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Queue {

    void pull(Consumer callback);

    void pull(Integer maxMessages, Consumer<TestMessage> callback);

    void push(TestMessage message);

    void push(TestMessage message, TemporalAmount delay);

    default void reschedule(TestMessage message) {
        reschedule(message, Duration.ZERO);
    }

    void reschedule(TestMessage message, TemporalAmount delay);

    void ensure(TestMessage message, TemporalAmount delay);

    default void retry() {

    }

    default void clear() {

    }

    TemporalAmount getAckTimeout();

    List<BiConsumer<TestMessage, Queue>> getDeadMessageCallbacks();

}
