package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.config.SpinnakerOrcaConfig;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.CancellableStage;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageNavigator;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.Task;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageDefinitionBuilderFactory;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.TaskResolver;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.concurrent.Executor;

@Component
public class CancelStageHandler extends AbstractOrcaMessageHandler<Messages.CancelStage> implements StageBuilderAware {

    private final StageDefinitionBuilderFactory stageDefinitionBuilderFactory;
    private final StageNavigator stageNavigator;
    private final Executor executor;
    private final TaskResolver taskResolver;

    public CancelStageHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock,
            StageDefinitionBuilderFactory stageDefinitionBuilderFactory,
            StageNavigator stageNavigator,
            @Qualifier(SpinnakerOrcaConfig.CANCELLABLE_STAGE_EXECUTOR) Executor executor,
            TaskResolver taskResolver) {
        super(queue, repository, publisher, clock);
        this.stageDefinitionBuilderFactory = stageDefinitionBuilderFactory;
        this.stageNavigator = stageNavigator;
        this.executor = executor;
        this.taskResolver = taskResolver;
    }

    @Override
    public Class<Messages.CancelStage> getMessageType() {
        return Messages.CancelStage.class;
    }

    @Override
    public StageDefinitionBuilderFactory getStageDefinitionBuilderFactory() {
        return stageDefinitionBuilderFactory;
    }

    @Override
    public void handle(Messages.CancelStage message) {
        withStage(message, stage -> {
            // 当执行以非 SUCCEEDED 状态结束时，仍在运行的阶段
            // 保持在 RUNNING 状态，直到它们的运行任务被出队到 RunTaskHandler。
            // 对于使用 getDynamicBackoffPeriod() 的任务，阶段可能会错误地
            // 报告为 RUNNING 相当长的时间，除非我们短路它们的回退时间。
            if (stage.getStatus() == ExecutionStatus.RUNNING) {
                stage.getTasks().stream()
                        .filter(task -> task.getStatus() == ExecutionStatus.RUNNING)
                        .forEach(task -> {
                            getQueue().reschedule(new Messages.RunTask(
                                    stage.getPipelineExecution().getType(),
                                    stage.getPipelineExecution().getId(),
                                    stage.getPipelineExecution().getNamespace(),
                                    stage.getId(),
                                    task.getId(),
                                    getTaskType(task)
                            ));
                        });
            }

            if (stage.getStatus().isHalt()) {
                StageDefinitionBuilder builder = builder(stage);
                if (builder instanceof CancellableStage) {
                    // 暂时将其作为离线线程执行，因为某些取消例程
                    // 可能运行足够长的时间导致消息确认超时
                    Runnable cancellationClosure = () -> {
                        ((CancellableStage) builder).cancel(stage);

                        // PipelineStage 的特殊情况，确保及时取消
                        // 子管道和部署策略，而不考虑任务回退
                        if (stage.getType().equalsIgnoreCase("pipeline") &&
                                stage.getContext().containsKey("executionId")) {
                            Long childId = (Long) stage.getContext().get("executionId");
                            if (childId != null) {
                                PipelineExecution pipelineExecution = getRepository().retrieve(ExecutionType.PIPELINE, childId);
                                getQueue().push(new Messages.RescheduleExecution(pipelineExecution));
                            }
                        }
                    };
                    executor.execute(cancellationClosure);
                }
            }
        });
    }

    private Class<? extends Task> getTaskType(TaskExecution task) {
        return taskResolver.getTaskClass(task.getImplementingClass());
    }

}
