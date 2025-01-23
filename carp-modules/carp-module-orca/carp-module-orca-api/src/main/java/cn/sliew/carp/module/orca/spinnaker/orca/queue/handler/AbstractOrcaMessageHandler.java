package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Clock;

public abstract class AbstractOrcaMessageHandler<M extends Message> implements OrcaMessageHandler<M> {

    private final Queue queue;
    private final ExecutionRepository repository;
    protected final ApplicationEventPublisher publisher;
    protected final Clock clock;

    public AbstractOrcaMessageHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock) {
        this.queue = queue;
        this.repository = repository;
        this.publisher = publisher;
        this.clock = clock;
    }

    @Override
    public Queue getQueue() {
        return queue;
    }

    @Override
    public ExecutionRepository getRepository() {
        return repository;
    }

}
