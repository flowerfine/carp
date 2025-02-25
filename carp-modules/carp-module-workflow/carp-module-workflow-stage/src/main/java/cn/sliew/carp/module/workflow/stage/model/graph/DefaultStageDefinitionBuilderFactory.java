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
package cn.sliew.carp.module.workflow.stage.model.graph;

import cn.sliew.carp.framework.dag.service.dto.DagConfigStepDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.stage.model.resolver.StepResolver;
import jakarta.annotation.Nonnull;

public class DefaultStageDefinitionBuilderFactory implements StageDefinitionBuilderFactory {

    private final StepResolver stepResolver;

    public DefaultStageDefinitionBuilderFactory(StepResolver stepResolver) {
        this.stepResolver = stepResolver;
    }

    @Nonnull
    @Override
    public StageDefinitionBuilder builderFor(@Nonnull DagStepDTO step) {
        DagConfigStepDTO dagConfigStep = step.getDagConfigStep();
        String type = dagConfigStep.getStepMeta().path("type").asText();
        String alias = dagConfigStep.getStepMeta().path("alias").asText();
        return stepResolver.getStageDefinitionBuilder(type, alias);
    }
}
