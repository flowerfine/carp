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

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * The runtime execution state of a stage.
 */
public interface StageExecution {

    /**
     * A stage's unique identifier
     */
    Long getId();

    String getUuid();

    String getRefId();

    String getType();

    /**
     * The name of the stage. Can be different from type, but often will be the same.
     */
    String getName();

    /**
     * Gets the execution object for this stage
     */
    PipelineExecution getPipelineExecution();

    /**
     * Gets the start time for this stage. May return null if the stage has not been started.
     */
    Instant getStartTime();

    /**
     * Gets the end time for this stage. May return null if the stage has not yet finished.
     */
    Instant getEndTime();

    /**
     * Gets the start expiry timestamp for this stage. If the stage has not started before this
     * timestamp, the stage will be skipped.
     */
    Instant getStartTimeExpiry();

    /**
     * The execution status for this stage
     */
    ExecutionStatus getStatus();

    /**
     * The context driving this stage. Provides inputs necessary to component steps
     * TODO(rz): Try to use StageContext instead?
     */
    Map<String, Object> getContext();

    /**
     * Outputs from this stage which may be accessed by downstream stages.
     * TODO(rz): getOutputs(Class)?
     */
    Map<String, Object> getOutputs();

    /**
     * Returns the tasks that are associated with this stage. Tasks are the most granular unit of work
     * in a stage. Because tasks can be dynamically composed, this list is open updated during a
     * stage's execution.
     */
    List<TaskExecution> getTasks();

    /**
     * Stages can be synthetically injected into the pipeline by a StageDefinitionBuilder. This flag
     * indicates the relationship of a synthetic stage to its position in the graph. To derive the
     * owning stage, callers should directionally traverse the graph until the first non-synthetic
     * stage is found. If this property is null, the stage is not synthetic.
     */
    SyntheticStageOwner getSyntheticStageOwner();

    /**
     * This stage's parent stage.
     */
    Long getParentStageId();

    Collection<String> getRequisiteStageRefIds();

    /**
     * A date when this stage is scheduled to execute.
     */
    Instant getScheduledTime();

    LastModifiedDetails getLastModified();

    /**
     * Additional tags to be used with stage metrics. This is useful to add extra dimensions to the
     * metrics recorded for built-in or custom stages.
     */
    Map<String, String> getAdditionalMetricTags();

    // ------------- InternalStageExecution?
    // A lot of these methods are used in a single place somewhere in Orca. I don't know why we
    // decided to put a bunch
    // of these methods on the class...?

    TaskExecution taskById(Long taskId);

    List<StageExecution> ancestors();

    List<StageExecution> directAncestors();

    StageExecution findAncestor(Predicate<StageExecution> predicate);

    List<StageExecution> allDownstreamStages();

    List<StageExecution> downstreamStages();

    List<StageExecution> directChildren();

    StageExecution getParent();

    StageExecution getTopLevelStage();

    Optional<StageExecution> getParentWithTimeout();

    Optional<Long> getTimeout();

    <O> O mapTo(Class<O> type);

    <O> O mapTo(String pointer, Class<O> type);

    <O> O decodeBase64(String pointer, Class<O> type);

    void resolveStrategyParams();

    boolean getAllowSiblingStagesToContinueOnFailure();

    boolean getContinuePipelineOnFailure();

    boolean isJoin();

    boolean isManualJudgmentType();

    boolean withPropagateAuthentication();

    void appendErrorMessage(String errorMessage);

    @Data
    @NoArgsConstructor
    class LastModifiedDetails {
        private String user;
        private Instant lastModifiedTime;
    }
}
