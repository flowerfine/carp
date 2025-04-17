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
package cn.sliew.carp.plugin.workflow.engine.internal.core.engine.dispatch.handler.task;

import cn.sliew.carp.framework.dag.service.dto.DagStepTaskDTO;
import cn.sliew.carp.plugin.workflow.engine.internal.core.engine.dispatch.event.task.FailureTaskDTO;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.enums.CarpWorkflowTaskInstanceEvent;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.enums.CarpWorkflowTaskInstanceState;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.instance.WorkflowTaskInstance;
import cn.sliew.milky.common.exception.ThrowableTraceFormater;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class FailureTaskEventListener extends AbstractTaskEventListener<FailureTaskDTO> {

    @Override
    public CarpWorkflowTaskInstanceEvent getType() {
        return CarpWorkflowTaskInstanceEvent.PROCESS_FAILURE;
    }

    @Override
    protected CompletableFuture<?> handleEventAsync(FailureTaskDTO event) {
        return CompletableFuture.runAsync(new FailureRunner(event.getStepId(), event.getTaskId(), event.getThrowable())).toCompletableFuture();
    }

    private class FailureRunner implements Runnable, Serializable {

        private Long stepId;
        private Long taskId;
        private Optional<Throwable> throwable;

        public FailureRunner(Long stepId, Long taskId, Throwable throwable) {
            this.stepId = stepId;
            this.taskId = taskId;
            this.throwable = Optional.ofNullable(throwable);
        }

        @Override
        public void run() {
            DagStepTaskDTO dagStepUpdateParam = new DagStepTaskDTO();
            dagStepUpdateParam.setId(taskId);
            dagStepUpdateParam.setStatus(CarpWorkflowTaskInstanceState.FAILURE.getValue());
            dagStepUpdateParam.setEndTime(new Date());
            if (throwable.isPresent()) {
                dagStepUpdateParam.setTaskExceptionDetails(Map.of("exception", ThrowableTraceFormater.readStackTrace(throwable.get())));
            }
            dagStepTaskService.update(dagStepUpdateParam);

            WorkflowStepInstance stepInstance = workflowInstanceService.getStep(stepId);
            WorkflowTaskInstance taskInstance = workflowInstanceService.getTask(taskId);
            stepInstanceStateMachine.onTaskChange(stepInstance, taskInstance);
        }
    }

}
