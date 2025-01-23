package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigurationErrorHandler implements OrcaMessageHandler<Messages.ConfigurationError> {

    private final Queue queue;
    private final ExecutionRepository repository;

    public ConfigurationErrorHandler(Queue queue, ExecutionRepository repository) {
        this.queue = queue;
        this.repository = repository;
    }

    @Override
    public Queue getQueue() {
        return queue;
    }

    @Override
    public ExecutionRepository getRepository() {
        return repository;
    }

    @Override
    public Class<Messages.ConfigurationError> getMessageType() {
        return Messages.ConfigurationError.class;
    }

    @Override
    public void handle(Messages.ConfigurationError message) {
        if (message instanceof Messages.InvalidExecutionId invalidExecution) {
            log.error("No such {} {} for {}",
                    invalidExecution.getExecutionType(),
                    invalidExecution.getExecutionId(),
                    invalidExecution.getNamespace());
        } else {
            log.error("{} for {} {} for {}",
                    message.getClass().getSimpleName(),
                    message.getExecutionType(),
                    message.getExecutionId(),
                    message.getNamespace());
            repository.updateStatus(
                    message.getExecutionType(),
                    message.getExecutionId(),
                    ExecutionStatus.TERMINAL
            );
        }
    }
}