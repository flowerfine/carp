package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.Task;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.TaskResolver;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class ResumeTaskHandler implements OrcaMessageHandler<Messages.ResumeTask> {

    @Getter
    private final Queue queue;
    @Getter
    private final ExecutionRepository repository;
    private final TaskResolver taskResolver;

    public ResumeTaskHandler(
            Queue queue,
            ExecutionRepository repository,
            TaskResolver taskResolver) {
        this.queue = queue;
        this.repository = repository;
        this.taskResolver = taskResolver;
    }

    @Override
    public Class<Messages.ResumeTask> getMessageType() {
        return Messages.ResumeTask.class;
    }

    @Override
    public void handle(Messages.ResumeTask message) {
        withTask(message, (stage, task) -> {
            ((StageExecutionImpl) stage).setStatus(ExecutionStatus.RUNNING);
            queue.push(new Messages.RunTask(message, getTaskType(task)));
            repository.storeStage(stage);
        });
    }

    private Class<? extends Task> getTaskType(TaskExecution task) {
        return taskResolver.getTaskClass(task.getImplementingClass());
    }
}
