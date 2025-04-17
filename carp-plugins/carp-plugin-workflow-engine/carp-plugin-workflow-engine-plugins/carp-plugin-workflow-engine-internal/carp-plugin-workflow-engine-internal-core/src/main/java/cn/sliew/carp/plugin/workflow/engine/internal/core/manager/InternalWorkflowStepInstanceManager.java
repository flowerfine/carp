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

import cn.sliew.carp.plugin.workflow.engine.internal.api.manager.WorkflowStepInstanceManager;
import cn.sliew.carp.plugin.workflow.engine.internal.api.service.ServerlessWorkflowInstanceService;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.plugin.workflow.engine.internal.core.statemachine.InternalWorkflowStepInstanceStateMachine;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InternalWorkflowStepInstanceManager implements WorkflowStepInstanceManager {

    private ServerlessWorkflowInstanceService workflowInstanceService;
    private InternalWorkflowStepInstanceStateMachine stateMachine;

    @Override
    public void deploy(Long id) {
        stateMachine.deploy(get(id));
    }

    @Override
    public void shutdown(Long id) {
        stateMachine.shutdown(get(id));
    }

    @Override
    public void suspend(Long id) {
        stateMachine.suspend(get(id));
    }

    @Override
    public void resume(Long id) {
        stateMachine.resume(get(id));
    }

    @Override
    public void skip(Long id) {
        stateMachine.skip(get(id));
    }

    private WorkflowStepInstance get(Long id) {
        return workflowInstanceService.getStep(id);
    }
}
