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
package cn.sliew.carp.module.workflow.stage.model.domain.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.module.workflow.stage.model.domain.definition.WorkflowDefinitionGraphEdge;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowExecutionGraph;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import com.google.common.collect.Maps;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Map;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowExecutionGraphConvert extends BaseConvert<WorkflowExecutionGraph, DAG<WorkflowStepInstance>> {
    WorkflowExecutionGraphConvert INSTANCE = Mappers.getMapper(WorkflowExecutionGraphConvert.class);

    @Override
    default WorkflowExecutionGraph toDo(DAG<WorkflowStepInstance> dag) {
        throw new UnsupportedOperationException();
    }

    @Override
    default DAG<WorkflowStepInstance> toDto(WorkflowExecutionGraph graph) {
        DAG<WorkflowStepInstance> dag = new DAG<>();

        Map<String, WorkflowStepInstance> stepInstanceMap = Maps.newHashMap();
        for (WorkflowStepInstance stepInstance : graph.getTasks()) {
            stepInstanceMap.put(stepInstance.getNode().getStepId(), stepInstance);
            dag.addNode(stepInstance);
        }
        for (WorkflowDefinitionGraphEdge edge : graph.getEdges()) {
            dag.addEdge(stepInstanceMap.get(edge.getFromStepId()), stepInstanceMap.get(edge.getToStepId()));
        }

        if (graph.getPreTask() != null) {
            dag.addNode(graph.getPreTask());
            Set<WorkflowStepInstance> sources = dag.getSources();
            for (WorkflowStepInstance source : sources) {
                dag.addEdge(graph.getPreTask(), source);
            }
        }

        if (graph.getPostTask() != null) {
            dag.addNode(graph.getPostTask());
            Set<WorkflowStepInstance> sinks = dag.getSinks();
            for (WorkflowStepInstance sink : sinks) {
                dag.addEdge(sink, graph.getPostTask());
            }
        }
        return dag;
    }
}
