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
package cn.sliew.carp.module.workflow.internal.engine.dispatch.handler.step;

import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowStepInstanceEvent;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowStepInstanceState;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowStepInstanceEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class WorkflowStepInstanceFailureEventListener extends AbstractWorkflowStepInstanceEventListener<WorkflowStepInstanceEventDTO> {

    @Override
    public CarpWorkflowStepInstanceEvent getType() {
        return CarpWorkflowStepInstanceEvent.PROCESS_FAILURE;
    }

    @Override
    protected CompletableFuture<?> handleEventAsync(WorkflowStepInstanceEventDTO event) {
        return CompletableFuture.runAsync(new FailureRunner(event.getWorkflowInstanceId(), event.getStepId(), event.getThrowable())).toCompletableFuture();
    }

    private class FailureRunner implements Runnable, Serializable {

        private Long workflowInstanceId;
        private Long stepId;
        private Optional<Throwable> throwable;

        public FailureRunner(Long workflowInstanceId, Long stepId, Throwable throwable) {
            this.workflowInstanceId = workflowInstanceId;
            this.stepId = stepId;
            this.throwable = Optional.ofNullable(throwable);
        }

        @Override
        public void run() {
            DagStepDTO dagStepUpdateParam = new DagStepDTO();
            dagStepUpdateParam.setId(stepId);
            dagStepUpdateParam.setStatus(CarpWorkflowStepInstanceState.FAILURE.getValue());
            dagStepUpdateParam.setEndTime(new Date());
            dagStepService.update(dagStepUpdateParam);

            instanceStateMachine.onStepChange(workflowInstanceService.get(workflowInstanceId));
        }
    }

}
