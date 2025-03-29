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
package cn.sliew.carp.module.workflow.internal.engine.dispatch.handler.task;

import cn.sliew.carp.framework.dag.service.dto.DagStepTaskDTO;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowTaskInstanceEvent;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowTaskInstanceEventDTO;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Component
public class WorkflowTaskInstanceDeployEventListener extends AbstractWorkflowTaskInstanceEventListener {

    @Override
    public CarpWorkflowTaskInstanceEvent getType() {
        return CarpWorkflowTaskInstanceEvent.COMMAND_DEPLOY;
    }

    @Override
    protected CompletableFuture handleEventAsync(WorkflowTaskInstanceEventDTO event) {
        CompletableFuture<?> future = CompletableFuture.runAsync(() -> run(event)).toCompletableFuture();
        future.whenCompleteAsync((unused, throwable) -> {
            if (throwable != null) {
                onFailure(event.getStepId(), event.getTaskId(), throwable);
            }
        });
        return future;
    }

    private void run(WorkflowTaskInstanceEventDTO event) {
        DagStepTaskDTO dagStepTaskUpdateParam = new DagStepTaskDTO();
        dagStepTaskUpdateParam.setId(event.getTaskId());
        dagStepTaskUpdateParam.setStatus(event.getNextState().getValue());
        dagStepTaskUpdateParam.setStartTime(new Date());
        dagStepTaskService.update(dagStepTaskUpdateParam);

        // todo run task

        stateMachine.onSuccess(workflowInstanceService.getStep(event.getStepId()), workflowInstanceService.getTask(event.getTaskId()));
    }
}
