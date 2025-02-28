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
import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Component
public class StartDagHandler extends AbstractDagMessageHandler<Messages.StartWorkflow> {

    @Override
    public Class<Messages.StartWorkflow> getMessageType() {
        return Messages.StartWorkflow.class;
    }

    @Override
    public void handle(Messages.StartWorkflow message) {
        withWorkflow(message, workflowInstance -> {
            if (StringUtils.equalsIgnoreCase(workflowInstance.getStatus(), ExecutionStatus.NOT_STARTED.name())
                    || isCanceled(workflowInstance) == false) {
                if (shouldLimit(workflowInstance)) {
                    getLog().info("Workflow Instance (namespace: {}, type: {}, workflowInstanceId: {}) execution limit and queue",
                            message.getNamespace(), message.getType(), message.getDagId());
                    // todo pending execution
//                    pendingExecutionService.enqueue(execution.getPipelineConfigId(), message);
                } else {
                    start(workflowInstance);
                }
            } else {
                terminate(workflowInstance);
            }
        });
    }

    private boolean shouldLimit(WorkflowInstance workflowInstance) {
        // 并发和速率限流
        return false;
    }

    private void start(WorkflowInstance workflowInstance) {
        if (isAfterStartTimeExpiry(workflowInstance)) {
            getLog().warn("Workflow Instance (namespace: {}, type: {}, workflowInstanceId: {}) start was canceled because start time would be after defined start time expiry (now: {}, expiry: {})",
                    workflowInstance.getNamespace(), workflowInstance.getDefinition().getType(), workflowInstance.getId(), Instant.now(),
//                    dagInstanceDTO.getStartTimeExpiry()
                    null
            );
            push(new Messages.CancelWorkflow(workflowInstance, "system", "Could not begin workflow before start time expiry"));
        } else {
            DAG<WorkflowStepInstance> dag = getWorkflowRepository().getDAG(workflowInstance.getId());
            Set<WorkflowStepInstance> initialSteps = dag.getSources();
            if (CollectionUtils.isEmpty(initialSteps)) {
                getLog().warn("Workflow Instance (namespace: {}, type: {}, workflowInstanceId: {}) found no initial steps",
                        workflowInstance.getNamespace(), workflowInstance.getDefinition().getType(), workflowInstance.getId());
                getWorkflowRepository().update(
                        WorkflowInstance.builder()
                                .id(workflowInstance.getId())
                                .status(ExecutionStatus.TERMINAL.name())
                                .build());
            } else {
                getWorkflowRepository().update(
                        WorkflowInstance.builder()
                                .id(workflowInstance.getId())
                                .status(ExecutionStatus.RUNNING.name())
                                .startTime(new Date())
                                .build());
                initialSteps.forEach(stage -> push(new Messages.StartStep(stage)));
            }
        }
    }

    private void terminate(WorkflowInstance workflowInstance) {
        if (StringUtils.equalsIgnoreCase(workflowInstance.getStatus(), ExecutionStatus.CANCELED.name())
                || isCanceled(workflowInstance)) {
//            push(new Messages.StartWaitingExecutions(dagInstanceDTO.getDagConfig().getId(), !dagInstanceDTO.isKeepWaitingPipelines()));
        } else {
            getLog().warn("Workflow Instance (namespace: {}, type: {}, workflowInstanceId: {}, status: {}) cannot be started unless state is NOT_STARTED. Ignoring StartWorkflow message.",
                    workflowInstance.getNamespace(), workflowInstance.getDefinition().getType(), workflowInstance.getId(), workflowInstance.getStatus(), workflowInstance.getNamespace());
        }
    }

    private boolean isAfterStartTimeExpiry(WorkflowInstance workflowInstance) {
//        return Objects.nonNull(dagInstanceDTO.getStartTimeExpiry()) && dagInstanceDTO.getStartTimeExpiry().isBefore(clock.instant());
        return false;
    }
}
