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
package cn.sliew.carp.module.orca.spinnaker.api.persistence;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public interface ExecutionRepository {

    PipelineExecution retrieve(ExecutionType type, Long id) throws ExecutionNotFoundException;

    Observable<PipelineExecution> retrieve(ExecutionType type);

    Long store(PipelineExecution execution);

    Long storeStage(StageExecution stage);

    Long addStage(StageExecution stage);

    void removeStage(PipelineExecution execution, Long stageId);

    default void updateStageContext(StageExecution stage) {
        storeStage(stage);
    }

    boolean isCanceled(ExecutionType type, Long id);

    default void cancel(ExecutionType type, Long id) {
        cancel(type, id, null, null);
    }

    void cancel(ExecutionType type, Long id, String user, String reason);

    void pause(ExecutionType type, Long id, String user);

    void resume(ExecutionType type, Long id, String user);

    void resume(ExecutionType type, Long id, String user, boolean ignoreCurrentStatus);

    default void updateStatus(PipelineExecution execution) {
        updateStatus(execution.getType(), execution.getId(), execution.getStatus());
    }

    void updateStatus(ExecutionType type, Long id, ExecutionStatus status);

    void delete(ExecutionType type, Long id);

    default void delete(ExecutionType type, List<Long> ids) {
        ids.forEach(id -> delete(type, id));
    }

    boolean hasExecution(ExecutionType type, Long id);

    List<Long> retrieveAllExecutionIds(ExecutionType type);

    // defaulting to a no-op because in normal cases, this is a no-op for execution repositories
    // execution repositories that support foreign peers can override this to support restarting
    // foreign executions
    default void restartStage(Long executionId, Long stageId) {
    }

}
