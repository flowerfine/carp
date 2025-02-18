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
package cn.sliew.carp.module.orca.spinnaker.api.resolver;

import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageGraphBuilderImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.TaskNode;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.RestrictExecutionDuringTimeWindow;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionFactory;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Iterators;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public enum StageDefinitionBuilders {
    ;

    /**
     * Build and append the tasks for stage.
     */
    public static void buildTasks(
            StageDefinitionBuilder stageDefinitionBuilder,
            StageExecution stage,
            TaskImplementationResolver taskImplementationResolver) {

        ListIterator<TaskNode> iterator = stageDefinitionBuilder.buildTaskGraph(stage).listIterator();
        Iterators.forEachWithMetadata(
                iterator,
                element -> processTaskNode(stage, element, taskImplementationResolver, false));
    }

    /**
     * Add context flags to the stage.
     */
    public static void addContextFlags(StageDefinitionBuilder stageDefinitionBuilder, StageExecution stage) {
        if (stageDefinitionBuilder.canManuallySkip(stage)) {
            // Provides a flag for the UI to indicate that the stage can be skipped.
            stage.getContext().put("canManuallySkip", true);
        }
    }

    private static void processTaskNode(
            StageExecution stage,
            Iterators.IteratorElement<TaskNode> element,
            TaskImplementationResolver resolver,
            boolean isSubGraph) {

        if (element.getValue() instanceof TaskNode.DefinedTask) {
            TaskNode.DefinedTask definedTask = (TaskNode.DefinedTask) element.getValue();
            TaskExecutionImpl task = buildTaskExecution(stage, resolver.resolve(stage, definedTask));

            if (isSubGraph) {
                task.setLoopStart(element.isFirst());
                task.setLoopEnd(element.isLast());
            } else {
                task.setStageStart(element.isFirst());
                task.setStageEnd(element.isLast());
            }
            stage.getTasks().add(task);
        } else if (element.getValue() instanceof TaskNode.TaskGraph) {
            TaskNode.TaskGraph taskGraph = (TaskNode.TaskGraph) element.getValue();
            ListIterator<TaskNode> iterator = taskGraph.listIterator();
            Iterators.forEachWithMetadata(
                    iterator,
                    item -> processTaskNode(stage, item, resolver, true));
        }
    }

    private static TaskExecutionImpl buildTaskExecution(StageExecution stage, TaskNode.DefinedTask taskNode) {
        TaskExecutionImpl taskExecution = new TaskExecutionImpl();
        taskExecution.setId(stage.getTasks().size() + 1L);
        taskExecution.setName(taskNode.getName());
        taskExecution.setImplementingClass(taskNode.getImplementingClassName());
        return taskExecution;
    }

    /**
     * Build the synthetic stages for stage and inject them into the execution.
     */
    public static void buildBeforeStages(
            StageDefinitionBuilder stageDefinitionBuilder,
            StageExecution stage,
            Consumer<StageExecution> callback) {

        StageExecution executionWindow = buildExecutionWindow(stage);

        StageGraphBuilderImpl graph = StageGraphBuilderImpl.beforeStages(stage, executionWindow);
        stageDefinitionBuilder.beforeStages(stage, graph);
        List<StageExecution> beforeStages = StreamSupport.stream(graph.build().spliterator(), false)
                .collect(Collectors.toList());

        for (StageExecution beforeStage : beforeStages) {
            sanitizeContext(beforeStage);
            injectStage(stage.getPipelineExecution(), stage.getPipelineExecution().getStages().indexOf(stage), beforeStage);
            callback.accept(beforeStage);
        }
    }

    public static void buildAfterStages(
            StageDefinitionBuilder stageDefinitionBuilder,
            StageExecution stage,
            Consumer<StageExecution> callback) {

        StageGraphBuilderImpl graph = StageGraphBuilderImpl.afterStages(stage);
        stageDefinitionBuilder.afterStages(stage, graph);
        List<StageExecution> afterStages = StreamSupport.stream(graph.build().spliterator(), false)
                .collect(Collectors.toList());

        appendAfterStages(stage, afterStages, callback);
    }

    public static void buildFailureStages(
            StageDefinitionBuilder stageDefinitionBuilder,
            StageExecution stage,
            Consumer<StageExecution> callback) {

        StageGraphBuilderImpl graph = StageGraphBuilderImpl.afterStages(stage);
        stageDefinitionBuilder.onFailureStages(stage, graph);
        List<StageExecution> afterStages = StreamSupport.stream(graph.build().spliterator(), false)
                .collect(Collectors.toList());

        appendAfterStages(stage, afterStages, callback);
    }

    public static void appendAfterStages(
            StageExecution stage,
            List<StageExecution> afterStages,
            Consumer<StageExecution> callback) {

        int index = stage.getPipelineExecution().getStages().indexOf(stage) + 1;
        Collections.reverse(afterStages);

        for (StageExecution afterStage : afterStages) {
            sanitizeContext(afterStage);
            injectStage(stage.getPipelineExecution(), index, afterStage);
            callback.accept(afterStage);
        }
    }

    private static StageExecution buildExecutionWindow(StageExecution stage) {
        Boolean restrictExecutionDuringTimeWindow = (Boolean) stage.getContext()
                .getOrDefault("restrictExecutionDuringTimeWindow", false);

        if (restrictExecutionDuringTimeWindow) {
            PipelineExecution execution = stage.getPipelineExecution();
            Map<String, Object> windowContext = new HashMap<>(stage.getContext());
            Set<String> keysToRemove = Set.of(
                    "restrictExecutionDuringTimeWindow",
                    "stageTimeoutMs",
                    "alias");
            keysToRemove.forEach(windowContext::remove);

            StageExecutionImpl executionWindow = StageExecutionFactory.newStage(
                    execution,
                    RestrictExecutionDuringTimeWindow.TYPE,
                    RestrictExecutionDuringTimeWindow.TYPE,
                    windowContext,
                    stage,
                    SyntheticStageOwner.STAGE_BEFORE
            );

            executionWindow.setRefId(stage.getRefId() + "<0");
            return executionWindow;
        }
        return null;
    }

    private static void injectStage(PipelineExecution pipelineExecution, int index, StageExecution stage) {
        pipelineExecution.getStages().add(index, stage);
    }

    private static void sanitizeContext(StageExecution stage) {
        if (stage.getType() != RestrictExecutionDuringTimeWindow.TYPE) {
            Map<String, Object> context = stage.getContext();
            context.remove("restrictExecutionDuringTimeWindow");
            context.remove("restrictedExecutionWindow");
        }
    }

}
