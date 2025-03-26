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
import cn.sliew.carp.module.workflow.api.engine.dispatch.event.WorkflowStepInstanceStatusEvent;
import cn.sliew.carp.module.workflow.api.engine.dispatch.publisher.WorkflowStepInstanceEventPublisher;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.InternalWorkflowInstanceEventDispatcher;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowStepInstanceEventDTO;

public class InternalWorkflowStepInstanceEventPublisher implements WorkflowStepInstanceEventPublisher {

    private QueueFactory queueFactory;

    public InternalWorkflowStepInstanceEventPublisher(QueueFactory queueFactory) {
        this.queueFactory = queueFactory;
    }

    @Override
    public void publish(WorkflowStepInstanceStatusEvent event) {
        if (event instanceof WorkflowStepInstanceEventDTO eventDTO) {
            Queue queue = queueFactory.get(InternalWorkflowInstanceEventDispatcher.TOPIC);
            SerDer serDer = JdkSerDerFactory.INSTANCE.getInstance();
            Message message = Message.builder()
                    .topic(queue.getName())
                    .body(serDer.serialize(eventDTO))
                    .build();
            queue.push(message);
            return;
        }

        throw new RuntimeException();
    }
}
