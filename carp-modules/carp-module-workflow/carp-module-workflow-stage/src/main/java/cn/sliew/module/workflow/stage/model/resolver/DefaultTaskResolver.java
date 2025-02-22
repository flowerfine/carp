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
package cn.sliew.module.workflow.stage.model.resolver;

import cn.sliew.module.workflow.stage.model.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DefaultTaskResolver implements TaskResolver {

    private final ConcurrentHashMap<String, Task> taskByAlias = new ConcurrentHashMap<>();
    private final ObjectProvider<Collection<Task>> tasksProvider;
    private final boolean allowFallback;

    public DefaultTaskResolver(ObjectProvider<Collection<Task>> tasksProvider) {
        this(tasksProvider, true);
    }

    public DefaultTaskResolver(ObjectProvider<Collection<Task>> tasksProvider, boolean allowFallback) {
        this.tasksProvider = tasksProvider;
        this.allowFallback = allowFallback;
        var tasks = tasksProvider.getIfAvailable(ArrayList::new);
        for (Task task : tasks) {
            taskByAlias.put(task.getExtensionClass().getCanonicalName(), task);
            addAliases(task);
        }
    }

    @Override
    public Task getTask(String taskTypeIdentifier) {
        Task task = taskByAlias.get(taskTypeIdentifier);

        if (task == null) {
            log.debug(
                    "{} task not found in task cache, fetching missing tasks from task provider.",
                    taskTypeIdentifier);
            addMissingTasksFromTaskProvider();
            task = taskByAlias.get(taskTypeIdentifier);
            if (task == null) {
                throw new NoSuchTaskException(taskTypeIdentifier);
            }
        }

        return task;
    }

    @Override
    public Task getTask(Class<? extends Task> taskType) {
        Optional<Task> optionalTask =
                taskByAlias.values().stream()
                        .filter((Task task) -> taskType.isAssignableFrom(task.getExtensionClass()))
                        .findFirst();

        if (!optionalTask.isPresent()) {
            log.debug(
                    "{} task not found in task cache, fetching missing tasks from task provider.",
                    taskType);
            addMissingTasksFromTaskProvider();
            return taskByAlias.values().stream()
                    .filter((Task task) -> taskType.isAssignableFrom(task.getExtensionClass()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchTaskException(taskType.getCanonicalName()));
        }

        return optionalTask.get();
    }

    @Override
    public Class<? extends Task> getTaskClass(String taskTypeIdentifier) {
        try {
            Task task = getTask(taskTypeIdentifier);
            return (Class<? extends Task>) task.getExtensionClass();
        } catch (IllegalArgumentException e) {
            if (!allowFallback) {
                throw e;
            }

            try {
                return (Class<? extends Task>) Class.forName(taskTypeIdentifier);
            } catch (ClassNotFoundException ex) {
                throw e;
            }
        }
    }


    /**
     * Add task aliases to taskByAlias map, throwing {@link DuplicateTaskAliasException} if duplicates
     * are detected.
     */
    private void addAliases(Task task) {
        for (String alias : task.aliases()) {
            if (taskByAlias.containsKey(alias)) {
                throw new DuplicateTaskAliasException(
                        String.format(
                                "Duplicate task alias detected (alias: %s, previous: %s, current: %s)",
                                alias,
                                taskByAlias.get(alias).getExtensionClass().getCanonicalName(),
                                task.getExtensionClass().getCanonicalName()));
            }

            taskByAlias.put(alias, task);
        }
    }

    /**
     * Lookup tasks from the task provider and add missing tasks to taskByAlias cache.
     */
    private void addMissingTasksFromTaskProvider() {
        for (Task task : tasksProvider.getIfAvailable(ArrayList::new)) {
            if (taskByAlias.get(task.getExtensionClass().getCanonicalName()) == null) {
                taskByAlias.put(task.getExtensionClass().getCanonicalName(), task);
                addAliases(task);
                log.info(
                        "{} task resolved from task provider and added to cache",
                        task.getExtensionClass().toString());
            }
        }
    }
}
