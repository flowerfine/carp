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
package cn.sliew.carp.module.queue.api;

import lombok.Builder;
import lombok.Data;
import com.github.f4b6a3.uuid.UuidCreator;

import java.util.EventObject;
import java.util.Map;
import java.util.Objects;

@Data
@Builder
public final class Message extends EventObject {

    public Message() {
        super(new Object());
    }

    public Message(Object source) {
        super(source);
    }

    public Message(String id, String topic, Integer retry, Integer maxRetry, Integer backoffMills, Map<String, Object> headers, byte[] body) {
        super(new Object());
        this.id = id;
        this.topic = topic;
        this.retry = retry;
        this.maxRetry = maxRetry;
        if (Objects.nonNull(backoffMills)) {
            this.backoffMills = backoffMills;
        }
        this.headers = headers;
        this.body = body;
    }

    @Builder.Default
    private String id = UuidCreator.getShortPrefixComb().toString();
    private String topic;

    @Builder.Default
    private Integer retry = 1;
    @Builder.Default
    private Integer maxRetry = 3;
    private Integer backoffMills = 0;

    private Map<String, Object> headers;
    private byte[] body;
}
