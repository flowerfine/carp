package cn.sliew.carp.module.orca.spinnaker.orca.core.events;

import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.annotation.Nonnull;

/**
 * An event emitted immediately before the initial persist of an execution.
 */
public final class BeforeInitialExecutionPersist extends ApplicationEvent {
    @Getter
    private final PipelineExecution execution;

    public BeforeInitialExecutionPersist(
            @Nonnull Object source, @Nonnull PipelineExecution execution) {
        super(source);
        this.execution = execution;
    }
}
