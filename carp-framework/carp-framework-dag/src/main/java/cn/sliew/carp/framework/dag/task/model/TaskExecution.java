package cn.sliew.carp.framework.dag.task.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Map;

/**
 * The runtime execution state of a task.
 */
public interface TaskExecution {

    @Nonnull
    String getId();

    @Nonnull
    String getImplementingClass();

    @Nonnull
    String getName();

    @Nullable
    Long getStartTime();

    @Nullable
    Long getEndTime();

    @Nonnull
    ExecutionStatus getStatus();

    boolean isStageStart();

    boolean isStageEnd();

    boolean isLoopStart();

    boolean isLoopEnd();

    Map<String, Object> getTaskExceptionDetails();
}