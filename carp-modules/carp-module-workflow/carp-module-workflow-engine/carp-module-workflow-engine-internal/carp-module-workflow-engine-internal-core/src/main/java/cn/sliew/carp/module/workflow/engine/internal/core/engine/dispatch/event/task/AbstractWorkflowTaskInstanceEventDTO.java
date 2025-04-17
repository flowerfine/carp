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
package cn.sliew.carp.module.workflow.engine.internal.core.engine.dispatch.event.task;

import cn.sliew.carp.module.workflow.engine.internal.core.engine.dispatch.event.InternalWorkflowTaskInstanceStatusEvent;
import cn.sliew.carp.module.workflow.engine.internal.domain.enums.CarpWorkflowTaskInstanceEvent;
import cn.sliew.carp.module.workflow.engine.internal.domain.enums.CarpWorkflowTaskInstanceState;
import cn.sliew.carp.module.workflow.engine.internal.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.engine.internal.domain.instance.WorkflowTaskInstance;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public abstract class AbstractWorkflowTaskInstanceEventDTO implements InternalWorkflowTaskInstanceStatusEvent, Serializable {

    private final CarpWorkflowTaskInstanceState state;
    private final CarpWorkflowTaskInstanceState nextState;
    private final CarpWorkflowTaskInstanceEvent event;

    private final String namespace;
    private final String type;
    private final Long workflowInstanceId;
    private final Long stepId;
    private final Long taskId;

    public AbstractWorkflowTaskInstanceEventDTO(CarpWorkflowTaskInstanceState state, CarpWorkflowTaskInstanceState nextState, CarpWorkflowTaskInstanceEvent event, WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance) {
        this(state, nextState, event, stepInstance.getNamespace(), stepInstance.getWorkflowInstance().getDefinition().getType(), stepInstance.getWorkflowInstance().getId(), stepInstance.getId(), taskInstance.getId());
    }
}
