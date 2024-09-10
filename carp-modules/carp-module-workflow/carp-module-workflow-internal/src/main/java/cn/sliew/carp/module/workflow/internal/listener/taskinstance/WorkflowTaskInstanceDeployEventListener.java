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

package cn.sliew.carp.module.workflow.internal.listener.taskinstance;

import cn.sliew.carp.framework.dag.service.DagConfigStepService;
import cn.sliew.carp.framework.dag.service.DagInstanceComplexService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagConfigStepDTO;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.queue.api.MessageListener;
import cn.sliew.carp.module.workflow.api.graph.WorkflowTaskDefinitionMeta;
import cn.sliew.carp.module.workflow.internal.statemachine.WorkflowTaskInstanceStateMachine;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.annotation.RInject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@MessageListener(topic = WorkflowTaskInstanceDeployEventListener.TOPIC, consumerGroup = WorkflowTaskInstanceStateMachine.CONSUMER_GROUP)
public class WorkflowTaskInstanceDeployEventListener extends AbstractWorkflowTaskInstanceEventListener {

    public static final String TOPIC = "TOPIC_WORKFLOW_TASK_INSTANCE_COMMAND_DEPLOY";

    @Override
    protected CompletableFuture handleEventAsync(WorkflowTaskInstanceEventDTO event) {
        CompletableFuture<?> future = executorService.submit(new DeployRunner(event)).toCompletableFuture();
        future.whenCompleteAsync((unused, throwable) -> {
            if (throwable != null) {
                onFailure(event.getWorkflowTaskInstanceId(), throwable);
            }
        });
        return future;
    }

    @Slf4j
    public static class DeployRunner implements Runnable, Serializable {

        private WorkflowTaskInstanceEventDTO event;

        @RInject
        private String taskId;
        @Autowired
        private DagInstanceComplexService dagInstanceComplexService;
        @Autowired
        private DagConfigStepService dagConfigStepService;
        @Autowired
        private DagStepService dagStepService;
        @Autowired
        protected WorkflowTaskInstanceStateMachine stateMachine;

        public DeployRunner(WorkflowTaskInstanceEventDTO event) {
            this.event = event;
        }

        @Override
        public void run() {
            DagStepDTO dagStepUpdateParam = new DagStepDTO();
            dagStepUpdateParam.setId(event.getWorkflowTaskInstanceId());
            dagStepUpdateParam.setStatus(event.getNextState().getValue());
            dagStepUpdateParam.setStartTime(new Date());
            dagStepService.update(dagStepUpdateParam);

            DagStepDTO stepDTO = dagStepService.get(event.getWorkflowTaskInstanceId());
            DagInstanceDTO dagInstanceDTO = dagInstanceComplexService.selectSimpleOne(stepDTO.getDagInstanceId());
            DagConfigStepDTO configStepDTO = dagConfigStepService.get(stepDTO.getDagConfigStep().getId());
            WorkflowTaskDefinitionMeta workflowTaskDefinitionMeta = JacksonUtil.toObject(configStepDTO.getStepMeta(), WorkflowTaskDefinitionMeta.class);

        }

    }
}
