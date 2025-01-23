package cn.sliew.carp.module.orca.spinnaker.orca.core.events;

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import lombok.Getter;

import javax.annotation.Nonnull;

public final class StageStarted extends ExecutionEvent {
    @Getter
    private final StageExecution stage;

    public StageStarted(@Nonnull Object source, @Nonnull StageExecution stage) {
        super(source, stage.getPipelineExecution().getType(), stage.getPipelineExecution().getId());

        this.stage = stage;
    }
}
