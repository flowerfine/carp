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
package cn.sliew.carp.module.workflow.stage.internal.log;

import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.task.*;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LogStepTask implements RetryableTask, SkippableTask {

    @Override
    public Duration getBackoffPeriod() {
        return Duration.ofSeconds(15L);
    }

    @Override
    public Duration getTimeout() {
        return Duration.ofMinutes(1L);
    }

    @Override
    public TaskResult execute(DagStepDTO step, TaskExecution task) {
        log.info("Dag Step (namespace: {}, id: {}, stepId: {}, stepName: {}) log task: {} (taskId: {}) execute, currentTask: {}, tasks: {}",
                step.getNamespace(), step.getDagInstance().getId(), step.getId(), step.getDagConfigStep().getStepName(),
                task.getName(), task.getId(), JacksonUtil.toJsonString(task), JacksonUtil.toJsonString(mapTask(getTasks(step))));
        return TaskResult.builder(ExecutionStatus.SUCCEEDED)
                .output("log-task-1", "log-task-1")
                .output("log-task-2", "log-task-2")
                .build();
    }

    public static List<TaskExecutionImpl> getTasks(DagStepDTO step) {
        if (Objects.isNull(step.getBody()) || step.getBody().isNull()) {
            return Collections.emptyList();
        }

        return JacksonUtil.toObject(step.getBody().path("tasks"), new TypeReference<List<TaskExecutionImpl>>() {
        });
    }

    public static List<Map> mapTask(List<TaskExecutionImpl> tasks) {
        return tasks.stream().map(task -> {
            return Map.of(
                    "id", task.getId(),
                    "name", task.getName(),
                    "status", task.getStatus(),
                    "stageStart", task.isStageStart(),
                    "stageEnd", task.isStageEnd(),
                    "loopStart", task.isLoopStart(),
                    "loopEnd", task.isLoopEnd()
            );
        }).collect(Collectors.toUnmodifiableList());
    }

    public static Map currentTask(List<TaskExecutionImpl> tasks) {
        return tasks.stream().filter(task -> task.getStatus() == ExecutionStatus.RUNNING)
                .findFirst()
                .map(task -> {
                    return Map.of(
                            "id", task.getId(),
                            "name", task.getName(),
                            "status", task.getStatus(),
                            "stageStart", task.isStageStart(),
                            "stageEnd", task.isStageEnd(),
                            "loopStart", task.isLoopStart(),
                            "loopEnd", task.isLoopEnd()
                    );
                }).orElse(null);
    }
}
