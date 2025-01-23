package cn.sliew.carp.module.orca.spinnaker.orca.core.events;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.annotation.Nonnull;
import java.time.Instant;

/**
 * Events emitted at various stages in the lifecycle of an execution.
 */
@Getter
public abstract class ExecutionEvent extends ApplicationEvent {

    private final ExecutionType executionType;
    private final Long executionId;

    protected ExecutionEvent(
            @Nonnull Object source, @Nonnull ExecutionType executionType, @Nonnull Long executionId) {
        super(source);
        this.executionType = executionType;
        this.executionId = executionId;
    }

    public final @Nonnull Instant timestamp() {
        return Instant.ofEpochMilli(super.getTimestamp());
    }
}
