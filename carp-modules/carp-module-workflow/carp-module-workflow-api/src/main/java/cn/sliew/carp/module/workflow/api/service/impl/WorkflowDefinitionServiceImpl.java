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

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.algorithm.DefaultDagEdge;
import cn.sliew.carp.framework.dag.service.dto.*;
import cn.sliew.carp.module.workflow.api.graph.WorkflowDefinitionGraph;
import cn.sliew.carp.module.workflow.api.graph.WorkflowExecutionGraph;
import cn.sliew.carp.module.workflow.api.graph.WorkflowTaskDefinition;
import cn.sliew.carp.module.workflow.api.graph.WorkflowTaskInstance;
import cn.sliew.carp.module.workflow.api.service.WorkflowDagService;
import cn.sliew.carp.module.workflow.api.service.WorkflowDefinitionService;
import cn.sliew.carp.module.workflow.api.service.convert.WorkflowTaskDefinitionConvert;
import cn.sliew.carp.module.workflow.api.service.convert.WorkflowTaskInstanceConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WorkflowDefinitionServiceImpl implements WorkflowDefinitionService {

    @Autowired
    private WorkflowDagService workflowDagService;

    @Override
    public WorkflowDefinitionGraph get(Long dagId) {
        DagConfigComplexDTO complexDTO = workflowDagService.getDag(dagId);
        WorkflowDefinitionGraph graph = new WorkflowDefinitionGraph();
        DAG<WorkflowTaskDefinition> dag = new DAG<>();
        Map<String, WorkflowTaskDefinition> taskDefinitionMap = new HashMap<>();
        for (DagConfigStepDTO stepDTO : complexDTO.getSteps()) {
            WorkflowTaskDefinition taskDefinition = WorkflowTaskDefinitionConvert.INSTANCE.toDto(stepDTO);
            taskDefinitionMap.put(taskDefinition.getStepId(), taskDefinition);
            dag.addNode(taskDefinition);
        }
        for (DagConfigLinkDTO linkDTO : complexDTO.getLinks()) {
            dag.addEdge(taskDefinitionMap.get(linkDTO.getFromStepId()), taskDefinitionMap.get(linkDTO.getToStepId()));
        }
        graph.setDag(dag);
        return graph;
    }

    @Override
    public WorkflowExecutionGraph getExecutionGraph(Long dagInstanceId) {
        DagInstanceComplexDTO complexDTO = workflowDagService.getDagInstance(dagInstanceId);
        WorkflowDefinitionGraph definitionGraph = get(complexDTO.getDagConfig().getId());
        WorkflowExecutionGraph executionGraph = new WorkflowExecutionGraph();
        DAG<WorkflowTaskInstance> dag = new DAG<>();
        Map<String, WorkflowTaskInstance> taskInstanceMap = new HashMap<>();
        for (DagStepDTO stepDTO : complexDTO.getSteps()) {
            WorkflowTaskInstance taskInstance = WorkflowTaskInstanceConvert.INSTANCE.toDto(stepDTO);
            taskInstanceMap.put(taskInstance.getDefinition().getStepId(), taskInstance);
            dag.addNode(taskInstance);
        }
        for (DefaultDagEdge<WorkflowTaskDefinition> edge : definitionGraph.getDag().edges()) {
            dag.addEdge(taskInstanceMap.get(edge.getSource().getStepId()), taskInstanceMap.get(edge.getTarget().getStepId()));
        }
        executionGraph.setDag(dag);
        return executionGraph;
    }
}
