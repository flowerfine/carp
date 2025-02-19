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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.sliew.carp.framework.dag.service.DagLinkService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagLinkDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class StageGraphBuilderImpl implements StageGraphBuilder {

    private final DagStepDTO parent;
    private final SyntheticStageOwner type;
    private final MutableGraph<DagStepDTO> graph = GraphBuilder.directed().build();
    private final Optional<DagStepDTO> requiredPrefix;
    private DagStepDTO lastAdded = null;

    private StageGraphBuilderImpl(DagStepDTO parent, SyntheticStageOwner type, Optional<DagStepDTO> requiredPrefix) {
        this.parent = parent;
        this.type = type;
        this.requiredPrefix = requiredPrefix;
        this.requiredPrefix.ifPresent(this::add);
    }

    public static StageGraphBuilderImpl beforeStages(DagStepDTO parent) {
        return new StageGraphBuilderImpl(parent, SyntheticStageOwner.STAGE_BEFORE, Optional.empty());
    }

    public static StageGraphBuilderImpl beforeStages(DagStepDTO parent, DagStepDTO requiredPrefix) {
        return new StageGraphBuilderImpl(parent, SyntheticStageOwner.STAGE_BEFORE, Optional.ofNullable(requiredPrefix));
    }

    public static StageGraphBuilderImpl afterStages(DagStepDTO parent) {
        return new StageGraphBuilderImpl(parent, SyntheticStageOwner.STAGE_AFTER, Optional.empty());
    }

    @Override
    public DagStepDTO add(Consumer<DagStepDTO> init) {
        DagStepDTO stage = newStage(init);
        add(stage);
        return stage;
    }

    @Override
    public void add(DagStepDTO stage) {
        stage.setDagInstance(parent.getDagInstance());
//        stage.setParentStageId(parent.getId());
//        stage.setSyntheticStageOwner(type);
        if (graph.addNode(stage)) {
//            stage.setRefId(generateRefId());
        }
        lastAdded = stage;
    }

    @Override
    public DagStepDTO append(Consumer<DagStepDTO> init) {
        if (Objects.isNull(lastAdded)) {
            return add(init);
        } else {
            return connect(lastAdded, init);
        }
    }

    @Override
    public void append(DagStepDTO stage) {
        if (Objects.isNull(lastAdded)) {
            add(stage);
        } else {
            connect(lastAdded, stage);
        }
    }

    @Override
    public Iterable<DagStepDTO> build() {
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

    private DagStepDTO newStage(Consumer<DagStepDTO> init) {
        DagStepDTO stage = new DagStepDTO();
        init.accept(stage);
        return stage;
    }
}
