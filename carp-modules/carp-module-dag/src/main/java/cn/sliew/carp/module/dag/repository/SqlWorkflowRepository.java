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
package cn.sliew.carp.module.dag.repository;

import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowStepType;
import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.service.DagConfigLinkService;
import cn.sliew.carp.framework.dag.service.DagInstanceComplexService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagConfigLinkDTO;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceComplexDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.stage.model.domain.convert.WorkflowDefinitionGraphEdgeConvert;
import cn.sliew.carp.module.workflow.stage.model.domain.convert.WorkflowInstanceConvert;
import cn.sliew.carp.module.workflow.stage.model.domain.convert.WorkflowStepInstanceConvert;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowExecutionGraph;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.stage.model.repository.WorkflowRepository;
import cn.sliew.carp.module.workflow.stage.model.util.WorkflowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SqlWorkflowRepository implements WorkflowRepository {

    @Autowired
    private DagInstanceComplexService dagInstanceComplexService;
    @Autowired
    private DagConfigLinkService dagConfigLinkService;
    @Autowired
    private DagStepService dagStepService;

    @Override
    public WorkflowInstance get(Long id) {
        DagInstanceComplexDTO complexDTO = dagInstanceComplexService.selectOne(id);
        WorkflowInstance workflowInstance = WorkflowInstanceConvert.INSTANCE.toDto(complexDTO);
        WorkflowExecutionGraph graph = new WorkflowExecutionGraph();

        List<WorkflowStepInstance> allNodes = WorkflowStepInstanceConvert.INSTANCE.toDto(complexDTO.getSteps());
        WorkflowStepInstance preNode = allNodes.stream().filter(node -> node.getNode().getMeta().getStepType() == CarpWorkflowStepType.PRE).findFirst().orElse(null);
        WorkflowStepInstance postNode = allNodes.stream().filter(node -> node.getNode().getMeta().getStepType() == CarpWorkflowStepType.POST).findFirst().orElse(null);
        List<WorkflowStepInstance> normalNodes = allNodes.stream().filter(node -> node.getNode().getMeta().getStepType() == CarpWorkflowStepType.NORMAL).collect(Collectors.toList());
        graph.setPreTask(preNode);
        graph.setPostTask(postNode);
        graph.setTasks(normalNodes);
        List<DagConfigLinkDTO> linkDTOS = dagConfigLinkService.listLinks(complexDTO.getDagConfig().getId());
        graph.setEdges(WorkflowDefinitionGraphEdgeConvert.INSTANCE.toDto(linkDTOS));

        workflowInstance.setGraph(graph);
        return workflowInstance;
    }

    @Override
    public DAG<WorkflowStepInstance> getDAG(Long id) {
        DagInstanceComplexDTO complexDTO = dagInstanceComplexService.selectOne(id);
        return WorkflowUtil.buildDag(complexDTO);
    }

    @Override
    public WorkflowStepInstance getStepInstance(Long stepInstanceId) {
        DagStepDTO dagStepDTO = dagStepService.getWithConfig(stepInstanceId);
        return WorkflowStepInstanceConvert.INSTANCE.toDto(dagStepDTO);
    }
}
