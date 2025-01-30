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
package cn.sliew.carp.module.workflow.internal.executor;

import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowExecuteType;
import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.algorithm.DefaultDagEdge;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowTaskInstance;

import java.util.Set;

public interface WorkflowInstanceExecutor {

    CarpWorkflowExecuteType getExecuteType();

    void execute(WorkflowInstance instance, DAG<WorkflowTaskInstance> dag);

    boolean checkEdge(WorkflowInstance instance, DAG<WorkflowTaskInstance> dag, DefaultDagEdge<WorkflowTaskInstance> edge);

    boolean checkTask(WorkflowInstance instance, DAG<WorkflowTaskInstance> dag, WorkflowTaskInstance task);

    void executeTasks(Set<WorkflowTaskInstance> task);
}
