package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.TaskResolver;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RescheduleExecutionHandler implements OrcaMessageHandler<Messages.RescheduleExecution> {

    @Getter
    private final Queue queue;
    @Getter
    private final ExecutionRepository repository;
    private final TaskResolver taskResolver;

    public RescheduleExecutionHandler(
            Queue queue,
            ExecutionRepository repository,
            TaskResolver taskResolver) {
        this.queue = queue;
        this.repository = repository;
        this.taskResolver = taskResolver;
    }

    @Override
    public Class<Messages.RescheduleExecution> getMessageType() {
        return Messages.RescheduleExecution.class;
    }

    @Override
    public void handle(Messages.RescheduleExecution message) {
        withExecution(message, execution -> {
            execution.getStages().stream()
                    .filter(stage -> stage.getStatus() == ExecutionStatus.RUNNING)
                    .forEach(stage -> {
                        stage.getTasks().stream()
                                .filter(task -> task.getStatus() == ExecutionStatus.RUNNING)
                                .forEach(task -> {
                                    Messages.RunTask taskMessage = new Messages.RunTask(
                                            message,
                                            stage.getId(),
                                            task.getId(),
                                            taskResolver.getTaskClass(task.getImplementingClass())
                                    );
                                    queue.ensure(taskMessage, Duration.ZERO);
                                    queue.reschedule(taskMessage);
                                });
                    });
        });
    }


}