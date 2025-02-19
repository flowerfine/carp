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

import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowTaskInstanceEvent;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
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
                onFailure(event.getWorkflowTaskInstanceId(), throwable);
            }
        });
        return future;
    }

    private void run(WorkflowTaskInstanceEventDTO event) {
        DagStepDTO dagStepUpdateParam = new DagStepDTO();
        dagStepUpdateParam.setId(event.getWorkflowTaskInstanceId());
        dagStepUpdateParam.setStatus(event.getNextState().getValue());
        dagStepUpdateParam.setStartTime(new Date());
        dagStepService.update(dagStepUpdateParam);
        // todo run task
        stateMachine.onSuccess(workflowInstanceService.getTask(event.getWorkflowTaskInstanceId()));
    }
}
