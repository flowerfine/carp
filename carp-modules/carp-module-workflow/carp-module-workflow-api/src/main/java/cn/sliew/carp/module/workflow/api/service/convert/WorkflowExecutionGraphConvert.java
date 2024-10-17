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

package cn.sliew.carp.module.workflow.api.service.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinitionGraphEdge;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowExecutionGraph;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowTaskInstance;
import com.google.common.collect.Maps;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Map;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowExecutionGraphConvert extends BaseConvert<WorkflowExecutionGraph, DAG<WorkflowTaskInstance>> {
    WorkflowExecutionGraphConvert INSTANCE = Mappers.getMapper(WorkflowExecutionGraphConvert.class);

    @Override
    default WorkflowExecutionGraph toDo(DAG<WorkflowTaskInstance> dag) {
        throw new UnsupportedOperationException();
    }

    @Override
    default DAG<WorkflowTaskInstance> toDto(WorkflowExecutionGraph graph) {
        DAG<WorkflowTaskInstance> dag = new DAG<>();

        Map<String, WorkflowTaskInstance> taskInstanceMap = Maps.newHashMap();
        for (WorkflowTaskInstance taskInstance : graph.getTasks()) {
            taskInstanceMap.put(taskInstance.getNode().getStepId(), taskInstance);
            dag.addNode(taskInstance);
        }
        for (WorkflowDefinitionGraphEdge edge : graph.getEdges()) {
            dag.addEdge(taskInstanceMap.get(edge.getFromStepId()), taskInstanceMap.get(edge.getToStepId()));
        }

        if (graph.getPreTask() != null) {
            dag.addNode(graph.getPreTask());
            Set<WorkflowTaskInstance> sources = dag.getSources();
            for (WorkflowTaskInstance source : sources) {
                dag.addEdge(graph.getPreTask(), source);
            }
        }

        if (graph.getPostTask() != null) {
            dag.addNode(graph.getPostTask());
            Set<WorkflowTaskInstance> sinks = dag.getSinks();
            for (WorkflowTaskInstance sink : sinks) {
                dag.addEdge(sink, graph.getPostTask());
            }
        }
        return dag;
    }
}
