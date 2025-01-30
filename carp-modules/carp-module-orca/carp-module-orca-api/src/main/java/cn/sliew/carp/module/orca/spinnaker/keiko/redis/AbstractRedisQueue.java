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
package cn.sliew.carp.module.orca.spinnaker.keiko.redis;

import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;

import java.time.Clock;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

abstract class AbstractRedisQueue implements Queue {

    private Clock clock;
    private TemporalAmount ackTimeout = Duration.ofMinutes(1);
    private List<BiConsumer<Queue, Message>> deadMessageHandlers = new ArrayList<>();
    private Boolean canPollMany = false;

    private ObjectMapper mapper;
    private Integer lockTtlSeconds = 10;

    protected abstract String getQueueKey();
    protected abstract String getUnackedKey();
    protected abstract String getMessagesKey();
    protected abstract String getLocksKey();
    protected abstract String getAttemptsKey();

    protected abstract String getCacheScript();
    protected abstract String getReadMessageWithLockScriptSha();

    protected void handleDeadMessage(Message message) {
        if (CollectionUtils.isNotEmpty(deadMessageHandlers)) {
            deadMessageHandlers.forEach(handler -> handler.accept(this, message));
        }
    }

    protected Double score(TemporalAmount delay) {
        if (Objects.isNull(delay)) {
            delay = Duration.ZERO;
        }
        return Double.valueOf(clock.instant().plus(delay).toEpochMilli());
    }


}
