package cn.sliew.carp.module.orca.spinnaker.orca.core.events;

import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import lombok.Getter;

import javax.annotation.Nonnull;

public final class ExecutionStarted extends ExecutionEvent {
    @Getter
    private final PipelineExecution execution;

    public ExecutionStarted(@Nonnull Object source, @Nonnull PipelineExecution execution) {
        super(source, execution.getType(), execution.getId());
        this.execution = execution;
    }
}
