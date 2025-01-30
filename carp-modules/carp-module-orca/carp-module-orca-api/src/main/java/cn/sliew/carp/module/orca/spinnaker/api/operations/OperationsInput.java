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
package cn.sliew.carp.module.orca.spinnaker.api.operations;

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class OperationsInput {

    /**
     * The operations collection.
     */
    private Collection<? extends Map<String, Map>> operations;

    /**
     * The {@link StageExecution} that runs this operation.
     */
    private StageExecution stageExecution;

    /**
     * The context key is passed to {@link OperationsContext#contextKey()} and used to identify the
     * corresponding {@link OperationsContext#contextValue()}.
     */
    @Nullable
    private String contextKey;

    public static OperationsInput of(
            Collection<? extends Map<String, Map>> operations,
            StageExecution stageExecution) {
        return builder()
                .operations(operations)
                .stageExecution(stageExecution)
                .build();
    }

}
