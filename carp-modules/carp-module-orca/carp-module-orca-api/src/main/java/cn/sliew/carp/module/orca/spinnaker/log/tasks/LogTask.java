package cn.sliew.carp.module.orca.spinnaker.log.tasks;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.RetryableTask;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskResult;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LogTask implements RetryableTask {

    @Override
    public Duration getBackoffPeriod() {
        return Duration.ofSeconds(10L);
    }

    @Override
    public Duration getTimeout() {
        return Duration.ofMinutes(5L);
    }

    /**
     * 获取之前节点的输出
     */
    @Override
    public @NotNull TaskResult execute(@NotNull StageExecution stageExecution) {
        log.info("log task execute");
        Map<String, Object> context = new HashMap<>();
        context.put("log-task-context", "log-task-context");
        return TaskResult.builder(ExecutionStatus.SUCCEEDED)
                .output("log-task-output", "log-task-output-data")
                .context(context)
                .build();
    }
}
