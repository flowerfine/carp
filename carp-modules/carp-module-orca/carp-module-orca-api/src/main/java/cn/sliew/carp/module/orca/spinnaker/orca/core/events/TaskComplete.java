package cn.sliew.carp.module.orca.spinnaker.orca.core.events;

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import lombok.Getter;

import javax.annotation.Nonnull;

public class TaskComplete extends ExecutionEvent {
    @Getter
    private final StageExecution stage;
    @Getter
    private final TaskExecution task;

    public TaskComplete(
            @Nonnull Object source, @Nonnull StageExecution stage, @Nonnull TaskExecution task) {
        super(source, stage.getPipelineExecution().getType(), stage.getPipelineExecution().getId());

        this.stage = stage;
        this.task = task;
    }
}
