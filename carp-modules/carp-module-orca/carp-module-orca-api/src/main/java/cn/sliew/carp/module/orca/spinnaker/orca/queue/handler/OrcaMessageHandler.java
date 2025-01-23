package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionNotFoundException;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.MessageHandler;
import cn.sliew.carp.module.orca.spinnaker.orca.core.exceptions.ExceptionHandler;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface OrcaMessageHandler<M extends Message> extends MessageHandler<M> {

    int MIN_PAGE_SIZE = 2;

    default Logger getLog() {
        return LoggerFactory.getLogger(getClass());
    }

    ExecutionRepository getRepository();

    default ExceptionHandler.Response shouldRetry(Collection<ExceptionHandler> exceptionHandlers,
                                                  Exception ex,
                                                  String taskName) {
        return exceptionHandlers.stream()
                .filter(handler -> handler.handles(ex))
                .findFirst()
                .map(handler -> handler.handle(taskName != null ? taskName : "unspecified", ex))
                .orElse(null);
    }


    default void withTask(Messages.TaskLevel taskLevel, BiConsumer<StageExecution, TaskExecution> block) {
        withStage(taskLevel, stage -> {
            TaskExecution task = stage.taskById(taskLevel.getTaskId());
            if (task == null) {
                getLog().error("InvalidTaskId: Unable to find task {} in stage '{}' while processing message {}",
                        taskLevel.getTaskId(),
                        JacksonUtil.toJsonString(stage),
                        taskLevel);
                getQueue().push(new Messages.InvalidTaskId(taskLevel));
            } else {
                block.accept(stage, task);
            }
        });
    }

    default void withStage(Messages.StageLevel stageLevel, Consumer<StageExecution> block) {
        withExecution(stageLevel, execution -> {
            try {
                StageExecution stage = execution.stageById(stageLevel.getStageId());
                // 必要的上下文处理，例如移除 refId 和 requisiteRefIds
                new StageExecutionImpl(execution, stage.getType(), stage.getContext());
                block.accept(stage);
            } catch (IllegalArgumentException e) {
                getLog().error("Failed to locate stage with id: {}", stageLevel.getStageId(), e);
                getQueue().push(new Messages.InvalidStageId(stageLevel));
            }
        });
    }

    default void withExecution(Messages.ExecutionLevel executionLevel, Consumer<PipelineExecution> block) {
        try {
            PipelineExecution execution = getRepository().retrieve(
                    executionLevel.getExecutionType(),
                    executionLevel.getExecutionId()
            );
            block.accept(execution);
        } catch (ExecutionNotFoundException e) {
            getQueue().push(new Messages.InvalidExecutionId(executionLevel));
        }
    }


    default void startNext(StageExecution stage) {
        PipelineExecution execution = stage.getPipelineExecution();
        List<StageExecution> downstreamStages = stage.downstreamStages();
        SyntheticStageOwner phase = stage.getSyntheticStageOwner();

        if (CollectionUtils.isNotEmpty(downstreamStages)) {
            downstreamStages.forEach(s ->
                    getQueue().push(new Messages.StartStage(s))
            );
        } else if (phase != null) {
            getQueue().ensure(
                    new Messages.ContinueParentStage(stage.getParent(), phase),
                    Duration.ZERO
            );
        } else {
            getQueue().push(new Messages.CompleteExecution(execution));
        }
    }


    default boolean shouldQueue(PipelineExecution execution) {
        return false;
//        String configId = execution.getPipelineConfigId();
//        if (Objects.isNull(configId)) {
//            return false;
//        }
//
//        if (!execution.isLimitConcurrent()) {
//            if (execution.getMaxConcurrentExecutions() > 0) {
//                ExecutionRepository.ExecutionCriteria criteria = new ExecutionRepository.ExecutionCriteria()
//                        .setPageSize(execution.getMaxConcurrentExecutions() + MIN_PAGE_SIZE)
//                        .setStatuses(ExecutionStatus.RUNNING);

//                return getRepository()
//                        .retrievePipelinesForPipelineConfigId(configId, criteria)
//                        .filter(e -> !e.getId().equals(execution.getId()))
//                        .count()
//                        .toBlocking()
//                        .first() >= execution.getMaxConcurrentExecutions();
//                return false;
//            }
//            return false;
//        } else {
//            ExecutionRepository.ExecutionCriteria criteria = new ExecutionRepository.ExecutionCriteria()
//                    .setPageSize(MIN_PAGE_SIZE)
//                    .setStatuses(ExecutionStatus.RUNNING);

//            return getRepository()
//                    .retrievePipelinesForPipelineConfigId(configId, criteria)
//                    .filter(e -> !e.getId().equals(execution.getId()))
//                    .count()
//                    .toBlocking()
//                    .first() > 0;
//            return false;
//        }
    }

}
