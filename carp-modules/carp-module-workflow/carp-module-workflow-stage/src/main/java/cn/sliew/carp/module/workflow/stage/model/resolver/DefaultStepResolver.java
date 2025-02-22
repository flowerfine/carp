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
package cn.sliew.carp.module.workflow.stage.model.resolver;

import cn.hutool.extra.spring.SpringUtil;
import cn.sliew.carp.module.workflow.stage.model.graph.StageDefinitionBuilder;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DefaultStepResolver implements StepResolver {

    private final ConcurrentHashMap<String, StageDefinitionBuilder> stageDefinitionBuilderByAlias = new ConcurrentHashMap<>();

    public DefaultStepResolver(List<StageDefinitionBuilder> stageDefinitionBuilders) {
        for (StageDefinitionBuilder stageDefinitionBuilder : stageDefinitionBuilders) {
            stageDefinitionBuilderByAlias.put(stageDefinitionBuilder.getType(), stageDefinitionBuilder);
            addAliases(stageDefinitionBuilder);
        }
    }

    @Nonnull
    @Override
    public StageDefinitionBuilder getStageDefinitionBuilder(@Nonnull String type, String typeAlias) {
        StageDefinitionBuilder stageDefinitionBuilder = getOrDefault(type, typeAlias);

        if (stageDefinitionBuilder == null) {
            log.debug("Step definition builder for '{}' not found in initial step definition builder cache, fetching missing step from step provider.", type);
            addMissingStepsFromStepProvider();
            stageDefinitionBuilder = getOrDefault(type, typeAlias);

            if (stageDefinitionBuilder == null) {
                throw new NoSuchStepDefinitionBuilderException(type, typeAlias, stageDefinitionBuilderByAlias.keySet());
            }
        }

        return stageDefinitionBuilder;
    }


    /**
     * {@link ConcurrentHashMap#get)} throws an NPE if the parameter is null, so first check if
     * typeAlias is null and then perform the necessary lookups.
     */
    private StageDefinitionBuilder getOrDefault(@Nonnull String type, String typeAlias) {
        StageDefinitionBuilder stageDefinitionBuilder = null;
        if (typeAlias == null) {
            stageDefinitionBuilder = stageDefinitionBuilderByAlias.get(type);
        }

        if (stageDefinitionBuilder == null && typeAlias != null) {
            stageDefinitionBuilder = stageDefinitionBuilderByAlias.get(typeAlias);
        }

        return stageDefinitionBuilder;
    }

    private void addAliases(StageDefinitionBuilder stageDefinitionBuilder) {
        for (String alias : stageDefinitionBuilder.aliases()) {
            if (stageDefinitionBuilderByAlias.containsKey(alias)) {
                throw new DuplicateStepAliasException(
                        String.format(
                                "Duplicate step alias detected (alias: %s, previous: %s, current: %s)",
                                alias,
                                stageDefinitionBuilderByAlias.get(alias).getClass().getCanonicalName(),
                                stageDefinitionBuilder.getClass().getCanonicalName()));
            }

            stageDefinitionBuilderByAlias.put(alias, stageDefinitionBuilder);
        }
    }

    /**
     * Lookup stages from the stage provider and add missing stages to stageDefinitionBuilderByAlias
     * cache.
     */
    private void addMissingStepsFromStepProvider() {
        for (StageDefinitionBuilder stageDefinitionBuilder : SpringUtil.getBeansOfType(StageDefinitionBuilder.class).values()) {
            if (stageDefinitionBuilderByAlias.get(stageDefinitionBuilder.getType()) == null) {
                stageDefinitionBuilderByAlias.put(stageDefinitionBuilder.getType(), stageDefinitionBuilder);
                addAliases(stageDefinitionBuilder);
                log.info(
                        "{} step resolved from step provider and added to cache",
                        stageDefinitionBuilder.getType());
            }
        }
    }
}
