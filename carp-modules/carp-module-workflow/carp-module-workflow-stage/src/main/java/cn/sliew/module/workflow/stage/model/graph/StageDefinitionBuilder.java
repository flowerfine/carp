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
package cn.sliew.module.workflow.stage.model.graph;

import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * todo before、after、onFailure
 */
public interface StageDefinitionBuilder {

    /**
     * Returns the stage type this builder handles.
     */
    @SuppressWarnings("unchecked")
    default String getType() {
        return null;
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

    default TaskNode.TaskGraph buildTaskGraph(DagStepDTO step) {
        TaskNode.Builder graphBuilder = TaskNode.Builder(TaskNode.GraphType.FULL);
        taskGraph(step, graphBuilder);
        return graphBuilder.build();
    }

    /**
     * Implement this method to define any tasks that should run as part of this stage's workflow.
     *
     * @param step   The execution runtime of the step
     * @param builder The task graph builder
     */
    default void taskGraph(DagStepDTO step, TaskNode.Builder builder) {
    }

    /**
     * Implementations can override this if they need any special cleanup on restart.
     *
     * @param step The execution runtime of the step
     */
    default void prepareStageForRestart(DagStepDTO step) {
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
