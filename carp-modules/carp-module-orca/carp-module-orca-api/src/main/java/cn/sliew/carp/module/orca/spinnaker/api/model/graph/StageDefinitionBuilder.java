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
package cn.sliew.carp.module.orca.spinnaker.api.model.graph;

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Provides a low-level API for building stages.
 *
 * <p>A stage in its simplest form will consist of a series of Tasks that are executed serially.
 * However, a stage can also configure other stages to be run before it, or after it, and or on
 * failure only. This can enable you to build stages which compose and abstract the details of other
 * stages.
 */
public interface StageDefinitionBuilder {

    default TaskNode.TaskGraph buildTaskGraph(StageExecution stage) {
        TaskNode.Builder graphBuilder = TaskNode.Builder(TaskNode.GraphType.FULL);
        taskGraph(stage, graphBuilder);
        return graphBuilder.build();
    }

    /**
     * Implement this method to define any tasks that should run as part of this stage's workflow.
     *
     * @param stage   The execution runtime of the stage
     * @param builder The task graph builder
     */
    default void taskGraph(StageExecution stage, TaskNode.Builder builder) {
    }

    /**
     * Implement this method to define any stages that should run before any tasks in this stage as
     * part of a composed workflow.
     *
     * @param parent The execution runtime of the stage (which is the parent of any stages created
     *               herein)
     * @param graph  The stage graph builder
     */
    default void beforeStages(StageExecution parent, StageGraphBuilder graph) {
    }

    /**
     * Implement this method to define any stages that should run after any tasks in this stage as
     * part of a composed workflow.
     *
     * @param parent The execution runtime of the stage (which is the parent of any stages created
     *               herein)
     * @param graph  The stage graph builder
     */
    default void afterStages(StageExecution parent, StageGraphBuilder graph) {
    }

    /**
     * Implement this method to define any stages that should run in response to a failure in tasks,
     * before or after stages.
     *
     * @param stage The execution runtime of the stage
     * @param graph The stage graph builder
     */
    default void onFailureStages(StageExecution stage, StageGraphBuilder graph) {
    }

    /**
     * Returns the stage type this builder handles.
     */
    @SuppressWarnings("unchecked")
    default String getType() {
        return null;
    }

    /**
     * Implementations can override this if they need any special cleanup on restart.
     *
     * @param stage The execution runtime of the stage
     */
    default void prepareStageForRestart(StageExecution stage) {
    }

    /**
     * Get the pipeline configuration-friendly type name for this stage.
     *
     * <p>If a stage class is {@code MyFancyStage}, the resulting type would be {@code myFancy}.
     *
     * @param clazz The stage definition builder class
     */
    static String getType(Class<? extends StageDefinitionBuilder> clazz) {
        String className = clazz.getSimpleName();
        if (className.equals("")) {
            throw new IllegalStateException(
                    "StageDefinitionBuilder.getType() cannot be called on an anonymous type");
        }
        return className.substring(0, 1).toLowerCase()
                + className
                .substring(1)
                .replaceFirst("StageDefinitionBuilder$", "")
                .replaceFirst("Stage$", "");
    }

    /**
     * Return true if the stage can be manually skipped from the API.
     */
    default boolean canManuallySkip(StageExecution stage) {
        return false;
    }

    /**
     * A collection of known aliases.
     */
    default Collection<String> aliases() {
        if (this.getClass().isAnnotationPresent(Aliases.class)) {
            return Arrays.asList(getClass().getAnnotation(Aliases.class).value());
        }

        return Collections.emptyList();
    }

    /**
     * Allows backwards compatibility of a stage's "type", even through class renames / refactors.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Aliases {
        String[] value() default {};
    }
}
