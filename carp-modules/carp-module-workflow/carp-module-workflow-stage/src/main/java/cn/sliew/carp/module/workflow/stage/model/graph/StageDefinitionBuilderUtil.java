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

import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecutionImpl;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ListIterator;
import java.util.Objects;

public enum StageDefinitionBuilderUtil {
    ;

    /**
     * Build and append the tasks for stage.
     */
    public static void buildTasks(StageDefinitionBuilder stageDefinitionBuilder, DagStepDTO step) {
        ListIterator<TaskNode> iterator = stageDefinitionBuilder.buildTaskGraph(step).listIterator();
        Iterators.forEachWithMetadata(
                iterator,
                element -> processTaskNode(step, element, false));
    }

    private static void processTaskNode(
            DagStepDTO step,
            Iterators.IteratorElement<TaskNode> element,
            boolean isSubGraph) {

        if (element.getValue() instanceof TaskNode.DefinedTask) {
            TaskNode.DefinedTask definedTask = (TaskNode.DefinedTask) element.getValue();
            TaskExecutionImpl task = buildTaskExecution(step, definedTask);

            if (isSubGraph) {
                task.setLoopStart(element.isFirst());
                task.setLoopEnd(element.isLast());
            } else {
                task.setStageStart(element.isFirst());
                task.setStageEnd(element.isLast());
            }
            step.setBody(addTasks(step.getBody(), task));
        } else if (element.getValue() instanceof TaskNode.TaskGraph) {
            TaskNode.TaskGraph taskGraph = (TaskNode.TaskGraph) element.getValue();
            ListIterator<TaskNode> iterator = taskGraph.listIterator();
            Iterators.forEachWithMetadata(
                    iterator,
                    item -> processTaskNode(step, item, true));
        }
    }

    private static TaskExecutionImpl buildTaskExecution(DagStepDTO step, TaskNode.DefinedTask taskNode) {
        TaskExecutionImpl taskExecution = new TaskExecutionImpl();
        taskExecution.setId(getTaskSize(step.getBody()) + 1L);
        taskExecution.setName(taskNode.getName());
        taskExecution.setImplementingClass(taskNode.getImplementingClassName());
        return taskExecution;
    }

    private static JsonNode addTasks(JsonNode body, Object task) {
        ObjectNode objectNode;
        if (Objects.isNull(body) || body.isNull() || body.isEmpty()) {
            objectNode = JacksonUtil.createObjectNode();
        } else {
            objectNode = (ObjectNode) body;
        }
        JsonNode tasksNode = objectNode.path("tasks");
        ArrayNode arrayNode;
        if (tasksNode.isNull() || tasksNode.isEmpty()) {
            arrayNode = JacksonUtil.createArrayNode();
            objectNode.set("tasks", arrayNode);
        } else {
            arrayNode = (ArrayNode) tasksNode;
        }
        arrayNode.add(JacksonUtil.toJsonNode(task));

        return objectNode;
    }

    private static int getTaskSize(JsonNode body) {
        if (Objects.isNull(body) || body.isNull()) {
            return 0;
        }
        JsonNode tasksNode = body.path("tasks");
        if (tasksNode.isNull() == false && tasksNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) tasksNode;
            return arrayNode.size();
        }
        return 0;
    }

}
