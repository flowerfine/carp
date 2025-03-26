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
package cn.sliew.carp.module.workflow.api.util;

import cn.hutool.extra.spring.SpringUtil;
import cn.sliew.carp.module.workflow.api.service.WorkflowInstanceService;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowTaskInstance;
import cn.sliew.carp.module.workflow.stage.model.graph.Iterators;
import cn.sliew.carp.module.workflow.stage.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.workflow.stage.model.graph.TaskNode;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.ListIterator;

public enum StageDefinitionBuilderUtil {
    ;

    /**
     * Build and append the tasks for stage.
     */
    public static void buildTasks(StageDefinitionBuilder stageDefinitionBuilder, WorkflowStepInstance step) {
        ListIterator<TaskNode> iterator = stageDefinitionBuilder.buildTaskGraph(step).listIterator();
        List<WorkflowTaskInstance> tasks = Lists.newArrayList();
        Iterators.forEachWithMetadata(
                iterator,
                element -> processTaskNode(step, element, tasks, false));

        WorkflowInstanceService workflowInstanceService = SpringUtil.getBean(WorkflowInstanceService.class);
        tasks.forEach(task -> workflowInstanceService.addTask(step, task));
    }

    private static void processTaskNode(
            WorkflowStepInstance step,
            Iterators.IteratorElement<TaskNode> element,
            List<WorkflowTaskInstance> tasks,
            boolean isSubGraph) {

        if (element.getValue() instanceof TaskNode.DefinedTask) {
            TaskNode.DefinedTask definedTask = (TaskNode.DefinedTask) element.getValue();
            WorkflowTaskInstance task = buildTaskExecution(step, tasks, definedTask);

            if (isSubGraph) {
                task.setLoopStart(element.isFirst());
                task.setLoopEnd(element.isLast());
            } else {
                task.setStageStart(element.isFirst());
                task.setStageEnd(element.isLast());
            }

            tasks.add(task);
        } else if (element.getValue() instanceof TaskNode.TaskGraph) {
            TaskNode.TaskGraph taskGraph = (TaskNode.TaskGraph) element.getValue();
            ListIterator<TaskNode> iterator = taskGraph.listIterator();
            Iterators.forEachWithMetadata(
                    iterator,
                    item -> processTaskNode(step, item, tasks, true));
        }
    }

    private static WorkflowTaskInstance buildTaskExecution(WorkflowStepInstance step, List<WorkflowTaskInstance> tasks, TaskNode.DefinedTask taskNode) {
        WorkflowTaskInstance taskExecution = new WorkflowTaskInstance();
        taskExecution.setNamespace(step.getNamespace());
        taskExecution.setWorkflowInstanceId(step.getWorkflowInstance().getId());
        taskExecution.setStepId(step.getId());
        taskExecution.setTaskId(tasks.size() + 1L);
        taskExecution.setName(taskNode.getName());
        taskExecution.setImplementingClass(taskNode.getImplementingClassName());
        return taskExecution;
    }

}
