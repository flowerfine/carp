package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class ResumeStageHandler implements OrcaMessageHandler<Messages.ResumeStage> {

    @Getter
    private final Queue queue;
    @Getter
    private final ExecutionRepository repository;

    public ResumeStageHandler(Queue queue, ExecutionRepository repository) {
        this.queue = queue;
        this.repository = repository;
    }

    @Override
    public Class<Messages.ResumeStage> getMessageType() {
        return Messages.ResumeStage.class;
    }

    @Override
    public void handle(Messages.ResumeStage message) {
        withStage(message, stage -> {
            ((StageExecutionImpl) stage).setStatus(ExecutionStatus.RUNNING);
            repository.storeStage(stage);

            stage.getTasks().stream()
                    .filter(task -> task.getStatus() == ExecutionStatus.PAUSED)
                    .forEach(task -> queue.push(new Messages.ResumeTask(message, task.getId())));
        });
    }
}
