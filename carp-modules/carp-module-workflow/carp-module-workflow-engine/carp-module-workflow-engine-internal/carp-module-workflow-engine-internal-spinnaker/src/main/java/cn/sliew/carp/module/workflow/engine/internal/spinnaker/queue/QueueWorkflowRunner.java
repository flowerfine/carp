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
package cn.sliew.carp.module.workflow.engine.internal.spinnaker.queue;

import cn.sliew.carp.framework.common.serder.SerDer;
import cn.sliew.carp.framework.common.serder.jdk.JdkSerDerFactory;
import cn.sliew.carp.framework.pubsub.model.PubsubChannel;
import cn.sliew.carp.framework.pubsub.model.PubsubChannelFactory;
import cn.sliew.carp.module.workflow.engine.internal.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.engine.internal.spinnaker.dispatch.InternalWorkflowInstanceDispatcher;
import cn.sliew.carp.module.workflow.engine.internal.spinnaker.model.WorkflowRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class QueueWorkflowRunner implements WorkflowRunner {

    @Autowired
    private PubsubChannelFactory pubsubChannelFactory;

    @Override
    public void start(WorkflowInstance workflowInstance,
                      Map<String, Object> inputs,
                      Map<String, Map<String, Object>> stepInputs) {
        PubsubChannel channel = pubsubChannelFactory.get(InternalWorkflowInstanceDispatcher.TOPIC);
        SerDer serDer = JdkSerDerFactory.INSTANCE.getInstance();
        channel.push(new String(serDer.serialize(new Messages.InitWorkflow(workflowInstance, inputs, stepInputs)), StandardCharsets.UTF_8));
    }
}
