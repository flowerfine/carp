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

import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageGraphBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.TaskNode;
import cn.sliew.carp.module.orca.spinnaker.log.tasks.LogTask;
import cn.sliew.milky.common.util.JacksonUtil;
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
    public void beforeStages(@NotNull DagStepDTO parent, @NotNull StageGraphBuilder graph) {
        log.info("build before, parent: {}={}", parent.getDagConfigStep().getStepName(), parent.getDagConfigStep().getStepName());
    }

    @Override
    public void taskGraph(@NotNull DagStepDTO stage, @NotNull TaskNode.Builder builder) {
        log.info("build task: {}", JacksonUtil.toObject(stage.getInputs(), StageData.class));
        builder.withTask("log", LogTask.class);
    }

    @Override
    public void afterStages(@NotNull DagStepDTO parent, @NotNull StageGraphBuilder graph) {
        log.info("build after, parent: {}={}", parent.getDagConfigStep().getStepName(), parent.getDagConfigStep().getStepName());
    }

    @Override
    public void onFailureStages(@NotNull DagStepDTO stage, @NotNull StageGraphBuilder graph) {

    }

    @Data
    public static class StageData {
        private String id;
        private Map<String, Object> context;
    }
}
