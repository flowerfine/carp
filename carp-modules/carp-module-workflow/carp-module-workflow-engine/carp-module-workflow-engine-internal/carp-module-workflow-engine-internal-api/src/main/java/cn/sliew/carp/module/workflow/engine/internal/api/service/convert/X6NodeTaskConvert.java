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
package cn.sliew.carp.module.workflow.engine.internal.api.service.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.framework.dag.x6.dnd.X6NodeDTO;
import cn.sliew.carp.framework.dag.x6.dnd.X6NodeDataDTO;
import cn.sliew.carp.framework.dag.x6.dnd.X6NodePortDTO;
import cn.sliew.carp.framework.dag.x6.dnd.X6PositionDTO;
import cn.sliew.carp.module.workflow.engine.internal.domain.definition.WorkflowDefinitionGraphNode;
import cn.sliew.carp.module.workflow.engine.internal.domain.instance.WorkflowStepInstance;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface X6NodeTaskConvert extends BaseConvert<WorkflowStepInstance, X6NodeDTO> {
    X6NodeTaskConvert INSTANCE = Mappers.getMapper(X6NodeTaskConvert.class);

    @Override
    default X6NodeDTO toDto(WorkflowStepInstance entity) {
        WorkflowDefinitionGraphNode node = entity.getNode();
        return X6NodeDTO.builder()
                .id(node.getStepId())
                .shape(node.getShape())
                .position(X6PositionDTO.builder()
                        .x(node.getPositionX())
                        .y(node.getPositionY())
                        .build())
                .port(X6NodePortDTO.builder()
                        .id(node.getStepId() + "_in")
                        .group("in")
                        .build())
                .port(X6NodePortDTO.builder()
                        .id(node.getStepId() + "_out")
                        .group("out")
                        .build())
                .data(X6NodeDataDTO.builder()
                        .label(node.getStepName())
                        .meta(node.getMeta())
                        .attrs(node.getAttrs())
                        .build())
                .build();
    }

    @Override
    default WorkflowStepInstance toDo(X6NodeDTO dto) {
        throw new UnsupportedOperationException();
    }

}
