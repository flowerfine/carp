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
package cn.sliew.carp.module.workflow.spinnaker.manager;

import cn.sliew.carp.framework.common.serder.SerDer;
import cn.sliew.carp.framework.common.serder.jdk.JdkSerDerFactory;
import cn.sliew.carp.framework.pubsub.model.PubsubChannel;
import cn.sliew.carp.framework.pubsub.model.PubsubChannelFactory;
import cn.sliew.carp.module.workflow.api.manager.WorkflowInstanceManager;
import cn.sliew.carp.module.workflow.api.service.ServerlessWorkflowInstanceService;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.spinnaker.dispatch.InternalWorkflowInstanceDispatcher;
import cn.sliew.carp.module.workflow.spinnaker.queue.Messages;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@AllArgsConstructor
public class SpinnakerWorkflowInstanceManager implements WorkflowInstanceManager {

    private ServerlessWorkflowInstanceService workflowInstanceService;
    private PubsubChannelFactory pubsubChannelFactory;

    @Override
    public void deploy(Long id, JsonNode globalVariable) {
        WorkflowInstance workflowInstance = workflowInstanceService.getGraph(id);
        Map<String, Object> inputs = Map.of("foo", "foo-data", "bar", "bar-data");
        Map<String, Map<String, Object>> stepInputs = Map.of(
                "cae1a622-6c96-4cec-81d3-883510c17702", Map.of("url", "url-data", "payload", "payload-data"),
                "2c2cb6c8-794b-4cc1-8258-cd1898912744", Map.of("url", "url-data", "payload", "payload-data"),
                "d82a947b-f414-4273-973a-06f20fe33f0d", Map.of("url", "url-data", "payload", "payload-data"),
                "027db10b-9150-403d-9d11-e4a36c99e1db", Map.of("url", "url-data", "payload", "payload-data")
        );
        PubsubChannel channel = pubsubChannelFactory.get(InternalWorkflowInstanceDispatcher.TOPIC);
        SerDer serDer = JdkSerDerFactory.INSTANCE.getInstance();
        channel.push(new String(serDer.serialize(new Messages.InitWorkflow(workflowInstance, inputs, stepInputs)), StandardCharsets.UTF_8));
    }

    @Override
    public void shutdown(Long id) {

    }

    @Override
    public void suspend(Long id) {

    }

    @Override
    public void resume(Long id) {

    }
}
