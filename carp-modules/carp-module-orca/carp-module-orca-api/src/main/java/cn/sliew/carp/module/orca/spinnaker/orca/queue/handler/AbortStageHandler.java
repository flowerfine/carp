package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.core.events.StageComplete;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.EnumSet;
import java.util.Set;

@Component
public class AbortStageHandler extends AbstractOrcaMessageHandler<Messages.AbortStage> {

    private static final Set<ExecutionStatus> RUNNING_STATUSES =
            EnumSet.of(ExecutionStatus.RUNNING, ExecutionStatus.NOT_STARTED);

    public AbortStageHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock) {
        super(queue, repository, publisher, clock);
    }

    @Override
    public Class<Messages.AbortStage> getMessageType() {
        return Messages.AbortStage.class;
    }

    @Override
    public void handle(Messages.AbortStage message) {
        withStage(message, stage -> {
            if (RUNNING_STATUSES.contains(stage.getStatus())) {
                StageExecutionImpl stageImpl = (StageExecutionImpl) stage;
                stageImpl.setStatus(ExecutionStatus.TERMINAL);
                stageImpl.setEndTime(Instant.now(clock));
                getRepository().storeStage(stage);

                getQueue().push(new Messages.CancelStage(message));

                if (stage.getParentStageId() == null) {
                    getQueue().push(new Messages.CompleteExecution(message));
                } else {
                    getQueue().push(new Messages.CompleteStage(stage.getParent()));
                }

                publisher.publishEvent(new StageComplete(this, stage));
            }
        });
    }

}
