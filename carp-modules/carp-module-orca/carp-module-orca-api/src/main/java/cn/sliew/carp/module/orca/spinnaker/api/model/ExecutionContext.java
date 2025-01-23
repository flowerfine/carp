package cn.sliew.carp.module.orca.spinnaker.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ExecutionContext {

    private static final ThreadLocal<ExecutionContext> threadLocal = new ThreadLocal<>();

    private final String executionType;
    private final Long executionId;
    private final Long stageId;
    private final String origin;
    private final Instant stageStartTime;

    public static void set(ExecutionContext executionContext) {
        threadLocal.set(executionContext);
    }

    public static ExecutionContext get() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }
}
