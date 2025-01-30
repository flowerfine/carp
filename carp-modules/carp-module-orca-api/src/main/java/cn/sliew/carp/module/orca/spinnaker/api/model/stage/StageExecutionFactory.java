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
package cn.sliew.carp.module.orca.spinnaker.api.model.stage;

import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Pulled from StageDefinitionBuilder. No idea what is supposed to replace this method, but it's
 * still used everywhere.
 */
public class StageExecutionFactory {

    @Deprecated
    public static @Nonnull StageExecutionImpl newStage(
            @Nonnull PipelineExecution execution,
            @Nonnull String type,
            @Nullable String name,
            @Nonnull Map<String, Object> context,
            @Nullable StageExecution parent,
            @Nullable SyntheticStageOwner stageOwner) {
        StageExecutionImpl stage = new StageExecutionImpl(execution, type, name, context);
        if (parent != null) {
            stage.setParentStageId(parent.getId());
        }
        stage.setSyntheticStageOwner(stageOwner);
        return stage;
    }
}
