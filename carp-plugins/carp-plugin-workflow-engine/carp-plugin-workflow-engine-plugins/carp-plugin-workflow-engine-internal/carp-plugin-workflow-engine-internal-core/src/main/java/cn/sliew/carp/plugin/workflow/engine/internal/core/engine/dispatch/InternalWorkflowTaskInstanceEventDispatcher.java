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
package cn.sliew.carp.plugin.workflow.engine.internal.core.engine.dispatch;

import cn.hutool.core.util.ClassUtil;
import cn.sliew.carp.framework.common.serder.SerDer;
import cn.sliew.carp.framework.common.serder.jdk.JdkSerDerFactory;
import cn.sliew.carp.framework.pubsub.annotation.PubsubListener;
import cn.sliew.carp.framework.queue.kekio.MessageHandler;
import cn.sliew.carp.framework.queue.kekio.Queue;
import cn.sliew.carp.framework.queue.kekio.message.CommonMessage;
import cn.sliew.carp.plugin.workflow.engine.internal.api.engine.dispatch.WorkflowTaskInstanceEventDispatcher;
import cn.sliew.carp.plugin.workflow.engine.internal.api.engine.dispatch.event.WorkflowTaskInstanceStatusEvent;
import cn.sliew.carp.plugin.workflow.engine.internal.core.engine.dispatch.event.InternalWorkflowTaskInstanceStatusEvent;
import cn.sliew.carp.plugin.workflow.engine.internal.core.engine.dispatch.handler.task.InternalTaskEventListener;
import cn.sliew.carp.plugin.workflow.engine.internal.core.statemachine.InternalWorkflowTaskInstanceStateMachine;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.enums.CarpWorkflowTaskInstanceEvent;
import cn.sliew.milky.common.util.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@PubsubListener(queue = InternalWorkflowTaskInstanceEventDispatcher.TOPIC, group = InternalWorkflowTaskInstanceStateMachine.CONSUMER_GROUP)
public class InternalWorkflowTaskInstanceEventDispatcher implements WorkflowTaskInstanceEventDispatcher, MessageHandler<CommonMessage>, InitializingBean, DisposableBean {

    public static final String TOPIC = "TOPIC_CARP_INTERNAL_WORKFLOW_TASK_INSTANCE_EVENT";

    @Autowired(required = false)
    private List<InternalTaskEventListener> handlers;

    private Map<CarpWorkflowTaskInstanceEvent, List<InternalTaskEventListener>> registry = new HashMap<>();
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(handlers) == false) {
            handlers.stream().forEach(handler -> MapUtil.computeIfAbsent(registry, handler.getType(), k -> new ArrayList<>()).add(handler));
        }
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(5);
        executor.setCorePoolSize(1);
        executor.setThreadNamePrefix("workflow-task-instance-thread-pool-");
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
    public Queue getQueue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<CommonMessage> getMessageType() {
        return CommonMessage.class;
    }

    @Override
    public void handle(CommonMessage message) {
        if (message.getBody() != null) {
            SerDer serDer = JdkSerDerFactory.INSTANCE.getInstance();
            Object messageBody = serDer.deserialize(message.getBody(), Object.class);
            if (messageBody instanceof InternalWorkflowTaskInstanceStatusEvent eventDTO) {
                dispatch(eventDTO);
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }

    @Override
    public void dispatch(WorkflowTaskInstanceStatusEvent event) {
        if (registry.containsKey(event.getEvent()) == false) {
            throw new RuntimeException("unknown workflow task instance event: "
                    + event.getEvent().getLabel() + "[" + event.getEvent().getValue() + "]");
        }

        List<InternalTaskEventListener> eventHandlers = registry.get(event.getEvent());
        InternalTaskEventListener handler = eventHandlers.stream()
                .filter(item -> {
                    Class<?> typeArgument = ClassUtil.getTypeArgument(item.getClass());
                    if (Objects.nonNull(typeArgument)) {
                        return typeArgument.isAssignableFrom(event.getClass());
                    } else {
                        return false;
                    }
                })
                .findFirst()
                .orElseThrow(() -> new RuntimeException("unknown workflow task instance event: " + event.getClass().getSimpleName()));

        CompletableFuture.runAsync(() -> handler.handle((InternalWorkflowTaskInstanceStatusEvent) event), taskExecutor)
                .whenComplete((unused, throwable) -> {
                    if (throwable != null) {
                        log.error("workflow task instance event dispatch failed", throwable);
                    }
                });
    }
}
