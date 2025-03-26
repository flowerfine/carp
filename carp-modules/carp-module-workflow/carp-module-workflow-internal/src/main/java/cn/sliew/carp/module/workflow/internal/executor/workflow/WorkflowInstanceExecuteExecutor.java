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

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.algorithm.DagUtil;
import cn.sliew.carp.framework.dag.algorithm.DefaultDagEdge;
import cn.sliew.carp.module.workflow.api.enums.CarpWorkflowExecuteType;
import cn.sliew.carp.module.workflow.api.enums.CarpWorkflowStepInstanceState;
import cn.sliew.carp.module.workflow.api.manager.WorkflowStepInstanceManager;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.internal.executor.WorkflowInstanceExecutor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class WorkflowInstanceExecuteExecutor implements WorkflowInstanceExecutor {

    @Autowired
    private WorkflowStepInstanceManager workflowStepInstanceManager;

    @Override
    public CarpWorkflowExecuteType getExecuteType() {
        return CarpWorkflowExecuteType.EXECUTE;
    }

    @Override
    public void execute(WorkflowInstance instance, DAG<WorkflowStepInstance> dag) {
        DagUtil.execute(dag, (dag1, node) -> checkStep(instance, dag1, node), (dag1, edge) -> checkEdge(instance, dag, edge), this::executeSteps);
    }

    @Override
    public boolean checkEdge(WorkflowInstance instance, DAG<WorkflowStepInstance> dag, DefaultDagEdge<WorkflowStepInstance> edge) {
        return CarpWorkflowStepInstanceState.of(edge.getSource().getStatus()).isSuccess();
    }

    @Override
    public boolean checkStep(WorkflowInstance instance, DAG<WorkflowStepInstance> dag, WorkflowStepInstance step) {
        return StringUtils.equalsIgnoreCase(step.getStatus(), CarpWorkflowStepInstanceState.PENDING.name());
    }

    @Override
    public void executeSteps(Set<WorkflowStepInstance> steps) {
        steps.forEach(step -> workflowStepInstanceManager.deploy(step.getId()));
    }
}
