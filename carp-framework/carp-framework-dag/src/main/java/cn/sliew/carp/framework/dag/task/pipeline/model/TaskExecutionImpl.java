package cn.sliew.carp.framework.dag.task.pipeline.model;

import cn.sliew.carp.framework.dag.task.model.ExecutionStatus;
import cn.sliew.carp.framework.dag.task.model.TaskExecution;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Map;

public class TaskExecutionImpl implements TaskExecution {

    @Nonnull
    @Override
    public String getId() {
        return "";
    }

    @Nonnull
    @Override
    public String getImplementingClass() {
        return "";
    }

    @Nonnull
    @Override
    public String getName() {
        return "";
    }

    @Nullable
    @Override
    public Long getStartTime() {
        return 0;
    }

    @Nullable
    @Override
    public Long getEndTime() {
        return 0;
    }

    @Nonnull
    @Override
    public ExecutionStatus getStatus() {
        return null;
    }

    @Override
    public boolean isStageStart() {
        return false;
    }

    @Override
    public boolean isStageEnd() {
        return false;
    }

    @Override
    public boolean isLoopStart() {
        return false;
    }

    @Override
    public boolean isLoopEnd() {
        return false;
    }

    @Override
    public Map<String, Object> getTaskExceptionDetails() {
        return Map.of();
    }
}
