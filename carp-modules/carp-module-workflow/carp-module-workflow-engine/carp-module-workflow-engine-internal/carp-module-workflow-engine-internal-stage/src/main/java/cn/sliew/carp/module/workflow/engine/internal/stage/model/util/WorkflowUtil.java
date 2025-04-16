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
package cn.sliew.carp.module.workflow.engine.internal.stage.model.util;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.algorithm.DagUtil;
import cn.sliew.carp.framework.dag.service.dto.DagConfigComplexDTO;
import cn.sliew.carp.framework.dag.service.dto.DagConfigStepDTO;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceComplexDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.engine.internal.domain.convert.WorkflowDefinitionGraphNodeConvert;
import cn.sliew.carp.module.workflow.engine.internal.domain.convert.WorkflowStepInstanceConvert;
import cn.sliew.carp.module.workflow.engine.internal.domain.definition.WorkflowDefinitionGraphNode;
import cn.sliew.carp.module.workflow.engine.internal.domain.instance.WorkflowStepInstance;
import com.google.common.collect.Maps;

import java.util.Map;

public enum WorkflowUtil {
    ;

    public static DAG<WorkflowDefinitionGraphNode> buildDag(DagConfigComplexDTO dagConfigComplexDTO) {
        DAG<DagConfigStepDTO> dag = DagUtil.buildDag(dagConfigComplexDTO);
        Map<String, WorkflowDefinitionGraphNode> nodeMap = Maps.newHashMap();
        DAG<WorkflowDefinitionGraphNode> result = new DAG<>();
        dag.nodes().forEach(node -> {
            WorkflowDefinitionGraphNode graphNode = WorkflowDefinitionGraphNodeConvert.INSTANCE.toDto(node);
            nodeMap.put(graphNode.getStepId(), graphNode);
            result.addNode(graphNode);
        });
        dag.edges().forEach(edge -> {
            result.addEdge(nodeMap.get(edge.getSource().getStepId()), nodeMap.get(edge.getTarget().getStepId()));
        });
        return result;
    }

    public static DAG<WorkflowStepInstance> buildDag(DagInstanceComplexDTO dagInstanceComplexDTO) {
        DAG<DagStepDTO> dag = DagUtil.buildDag(dagInstanceComplexDTO);
        Map<String, WorkflowStepInstance> stepMap = Maps.newHashMap();
        DAG<WorkflowStepInstance> result = new DAG<>();
        dag.nodes().forEach(node -> {
            WorkflowStepInstance stepInstance = WorkflowStepInstanceConvert.INSTANCE.toDto(node);
            stepMap.put(stepInstance.getUuid(), stepInstance);
            result.addNode(stepInstance);
        });
        dag.edges().forEach(edge -> {
            result.addEdge(stepMap.get(edge.getSource().getUuid()), stepMap.get(edge.getTarget().getUuid()));
        });
        return result;
    }

}
