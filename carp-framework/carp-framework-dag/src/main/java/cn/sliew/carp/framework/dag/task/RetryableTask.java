package cn.sliew.carp.framework.dag.task;

import cn.sliew.carp.framework.dag.task.model.StageExecution;

import java.time.Duration;

public interface RetryableTask extends Task{

    long getBackoffPeriod();

    long getTimeout();

    default long getDynamicTimeout(StageExecution stage) {
        return getTimeout();
    }

    default long getDynamicBackoffPeriod(Duration taskDuration) {
        return getBackoffPeriod();
    }

    default long getDynamicBackoffPeriod(StageExecution stage, Duration taskDuration) {
        return getDynamicBackoffPeriod(taskDuration);
    }
}
