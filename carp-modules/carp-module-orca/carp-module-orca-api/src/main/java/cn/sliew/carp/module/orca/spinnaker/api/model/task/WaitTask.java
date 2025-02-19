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
package cn.sliew.carp.module.orca.spinnaker.api.model.task;

import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.WaitStage;
import cn.sliew.milky.common.util.JacksonUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Component
public class WaitTask implements RetryableTask {

    private final Clock clock;

    public WaitTask(Clock clock) {
        this.clock = clock;
    }

    @Override
    public @Nonnull TaskResult execute(@Nonnull DagStepDTO stage) {
        WaitStage.WaitStageContext context = JacksonUtil.toObject(stage.getInputs(), WaitStage.WaitStageContext.class);

        Instant now = clock.instant();

        if (context.isSkipRemainingWait()) {
            return TaskResult.SUCCEEDED;
        } else if (stage.getStartTime() != null
                && stage.getStartTime().toInstant()
                .plus(context.getWaitDuration())
                .isBefore(now)) {
            return TaskResult.SUCCEEDED;
        } else {
            return TaskResult.RUNNING;
        }
    }

    @Override
    public Duration getBackoffPeriod() {
        return Duration.ofSeconds(1L);
    }

    @Override
    public Duration getDynamicBackoffPeriod(DagStepDTO stage, Duration taskDuration) {
        WaitStage.WaitStageContext context = JacksonUtil.toObject(stage.getInputs(), WaitStage.WaitStageContext.class);

        if (context.isSkipRemainingWait()) {
            return Duration.ZERO;
        }

        // Return a backoff time that reflects the requested waitTime
        if (stage.getStartTime() != null) {
            Instant now = clock.instant();
            Instant completion =
                    stage.getStartTime().toInstant().plus(context.getWaitDuration());

            if (completion.isAfter(now)) {
                return Duration.between(completion, now);
            }
        }

        return getBackoffPeriod();
    }

    @Override
    public Duration getTimeout() {
        return Duration.ofMillis(Long.MAX_VALUE);
    }
}
