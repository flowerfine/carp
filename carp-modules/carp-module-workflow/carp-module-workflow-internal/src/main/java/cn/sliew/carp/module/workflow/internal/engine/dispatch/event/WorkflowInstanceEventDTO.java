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

import cn.sliew.carp.module.workflow.api.engine.dispatch.event.WorkflowInstanceStatusEvent;
import cn.sliew.carp.module.workflow.api.enums.CarpWorkflowInstanceEvent;
import cn.sliew.carp.module.workflow.api.enums.CarpWorkflowInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class WorkflowInstanceEventDTO implements WorkflowInstanceStatusEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private final String namespace;
    private final String type;
    private final Long workflowInstanceId;
    private final CarpWorkflowInstanceState state;
    private final CarpWorkflowInstanceState nextState;
    private final CarpWorkflowInstanceEvent event;
    private final Throwable throwable;

    public WorkflowInstanceEventDTO(WorkflowInstance workflowInstance, CarpWorkflowInstanceState state, CarpWorkflowInstanceState nextState, CarpWorkflowInstanceEvent event) {
        this(workflowInstance, state, nextState, event, null);
    }

    public WorkflowInstanceEventDTO(WorkflowInstance workflowInstance, CarpWorkflowInstanceState state, CarpWorkflowInstanceState nextState, CarpWorkflowInstanceEvent event, Throwable throwable) {
        this.namespace = workflowInstance.getNamespace();
        this.type = workflowInstance.getDefinition().getType();
        this.workflowInstanceId = workflowInstance.getId();
        this.state = state;
        this.nextState = nextState;
        this.event = event;
        this.throwable = throwable;
    }
}
