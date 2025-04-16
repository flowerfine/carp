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
package cn.sliew.carp.module.workflow.engine.internal.core.engine.dispatch.handler.workflow;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.module.workflow.engine.internal.core.executor.WorkflowInstanceExecutorManager;
import cn.sliew.carp.module.workflow.engine.internal.domain.convert.WorkflowExecutionGraphConvert;
import cn.sliew.carp.module.workflow.engine.internal.domain.enums.CarpWorkflowExecuteType;
import cn.sliew.carp.module.workflow.engine.internal.domain.enums.CarpWorkflowInstanceEvent;
import cn.sliew.carp.module.workflow.engine.internal.domain.enums.CarpWorkflowInstanceState;
import cn.sliew.carp.module.workflow.engine.internal.domain.enums.CarpWorkflowStepInstanceState;
import cn.sliew.carp.module.workflow.engine.internal.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.engine.internal.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.engine.internal.core.engine.dispatch.event.workflow.WorkflowInstanceEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class StepChangeWorkflowEventListener extends AbstractWorkflowEventListener<WorkflowInstanceEventDTO> {

    @Autowired
    private WorkflowInstanceExecutorManager workflowInstanceExecutorManager;

    @Override
    public CarpWorkflowInstanceEvent getType() {
        return CarpWorkflowInstanceEvent.PROCESS_STEP_CHANGE;
    }

    @Override
    protected CompletableFuture<?> handleEventAsync(WorkflowInstanceEventDTO event) {
        return CompletableFuture.runAsync(() -> run(event.getWorkflowInstanceId()));
    }

    private void run(Long workflowInstanceId) {
        WorkflowInstance workflowInstance = workflowInstanceService.getGraph(workflowInstanceId);
        CarpWorkflowInstanceState workflowInstanceState = CarpWorkflowInstanceState.of(workflowInstance.getStatus());
        if (workflowInstanceState.isEnd()) {
            return;
        }

        DAG<WorkflowStepInstance> dag = WorkflowExecutionGraphConvert.INSTANCE.toDto(workflowInstance.getGraph());
        // 检测所有任务的状态，如果有一个失败，则失败。如果都执行成功，则成功
        int successTaskCount = 0;
        boolean isAnyFailure = false;
        String anyFailureMessage = null;
        for (WorkflowStepInstance stepInstance : dag.nodes()) {
            CarpWorkflowStepInstanceState stepInstanceState = CarpWorkflowStepInstanceState.of(stepInstance.getStatus());
            if (stepInstanceState.isEnd()) {
                if (stepInstanceState.isFailureOrShutdown()) {
                    isAnyFailure = true;
                    break;
                }
                if (stepInstanceState.isSuccess()) {
                    successTaskCount++;
                }
            }
        }

        // 全部成功，运行结束
        if (successTaskCount == dag.nodes().size()) {
            stateMachine.onSuccess(workflowInstanceService.get(workflowInstanceId));
            return;
        }

        if (isAnyFailure) {
            onFailure(workflowInstanceId, new Exception(anyFailureMessage));
            return;
        }

        // todo 未传播 failure 和 shutdown 事件

        // 继续执行剩余节点
        workflowInstanceExecutorManager.execute(CarpWorkflowExecuteType.EXECUTE, workflowInstance, dag);
    }

}
