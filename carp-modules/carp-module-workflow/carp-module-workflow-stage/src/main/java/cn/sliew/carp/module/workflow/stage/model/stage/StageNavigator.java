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
package cn.sliew.carp.module.workflow.stage.model.stage;

import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageResolver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Provides an enhanced version of {@link StageExecution#ancestors()} that returns tuples of the
 * ancestor stages and their {@link StageDefinitionBuilder}s.
 */
@Component
@AllArgsConstructor
public class StageNavigator {

    private StageResolver stageResolver;

    /**
     * As per `Stage.ancestors` except this method returns tuples of the stages and their
     * `StageDefinitionBuilder`.
     */
    public List<Result> ancestors(StageExecution startingStage) {
        return startingStage.ancestors().stream()
                .map(
                        it ->
                                new Result(
                                        it,
                                        stageResolver.getStageDefinitionBuilder(
                                                it.getType(), (String) it.getContext().get("alias"))))
                .collect(toList());
    }

    @Getter
    @AllArgsConstructor
    public static class Result {
        private final StageExecution stage;
        private final StageDefinitionBuilder stageBuilder;
    }
}
