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
package cn.sliew.carp.module.workflow.internal.engine.dispatch.event;

import cn.sliew.carp.module.workflow.api.engine.dispatch.event.WorkflowStepInstanceStatusEvent;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowStepInstanceEvent;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowStepInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowTaskInstance;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class WorkflowStepInstanceEventDTO implements WorkflowStepInstanceStatusEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private final String namespace;
    private final String type;
    private final Long workflowInstanceId;
    private final Long stepId;
    private final Long taskId;
    private final CarpWorkflowStepInstanceState state;
    private final CarpWorkflowStepInstanceState nextState;
    private final CarpWorkflowStepInstanceEvent event;
    private final Throwable throwable;

    public WorkflowStepInstanceEventDTO(WorkflowStepInstance workflowStepInstance, CarpWorkflowStepInstanceState state, CarpWorkflowStepInstanceState nextState, CarpWorkflowStepInstanceEvent event) {
        this(workflowStepInstance, null, state, nextState, event, null);
    }

    public WorkflowStepInstanceEventDTO(WorkflowStepInstance workflowStepInstance, WorkflowTaskInstance workflowTaskInstance, CarpWorkflowStepInstanceState state, CarpWorkflowStepInstanceState nextState, CarpWorkflowStepInstanceEvent event, Throwable throwable) {
        this.namespace = workflowStepInstance.getNamespace();
        this.type = workflowStepInstance.getWorkflowInstance().getDefinition().getType();
        this.workflowInstanceId = workflowStepInstance.getWorkflowInstance().getId();
        this.stepId = workflowStepInstance.getId();
        if (Objects.nonNull(workflowTaskInstance)) {
            taskId = workflowTaskInstance.getId();
        } else {
            taskId = null;
        }
        this.state = state;
        this.nextState = nextState;
        this.event = event;
        this.throwable = throwable;
    }
}
