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

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.trigger.Trigger;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * The runtime execution state of a Pipeline.
 */
public interface PipelineExecution {

    String getNamespace();

    ExecutionType getType();

    Long getId();

    String getUuid();

    String getName();

    String getRemark();

    String getOrigin();

    Long getPipelineConfigId();

    boolean isLimitConcurrent();

    int getMaxConcurrentExecutions();

    boolean isKeepWaitingPipelines();

    Instant getBuildTime();

    Instant getStartTime();

    Instant getEndTime();

    /**
     * Gets the start expiry timestamp for this execution. If the execution has not started before
     * this timestamp, the execution will immediately terminate.
     */
    Instant getStartTimeExpiry();

    PausedDetails getPaused();

    boolean isCanceled();

    String getCanceledBy();

    String getCancellationReason();

    Trigger getTrigger();

    List<Map<String, Object>> getNotifications();

    String getSpelEvaluator();

    Map<String, Object> getTemplateVariables();

    String getPartition();

    List<StageExecution> getStages();

    Map<String, Object> getContext();

    ExecutionStatus getStatus();

    // -------

    /**
     * Based on the value of `status`, will also update synthetic fields like `canceled` and `endTime`
     */
    void updateStatus(ExecutionStatus status);

    StageExecution namedStage(String type);

    StageExecution stageById(Long stageId);

    StageExecution stageByRef(String refId);

    @Data
    class PausedDetails {
        private String pausedBy;
        private Instant pauseTime;
        private String resumedBy;
        private Instant resumeTime;

        public boolean isPaused() {
            return pauseTime != null && resumeTime == null;
        }

        public long getPausedMs() {
            return (pauseTime != null && resumeTime != null) ? Duration.between(pauseTime, resumeTime).toMillis() : 0;
        }
    }
}
