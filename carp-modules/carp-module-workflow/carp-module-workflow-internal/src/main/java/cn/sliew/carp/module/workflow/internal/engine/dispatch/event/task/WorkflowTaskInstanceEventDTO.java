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
package cn.sliew.carp.module.workflow.internal.engine.dispatch.event.task;

import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowTaskInstanceEvent;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowTaskInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowTaskInstance;
import lombok.Getter;

import java.io.Serial;

@Getter
public class WorkflowTaskInstanceEventDTO extends AbstractWorkflowTaskInstanceEventDTO {
    @Serial
    private static final long serialVersionUID = -5907661838804259235L;

    public WorkflowTaskInstanceEventDTO(CarpWorkflowTaskInstanceState state, CarpWorkflowTaskInstanceState nextState, CarpWorkflowTaskInstanceEvent event, WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance) {
        super(state, nextState, event, stepInstance, taskInstance);
    }
}
