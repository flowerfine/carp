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

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.trigger.Trigger;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import de.huxhorn.sulky.ulid.ULID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.*;
import static java.util.stream.Collectors.toList;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StageExecutionImpl implements StageExecution, Serializable {

    public static final String STAGE_TIMEOUT_OVERRIDE_KEY = "stageTimeoutMs";
    private static final ULID ID_GENERATOR = new ULID();

    private Long id;
    private String uuid = ID_GENERATOR.nextULID();
    private String refId;
    private String type;
    private String name;
    @JsonBackReference
    private PipelineExecution pipelineExecution;
    private Instant startTime;
    private Instant endTime;
    private Instant startTimeExpiry;
    private ExecutionStatus status = ExecutionStatus.NOT_STARTED;
    private Map<String, Object> context = new StageContext(this);
    private Map<String, Object> outputs = new HashMap<>();
    private List<TaskExecution> tasks = new ArrayList<>();
    private SyntheticStageOwner syntheticStageOwner;
    private Long parentStageId;
    @JsonDeserialize(using = RequisiteStageRefIdDeserializer.class)
    private Collection<String> requisiteStageRefIds = emptySet();
    private Instant scheduledTime;
    private LastModifiedDetails lastModified;
    private Map<String, String> additionalMetricTags;

    /**
     * Sorts stages into order according to their refIds / requisiteStageRefIds.
     */
    public static Stream<StageExecution> topologicalSort(Collection<StageExecution> stages) {
        List<StageExecution> unsorted =
                stages.stream().filter(it -> it.getParentStageId() == null).collect(toList());
        ImmutableList.Builder<StageExecution> sorted = ImmutableList.builder();
        Set<String> refIds = new HashSet<>();
        while (!unsorted.isEmpty()) {
            List<StageExecution> sortable =
                    unsorted.stream()
                            .filter(it -> refIds.containsAll(it.getRequisiteStageRefIds()))
                            .collect(toList());
            if (sortable.isEmpty()) {
                throw new IllegalStateException(
                        format(
                                "Invalid stage relationships found %s",
                                join(
                                        ", ",
                                        stages.stream()
                                                .map(it -> format("%s->%s", it.getRequisiteStageRefIds(), it.getRefId()))
                                                .collect(toList()))));
            }
            sortable.forEach(
                    it -> {
                        unsorted.remove(it);
                        refIds.add(it.getRefId());
                        sorted.add(it);
                    });
        }
        return sorted.build().stream();
    }

    public StageExecutionImpl(PipelineExecution execution, String type) {
        this(execution, type, emptyMap());
    }

    public StageExecutionImpl(PipelineExecution execution, String type, Map<String, Object> context) {
        this(execution, type, null, context);
    }

    @SuppressWarnings("unchecked")
    public StageExecutionImpl(
            PipelineExecution pipelineExecution, String type, String name, Map<String, Object> context) {
        this.pipelineExecution = pipelineExecution;
        this.type = type;
        this.name = name;

        this.refId = (String) context.remove("refId");
        this.startTimeExpiry =
                Optional.ofNullable(context.remove("startTimeExpiry"))
                        .map(expiry -> Instant.ofEpochMilli(Long.valueOf((String) expiry)))
                        .orElse(null);
        this.requisiteStageRefIds =
                Optional.ofNullable((Collection<String>) context.remove("requisiteStageRefIds"))
                        .orElse(emptySet());

        this.context.putAll(context);
    }

    public void setContext(@Nonnull Map<String, Object> context) {
        if (context instanceof StageContext) {
            this.context = context;
        } else {
            this.context = new StageContext(this, context);
        }
    }

    public void setLastModified(@Nullable LastModifiedDetails lastModified) {
        if (lastModified != null
                && this.lastModified != null
                && lastModified.getLastModifiedTime().isBefore(this.lastModified.getLastModifiedTime())) {
            log.warn(
                    "Setting lastModified to a value with an older timestamp, current={}, new={}",
                    this.lastModified,
                    lastModified);
        }

        this.lastModified = lastModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StageExecutionImpl stage = (StageExecutionImpl) o;
        return Objects.equals(id, stage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Stage {id='" + id + "', executionId='" + pipelineExecution.getId() + "'}";
    }

    public TaskExecution taskById(@Nonnull Long taskId) {
        return tasks.stream().filter(it -> it.getId().equals(taskId)).findFirst().orElse(null);
    }

    /**
     * Gets all ancestor stages, including the current stage.
     *
     * <p>Ancestors include: <br>
     * - stages this stage depends on (via requisiteStageRefIds) <br>
     * - parent stages of this stage <br>
     * - synthetic stages that share a parent with this stage & occur prior to current stage <br>
     */
    @Nonnull
    public List<StageExecution> ancestors() {
        if (pipelineExecution != null) {
            Set<String> visited = Sets.newHashSetWithExpectedSize(pipelineExecution.getStages().size());
            return ImmutableList.<StageExecution>builder()
                    .add(this)
                    .addAll(StageExecutionInternals.getAncestorsImpl(this, visited, false))
                    .build();
        } else {
            return emptyList();
        }
    }

    /**
     * Gets all ancestor stages that are direct parents, including the current stage.
     *
     * <p>Ancestors include: <br>
     * - parent stages of this stage <br>
     * - synthetic stages that share a parent with this stage & occur prior to current stage
     */
    @Nonnull
    public List<StageExecution> directAncestors() {
        if (pipelineExecution != null) {
            Set<String> visited = Sets.newHashSetWithExpectedSize(pipelineExecution.getStages().size());
            return ImmutableList.<StageExecution>builder()
                    .add(this)
                    .addAll(StageExecutionInternals.getAncestorsImpl(this, visited, true))
                    .build();
        } else {
            return emptyList();
        }
    }

    /**
     * Find the ancestor stage that satisfies the given predicate (including traversing the parent
     * execution graphs)
     *
     * <p>Ancestor stages include: <br>
     * - stages this stage depends on (via requisiteStageRefIds) <br>
     * - parent stages of this stage <br>
     * - synthetic stages that share a parent with this stage & occur prior to current stage <br>
     * - stages in parent execution that satisfy above criteria <br>
     *
     * @return the first stage that matches the predicate, or null
     */
    public StageExecution findAncestor(Predicate<StageExecution> predicate) {
        return findAncestor(this, this.pipelineExecution, predicate);
    }

    private static StageExecution findAncestor(
            StageExecution stage, PipelineExecution execution, Predicate<StageExecution> predicate) {
        StageExecution matchingStage = null;

        if (stage != null && !stage.getRequisiteStageRefIds().isEmpty()) {
            List<StageExecution> previousStages =
                    execution.getStages().stream()
                            .filter(s -> stage.getRequisiteStageRefIds().contains(s.getRefId()))
                            .collect(toList());

            Set<Long> previousStageIds =
                    new HashSet<>(previousStages.stream().map(StageExecution::getId).collect(toList()));
            List<StageExecution> syntheticStages =
                    execution.getStages().stream()
                            .filter(s -> previousStageIds.contains(s.getParentStageId()))
                            .collect(toList());

            List<StageExecution> priorStages = new ArrayList<>();
            priorStages.addAll(previousStages);
            priorStages.addAll(syntheticStages);

            matchingStage = priorStages.stream().filter(predicate).findFirst().orElse(null);

            if (matchingStage == null) {
                for (StageExecution s : previousStages) {
                    matchingStage = findAncestor(s, execution, predicate);

                    if (matchingStage != null) {
                        break;
                    }
                }
            }
        } else if ((stage != null) && Objects.nonNull(stage.getParentStageId())) {
            Optional<StageExecution> parent =
                    execution.getStages().stream()
                            .filter(s -> s.getId().equals(stage.getParentStageId()))
                            .findFirst();

            if (!parent.isPresent()) {
                throw new IllegalStateException(
                        "Couldn't find parent of stage "
                                + stage.getId()
                                + " with parent "
                                + stage.getParentStageId());
            }

            if (predicate.test(parent.get())) {
                matchingStage = parent.get();
            } else {
                matchingStage = findAncestor(parent.get(), execution, predicate);
            }
        }

        return matchingStage;
    }

    /**
     * Recursively get all stages that are children of the current one
     */
    @Nonnull
    public List<StageExecution> allDownstreamStages() {
        List<StageExecution> children = new ArrayList<>();

        if (pipelineExecution != null) {
            List<StageExecution> notVisited = new ArrayList<>(pipelineExecution.getStages());
            LinkedList<StageExecution> queue = new LinkedList<>();

            queue.push(this);
            boolean first = true;

            while (!queue.isEmpty()) {
                StageExecution stage = queue.pop();
                if (!first) {
                    children.add(stage);
                }

                first = false;
                notVisited.remove(stage);

                notVisited.stream()
                        .filter(
                                s -> s.getRequisiteStageRefIds().contains(stage.getRefId()) && !queue.contains(s))
                        .forEach(queue::add);
            }
        }

        return children;
    }

    /**
     * Gets all direct children of the current stage. This is not a recursive method and will return
     * only the children in the first level of the stage.
     */
    @Nonnull
    public List<StageExecution> directChildren() {
        if (pipelineExecution != null) {
            return pipelineExecution.getStages().stream()
                    .filter(
                            stage -> stage.getParentStageId() != null && stage.getParentStageId().equals(getId()))
                    .collect(toList());
        }
        return emptyList();
    }


    /**
     * Returns the parent of this stage or null if it is a top-level stage.
     */
    @JsonIgnore
    public @Nullable StageExecution getParent() {
        if (parentStageId == null) {
            return null;
        } else {
            return pipelineExecution.stageById(parentStageId);
        }
    }

    /**
     * Returns the top-most stage.
     */
    @Nonnull
    @JsonIgnore
    public StageExecution getTopLevelStage() {
        StageExecution topLevelStage = this;
        while (topLevelStage.getParentStageId() != null) {
            Long sid = topLevelStage.getParentStageId();
            Optional<StageExecution> stage =
                    pipelineExecution.getStages().stream().filter(s -> s.getId().equals(sid)).findFirst();
            if (stage.isPresent()) {
                topLevelStage = stage.get();
            } else {
                throw new IllegalStateException(
                        "Could not find stage by parentStageId (stage: "
                                + topLevelStage.getId()
                                + ", parentStageId:"
                                + sid
                                + ")");
            }
        }
        return topLevelStage;
    }

    @Nonnull
    @JsonIgnore
    public Optional<StageExecution> getParentWithTimeout() {
        StageExecution current = this;
        Optional<Long> timeout = Optional.empty();

        while (current != null && !timeout.isPresent()) {
            timeout = current.getTimeout();
            if (!timeout.isPresent()) {
                current = current.getParent();
            }
        }

        return timeout.isPresent() ? Optional.of(current) : Optional.empty();
    }

    @Nonnull
    @JsonIgnore
    public Optional<Long> getTimeout() {
        Object timeout = getContext().get(STAGE_TIMEOUT_OVERRIDE_KEY);
        if (timeout instanceof Integer) {
            return Optional.of((Integer) timeout).map(Integer::longValue);
        } else if (timeout instanceof Long) {
            return Optional.of((Long) timeout);
        } else if (timeout instanceof Double) {
            return Optional.of((Double) timeout).map(Double::longValue);
        }
        return Optional.empty();
    }

    /**
     * Maps the stage's context to a typed object
     */
    @Nonnull
    public <O> O mapTo(@Nonnull Class<O> type) {
        return mapTo(null, type);
    }

    /**
     * Maps the stage's context to a typed object at a provided pointer. Uses <a
     * href="https://tools.ietf.org/html/rfc6901">JSON Pointer</a> notation for determining the
     * pointer's position
     */
    @Nonnull
    public <O> O mapTo(@Nullable String pointer, @Nonnull Class<O> type) {
        try {
            ObjectMapper objectMapper = JacksonUtil.getMapper();
            return objectMapper.readValue(
                    new TreeTraversingParser(
                            getPointer(pointer != null ? pointer : "", contextToNode()), objectMapper),
                    type);
        } catch (IOException e) {
            throw new IllegalArgumentException(format("Unable to map context to %s", type), e);
        }
    }

    @Nonnull
    public <O> O decodeBase64(@Nullable String pointer, @Nonnull Class<O> type) {
        return decodeBase64(pointer, type, JacksonUtil.getMapper());
    }

    public <O> O decodeBase64(String pointer, Class<O> type, ObjectMapper objectMapper) {
        byte[] data;
        try {
            TreeTraversingParser parser =
                    new TreeTraversingParser(
                            getPointer(pointer != null ? pointer : "", contextToNode()), objectMapper);
            parser.nextToken();
            data = Base64.getDecoder().decode(parser.getText());
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "Value in stage context at pointer " + pointer + " is not base 64 encoded", e);
        }

        try {
            return objectMapper.readValue(data, type);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not convert " + new String(data, UTF_8) + " to " + type.getSimpleName(), e);
        }
    }

    public void appendErrorMessage(String errorMessage) {
        Map<String, Object> exception =
                (Map<String, Object>) getContext().getOrDefault("exception", new HashMap<String, Object>());

        Map<String, Object> exceptionDetails =
                (Map<String, Object>) exception.getOrDefault("details", new HashMap<String, Object>());
        exception.putIfAbsent("details", exceptionDetails);
        List<String> errors =
                (List<String>) exceptionDetails.getOrDefault("errors", new ArrayList<String>());
        exceptionDetails.putIfAbsent("errors", errors);

        // Path: exception.details.errors
        errors.add(errorMessage);

        // This might be a no-op, but if there wasn't an exception object there, we should add it to the
        // context
        getContext().put("exception", exception);
    }

    private JsonNode getPointer(String pointer, ObjectNode rootNode) {
        return pointer != null ? rootNode.at(pointer) : rootNode;
    }

    private ObjectNode contextToNode() {
        return (ObjectNode) JacksonUtil.toJsonNode(context);
    }

    /**
     * Enriches stage context if it supports strategies
     */
    @SuppressWarnings("unchecked")
    public void resolveStrategyParams() {
        if (pipelineExecution.getType() == ExecutionType.PIPELINE) {
            Map<String, Object> parameters =
                    Optional.ofNullable(pipelineExecution.getTrigger())
                            .map(Trigger::getParameters)
                            .orElse(emptyMap());
            boolean strategy = false;
            if (parameters.get("strategy") != null) {
                strategy = (boolean) parameters.get("strategy");
            }
            if (strategy) {
                context.put("cloudProvider", parameters.get("cloudProvider"));
                context.put("cluster", parameters.get("cluster"));
                context.put("credentials", parameters.get("credentials"));
                if (parameters.get("region") != null) {
                    context.put("regions", singletonList(parameters.get("region")));
                } else if (parameters.get("zone") != null) {
                    context.put("zones", singletonList(parameters.get("zone")));
                }
            }
        }
    }


    /**
     * Check if this stage should propagate FAILED_CONTINUE to parent stage. Normally, if a synthetic
     * child fails with FAILED_CONTINUE {@link
     * com.netflix.spinnaker.orca.q.handler.CompleteStageHandler} will propagate the FAILED_CONTINUE
     * status to the parent, preventing all subsequent sibling stages from executing. This allows for
     * an option (similar to Tasks) to continue execution if a child stage returns FAILED_CONTINUE
     *
     * @return true if we want to allow subsequent siblings to continue even if this stage returns
     * FAILED_CONTINUE
     */
    @JsonIgnore
    public boolean getAllowSiblingStagesToContinueOnFailure() {
        if (parentStageId == null) {
            return false;
        }

        StageContext context = (StageContext) getContext();
        return (boolean) context.getCurrentOnly("allowSiblingStagesToContinueOnFailure", false);
    }


    @JsonIgnore
    public void setContinuePipelineOnFailure(boolean continuePipeline) {
        context.put("continuePipeline", continuePipeline);
    }

    @JsonIgnore
    public boolean getContinuePipelineOnFailure() {
        StageContext context = (StageContext) getContext();
        return (boolean) context.getCurrentOnly("continuePipeline", false);
    }

    @JsonIgnore
    public boolean isJoin() {
        return getRequisiteStageRefIds().size() > 1;
    }

    @JsonIgnore
    @Override
    public boolean isManualJudgmentType() {
        return Objects.equals(this.type, "manualJudgment");
    }

    @Override
    public boolean withPropagateAuthentication() {
        return context.get("propagateAuthenticationContext") != null
                && Boolean.parseBoolean(context.get("propagateAuthenticationContext").toString());
    }

    @Nonnull
    @JsonIgnore
    public List<StageExecution> downstreamStages() {
        return pipelineExecution.getStages().stream()
                .filter(it -> it.getRequisiteStageRefIds().contains(getRefId()))
                .collect(toList());
    }

    /**
     * NOTE: this function is mostly for convenience to endusers using SpEL
     *
     * @return true if stage has succeeded
     */
    @JsonIgnore
    public boolean getHasSucceeded() {
        return (status == ExecutionStatus.SUCCEEDED);
    }

    /**
     * NOTE: this function is mostly for convenience to endusers using SpEL
     *
     * @return true if stage has failed
     */
    @JsonIgnore
    public boolean getHasFailed() {
        return (status == ExecutionStatus.TERMINAL);
    }


}
