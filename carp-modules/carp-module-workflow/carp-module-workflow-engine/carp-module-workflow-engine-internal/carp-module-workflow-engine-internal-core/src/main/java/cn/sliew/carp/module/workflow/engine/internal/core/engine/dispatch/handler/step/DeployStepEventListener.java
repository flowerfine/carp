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
package cn.sliew.carp.module.workflow.engine.internal.core.engine.dispatch.handler.step;

import cn.sliew.carp.framework.dag.service.DagStepTaskService;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepTaskDTO;
import cn.sliew.carp.module.workflow.engine.internal.api.util.StageDefinitionBuilderUtil;
import cn.sliew.carp.module.workflow.engine.internal.domain.enums.CarpWorkflowStepInstanceEvent;
import cn.sliew.carp.module.workflow.engine.internal.domain.enums.CarpWorkflowTaskInstanceState;
import cn.sliew.carp.module.workflow.engine.internal.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.engine.internal.domain.instance.WorkflowTaskInstance;
import cn.sliew.carp.module.workflow.engine.internal.core.engine.dispatch.event.step.WorkflowStepInstanceEventDTO;
import cn.sliew.carp.module.workflow.engine.internal.core.util.DagExecutionUtil;
import cn.sliew.carp.module.workflow.engine.internal.stage.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.workflow.engine.internal.stage.model.graph.StageDefinitionBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class DeployStepEventListener extends AbstractStepEventListener<WorkflowStepInstanceEventDTO> implements StepBuilderAware {

    @Autowired
    private StageDefinitionBuilderFactory stageDefinitionBuilderFactory;
    @Autowired
    private DagStepTaskService dagStepTaskService;

    @Override
    public CarpWorkflowStepInstanceEvent getType() {
        return CarpWorkflowStepInstanceEvent.COMMAND_DEPLOY;
    }

    @Override
    public StageDefinitionBuilderFactory getStageDefinitionBuilderFactory() {
        return stageDefinitionBuilderFactory;
    }

    @Override
    protected CompletableFuture<?> handleEventAsync(WorkflowStepInstanceEventDTO event) {
        return CompletableFuture.runAsync(() -> run(event)).toCompletableFuture();
    }

    private void run(WorkflowStepInstanceEventDTO event) {
        DagStepDTO dagStepUpdateParam = new DagStepDTO();
        dagStepUpdateParam.setId(event.getStepId());
        dagStepUpdateParam.setStatus(event.getNextState().getValue());
        dagStepUpdateParam.setStartTime(new Date());
        dagStepService.update(dagStepUpdateParam);

        WorkflowStepInstance stepInstance = workflowInstanceService.getStep(event.getStepId());
        plan(stepInstance);
        WorkflowTaskInstance task = DagExecutionUtil.firstTask(stepInstance);
        if (Objects.nonNull(task)) {
            taskInstanceManager.deploy(event.getStepId(), task.getId());
        } else {
            stateMachine.onSuccess(workflowInstanceService.getStep(event.getStepId()));
        }
    }

    private void plan(WorkflowStepInstance stepInstance) {
        StageDefinitionBuilder builder = builder(stepInstance);
        StageDefinitionBuilderUtil.buildTasks(builder, stepInstance);
        List<DagStepTaskDTO> tasks = dagStepTaskService.listTasks(stepInstance.getId());
        tasks.forEach(task -> {
            if (StringUtils.isBlank(task.getStatus())) {
                task.setStatus(CarpWorkflowTaskInstanceState.PENDING.getValue());
                dagStepTaskService.update(task);
            }
        });
    }
}
