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
package cn.sliew.carp.plugin.workflow.engine.internal.core.manager;

import cn.sliew.carp.plugin.workflow.engine.internal.api.manager.WorkflowTaskInstanceManager;
import cn.sliew.carp.plugin.workflow.engine.internal.api.service.ServerlessWorkflowInstanceService;
import cn.sliew.carp.plugin.workflow.engine.internal.core.statemachine.InternalWorkflowTaskInstanceStateMachine;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.instance.WorkflowTaskInstance;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InternalWorkflowTaskInstanceManager implements WorkflowTaskInstanceManager {

    private ServerlessWorkflowInstanceService workflowInstanceService;
    private InternalWorkflowTaskInstanceStateMachine stateMachine;

    @Override
    public void deploy(Long workflowStepInstanceId, Long workflowTaskInstanceId) {
        stateMachine.deploy(getStep(workflowStepInstanceId), getTask(workflowTaskInstanceId));
    }

    @Override
    public void shutdown(Long workflowStepInstanceId, Long workflowTaskInstanceId) {
        stateMachine.shutdown(getStep(workflowStepInstanceId), getTask(workflowTaskInstanceId));
    }

    private WorkflowStepInstance getStep(Long id) {
        return workflowInstanceService.getStep(id);
    }

    private WorkflowTaskInstance getTask(Long id) {
        return workflowInstanceService.getTask(id);
    }
}
