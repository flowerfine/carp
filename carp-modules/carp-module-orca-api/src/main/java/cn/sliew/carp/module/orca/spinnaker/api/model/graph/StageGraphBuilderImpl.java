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
package cn.sliew.carp.module.orca.spinnaker.api.model.graph;

import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class StageGraphBuilderImpl implements StageGraphBuilder {

    private final StageExecution parent;
    private final SyntheticStageOwner type;
    private final MutableGraph<StageExecution> graph = GraphBuilder.directed().build();
    private final Optional<StageExecution> requiredPrefix;
    private StageExecution lastAdded = null;

    private StageGraphBuilderImpl(StageExecution parent, SyntheticStageOwner type, Optional<StageExecution> requiredPrefix) {
        this.parent = parent;
        this.type = type;
        this.requiredPrefix = requiredPrefix;
        this.requiredPrefix.ifPresent(this::add);
    }

    public static StageGraphBuilderImpl beforeStages(StageExecution parent) {
        return new StageGraphBuilderImpl(parent, SyntheticStageOwner.STAGE_BEFORE, Optional.empty());
    }

    public static StageGraphBuilderImpl beforeStages(StageExecution parent, StageExecution requiredPrefix) {
        return new StageGraphBuilderImpl(parent, SyntheticStageOwner.STAGE_BEFORE, Optional.ofNullable(requiredPrefix));
    }

    public static StageGraphBuilderImpl afterStages(StageExecution parent) {
        return new StageGraphBuilderImpl(parent, SyntheticStageOwner.STAGE_AFTER, Optional.empty());
    }

    @Override
    public StageExecution add(Consumer<StageExecution> init) {
        StageExecution stage = newStage(init);
        add(stage);
        return stage;
    }

    @Override
    public void add(StageExecution stage) {
        StageExecutionImpl stageImpl = (StageExecutionImpl) stage;
        stageImpl.setPipelineExecution(parent.getPipelineExecution());
        stageImpl.setParentStageId(parent.getId());
        stageImpl.setSyntheticStageOwner(type);
        if (graph.addNode(stage)) {
            stageImpl.setRefId(generateRefId());
        }
        lastAdded = stage;
    }

    @Override
    public void connect(StageExecution previous, StageExecution next) {
        StageExecutionImpl nextStageImpl = (StageExecutionImpl) next;
        add(previous);
        add(next);
        Set<String> requisiteStageRefIds = new HashSet<>(next.getRequisiteStageRefIds());
        requisiteStageRefIds.add(previous.getRefId());
        nextStageImpl.setRequisiteStageRefIds(requisiteStageRefIds);
        graph.putEdge(previous, next);
    }

    @Override
    public StageExecution append(Consumer<StageExecution> init) {
        if (Objects.isNull(lastAdded)) {
            return add(init);
        } else {
            return connect(lastAdded, init);
        }
    }

    @Override
    public void append(StageExecution stage) {
        if (Objects.isNull(lastAdded)) {
            add(stage);
        } else {
            connect(lastAdded, stage);
        }
    }

    @Override
    public Iterable<StageExecution> build() {
        requiredPrefix.ifPresent(
                prefix ->
                        graph
                                .nodes()
                                .forEach(
                                        it -> {
                                            if (it != prefix && it.getRequisiteStageRefIds().isEmpty()) {
                                                connect(prefix, it);
                                            }
                                        }));
        return graph.nodes();
    }

    private String generateRefId() {
        long offset =
                parent.getPipelineExecution().getStages().stream()
                        .filter(
                                i ->
                                        parent.getId().equals(i.getParentStageId())
                                                && type == i.getSyntheticStageOwner())
                        .count();

        return String.format(
                "%s%s%d",
                parent.getRefId(), type == SyntheticStageOwner.STAGE_BEFORE ? "<" : ">", offset + graph.nodes().size());
    }

    private StageExecution newStage(Consumer<StageExecution> init) {
        StageExecution stage = new StageExecutionImpl();
        init.accept(stage);
        return stage;
    }
}
