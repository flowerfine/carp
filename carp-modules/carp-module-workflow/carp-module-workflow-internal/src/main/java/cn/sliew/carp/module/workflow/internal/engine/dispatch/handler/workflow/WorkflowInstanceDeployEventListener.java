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
import cn.sliew.carp.module.workflow.domain.convert.WorkflowExecutionGraphConvert;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowExecuteType;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowInstanceEvent;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowInstanceEventDTO;
import cn.sliew.carp.module.workflow.internal.executor.WorkflowInstanceExecutorManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class WorkflowInstanceDeployEventListener extends AbstractWorkflowInstanceEventListener<WorkflowInstanceEventDTO> {

    @Autowired
    private DagInstanceService dagInstanceService;
    @Autowired
    private WorkflowInstanceExecutorManager workflowInstanceExecutorManager;

    @Override
    public CarpWorkflowInstanceEvent getType() {
        return CarpWorkflowInstanceEvent.COMMAND_DEPLOY;
    }

    @Override
    protected CompletableFuture<?> handleEventAsync(WorkflowInstanceEventDTO event) {
        return CompletableFuture.runAsync(() -> run(event));
    }

    private void run(WorkflowInstanceEventDTO event) {
        WorkflowInstance workflowInstance = workflowInstanceService.getGraph(event.getWorkflowInstanceId());
        if (StringUtils.equalsIgnoreCase(workflowInstance.getStatus(), CarpWorkflowInstanceState.PENDING.getValue())) {
            start(event);
        } else {
            log.warn("Workflow Instance (namespace: {}, type: {}, workflowInstanceId: {}, status: {}) cannot be started unless state is PENDING. ignore.",
                    event.getNamespace(), event.getType(), event.getWorkflowInstanceId(), workflowInstance.getStatus());
        }
    }

    private void start(WorkflowInstanceEventDTO event) {
        dagInstanceService.updateStatus(event.getWorkflowInstanceId(), event.getState().getValue(), event.getNextState().getValue());
        DagInstanceDTO dagInstanceDTO = new DagInstanceDTO();
        dagInstanceDTO.setId(event.getWorkflowInstanceId());
        dagInstanceDTO.setStartTime(new Date());
        dagInstanceService.update(dagInstanceDTO);

        // 重新获取最新数据，上一步更新过 status
        WorkflowInstance workflowInstance = workflowInstanceService.getGraph(event.getWorkflowInstanceId());
        DAG<WorkflowStepInstance> dag = WorkflowExecutionGraphConvert.INSTANCE.toDto(workflowInstance.getGraph());
        // 无节点，直接成功
        if (CollectionUtils.isEmpty(dag.nodes())) {
            log.debug("Workflow Instance (namespace: {}, type: {}, workflowInstanceId: {}) found no steps, succeed",
                    workflowInstance.getNamespace(), workflowInstance.getDefinition().getType(), workflowInstance.getId());
            stateMachine.onSuccess(workflowInstance);
            return;
        }
        // 无初始节点，直接失败
        Set<WorkflowStepInstance> initialSteps = dag.getSources();
        if (CollectionUtils.isEmpty(initialSteps)) {
            log.warn("Workflow Instance (namespace: {}, type: {}, workflowInstanceId: {}) found no initial steps, fail",
                    workflowInstance.getNamespace(), workflowInstance.getDefinition().getType(), workflowInstance.getId());
            stateMachine.onFailure(workflowInstance, new IllegalStateException("Workflow Instance found no initial steps"));
            return;
        }
        // 执行节点
        workflowInstanceExecutorManager.execute(CarpWorkflowExecuteType.EXECUTE, workflowInstance, dag);
    }


}
