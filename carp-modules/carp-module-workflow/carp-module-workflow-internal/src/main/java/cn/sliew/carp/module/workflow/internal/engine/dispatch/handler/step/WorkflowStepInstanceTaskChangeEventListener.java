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

import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowStepInstanceEvent;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowTaskInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowTaskInstance;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowStepInstanceEventDTO;
import cn.sliew.carp.module.workflow.internal.util.DagExecutionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class WorkflowStepInstanceTaskChangeEventListener extends AbstractWorkflowStepInstanceEventListener<WorkflowStepInstanceEventDTO> {

    @Override
    public CarpWorkflowStepInstanceEvent getType() {
        return CarpWorkflowStepInstanceEvent.PROCESS_TASK_CHANGE;
    }

    @Override
    protected CompletableFuture<?> handleEventAsync(WorkflowStepInstanceEventDTO event) {
        return CompletableFuture.runAsync(() -> run(event));
    }

    private void run(WorkflowStepInstanceEventDTO event) {
        WorkflowStepInstance stepInstance = workflowInstanceService.getStep(event.getStepId());
        // todo event 和 handler 的消息处理有点僵硬
        WorkflowTaskInstance taskInstance = workflowInstanceService.getTask(event.getTaskId());
        CarpWorkflowTaskInstanceState taskInstanceState = CarpWorkflowTaskInstanceState.of(taskInstance.getStatus());
        if (taskInstanceState.isEnd() == false) {
            return;
        }
        if (taskInstanceState == CarpWorkflowTaskInstanceState.FAILURE) {
            stateMachine.onFailure(stepInstance, null);
            return;
        } else if (taskInstanceState == CarpWorkflowTaskInstanceState.SHUTDOWN) {
            // ignore
            return;
        } else if (taskInstanceState == CarpWorkflowTaskInstanceState.SUCCESS) {
            WorkflowTaskInstance nextTask = DagExecutionUtil.nextTask(stepInstance, taskInstance);
            if (Objects.nonNull(nextTask)) {
                taskInstanceManager.deploy(event.getStepId(), nextTask.getId());
            } else {
                stateMachine.onSuccess(stepInstance);
            }
        }
    }

}
