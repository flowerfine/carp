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
package cn.sliew.carp.module.dag.queue.handler;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.framework.dag.service.DagConfigComplexService;
import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.DagLinkService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.*;
import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.convert.WorkflowInstanceConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class InitDagHandler extends AbstractDagMessageHandler<Messages.InitWorkflow> {

    @Autowired
    private DagConfigComplexService dagConfigComplexService;
    @Autowired
    private DagInstanceService dagInstanceService;
    @Autowired
    private DagStepService dagStepService;
    @Autowired
    private DagLinkService dagLinkService;

    @Override
    public Class getMessageType() {
        return Messages.InitWorkflow.class;
    }

    @Override
    public void handle(Messages.InitWorkflow message) {
        // inputs 处理
        DagConfigComplexDTO dagConfigComplexDTO = dagConfigComplexService.selectOne(message.getDagConfigId());
        List<DagConfigStepDTO> steps = dagConfigComplexDTO.getSteps();
        List<DagConfigLinkDTO> links = dagConfigComplexDTO.getLinks();
        dagConfigComplexDTO.setSteps(null);
        dagConfigComplexDTO.setLinks(null);

        // 插入 dag_instance
        DagInstanceDTO dagInstanceDTO = new DagInstanceDTO();
        dagInstanceDTO.setNamespace(dagConfigComplexDTO.getNamespace());
        dagInstanceDTO.setDagConfig(dagConfigComplexDTO);
        dagInstanceDTO.setUuid(UUIDUtil.randomUUId());
//        dagInstanceDTO.setBody(JacksonUtil.toJsonNode(dagConfigComplexDTO));
        dagInstanceDTO.setStatus(ExecutionStatus.NOT_STARTED.name());
        Long dagInstanceId = dagInstanceService.add(dagInstanceDTO);
        dagInstanceDTO.setId(dagInstanceId);
        // 插入 dag_step
        if (CollectionUtils.isEmpty(steps) == false) {
            for (DagConfigStepDTO dagConfigStepDTO : steps) {
                DagStepDTO dagStepDTO = new DagStepDTO();
                dagStepDTO.setNamespace(dagConfigComplexDTO.getNamespace());
                dagStepDTO.setDagInstance(dagInstanceDTO);
                dagStepDTO.setDagConfigStep(dagConfigStepDTO);
                dagStepDTO.setUuid(UUIDUtil.randomUUId());
//                dagStepDTO.setBody(JacksonUtil.toJsonNode(dagConfigStepDTO));
                dagStepDTO.setStatus(ExecutionStatus.NOT_STARTED.name());
                dagStepService.add(dagStepDTO);
            }
        }
        // 插入 dag_link
        if (CollectionUtils.isEmpty(links) == false) {
            for (DagConfigLinkDTO dagConfigLinkDTO : links) {
                DagLinkDTO dagLinkDTO = new DagLinkDTO();
                dagLinkDTO.setNamespace(dagConfigComplexDTO.getNamespace());
                dagLinkDTO.setDagInstance(dagInstanceDTO);
                dagLinkDTO.setDagConfigLink(dagConfigLinkDTO);
                dagLinkDTO.setUuid(UUIDUtil.randomUUId());
                dagLinkDTO.setInputs(dagConfigLinkDTO.getLinkAttrs());
//                dagLinkDTO.setBody(JacksonUtil.toJsonNode(dagConfigLinkDTO));
                dagLinkDTO.setStatus(ExecutionStatus.NOT_STARTED.name());
                dagLinkService.add(dagLinkDTO);
            }
        }

        DagInstanceDTO newDagInstanceDTO = dagInstanceService.get(dagInstanceId);
        push(new Messages.StartWorkflow(WorkflowInstanceConvert.INSTANCE.toDto(newDagInstanceDTO)));
    }
}
