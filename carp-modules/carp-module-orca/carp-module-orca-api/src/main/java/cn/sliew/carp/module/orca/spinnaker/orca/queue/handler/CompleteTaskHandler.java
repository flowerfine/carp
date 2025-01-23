package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.util.StageExecutionUtil;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageDefinitionBuilderFactory;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.core.events.TaskComplete;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.util.ContextParameterProcessor;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.List;

@Slf4j
@Component
public class CompleteTaskHandler extends AbstractOrcaMessageHandler<Messages.CompleteTask> implements ExpressionAware {

    private final StageDefinitionBuilderFactory stageDefinitionBuilderFactory;
    private final ContextParameterProcessor contextParameterProcessor;

    public CompleteTaskHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock,
            StageDefinitionBuilderFactory stageDefinitionBuilderFactory,
            ContextParameterProcessor contextParameterProcessor) {
        super(queue, repository, publisher, clock);
        this.stageDefinitionBuilderFactory = stageDefinitionBuilderFactory;
        this.contextParameterProcessor = contextParameterProcessor;
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public Class<Messages.CompleteTask> getMessageType() {
        return Messages.CompleteTask.class;
    }

    @Override
    public StageDefinitionBuilderFactory getStageDefinitionBuilderFactory() {
        return stageDefinitionBuilderFactory;
    }

    @Override
    public ContextParameterProcessor getContextParameterProcessor() {
        return contextParameterProcessor;
    }

    @Override
    public void handle(Messages.CompleteTask message) {
        withTask(message, (stage, task) -> {
            TaskExecutionImpl taskImpl = (TaskExecutionImpl) task;
            taskImpl.setStatus(message.getStatus());
            taskImpl.setEndTime(clock.instant());
            StageExecution mergedContextStage = withMergedContext(stage);

            if (message.getStatus() == ExecutionStatus.REDIRECT) {
                handleRedirect(mergedContextStage);
            } else {
                getRepository().storeStage(mergedContextStage);

                if (StageExecutionUtil.isManuallySkipped(stage)) {
                    getQueue().push(new Messages.SkipStage(stage.getTopLevelStage()));
                } else if (shouldCompleteStage(task, message.getStatus(), message.getOriginalStatus())) {
                    getQueue().push(new Messages.CompleteStage(message));
                } else {
                    TaskExecution nextTask = StageExecutionUtil.nextTask(mergedContextStage, task);
                    if (nextTask == null) {
                        getQueue().push(new Messages.NoDownstreamTasks(message));
                    } else {
                        getQueue().push(new Messages.StartTask(message, nextTask.getId()));
                    }
                }

                publisher.publishEvent(new TaskComplete(this, mergedContextStage, task));
            }
        });
    }

    public boolean shouldCompleteStage(TaskExecution task, ExecutionStatus status, ExecutionStatus originalStatus) {
        if (task.isStageEnd()) {
            // last task in the stage
            return true;
        }

        if (originalStatus == ExecutionStatus.FAILED_CONTINUE) {
            // the task explicitly returned FAILED_CONTINUE and _should_ run subsequent tasks
            return false;
        }

        // the task _should not_ run subsequent tasks
        return status.isHalt();
    }

    private void handleRedirect(StageExecution stage) {
        List<TaskExecution> tasks = stage.getTasks();
        int start = -1;
        int end = -1;

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).isLoopStart()) {
                start = i;
            }
            if (tasks.get(i).isLoopEnd()) {
                end = i;
            }
        }

        for (int i = start; i <= end; i++) {
            TaskExecutionImpl task = (TaskExecutionImpl) tasks.get(i);
            task.setEndTime(null);
            task.setStatus(ExecutionStatus.NOT_STARTED);
        }

        getRepository().storeStage(stage);
        getQueue().push(new Messages.StartTask(
                stage.getPipelineExecution().getType(),
                stage.getPipelineExecution().getId(),
                stage.getPipelineExecution().getNamespace(),
                stage.getId(),
                tasks.get(start).getId()
        ));
    }


}