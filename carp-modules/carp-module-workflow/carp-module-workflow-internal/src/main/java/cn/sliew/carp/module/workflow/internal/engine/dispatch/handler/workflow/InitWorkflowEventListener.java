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
package cn.sliew.carp.module.workflow.internal.engine.dispatch.handler.workflow;

import cn.sliew.carp.framework.common.util.KeyUtil;
import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.DagLinkService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.framework.dag.service.dto.DagLinkDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.api.util.WorkflowUtil;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowInstanceEvent;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowInstanceState;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowStepInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.workflow.InitWorkflowDTO;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class InitWorkflowEventListener extends AbstractWorkflowEventListener<InitWorkflowDTO> {

    @Autowired
    private DagInstanceService dagInstanceService;
    @Autowired
    private DagStepService dagStepService;
    @Autowired
    private DagLinkService dagLinkService;

    @Override
    public CarpWorkflowInstanceEvent getType() {
        return CarpWorkflowInstanceEvent.COMMAND_INIT;
    }

    @Override
    protected CompletableFuture<?> handleEventAsync(InitWorkflowDTO event) {
        return CompletableFuture.runAsync(() -> init(event));
    }

    private void init(InitWorkflowDTO event) {
        String streamKey = WorkflowUtil.buildWorkflowInstanceLogsKey(event.getWorkflowInstanceId());
        streamLogService.info(streamKey, log, "Init workflow instance, workflowInstanceId: {}", event.getWorkflowInstanceId());
        // 更新 dag_instance
        DagInstanceDTO instanceDTO = new DagInstanceDTO();
        instanceDTO.setId(event.getWorkflowInstanceId());
        if (Objects.isNull(event.getGlobalVariable())) {
            instanceDTO.setInputs(JacksonUtil.createObjectNode());
        } else {
            instanceDTO.setInputs(event.getGlobalVariable());
        }
        instanceDTO.setStatus(CarpWorkflowInstanceState.PENDING.getValue());
        dagInstanceService.update(instanceDTO);

        // 更新 dag_step
        List<DagStepDTO> steps = dagStepService.listSteps(event.getWorkflowInstanceId());
        if (CollectionUtils.isEmpty(steps) == false) {
            for (DagStepDTO dagStepDTO : steps) {
                dagStepDTO.setStatus(CarpWorkflowStepInstanceState.PENDING.getValue());
                dagStepService.update(dagStepDTO);
            }
        }
        // 更新 dag_link
        List<DagLinkDTO> links = dagLinkService.listLinks(event.getWorkflowInstanceId());
        if (CollectionUtils.isEmpty(links) == false) {
            for (DagLinkDTO dagLinkDTO : links) {
                dagLinkDTO.setStatus(CarpWorkflowStepInstanceState.PENDING.getValue());
                dagLinkService.update(dagLinkDTO);
            }
        }

        WorkflowInstance workflowInstance = workflowInstanceService.get(event.getWorkflowInstanceId());
        stateMachine.deploy(workflowInstance);
    }

}
