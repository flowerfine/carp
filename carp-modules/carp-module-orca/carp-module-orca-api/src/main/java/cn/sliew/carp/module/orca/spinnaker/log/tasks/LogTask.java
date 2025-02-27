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
package cn.sliew.carp.module.orca.spinnaker.log.tasks;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.RetryableTask;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskResult;
import cn.sliew.milky.common.util.JacksonUtil;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LogTask implements RetryableTask {

    @Override
    public Duration getBackoffPeriod() {
        return Duration.ofSeconds(10L);
    }

    @Override
    public Duration getTimeout() {
        return Duration.ofMinutes(5L);
    }

    /**
     * 获取之前节点的输出
     */
    @Override
    public @NotNull TaskResult execute(@NotNull StageExecution stageExecution) {
        log.info("log task execute, stage: {}, context: {}, currentTask: {}, tasks: {}",
                stageExecution.getName(), JacksonUtil.toJsonString(stageExecution.getContext()),
                JacksonUtil.toJsonString(currentTask(stageExecution.getTasks())), mapTask(stageExecution.getTasks()));
        Map<String, Object> context = new HashMap<>();
        context.put("log-task-context", "log-task-context");
        return TaskResult.builder(ExecutionStatus.SUCCEEDED)
                .output("log-task-output", "log-task-output-data")
                .context(context)
                .build();
    }


    public static List<Map> mapTask(List<TaskExecution> tasks) {
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

    public static Map currentTask(List<TaskExecution> tasks) {
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
