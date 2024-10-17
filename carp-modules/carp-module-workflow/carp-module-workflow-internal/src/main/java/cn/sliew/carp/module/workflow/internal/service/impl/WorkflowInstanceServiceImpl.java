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

package cn.sliew.carp.module.workflow.internal.service.impl;

import cn.sliew.carp.framework.common.dict.workflow.WorkflowInstanceState;
import cn.sliew.carp.framework.common.dict.workflow.WorkflowStepType;
import cn.sliew.carp.framework.common.dict.workflow.WorkflowTaskInstanceStage;
import cn.sliew.carp.framework.dag.service.DagConfigLinkService;
import cn.sliew.carp.framework.dag.service.DagInstanceComplexService;
import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagConfigLinkDTO;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceComplexDTO;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowExecutionGraph;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowTaskInstance;
import cn.sliew.carp.module.workflow.api.manager.WorkflowInstanceManager;
import cn.sliew.carp.module.workflow.api.service.WorkflowInstanceService;
import cn.sliew.carp.module.workflow.api.service.convert.WorkflowDefinitionGraphEdgeConvert;
import cn.sliew.carp.module.workflow.api.service.convert.WorkflowInstanceConvert;
import cn.sliew.carp.module.workflow.api.service.convert.WorkflowTaskInstanceConvert;
import cn.sliew.carp.module.workflow.api.service.param.WorkflowRunParam;
import cn.sliew.carp.module.workflow.api.service.param.WorkflowStopParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WorkflowInstanceServiceImpl implements WorkflowInstanceService {

    @Autowired
    private DagInstanceComplexService dagInstanceComplexService;
    @Autowired
    private DagInstanceService dagInstanceService;
    @Autowired
    private DagConfigLinkService dagConfigLinkService;
    @Autowired
    private DagStepService dagStepService;
    @Autowired
    private WorkflowInstanceManager workflowInstanceManager;

    @Override
    public WorkflowInstance get(Long workflowInstanceId) {
        DagInstanceDTO dagInstanceDTO = dagInstanceComplexService.selectSimpleOne(workflowInstanceId);
        return WorkflowInstanceConvert.INSTANCE.toDto(dagInstanceDTO);
    }

    @Override
    public WorkflowInstance getGraph(Long workflowInstanceId) {
        DagInstanceComplexDTO complexDTO = dagInstanceComplexService.selectOne(workflowInstanceId);
        WorkflowInstance dto = WorkflowInstanceConvert.INSTANCE.toDto(complexDTO);
        WorkflowExecutionGraph graph = new WorkflowExecutionGraph();
        dto.setGraph(graph);

        List<WorkflowTaskInstance> allNodes = WorkflowTaskInstanceConvert.INSTANCE.toDto(complexDTO.getSteps());
        WorkflowTaskInstance preNode = allNodes.stream().filter(node -> node.getNode().getMeta().getStepType() == WorkflowStepType.PRE).findFirst().orElse(null);
        WorkflowTaskInstance postNode = allNodes.stream().filter(node -> node.getNode().getMeta().getStepType() == WorkflowStepType.POST).findFirst().orElse(null);
        List<WorkflowTaskInstance> normalNodes = allNodes.stream().filter(node -> node.getNode().getMeta().getStepType() == WorkflowStepType.NORMAL).collect(Collectors.toList());
        graph.setPreTask(preNode);
        graph.setPostTask(postNode);
        graph.setTasks(normalNodes);

        List<DagConfigLinkDTO> linkDTOS = dagConfigLinkService.listLinks(complexDTO.getDagConfig().getId());
        graph.setEdges(WorkflowDefinitionGraphEdgeConvert.INSTANCE.toDto(linkDTOS));

        return dto;
    }

    @Override
    public WorkflowTaskInstance getTask(Long workflowTaskInstanceId) {
        DagStepDTO dagStepDTO = dagStepService.get(workflowTaskInstanceId);
        return WorkflowTaskInstanceConvert.INSTANCE.toDto(dagStepDTO);
    }

    @Override
    public Long simpleInitialize(Long workflowDefinitionId) {
        Long workflowInstanceId = dagInstanceComplexService.initialize(workflowDefinitionId);
        dagInstanceService.updateStatus(workflowInstanceId, null, WorkflowInstanceState.PENDING.getValue());
        List<DagStepDTO> dagStepDTOS = dagStepService.listSteps(workflowInstanceId);
        for (DagStepDTO dagStepDTO : dagStepDTOS) {
            dagStepService.updateStatus(dagStepDTO.getId(), null, WorkflowTaskInstanceStage.PENDING.getValue());
        }
        return workflowInstanceId;
    }

    @Override
    public Long run(WorkflowRunParam param) {
        Long workflowInstanceId = simpleInitialize(param.getId());
        // 更新参数
        DagInstanceDTO instanceDTO = new DagInstanceDTO();
        instanceDTO.setId(workflowInstanceId);
        instanceDTO.setInputs(param.getGlobalVariable());
        dagInstanceService.update(instanceDTO);
        workflowInstanceManager.deploy(workflowInstanceId);
        return workflowInstanceId;
    }

    @Override
    public void stop(WorkflowStopParam param) {

    }
}
