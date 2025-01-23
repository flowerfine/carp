package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.pending.PendingExecutionService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartWaitingExecutionsHandler implements OrcaMessageHandler<Messages.StartWaitingExecutions> {

    @Getter
    private final Queue queue;
    @Getter
    private final ExecutionRepository repository;
    private final PendingExecutionService pendingExecutionService;

    public StartWaitingExecutionsHandler(
            Queue queue,
            ExecutionRepository repository,
            PendingExecutionService pendingExecutionService) {
        this.queue = queue;
        this.repository = repository;
        this.pendingExecutionService = pendingExecutionService;
    }

    @Override
    public Class<Messages.StartWaitingExecutions> getMessageType() {
        return Messages.StartWaitingExecutions.class;
    }

    @Override
    public void handle(Messages.StartWaitingExecutions message) {
        if (message.getPurgeQueue()) {
            // 当清除队列时,运行最新的消息并丢弃其余的
            Message newestMessage = pendingExecutionService.popNewest(message.getPipelineConfigId());
            if (newestMessage != null) {
                pendingExecutionService.purge(message.getPipelineConfigId(), purgedMessage -> {
                    if (purgedMessage instanceof Messages.StartExecution) {
                        Messages.StartExecution startExecution = (Messages.StartExecution) purgedMessage;
                        log.info("Dropping queued pipeline {} {}",
                                startExecution.getNamespace(),
                                startExecution.getExecutionId());

                        queue.push(new Messages.CancelExecution(
                                startExecution,
                                "spinnaker",
                                "This execution was queued but then canceled because a newer queued execution superceded it. " +
                                        "This pipeline is configured to automatically cancel waiting executions."
                        ));
                    } else if (purgedMessage instanceof Messages.RestartStage) {
                        Messages.RestartStage restartStage = (Messages.RestartStage) purgedMessage;
                        log.info("Cancelling restart of {} {}",
                                restartStage.getNamespace(),
                                restartStage.getExecutionId());
                        // 不需要做其他事情
                    }
                });
            }
        } else {
            // 当不清除队列时,按照消息进入的顺序运行
            Message oldestMessage = pendingExecutionService.popOldest(message.getPipelineConfigId());
            if (oldestMessage != null) {
                if (oldestMessage instanceof Messages.ExecutionLevel) {
                    Messages.ExecutionLevel executionLevel = (Messages.ExecutionLevel) oldestMessage;
                    log.info("Starting queued pipeline {} {}",
                            executionLevel.getNamespace(),
                            executionLevel.getExecutionId());
                }
                queue.push(oldestMessage);
            }
        }
    }

}