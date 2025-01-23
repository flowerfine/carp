package cn.sliew.carp.module.orca.spinnaker.log.pipeline;

import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageGraphBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.TaskNode;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.log.tasks.LogTask;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class LogStage implements StageDefinitionBuilder {

    // very importent
    // orca 内部会通过 STAGE_TYPE 获取 stage type，及时代码中不用这个常量，也要定义
    public static String STAGE_TYPE = "log";

    @Override
    public @NotNull String getType() {
        return STAGE_TYPE;
    }

    @Override
    public void beforeStages(@NotNull StageExecution parent, @NotNull StageGraphBuilder graph) {
        log.info("build before, parent: {}={}", parent.getName(), parent.getType());
    }

    @Override
    public void taskGraph(@NotNull StageExecution stage, @NotNull TaskNode.Builder builder) {
        log.info("build task: " + stage.mapTo(StageData.class));
        builder.withTask("log", LogTask.class);
    }

    @Override
    public void afterStages(@NotNull StageExecution parent, @NotNull StageGraphBuilder graph) {
        log.info("build after, parent: {}={}", parent.getName(), parent.getType());
    }

    @Override
    public void onFailureStages(@NotNull StageExecution stage, @NotNull StageGraphBuilder graph) {

    }

    @Data
    public static class StageData {
        private String id;
        private Map<String, Object> context;
    }
}
