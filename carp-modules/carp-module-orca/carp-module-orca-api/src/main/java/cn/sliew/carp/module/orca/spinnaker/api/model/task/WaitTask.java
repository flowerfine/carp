package cn.sliew.carp.module.orca.spinnaker.api.model.task;

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.WaitStage;
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
    public @Nonnull TaskResult execute(@Nonnull StageExecution stage) {
        WaitStage.WaitStageContext context = stage.mapTo(WaitStage.WaitStageContext.class);

        Instant now = clock.instant();

        if (context.isSkipRemainingWait()) {
            return TaskResult.SUCCEEDED;
        } else if (stage.getStartTime() != null
                && stage.getStartTime()
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
    public Duration getDynamicBackoffPeriod(StageExecution stage, Duration taskDuration) {
        WaitStage.WaitStageContext context = stage.mapTo(WaitStage.WaitStageContext.class);

        if (context.isSkipRemainingWait()) {
            return Duration.ZERO;
        }

        // Return a backoff time that reflects the requested waitTime
        if (stage.getStartTime() != null) {
            Instant now = clock.instant();
            Instant completion =
                    stage.getStartTime().plus(context.getWaitDuration());

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
