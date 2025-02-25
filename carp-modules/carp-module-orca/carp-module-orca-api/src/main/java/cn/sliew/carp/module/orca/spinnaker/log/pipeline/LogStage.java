/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sliew.carp.module.orca.spinnaker.log.pipeline;

import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageGraphBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.TaskNode;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionFactory;
import cn.sliew.carp.module.orca.spinnaker.log.tasks.LogRedirectTask;
import cn.sliew.carp.module.orca.spinnaker.log.tasks.LogTask;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

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
        log.info("build before, parent name: {}, type: {}", parent.getName(), parent.getType());
        if (isTopLevelStage(parent)) {
            StageExecution logBefore1 = StageExecutionFactory.newStage(parent.getPipelineExecution(), "log", "log-before-1", Collections.emptyMap(), parent, SyntheticStageOwner.STAGE_BEFORE);
            StageExecution logBefore2 = StageExecutionFactory.newStage(parent.getPipelineExecution(), "log", "log-before-2", Collections.emptyMap(), parent, SyntheticStageOwner.STAGE_BEFORE);
            graph.connect(logBefore1, logBefore2);
        }
    }

    @Override
    public void taskGraph(@NotNull StageExecution stage, @NotNull TaskNode.Builder builder) {
        log.info("build task, stage: {}, context: {}", stage.getName(), stage.mapTo(StageData.class));
        if (isTopLevelStage(stage)) {
            // getPipelinesFromArtifact -> subGraph -> savePipelinesCompleteTask
            // subGraph 会在 getPipelinesFromArtifact 之后运行，savePipelinesCompleteTask 之前运行，即按照添加位置执行
            // 当 subGraph 中任务返回 REDIRECT 时，subGraph 中的 5 个任务会一起重复执行
            builder.withTask("getPipelinesFromArtifact", LogTask.class)
                    .withLoop(
                            subGraph -> {
                                subGraph.withTask("preparePipelineToSaveTask", LogTask.class);
                                subGraph.withTask("savePipeline", LogTask.class);
                                subGraph.withTask("waitForPipelineSave", LogTask.class);
                                subGraph.withTask("checkPipelineResults", LogRedirectTask.class);
                                subGraph.withTask("checkForRemainingPipelines", LogTask.class);
                            })
                    .withTask("savePipelinesCompleteTask", LogTask.class);
        }
        builder.withTask("log-all-1", LogTask.class);
        builder.withTask("log-all-2", LogTask.class);
    }

    @Override
    public void afterStages(@NotNull StageExecution parent, @NotNull StageGraphBuilder graph) {
        log.info("build after, parent name: {}, type: {}", parent.getName(), parent.getType());
    }

    @Override
    public void onFailureStages(@NotNull StageExecution stage, @NotNull StageGraphBuilder graph) {

    }

    private boolean isTopLevelStage(StageExecution stage) {
        return Objects.isNull(stage.getParentStageId());
    }

    @Data
    public static class StageData {
        private String id;
        private Map<String, Object> context;
    }
}
