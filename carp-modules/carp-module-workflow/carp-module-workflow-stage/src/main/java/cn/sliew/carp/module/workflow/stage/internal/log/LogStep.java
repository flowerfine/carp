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
package cn.sliew.carp.module.workflow.stage.internal.log;

import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.stage.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.workflow.stage.model.graph.TaskNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogStep implements StageDefinitionBuilder {

    public static final String STEP_TYPE = "log";

    @Override
    public String getType() {
        return STEP_TYPE;
    }

    @Override
    public void taskGraph(DagStepDTO step, TaskNode.Builder builder) {
        log.info("build task, step: {}", step.getDagConfigStep().getStepName());
        // getPipelinesFromArtifact -> subGraph -> savePipelinesCompleteTask
        // subGraph 会在 getPipelinesFromArtifact 之后运行，savePipelinesCompleteTask 之前运行，即按照添加位置执行
        // 当 subGraph 中任务返回 REDIRECT 时，subGraph 中的 5 个任务会一起重复执行
        builder.withTask("getPipelinesFromArtifact", LogStepTask.class)
                .withLoop(
                        subGraph -> {
                            subGraph.withTask("preparePipelineToSaveTask", LogStepTask.class);
                            subGraph.withTask("savePipeline", LogStepTask.class);
                            subGraph.withTask("waitForPipelineSave", LogStepTask.class);
//                            subGraph.withTask("checkPipelineResults", LogRedirectTask.class);
                            subGraph.withTask("checkForRemainingPipelines", LogStepTask.class);
                        })
                .withTask("savePipelinesCompleteTask", LogStepTask.class)
                .withTask("log-all-1", LogStepTask.class)
                .withTask("log-all-2", LogStepTask.class);
    }
}
