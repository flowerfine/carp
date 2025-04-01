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
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.task.InternalWorkflowTaskInstanceStatusEventBuilder;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.task.RunTaskDTO;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.task.WorkflowTaskInstanceEventDTO;
import cn.sliew.carp.module.workflow.stage.model.resolver.TaskResolver;
import cn.sliew.carp.module.workflow.stage.model.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Component
public class DeployTaskEventListener extends AbstractTaskEventListener<WorkflowTaskInstanceEventDTO> {

    @Autowired
    private TaskResolver taskResolver;

    @Override
    public CarpWorkflowTaskInstanceEvent getType() {
        return CarpWorkflowTaskInstanceEvent.COMMAND_DEPLOY;
    }

    @Override
    protected CompletableFuture<?> handleEventAsync(WorkflowTaskInstanceEventDTO event) {
        return CompletableFuture.runAsync(() -> run(event)).toCompletableFuture();
    }

    private void run(WorkflowTaskInstanceEventDTO event) {
        DagStepTaskDTO dagStepTaskUpdateParam = new DagStepTaskDTO();
        dagStepTaskUpdateParam.setId(event.getTaskId());
        dagStepTaskUpdateParam.setStatus(event.getNextState().getValue());
        dagStepTaskUpdateParam.setStartTime(new Date());
        dagStepTaskService.update(dagStepTaskUpdateParam);

        WorkflowStepInstance stepInstance = workflowInstanceService.getStep(event.getStepId());
        WorkflowTaskInstance taskInstance = workflowInstanceService.getTask(event.getTaskId());

        InternalWorkflowTaskInstanceStatusEventBuilder builder =
                (fromState, toState, eventEnum) ->
                        new RunTaskDTO(fromState, toState, eventEnum, stepInstance, taskInstance, getTaskType(taskInstance));
        stateMachine.run(CarpWorkflowTaskInstanceState.of(taskInstance.getStatus()), builder);
    }

    private Class<? extends Task> getTaskType(WorkflowTaskInstance task) {
        return taskResolver.getTaskClass(task.getImplementingClass());
    }
}
