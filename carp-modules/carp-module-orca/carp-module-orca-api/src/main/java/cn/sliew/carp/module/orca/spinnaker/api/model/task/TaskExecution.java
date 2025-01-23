package cn.sliew.carp.module.orca.spinnaker.api.model.task;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;

import java.time.Instant;
import java.util.Map;

/**
 * The runtime execution state of a task.
 */
public interface TaskExecution {

    Long getId();

    String getUuid();

    String getName();

    String getImplementingClass();

    Instant getStartTime();

    Instant getEndTime();

    ExecutionStatus getStatus();

    boolean isStageStart();

    boolean isStageEnd();

    boolean isLoopStart();

    boolean isLoopEnd();

    Map<String, Object> getTaskExceptionDetails();
}
