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

import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowTaskInstanceEvent;
import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowTaskInstanceStage;
import cn.sliew.carp.module.workflow.api.engine.dispatch.event.WorkflowTaskInstanceStatusEvent;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class WorkflowTaskInstanceEventDTO implements WorkflowTaskInstanceStatusEvent, Serializable {

    private static final long serialVersionUID = 1L;

    private final CarpWorkflowTaskInstanceStage state;
    private final CarpWorkflowTaskInstanceStage nextState;
    private final CarpWorkflowTaskInstanceEvent event;
    private final Long workflowTaskInstanceId;
    private final Throwable throwable;

    public WorkflowTaskInstanceEventDTO(CarpWorkflowTaskInstanceStage state, CarpWorkflowTaskInstanceStage nextState, CarpWorkflowTaskInstanceEvent event, Long workflowTaskInstanceId) {
        this(state, nextState, event, workflowTaskInstanceId, null);
    }

    public WorkflowTaskInstanceEventDTO(CarpWorkflowTaskInstanceStage state, CarpWorkflowTaskInstanceStage nextState, CarpWorkflowTaskInstanceEvent event, Long workflowTaskInstanceId, Throwable throwable) {
        this.state = state;
        this.nextState = nextState;
        this.event = event;
        this.workflowTaskInstanceId = workflowTaskInstanceId;
        this.throwable = throwable;
    }

    @Override
    public CarpWorkflowTaskInstanceEvent getEvent() {
        return event;
    }
}
