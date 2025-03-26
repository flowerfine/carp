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

import cn.sliew.carp.module.workflow.api.engine.dispatch.event.WorkflowTaskInstanceStatusEvent;
import cn.sliew.carp.module.workflow.api.enums.CarpWorkflowTaskInstanceEvent;
import cn.sliew.carp.module.workflow.api.enums.CarpWorkflowTaskInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowTaskInstance;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class WorkflowTaskInstanceEventDTO implements WorkflowTaskInstanceStatusEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private final String namespace;
    private final String type;
    private final Long workflowInstanceId;
    private final Long stepId;
    private final Long taskId;

    private final CarpWorkflowTaskInstanceState state;
    private final CarpWorkflowTaskInstanceState nextState;
    private final CarpWorkflowTaskInstanceEvent event;
    private final Throwable throwable;

    public WorkflowTaskInstanceEventDTO(WorkflowStepInstance workflowStepInstance, WorkflowTaskInstance taskInstance, CarpWorkflowTaskInstanceState state, CarpWorkflowTaskInstanceState nextState, CarpWorkflowTaskInstanceEvent event) {
        this(workflowStepInstance, taskInstance, state, nextState, event, null);
    }

    public WorkflowTaskInstanceEventDTO(WorkflowStepInstance workflowStepInstance, WorkflowTaskInstance taskInstance, CarpWorkflowTaskInstanceState state, CarpWorkflowTaskInstanceState nextState, CarpWorkflowTaskInstanceEvent event, Throwable throwable) {
        this.namespace = workflowStepInstance.getNamespace();
        this.type = workflowStepInstance.getWorkflowInstance().getDefinition().getType();
        this.workflowInstanceId = workflowStepInstance.getWorkflowInstance().getId();
        this.stepId = workflowStepInstance.getId();
        this.taskId = taskInstance.getId();
        this.state = state;
        this.nextState = nextState;
        this.event = event;
        this.throwable = throwable;
    }
}
