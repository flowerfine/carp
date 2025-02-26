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
package cn.sliew.carp.module.dag.queue.handler;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.dag.util.DagExecutionUtil;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CompleteDagHandler extends AbstractDagMessageHandler<Messages.CompleteWorkflow> {

    private final Duration RETRY_DELAY = Duration.ofSeconds(30L);

    @Autowired
    private DagStepService dagStepService;
    @Autowired
    private DagInstanceService dagInstanceService;

    @Override
    public Class<Messages.CompleteWorkflow> getMessageType() {
        return Messages.CompleteWorkflow.class;
    }

    @Override
    public void handle(Messages.CompleteWorkflow message) {
        withWorkflow(message, workflowInstance -> {
            if (ExecutionStatus.valueOf(workflowInstance.getStatus()).isComplete()) {
                log.info("Execution {} already completed with {} status",
                        workflowInstance.getId(), workflowInstance.getStatus());
            } else {
                determineFinalStatus(message, workflowInstance, status -> {
                    workflowInstance.setStatus(status.name());
                    dagInstanceService.updateStatus(workflowInstance.getId(), null, status.name());

//                    publisher.publishEvent(new ExecutionComplete(this, dagInstanceDTO));

                    if (status != ExecutionStatus.SUCCEEDED) {
                        List<DagStepDTO> steps = dagStepService.listSteps(workflowInstance.getId());
                        if (CollectionUtils.isNotEmpty(steps)) {
                            steps.stream()
                                    .filter(it -> StringUtils.equalsIgnoreCase(it.getStatus(), ExecutionStatus.RUNNING.name()))
                                    .forEach(it -> push(new Messages.CancelStep(it)));
                        }

                    }
                });
            }

//            log.debug("Execution {} is with {} status and Disabled concurrent executions is {}",
//                    dagInstanceDTO.getId(), dagInstanceDTO.getStatus(), dagInstanceDTO.isLimitConcurrent());

            if (StringUtils.equalsIgnoreCase(workflowInstance.getStatus(), ExecutionStatus.RUNNING.name()) == false) {
//                Long configId = dagInstanceDTO.getPipelineConfigId();
//                if (configId != null) {
//                    getQueue().push(new Messages.StartWaitingExecutions(
//                            configId,
//                            !dagInstanceDTO.isKeepWaitingPipelines()
//                    ));
//                }
            } else {
                log.debug("Not starting waiting workflows as workflow {} is currently RUNNING.",
                        workflowInstance.getId());
            }
        });
    }


    private void determineFinalStatus(Messages.CompleteWorkflow message, WorkflowInstance workflowInstance, Consumer<ExecutionStatus> block) {
        DAG<WorkflowStepInstance> dag = getWorkflowRepository().getDAG(workflowInstance.getId());
        Set<WorkflowStepInstance> steps = dag.nodes();
        if (CollectionUtils.isNotEmpty(steps)) {
            List<WorkflowStepInstance> filterSteps = steps.stream()
//                    .filter(it -> it.getParentStageId() == null)
                    .collect(Collectors.toList());

            if (filterSteps.stream()
                    .map(WorkflowStepInstance::getStatus)
                    .allMatch(it -> Set.of(ExecutionStatus.SUCCEEDED.name(), ExecutionStatus.SKIPPED.name(), ExecutionStatus.FAILED_CONTINUE.name()).contains(it))) {
                block.accept(ExecutionStatus.SUCCEEDED);
            } else if (filterSteps.stream().anyMatch(it -> StringUtils.equalsIgnoreCase(it.getStatus(), ExecutionStatus.TERMINAL.name()))) {
                block.accept(ExecutionStatus.TERMINAL);
            } else if (filterSteps.stream().anyMatch(it -> StringUtils.equalsIgnoreCase(it.getStatus(), ExecutionStatus.CANCELED.name()))) {
                block.accept(ExecutionStatus.CANCELED);
            } else if (filterSteps.stream().anyMatch(it -> StringUtils.equalsIgnoreCase(it.getStatus(), ExecutionStatus.STOPPED.name()))
                    && !hasOtherBranchesIncomplete(dag, filterSteps)) {
//                block.accept(shouldOverrideSuccess(dagInstanceDTO) ? ExecutionStatus.TERMINAL : ExecutionStatus.SUCCEEDED);
            } else {
//            Integer attempts = message.getAttribute(AttemptsAttribute.class)
//                    .map(AttemptsAttribute::getAttempts)
//                    .orElse(0);
//            log.info("Re-queuing {} as the execution is not yet complete (attempts: {})",
//                    message, attempts);
                push(message, RETRY_DELAY);
            }
        }
    }

    private boolean hasOtherBranchesIncomplete(DAG<WorkflowStepInstance> dag, List<WorkflowStepInstance> steps) {
        return steps.stream().anyMatch(it -> StringUtils.equalsIgnoreCase(it.getStatus(), ExecutionStatus.RUNNING.name()))
                || steps.stream().anyMatch(
                it ->
                        StringUtils.equalsIgnoreCase(it.getStatus(), ExecutionStatus.NOT_STARTED.name())
                                && DagExecutionUtil.allUpstreamStepsComplete(dag, it));
    }
}
