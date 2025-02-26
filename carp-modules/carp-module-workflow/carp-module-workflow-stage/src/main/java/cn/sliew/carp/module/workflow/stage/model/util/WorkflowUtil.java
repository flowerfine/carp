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
package cn.sliew.carp.module.workflow.stage.model.util;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.algorithm.DagUtil;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceComplexDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.stage.model.domain.convert.WorkflowStepInstanceConvert;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import com.google.common.collect.Maps;

import java.util.Map;

public enum WorkflowUtil {
    ;

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
