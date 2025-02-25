package cn.sliew.carp.module.orca.spinnaker.log.tasks;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.RetryableTask;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskResult;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class LogRedirectTask implements RetryableTask {

    @Override
    public Duration getBackoffPeriod() {
        return Duration.ofSeconds(10L);
    }

    @Override
    public Duration getTimeout() {
        return Duration.ofSeconds(300L);
    }

    @Override
    public @NotNull TaskResult execute(@NotNull StageExecution stageExecution) {
        if (RandomUtils.nextBoolean()) {
            log.info("log task execute, redirect, stage: {}, currentTask: {}, tasks: {}",
                    stageExecution.getName(), LogTask.currentTask(stageExecution.getTasks()), LogTask.mapTask(stageExecution.getTasks()));
            return TaskResult.builder(ExecutionStatus.REDIRECT)
                    .build();
        } else {
            log.info("log task execute, stage: {}, currentTask: {}, tasks: {}",
                    stageExecution.getName(), LogTask.currentTask(stageExecution.getTasks()), LogTask.mapTask(stageExecution.getTasks()));
            return TaskResult.SUCCEEDED;
        }
    }
}
