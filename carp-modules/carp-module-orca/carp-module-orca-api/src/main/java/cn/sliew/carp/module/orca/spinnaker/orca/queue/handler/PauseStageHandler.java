package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class PauseStageHandler implements OrcaMessageHandler<Messages.PauseStage> {

    @Getter
    private final Queue queue;
    @Getter
    private final ExecutionRepository repository;

    public PauseStageHandler(Queue queue, ExecutionRepository repository) {
        this.queue = queue;
        this.repository = repository;
    }

    @Override
    public Class<Messages.PauseStage> getMessageType() {
        return Messages.PauseStage.class;
    }

    @Override
    public void handle(Messages.PauseStage message) {
        withStage(message, stage -> {
            ((StageExecutionImpl) stage).setStatus(ExecutionStatus.PAUSED);
            repository.storeStage(stage);

            Long parentStageId = stage.getParentStageId();
            if (parentStageId != null) {
                queue.push(new Messages.PauseStage(message, parentStageId));
            }
        });
    }

}
