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
package cn.sliew.carp.module.orca.spinnaker.api.model.pipeline;

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.trigger.Trigger;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class PipelineBuilder {

    private final PipelineExecutionImpl pipeline;

    public PipelineBuilder(String name) {
        pipeline = PipelineExecutionImpl.newPipeline(name);
    }

    public PipelineBuilder withName(String name) {
        pipeline.setName(name);
        return this;
    }

    public PipelineBuilder withOrigin(String origin) {
        pipeline.setOrigin(origin);
        return this;
    }

    public PipelineBuilder withPipelineConfigId(String id) {
        pipeline.setPipelineConfigId(id);
        return this;
    }


    public PipelineBuilder withLimitConcurrent(boolean concurrent) {
        pipeline.setLimitConcurrent(concurrent);
        return this;
    }

    public PipelineBuilder withMaxConcurrentExecutions(int maxConcurrentExecutions) {
        pipeline.setMaxConcurrentExecutions(maxConcurrentExecutions);
        return this;
    }

    public PipelineBuilder withKeepWaitingPipelines(boolean waiting) {
        pipeline.setKeepWaitingPipelines(waiting);
        return this;
    }

    public PipelineBuilder withStartTimeExpiry(String startTimeExpiry) {
        if (startTimeExpiry != null) {
            pipeline.setStartTimeExpiry(Instant.ofEpochMilli(Long.valueOf(startTimeExpiry)));
        }
        return this;
    }

    public PipelineBuilder withTrigger(Trigger trigger) {
        if (trigger != null) {
            pipeline.setTrigger(trigger);
        }
        return this;
    }

    public PipelineBuilder withNotifications(List<Map<String, Object>> notifications) {
        pipeline.getNotifications().clear();
        if (notifications != null) {
            pipeline.getNotifications().addAll(notifications);
        }
        return this;
    }

    public PipelineBuilder withSpelEvaluator(String spelEvaluatorVersion) {
        pipeline.setSpelEvaluator(spelEvaluatorVersion);

        return this;
    }

    public PipelineBuilder withTemplateVariables(Map<String, Object> templateVariables) {
        pipeline.setTemplateVariables(templateVariables);

        return this;
    }

    public PipelineBuilder withInitialConfig(Map<String, Object> initialConfig) {
        pipeline.getInitialConfig().clear();
        if (initialConfig != null) {
            pipeline.getInitialConfig().putAll(initialConfig);
        }

        return this;
    }

    public PipelineBuilder withStage(String type) {
        return withStage(type, type);
    }

    public PipelineBuilder withStage(String type, String name) {
        return withStage(type, name, new HashMap<>());
    }

    public PipelineBuilder withStage(String type, String name, Map<String, Object> context) {
        pipeline.getStages().add(new StageExecutionImpl(pipeline, type, name, context));
        return this;
    }

    public PipelineBuilder withStages(List<Map<String, Object>> stages) {
        if (stages == null) {
            throw new IllegalArgumentException(
                    "null stages in pipeline '" + pipeline.getName() + "'");
        }
        stages.forEach(
                it -> {
                    String name = it.containsKey("name") ? it.remove("name").toString() : null;
                    if (!it.containsKey("type")) {
                        throw new IllegalArgumentException(
                                "type missing from pipeline '" + pipeline.getName() + "', stage name: '" + name + "'");
                    }
                    String type = it.remove("type").toString();
                    withStage(type, name != null ? name : type, it);
                });
        return this;
    }

    public PipelineExecution build() {
        pipeline.setBuildTime(Instant.now());
        return pipeline;
    }
}
