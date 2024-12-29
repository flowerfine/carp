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

package cn.sliew.carp.module.workflow.api.service.impl;

import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowStepType;
import cn.sliew.carp.framework.dag.service.DagConfigComplexService;
import cn.sliew.carp.framework.dag.service.dto.DagConfigComplexDTO;
import cn.sliew.carp.framework.dag.service.dto.DagConfigDTO;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinition;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinitionGraph;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinitionGraphEdge;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinitionGraphNode;
import cn.sliew.carp.module.workflow.api.service.WorkflowDefinitionService;
import cn.sliew.carp.module.workflow.api.service.convert.WorkflowDefinitionConvert;
import cn.sliew.carp.module.workflow.api.service.convert.WorkflowDefinitionGraphEdgeConvert;
import cn.sliew.carp.module.workflow.api.service.convert.WorkflowDefinitionGraphNodeConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkflowDefinitionServiceImpl implements WorkflowDefinitionService {

    @Autowired
    private DagConfigComplexService dagConfigComplexService;

    @Override
    public WorkflowDefinition get(Long id) {
        DagConfigDTO dagConfigDTO = dagConfigComplexService.selectSimpleOne(id);
        return WorkflowDefinitionConvert.INSTANCE.toDto(dagConfigDTO);
    }

    @Override
    public WorkflowDefinition getGraph(Long id) {
        DagConfigComplexDTO complexDTO = dagConfigComplexService.selectOne(id);
        WorkflowDefinition dto = WorkflowDefinitionConvert.INSTANCE.toDto(complexDTO);
        WorkflowDefinitionGraph graph = new WorkflowDefinitionGraph();
        dto.setGraph(graph);

        List<WorkflowDefinitionGraphEdge> edges = WorkflowDefinitionGraphEdgeConvert.INSTANCE.toDto(complexDTO.getLinks());
        List<WorkflowDefinitionGraphNode> allNodes = WorkflowDefinitionGraphNodeConvert.INSTANCE.toDto(complexDTO.getSteps());
        WorkflowDefinitionGraphNode preNode = allNodes.stream().filter(node -> node.getMeta().getStepType() == CarpWorkflowStepType.PRE).findFirst().orElse(null);
        WorkflowDefinitionGraphNode postNode = allNodes.stream().filter(node -> node.getMeta().getStepType() == CarpWorkflowStepType.POST).findFirst().orElse(null);
        List<WorkflowDefinitionGraphNode> normalNodes = allNodes.stream().filter(node -> node.getMeta().getStepType() == CarpWorkflowStepType.NORMAL).collect(Collectors.toList());

        graph.setEdges(edges);
        graph.setPreNode(preNode);
        graph.setPostNode(postNode);
        graph.setNodes(normalNodes);

        return dto;
    }

}
