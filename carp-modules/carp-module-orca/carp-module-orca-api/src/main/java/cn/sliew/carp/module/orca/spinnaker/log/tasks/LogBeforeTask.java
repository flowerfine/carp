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

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.RetryableTask;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskResult;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class LogBeforeTask implements RetryableTask {

    @Override
    public Duration getBackoffPeriod() {
        return Duration.ofSeconds(10L);
    }

    @Override
    public Duration getTimeout() {
        return Duration.ofMinutes(5L);
    }

    @Override
    public @NotNull TaskResult execute(@NotNull StageExecution stageExecution) {
        log.info("log before task execute");
        return TaskResult.SUCCEEDED;
    }
}
