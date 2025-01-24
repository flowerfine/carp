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
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.trigger.Trigger;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.huxhorn.sulky.ulid.ULID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PipelineExecutionImpl implements PipelineExecution, Serializable {

    public static final Trigger NO_TRIGGER = Trigger.builder().type("none").build();
    private static final ULID ID_GENERATOR = new ULID();

    private final Map<String, Object> initialConfig = new HashMap<>();

    private String namespace;
    private ExecutionType type;
    private Long id;
    private String uuid = ID_GENERATOR.nextULID();
    private String name;
    private String remark;
    private String origin;
    private String pipelineConfigId;
    private boolean limitConcurrent = false;
    private int maxConcurrentExecutions = 0;
    private boolean keepWaitingPipelines = false;
    private Instant buildTime;
    private Instant startTime;
    private Instant endTime;
    private Instant startTimeExpiry;
    private PausedDetails paused;
    private boolean canceled;
    private String canceledBy;
    private String cancellationReason;
    private Trigger trigger = NO_TRIGGER;
    private List<Map<String, Object>> notifications = new ArrayList<>();
    private String spelEvaluator;
    private Map<String, Object> templateVariables = null;
    private String partition = null;
    private ExecutionStatus status = ExecutionStatus.NOT_STARTED;
    private List<StageExecution> stages = new ArrayList<>();

    @JsonCreator
    public PipelineExecutionImpl(
            @JsonProperty("type") ExecutionType type,
            @JsonProperty("name") String name) {
        this.type = type;
        this.name = name;
    }

    @JsonIgnoreProperties(value = "pipelineExecution")
    public List<StageExecution> getStages() {
        return stages;
    }

    @JsonIgnore
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map<String, Object> getContext() {
        return StageExecutionImpl.topologicalSort(stages)
                .map(StageExecution::getOutputs)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(
                        toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (o, o2) -> {
                                    if (o instanceof Collection && o2 instanceof Collection) {
                                        return Stream.concat(((Collection) o).stream(), ((Collection) o2).stream())
                                                .distinct()
                                                .collect(Collectors.toList());
                                    }
                                    return o2;
                                }));
    }

    @Override
    public void updateStatus(ExecutionStatus status) {
        this.status = status;
        if (status == ExecutionStatus.RUNNING) {
            canceled = false;
            startTime = Instant.now();
        } else if (status.isComplete() && startTime != null) {
            endTime = Instant.now();
        }
    }

    public StageExecution namedStage(String type) {
        return stages.stream().filter(it -> it.getType().equals(type)).findFirst().orElse(null);
    }

    public StageExecution stageById(Long stageId) {
        return stages.stream()
                .filter(it -> it.getId().equals(stageId))
                .findFirst()
                .orElseThrow(
                        () ->
                                new IllegalArgumentException(
                                        String.format("No stage with id %s exists on execution %s", stageId, id)));
    }

    public StageExecution stageByRef(String refId) {
        return stages.stream()
                .filter(it -> refId.equals(it.getRefId()))
                .findFirst()
                .orElseThrow(
                        () ->
                                new IllegalArgumentException(
                                        String.format("No stage with refId %s exists", refId)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PipelineExecutionImpl execution = (PipelineExecutionImpl) o;
        return Objects.equals(id, execution.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Deprecated
    public static PipelineExecutionImpl newOrchestration(String name) {
        return new PipelineExecutionImpl(ExecutionType.ORCHESTRATION, name);
    }

    @Deprecated
    public static PipelineExecutionImpl newPipeline(String name) {
        return new PipelineExecutionImpl(ExecutionType.PIPELINE, name);
    }

}
