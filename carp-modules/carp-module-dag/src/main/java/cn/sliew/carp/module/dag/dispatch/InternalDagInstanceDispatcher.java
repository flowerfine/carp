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
package cn.sliew.carp.module.dag.dispatch;

import cn.sliew.carp.framework.common.serder.SerDer;
import cn.sliew.carp.framework.common.serder.jdk.JdkSerDerFactory;
import cn.sliew.carp.module.dag.queue.handler.DagMessageHandler;
import cn.sliew.carp.module.queue.api.Message;
import cn.sliew.carp.module.queue.api.MessageHandler;
import cn.sliew.carp.module.queue.api.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@MessageListener(topic = InternalDagInstanceDispatcher.TOPIC, consumerGroup = InternalDagInstanceDispatcher.CONSUMER_GROUP)
public class InternalDagInstanceDispatcher implements DagInstanceDispatcher, MessageHandler, InitializingBean, DisposableBean {

    public static final String TOPIC = "TOPIC_CARP_INTERNAL_DAG_INSTANCE_EVENT";
    public static final String CONSUMER_GROUP = "CONSUMER_GROUP_CARP_INTERNAL_DAG_INSTANCE_EVENT";

    @Autowired
    private List<DagMessageHandler> handlers;

    private Map<Class, DagMessageHandler> registry = new HashMap<>();
    private ThreadPoolTaskExecutor taskExecutor;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(handlers) == false) {
            handlers.stream().forEach(handler -> registry.put(handler.getMessageType(), handler));
        }
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(5);
        executor.setCorePoolSize(1);
        executor.setThreadNamePrefix("dag-instance-thread-pool-");
        executor.initialize();
        taskExecutor = executor;
    }

    @Override
    public void destroy() throws Exception {
        if (taskExecutor != null) {
            taskExecutor.shutdown();
        }
    }

    @Override
    public void handler(Message message) throws Exception {
        if (message.getBody() != null) {
            SerDer serDer = JdkSerDerFactory.INSTANCE.getInstance();
            Object eventDTO = serDer.deserialize(message.getBody(), Object.class);
            if (Objects.nonNull(eventDTO)) {
                dispatch(eventDTO);
            }
        }
    }

    @Override
    public void dispatch(Object event) {
        if (registry.containsKey(event.getClass()) == false) {
            throw new RuntimeException("unknown workflow instance event: " + event.getClass().getSimpleName());
        }
        DagMessageHandler handler = registry.get(event.getClass());
        CompletableFuture.runAsync(() -> handler.handle(event), taskExecutor)
                .whenComplete((unused, throwable) -> {
                    if (throwable != null) {
                        log.error("workflow instance event dispatch failed", throwable);
                    }
                });
    }

}
