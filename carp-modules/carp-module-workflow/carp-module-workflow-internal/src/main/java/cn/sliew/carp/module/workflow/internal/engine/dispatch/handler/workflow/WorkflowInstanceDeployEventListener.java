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
package cn.sliew.carp.module.workflow.internal.engine.dispatch.handler.workflow;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.module.workflow.api.enums.CarpWorkflowExecuteType;
import cn.sliew.carp.module.workflow.api.enums.CarpWorkflowInstanceEvent;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowInstanceEventDTO;
import cn.sliew.carp.module.workflow.internal.executor.WorkflowInstanceExecutorManager;
import cn.sliew.carp.module.workflow.domain.convert.WorkflowExecutionGraphConvert;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class WorkflowInstanceDeployEventListener extends AbstractWorkflowInstanceEventListener {

    @Autowired
    private DagInstanceService dagInstanceService;
    @Autowired
    private WorkflowInstanceExecutorManager workflowInstanceExecutorManager;

    @Override
    public CarpWorkflowInstanceEvent getType() {
        return CarpWorkflowInstanceEvent.COMMAND_DEPLOY;
    }

    @Override
    protected CompletableFuture handleEventAsync(WorkflowInstanceEventDTO event) {
        CompletableFuture<?> future = CompletableFuture.runAsync(() -> run(event));
        future.whenCompleteAsync((unused, throwable) -> {
            if (throwable != null) {
                log.error(throwable.getMessage(), throwable);
                onFailure(event.getWorkflowInstanceId(), throwable);
            }
        });
        return future;
    }

    private void run(WorkflowInstanceEventDTO event) {
        dagInstanceService.updateStatus(event.getWorkflowInstanceId(), event.getState().getValue(), event.getNextState().getValue());
        DagInstanceDTO dagInstanceDTO = new DagInstanceDTO();
        dagInstanceDTO.setId(event.getWorkflowInstanceId());
        dagInstanceDTO.setStartTime(new Date());
        dagInstanceService.update(dagInstanceDTO);

        WorkflowInstance workflowInstance = workflowInstanceService.getGraph(event.getWorkflowInstanceId());
        DAG<WorkflowStepInstance> dag = WorkflowExecutionGraphConvert.INSTANCE.toDto(workflowInstance.getGraph());
        // 无节点，直接成功
        if (CollectionUtils.isEmpty(dag.nodes())) {
            stateMachine.onSuccess(workflowInstance);
            return;
        }
        workflowInstanceExecutorManager.execute(CarpWorkflowExecuteType.EXECUTE, workflowInstance, dag);
    }
}
