package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class PauseTaskHandler implements OrcaMessageHandler<Messages.PauseTask> {

    @Getter
    private final Queue queue;
    @Getter
    private final ExecutionRepository repository;

    public PauseTaskHandler(Queue queue, ExecutionRepository repository) {
        this.queue = queue;
        this.repository = repository;
    }

    @Override
    public Class<Messages.PauseTask> getMessageType() {
        return Messages.PauseTask.class;
    }

    @Override
    public void handle(Messages.PauseTask message) {
        withTask(message, (stage, task) -> {
            ((TaskExecutionImpl) task).setStatus(ExecutionStatus.PAUSED);
            repository.storeStage(stage);
            queue.push(new Messages.PauseStage(message));
        });
    }
}
