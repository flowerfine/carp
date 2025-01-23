package cn.sliew.carp.module.orca.spinnaker.log.tasks;

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.RetryableTask;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskResult;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class LogBeforeTask implements RetryableTask {

    @Override
    public Duration getBackoffPeriod() {
        return Duration.ofSeconds(10L);
    }

    @Override
    public Duration getTimeout() {
        return Duration.ofMinutes(5L);
    }

    @Override
    public @NotNull TaskResult execute(@NotNull StageExecution stageExecution) {
        log.info("log before task execute");
        return TaskResult.SUCCEEDED;
    }
}
