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
package cn.sliew.carp.module.workflow.internal.executor.workflow;

import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowExecuteType;
import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowTaskInstanceStage;
import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.algorithm.DagUtil;
import cn.sliew.carp.framework.dag.algorithm.DefaultDagEdge;
import cn.sliew.carp.module.workflow.api.manager.WorkflowTaskInstanceManager;
import cn.sliew.carp.module.workflow.internal.executor.WorkflowInstanceExecutor;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class WorkflowInstanceExecuteExecutor implements WorkflowInstanceExecutor {

    @Autowired
    private WorkflowTaskInstanceManager workflowTaskInstanceManager;

    @Override
    public CarpWorkflowExecuteType getExecuteType() {
        return CarpWorkflowExecuteType.EXECUTE;
    }

    @Override
    public void execute(WorkflowInstance instance, DAG<WorkflowStepInstance> dag) {
        DagUtil.execute(dag, (dag1, node) -> checkTask(instance, dag1, node), (dag1, edge) -> checkEdge(instance, dag, edge), this::executeTasks);
    }

    @Override
    public boolean checkEdge(WorkflowInstance instance, DAG<WorkflowStepInstance> dag, DefaultDagEdge<WorkflowStepInstance> edge) {
        return CarpWorkflowTaskInstanceStage.of(edge.getSource().getStatus()).isSuccess();
    }

    @Override
    public boolean checkTask(WorkflowInstance instance, DAG<WorkflowStepInstance> dag, WorkflowStepInstance task) {
        return StringUtils.equalsIgnoreCase(task.getStatus(), CarpWorkflowTaskInstanceStage.PENDING.name());
    }

    @Override
    public void executeTasks(Set<WorkflowStepInstance> task) {
        task.forEach(taskInstance -> workflowTaskInstanceManager.deploy(taskInstance.getId()));
    }
}
