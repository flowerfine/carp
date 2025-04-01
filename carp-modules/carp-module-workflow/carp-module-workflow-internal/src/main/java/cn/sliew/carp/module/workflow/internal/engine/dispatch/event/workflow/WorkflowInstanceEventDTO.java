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
package cn.sliew.carp.module.workflow.internal.engine.dispatch.event.workflow;

import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowInstanceEvent;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.InternalWorkflowInstanceStatusEvent;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class WorkflowInstanceEventDTO extends AbstractWorkflowInstanceEventDTO implements InternalWorkflowInstanceStatusEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private final Throwable throwable;

    public WorkflowInstanceEventDTO(CarpWorkflowInstanceState state, CarpWorkflowInstanceState nextState, CarpWorkflowInstanceEvent event, WorkflowInstance source) {
        this(state, nextState, event, source, null);
    }

    public WorkflowInstanceEventDTO(CarpWorkflowInstanceState state, CarpWorkflowInstanceState nextState, CarpWorkflowInstanceEvent event, WorkflowInstance source, Throwable throwable) {
        super(state, nextState, event, source);
        this.throwable = throwable;
    }

    public WorkflowInstanceEventDTO(CarpWorkflowInstanceState state, CarpWorkflowInstanceState nextState, CarpWorkflowInstanceEvent event, String namespace, String type, Long workflowInstanceId, Throwable throwable) {
        super(state, nextState, event, namespace, type, workflowInstanceId);
        this.throwable = throwable;
    }
}
