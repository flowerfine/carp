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
package cn.sliew.carp.module.queue.api.memory;

import cn.sliew.carp.module.queue.api.ListenerManager;
import cn.sliew.carp.module.queue.api.Message;
import cn.sliew.carp.module.queue.api.MessageHandler;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
class MessageTask implements TimerTask {

    private final Message message;
    private final ListenerManager listenerManager;

    public MessageTask(Message message, ListenerManager listenerManager) {
        this.message = message;
        this.listenerManager = listenerManager;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        Map<String, List<MessageHandler>> listeners = listenerManager.get(message.getTopic());
        if (CollectionUtils.isEmpty(listeners) == false) {
            for (Map.Entry<String, List<MessageHandler>> entry : listeners.entrySet()) {
                List<MessageHandler> handlers = entry.getValue();
                if (CollectionUtils.isEmpty(handlers) == false) {
                    MessageHandler handler = handlers.get(0);
                    try {
                        handler.handler(message);
                    } catch (Exception e) {
                        log.error("message deliver error, msgId: {}, topic: {}, consumerGroup: {}, retry: {}, maxRetry: {}",
                                message.getId(), message.getTopic(), entry.getKey(), message.getRetry(), message.getMaxRetry(), e);
                        if (message.getRetry() < message.getMaxRetry()) {
                            message.setRetry(message.getRetry() + 1);
                            timeout.timer().newTimeout(this, message.getBackoffMills(), TimeUnit.MILLISECONDS);
                        } else {
                            log.error("message retry reach max retry, give up deliver! msgId: {}, topic: {}, consumerGroup: {}",
                                    message.getId(), message.getTopic(), entry.getKey(), e);
                        }
                    }
                }
            }
        }
    }
}
