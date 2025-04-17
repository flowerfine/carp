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
package cn.sliew.carp.module.workflow.engine.internal.core.engine.dispatch.publisher;

import cn.sliew.carp.framework.pubsub.model.PubsubChannel;
import cn.sliew.carp.framework.pubsub.model.PubsubChannelFactory;
import cn.sliew.carp.module.workflow.engine.internal.api.engine.dispatch.event.WorkflowTaskInstanceStatusEvent;
import cn.sliew.carp.module.workflow.engine.internal.api.engine.dispatch.publisher.WorkflowTaskInstanceEventPublisher;
import cn.sliew.carp.module.workflow.engine.internal.core.engine.dispatch.event.InternalWorkflowTaskInstanceStatusEvent;
import cn.sliew.carp.module.workflow.engine.internal.core.engine.dispatch.InternalWorkflowTaskInstanceEventDispatcher;

public class InternalWorkflowTaskInstanceEventPublisher implements WorkflowTaskInstanceEventPublisher {

    private PubsubChannelFactory pubsubChannelFactory;

    public InternalWorkflowTaskInstanceEventPublisher(PubsubChannelFactory pubsubChannelFactory) {
        this.pubsubChannelFactory = pubsubChannelFactory;
    }

    @Override
    public void publish(WorkflowTaskInstanceStatusEvent event) {
        if (event instanceof InternalWorkflowTaskInstanceStatusEvent eventDTO) {
            PubsubChannel channel = pubsubChannelFactory.get(InternalWorkflowTaskInstanceEventDispatcher.TOPIC);
            channel.push(eventDTO);
            return;
        }

        throw new RuntimeException();
    }
}
