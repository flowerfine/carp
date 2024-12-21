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
package cn.sliew.carp.module.workflow.internal.engine.dispatch.publisher;

import cn.sliew.carp.framework.common.serder.SerDer;
import cn.sliew.carp.framework.common.serder.jdk.JdkSerDerFactory;
import cn.sliew.carp.module.queue.api.Message;
import cn.sliew.carp.module.queue.api.Queue;
import cn.sliew.carp.module.queue.api.QueueFactory;
import cn.sliew.carp.module.workflow.api.engine.dispatch.event.WorkflowTaskInstanceStatusEvent;
import cn.sliew.carp.module.workflow.api.engine.dispatch.publisher.WorkflowTaskInstanceEventPublisher;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.InternalWorkflowTaskInstanceEventDispatcher;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowTaskInstanceEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InternalWorkflowTaskInstanceEventPublisher implements WorkflowTaskInstanceEventPublisher {

    @Autowired
    private QueueFactory queueFactory;

    @Override
    public void publish(WorkflowTaskInstanceStatusEvent event) {
        if (event instanceof WorkflowTaskInstanceEventDTO == false) {
            throw new RuntimeException();
        }

        WorkflowTaskInstanceEventDTO eventDTO = (WorkflowTaskInstanceEventDTO) event;

        Queue queue = queueFactory.get(InternalWorkflowTaskInstanceEventDispatcher.TOPIC);
        SerDer serDer = JdkSerDerFactory.INSTANCE.getInstance();
        Message message = Message.builder()
                .topic(queue.getName())
                .body(serDer.serialize(eventDTO))
                .build();
        queue.push(message);
    }
}
