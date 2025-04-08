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
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskResult;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class LogRedirectTask implements RetryableTask {

    @Override
    public Duration getBackoffPeriod() {
        return Duration.ofSeconds(10L);
    }

    @Override
    public Duration getTimeout() {
        return Duration.ofSeconds(300L);
    }

    @Override
    public @NotNull TaskResult execute(@NotNull StageExecution stageExecution) {
        if (RandomUtils.nextBoolean()) {
            log.info("log task execute, redirect, stage: {}, currentTask: {}, tasks: {}",
                    stageExecution.getName(), LogTask.currentTask(stageExecution.getTasks()), LogTask.mapTask(stageExecution.getTasks()));
            return TaskResult.builder(ExecutionStatus.REDIRECT)
                    .build();
        } else {
            log.info("log task execute, stage: {}, currentTask: {}, tasks: {}",
                    stageExecution.getName(), LogTask.currentTask(stageExecution.getTasks()), LogTask.mapTask(stageExecution.getTasks()));
            return TaskResult.SUCCEEDED;
        }
    }
}
