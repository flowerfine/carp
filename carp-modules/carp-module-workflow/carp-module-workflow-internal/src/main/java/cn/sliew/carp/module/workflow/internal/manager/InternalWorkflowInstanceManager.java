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
package cn.sliew.carp.module.workflow.internal.manager;

import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.DagLinkService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.framework.dag.service.dto.DagLinkDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.api.manager.WorkflowInstanceManager;
import cn.sliew.carp.module.workflow.api.service.WorkflowInstanceService;
import cn.sliew.carp.module.workflow.domain.ExecutionStatus;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowInstanceState;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowStepInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.internal.statemachine.InternalWorkflowInstanceStateMachine;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

@AllArgsConstructor
public class InternalWorkflowInstanceManager implements WorkflowInstanceManager {

    private DagInstanceService dagInstanceService;
    private DagStepService dagStepService;
    private DagLinkService dagLinkService;
    private WorkflowInstanceService workflowInstanceService;
    private InternalWorkflowInstanceStateMachine stateMachine;

    @Override
    public void deploy(Long id, JsonNode globalVariable) {

        stateMachine.init(get(id));
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

    private WorkflowInstance get(Long id) {
        return workflowInstanceService.get(id);
    }
}
