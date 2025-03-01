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

import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowInstanceBody;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CancelDagHandler extends AbstractDagMessageHandler<Messages.CancelWorkflow> {

    @Override
    public Class<Messages.CancelWorkflow> getMessageType() {
        return Messages.CancelWorkflow.class;
    }

    @Override
    public void handle(Messages.CancelWorkflow message) {
        withWorkflow(message, workflowInstance -> {
            // 取消执行
            if (StringUtils.equalsIgnoreCase(workflowInstance.getStatus(), ExecutionStatus.NOT_STARTED.name())) {
                workflowInstance.setStatus(ExecutionStatus.CANCELED.name());
            }
            WorkflowInstanceBody body;
            if (Objects.nonNull(workflowInstance.getBody())) {
                body = workflowInstance.getBody();
            } else {
                body = new WorkflowInstanceBody();
            }
            workflowInstance.setBody(body);
            body.setCanceled(
                    WorkflowInstanceBody.CanceledDetails.builder()
                            .canceled(true)
                            .canceledBy(message.getUser())
                            .cancellationReason(message.getReason())
                            .build());
            getWorkflowRepository().update(workflowInstance);

            // 恢复所有暂停的阶段，以便它们的 RunTaskHandler 可以执行
            // 并处理 "canceled" 标志
            List<WorkflowStepInstance> pausedStages = getWorkflowRepository().getStepInstances(workflowInstance.getId()).stream()
                    .filter(stepInstance -> StringUtils.equalsIgnoreCase(stepInstance.getStatus(), ExecutionStatus.PAUSED.name()))
                    .collect(Collectors.toList());
            pausedStages.forEach(stepInstance -> push(new Messages.ResumeStep(message, stepInstance.getId())));

            // 确保这些 runTask 消息立即运行
            push(new Messages.RescheduleWorkflow(workflowInstance));

            // 更新执行状态并发布事件
//            ((PipelineExecutionImpl) workflowInstance).setStatus(ExecutionStatus.CANCELED);
//            publisher.publishEvent(new ExecutionComplete(this, workflowInstance));
        });
    }
}
