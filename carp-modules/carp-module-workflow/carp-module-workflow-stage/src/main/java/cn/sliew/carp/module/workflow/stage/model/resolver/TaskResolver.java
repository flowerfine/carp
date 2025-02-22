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
package cn.sliew.carp.module.workflow.stage.model.resolver;

import cn.sliew.carp.module.workflow.stage.model.task.Task;

/**
 * {@code TaskResolver} allows for {@code Task} retrieval via class name or alias.
 *
 * <p>Aliases represent the previous class names of a {@code Task}.
 */
public interface TaskResolver {

    /**
     * Fetch a {@code Task} by {@code taskTypeIdentifier}.
     *
     * @param taskTypeIdentifier Task identifier (class name or alias)
     * @return the Task matching {@code taskTypeIdentifier}
     * @throws NoSuchTaskException if Task does not exist
     */
    Task getTask(String taskTypeIdentifier);

    /**
     * Fetch a {@code Task} by {@code Class type}.
     *
     * @param taskType Task type (class of task)
     * @return the Task matching {@code taskType}
     * @throws NoSuchTaskException if Task does not exist
     */
    Task getTask(Class<? extends Task> taskType);

    /**
     * @param taskTypeIdentifier Task identifier (class name or alias)
     * @return Task Class
     * @throws NoSuchTaskException if task does not exist
     */
    Class<? extends Task> getTaskClass(String taskTypeIdentifier);
}
