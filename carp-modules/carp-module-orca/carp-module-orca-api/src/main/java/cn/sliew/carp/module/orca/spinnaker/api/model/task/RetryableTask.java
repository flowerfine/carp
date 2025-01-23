package cn.sliew.carp.module.orca.spinnaker.api.model.task;

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;

import java.time.Duration;

/**
 * A retryable task defines its backoff period (the period between delays) and its timeout (the
 * total period of the task)
 */
public interface RetryableTask extends Task {

    Duration getBackoffPeriod();

    Duration getTimeout();

    default Duration getDynamicTimeout(StageExecution stage) {
        return getTimeout();
    }

    default Duration getDynamicBackoffPeriod(Duration taskDuration) {
        return getBackoffPeriod();
    }

    default Duration getDynamicBackoffPeriod(StageExecution stage, Duration taskDuration) {
        return getDynamicBackoffPeriod(taskDuration);
    }
}
