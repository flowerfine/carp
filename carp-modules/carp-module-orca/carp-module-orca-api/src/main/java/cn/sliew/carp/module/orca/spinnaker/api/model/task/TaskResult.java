package cn.sliew.carp.module.orca.spinnaker.api.model.task;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Map;

/**
 * Represents the state of a {@link TaskExecution}.
 */
@Data
@Builder
@SuppressWarnings("FallThrough")
public final class TaskResult {

    public static final TaskResult SUCCEEDED = TaskResult.ofStatus(ExecutionStatus.SUCCEEDED);
    public static final TaskResult RUNNING = TaskResult.ofStatus(ExecutionStatus.RUNNING);
    public static final TaskResult TERMINAL = TaskResult.ofStatus(ExecutionStatus.TERMINAL);

    private final ExecutionStatus status;

    /**
     * Stage-scoped data.
     */
    @Singular("context")
    private final Map<String, ?> context;

    /**
     * Pipeline-scoped data.
     */
    @Singular("output")
    private final Map<String, ?> outputs;

    public static TaskResult ofStatus(ExecutionStatus status) {
        return TaskResult.builder(status).build();
    }

    public static TaskResultBuilder builder(ExecutionStatus status) {
        return new TaskResultBuilder().status(status);
    }
}
