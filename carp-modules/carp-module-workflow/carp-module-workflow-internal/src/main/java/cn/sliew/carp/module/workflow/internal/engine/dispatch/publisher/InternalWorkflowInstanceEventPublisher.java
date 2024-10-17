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

import cn.sliew.carp.module.queue.api.Message;
import cn.sliew.carp.module.queue.api.Queue;
import cn.sliew.carp.module.queue.api.QueueFactory;
import cn.sliew.carp.module.queue.api.util.Serder;
import cn.sliew.carp.module.workflow.api.engine.dispatch.event.WorkflowInstanceStatusEvent;
import cn.sliew.carp.module.workflow.api.engine.dispatch.publisher.WorkflowInstanceEventPublisher;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.InternalWorkflowInstanceEventDispatcher;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowInstanceEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InternalWorkflowInstanceEventPublisher implements WorkflowInstanceEventPublisher {

    @Autowired
    private QueueFactory queueFactory;

    @Override
    public void publish(WorkflowInstanceStatusEvent event) {
        if (event instanceof WorkflowInstanceEventDTO == false) {
            throw new RuntimeException();
        }

        WorkflowInstanceEventDTO eventDTO = (WorkflowInstanceEventDTO) event;

        Queue queue = queueFactory.get(InternalWorkflowInstanceEventDispatcher.TOPIC);
        Message message = Message.builder()
                .topic(queue.getName())
                .body(Serder.serializeByJava(eventDTO))
                .build();
        queue.push(message);
    }
}