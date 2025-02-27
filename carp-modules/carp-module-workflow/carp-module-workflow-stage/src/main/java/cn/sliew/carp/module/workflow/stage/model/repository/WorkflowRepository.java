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
package cn.sliew.carp.module.workflow.stage.model.repository;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.module.workflow.stage.model.domain.definition.WorkflowDefinition;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecutionImpl;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;

import java.util.List;
import java.util.Map;

public interface WorkflowRepository {

    WorkflowDefinition getWorkflowDefinition(Long id);

    /**
     * todo 增加 not found exception
     */
    WorkflowInstance get(Long id);

    DAG<WorkflowStepInstance> getDAG(Long id);

    Long addFromDefinition(Long workflowDefinitionId,
                           Map<String, Object> inputs,
                           Map<String, Map<String, Object>> stepInputs);

    /**
     * todo 增加 not found exception
     */
    WorkflowStepInstance getStepInstance(Long stepInstanceId);

    List<TaskExecutionImpl> getStepTaskInstances(Long stepInstanceId);

    TaskExecutionImpl getStepTaskInstance(Long stepTaskInstanceId);

    TaskExecutionImpl getStepTaskInstance(Long workflowInstanceId, Long stepInstanceId, Long taskId);

    void addStepTaskInstance(WorkflowStepInstance stepInstance, TaskExecutionImpl taskExecution);
}
