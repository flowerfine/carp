package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class ResumeExecutionHandler implements OrcaMessageHandler<Messages.ResumeExecution> {

    @Getter
    private final Queue queue;
    @Getter
    private final ExecutionRepository repository;

    public ResumeExecutionHandler(Queue queue, ExecutionRepository repository) {
        this.queue = queue;
        this.repository = repository;
    }

    @Override
    public Class<Messages.ResumeExecution> getMessageType() {
        return Messages.ResumeExecution.class;
    }

    @Override
    public void handle(Messages.ResumeExecution message) {
        withExecution(message, execution -> {
            execution.getStages().stream()
                .filter(stage -> stage.getStatus() == ExecutionStatus.PAUSED)
                .forEach(stage -> queue.push(new Messages.ResumeStage(message, stage.getId())));
        });
    }
}
