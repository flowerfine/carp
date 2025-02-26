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
package cn.sliew.carp.module.workflow.stage.model.graph;

import cn.hutool.extra.spring.SpringUtil;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecutionImpl;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.stage.model.repository.WorkflowRepository;
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
        List<TaskExecutionImpl> tasks = Lists.newArrayList();
        Iterators.forEachWithMetadata(
                iterator,
                element -> processTaskNode(step, element, tasks, false));

        WorkflowRepository workflowRepository = SpringUtil.getBean(WorkflowRepository.class);
        tasks.forEach(task -> workflowRepository.addStepTaskInstance(step, task));
    }

    private static void processTaskNode(
            WorkflowStepInstance step,
            Iterators.IteratorElement<TaskNode> element,
            List<TaskExecutionImpl> tasks,
            boolean isSubGraph) {

        if (element.getValue() instanceof TaskNode.DefinedTask) {
            TaskNode.DefinedTask definedTask = (TaskNode.DefinedTask) element.getValue();
            TaskExecutionImpl task = buildTaskExecution(step, tasks, definedTask);

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

    private static TaskExecutionImpl buildTaskExecution(WorkflowStepInstance step, List<TaskExecutionImpl> tasks, TaskNode.DefinedTask taskNode) {
        TaskExecutionImpl taskExecution = new TaskExecutionImpl();
        taskExecution.setTaskId(tasks.size() + 1L);
        taskExecution.setName(taskNode.getName());
        taskExecution.setImplementingClass(taskNode.getImplementingClassName());
        return taskExecution;
    }

}
