package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.util.StageExecutionUtil;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;

@Slf4j
@Component
public class ContinueParentStageHandler extends AbstractOrcaMessageHandler<Messages.ContinueParentStage> {

    private final Duration retryDelay;

    public ContinueParentStageHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock,
            @Value("${queue.retry.delay.ms:5000}") Long retryDelayMs) {
        super(queue, repository, publisher, clock);
        this.retryDelay = Duration.ofMillis(retryDelayMs);
    }

    @Override
    public Class<Messages.ContinueParentStage> getMessageType() {
        return Messages.ContinueParentStage.class;
    }

    @Override
    public void handle(Messages.ContinueParentStage message) {
        withStage(message, stage -> {
            if (message.getPhase() == SyntheticStageOwner.STAGE_BEFORE) {
                if (StageExecutionUtil.allBeforeStagesSuccessful(stage)) {
                    if (hasTasks(stage)) {
                        runFirstTask(stage);
                    } else {
                        getQueue().push(new Messages.CompleteStage(stage));
                    }
                } else if (!StageExecutionUtil.anyBeforeStagesFailed(stage)) {
                    log.info("Re-queuing {} as other {} stages are still running", message, message.getPhase());
                    getQueue().push(message, retryDelay);
                }
            } else {
                if (StageExecutionUtil.allAfterStagesComplete(stage)) {
                    getQueue().push(new Messages.CompleteStage(stage));
                } else {
                    log.info("Re-queuing {} as other {} stages are still running", message, message.getPhase());
                    getQueue().push(message, retryDelay);
                }
            }
        });
    }


    private void runFirstTask(StageExecution stage) {
        TaskExecution firstTask = stage.getTasks().get(0);
        if (firstTask.getStatus() == ExecutionStatus.NOT_STARTED) {
            getQueue().push(new Messages.StartTask(stage, firstTask));
        } else {
            log.warn("Ignoring {} for {} as tasks are already running", getMessageType(), stage.getId());
        }
    }

    private boolean hasTasks(StageExecution stage) {
        return CollectionUtils.isNotEmpty(stage.getTasks());
    }
}
