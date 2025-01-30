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
package cn.sliew.carp.module.orca.spinnaker.keiko.core;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.function.BiConsumer;

public interface Queue {

    int maxRetries = 5;

    TemporalAmount getAckTimeout();

    List<BiConsumer<Queue, Message>> getDeadMessageHandlers();

    Boolean getCanPollMany();

    void poll(BiConsumer<Message, Runnable> callback);

    void poll(Integer maxMessages, BiConsumer<Message, Runnable> callback);

    default void push(Message message) {
        push(message, Duration.ZERO);
    }

    void push(Message message, TemporalAmount delay);

    default void reschedule(Message message) {
        reschedule(message, Duration.ZERO);
    }

    void reschedule(Message message, TemporalAmount delay);

    void ensure(Message message, TemporalAmount delay);

    void retry();

    void clear();

}
