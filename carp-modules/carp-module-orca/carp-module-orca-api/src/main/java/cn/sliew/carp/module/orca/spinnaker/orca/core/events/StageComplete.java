package cn.sliew.carp.module.orca.spinnaker.orca.core.events;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import lombok.Getter;

import javax.annotation.Nonnull;

public final class StageComplete extends ExecutionEvent {
    @Getter
    private final StageExecution stage;

    public StageComplete(@Nonnull Object source, @Nonnull StageExecution stage) {
        super(source, stage.getPipelineExecution().getType(), stage.getPipelineExecution().getId());

        this.stage = stage;
    }

    public @Nonnull Long getStageId() {
        return stage.getId();
    }

    public @Nonnull String getStageType() {
        return stage.getType();
    }

    public @Nonnull ExecutionStatus getStatus() {
        return stage.getStatus();
    }
}
