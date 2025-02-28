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

import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecution;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.stage.model.task.RetryableTask;
import cn.sliew.carp.module.workflow.stage.model.task.SkippableTask;
import cn.sliew.carp.module.workflow.stage.model.task.TaskResult;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

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
    public TaskResult execute(WorkflowStepInstance step, TaskExecution task) {
        log.info("Workflow Step (namespace: {}, id: {}, stepId: {}, stepName: {}) log task: {} (taskId: {}-{}) execute, context: {}, currentTask: {}",
                step.getNamespace(), step.getWorkflowInstance().getId(), step.getId(), step.getNode().getStepName(),
                task.getName(), task.getId(), task.getTaskId(),
                JacksonUtil.toJsonString(step.getContext()), JacksonUtil.toJsonString(task));
        return TaskResult.builder(ExecutionStatus.SUCCEEDED)
                .context("log-task-context-" + task.getTaskId(), "log-task-context-" + task.getTaskId())
                .output("log-task-output-" + task.getTaskId(), "log-task-output-" + task.getTaskId())
                .build();
    }
}
