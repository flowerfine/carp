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
package cn.sliew.carp.module.workflow.stage.internal.wait;

import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecution;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.stage.model.task.RetryableTask;
import cn.sliew.carp.module.workflow.stage.model.task.TaskResult;
import cn.sliew.milky.common.util.JacksonUtil;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;

@Component
public class WaitUntilTask implements RetryableTask {

    @Override
    public Duration getTimeout() {
        return Duration.ofMillis(Long.MAX_VALUE);
    }

    @Override
    public Duration getBackoffPeriod() {
        return Duration.ofSeconds(1L);
    }

    @Override
    public Duration getDynamicBackoffPeriod(WorkflowStepInstance step, Duration taskDuration) {
        WaitUntilStepContext context =
                JacksonUtil.toObject(JacksonUtil.toJsonNode(step.getContext()), WaitUntilStepContext.class);

        // Return a backoff time that reflects the requested target time.
        if (context.getStartTime() != null && context.getEpochMillis() != null) {
            Instant now = Instant.now();
            Instant completion = Instant.ofEpochMilli(context.getEpochMillis());
            if (completion.isAfter(now)) {
                return Duration.between(completion, now);
            }
        }
        return getBackoffPeriod();
    }

    @Override
    public TaskResult execute(WorkflowStepInstance step, TaskExecution task) {
        WaitUntilStepContext context =
                JacksonUtil.toObject(JacksonUtil.toJsonNode(step.getContext()), WaitUntilStepContext.class);

        if (context.getEpochMillis() == null) {
            return TaskResult.SUCCEEDED;
        }

        Instant now = Instant.now();

        if (context.getStartTime() == null || context.getStartTime() == Instant.EPOCH) {
            return TaskResult.builder(ExecutionStatus.RUNNING).context(Collections.singletonMap("startTime", now)).build();
        } else if (context.getEpochMillis() <= now.toEpochMilli()) {
            return TaskResult.SUCCEEDED;
        } else {
            return TaskResult.RUNNING;
        }
    }
}
