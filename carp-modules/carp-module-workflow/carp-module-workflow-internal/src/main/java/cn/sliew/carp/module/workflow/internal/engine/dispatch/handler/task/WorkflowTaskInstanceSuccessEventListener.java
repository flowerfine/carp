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
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowTaskInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowTaskInstance;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowTaskInstanceEventDTO;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Component
public class WorkflowTaskInstanceSuccessEventListener extends AbstractWorkflowTaskInstanceEventListener<WorkflowTaskInstanceEventDTO> {

    @Override
    public CarpWorkflowTaskInstanceEvent getType() {
        return CarpWorkflowTaskInstanceEvent.PROCESS_SUCCESS;
    }

    @Override
    protected CompletableFuture<?> handleEventAsync(WorkflowTaskInstanceEventDTO event) {
        return CompletableFuture.runAsync(new SuccessRunner(event.getStepId(), event.getTaskId())).toCompletableFuture();
    }

    private class SuccessRunner implements Runnable, Serializable {

        private Long stepId;
        private Long taskId;

        public SuccessRunner(Long stepId, Long taskId) {
            this.stepId = stepId;
            this.taskId = taskId;
        }

        @Override
        public void run() {
            DagStepTaskDTO dagStepUpdateParam = new DagStepTaskDTO();
            dagStepUpdateParam.setId(taskId);
            dagStepUpdateParam.setStatus(CarpWorkflowTaskInstanceState.SUCCESS.getValue());
            dagStepUpdateParam.setEndTime(new Date());
            dagStepTaskService.update(dagStepUpdateParam);

            WorkflowStepInstance stepInstance = workflowInstanceService.getStep(stepId);
            WorkflowTaskInstance taskInstance = workflowInstanceService.getTask(taskId);
            stepInstanceStateMachine.onTaskChange(stepInstance, taskInstance);
        }
    }

}
