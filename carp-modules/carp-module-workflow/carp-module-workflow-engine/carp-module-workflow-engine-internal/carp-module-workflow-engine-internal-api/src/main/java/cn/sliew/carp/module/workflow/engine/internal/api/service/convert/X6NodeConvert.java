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
import cn.sliew.carp.module.workflow.engine.internal.domain.definition.WorkflowDefinitionGraphNodeAttrs;
import cn.sliew.carp.module.workflow.engine.internal.domain.definition.WorkflowDefinitionGraphNodeMeta;
import cn.sliew.milky.common.util.JacksonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface X6NodeConvert extends BaseConvert<WorkflowDefinitionGraphNode, X6NodeDTO> {
    X6NodeConvert INSTANCE = Mappers.getMapper(X6NodeConvert.class);

    @Override
    default X6NodeDTO toDto(WorkflowDefinitionGraphNode entity) {
        return X6NodeDTO.builder()
                .id(entity.getStepId())
                .shape(entity.getShape())
                .position(X6PositionDTO.builder()
                        .x(entity.getPositionX())
                        .y(entity.getPositionY())
                        .build())
                .port(X6NodePortDTO.builder()
                        .id(entity.getStepId() + "_in")
                        .group("in")
                        .build())
                .port(X6NodePortDTO.builder()
                        .id(entity.getStepId() + "_out")
                        .group("out")
                        .build())
                .data(X6NodeDataDTO.builder()
                        .label(entity.getStepName())
                        .meta(entity.getMeta())
                        .attrs(entity.getAttrs())
                        .build())
                .build();
    }

    @Override
    default WorkflowDefinitionGraphNode toDo(X6NodeDTO dto) {
        WorkflowDefinitionGraphNode graphNode = new WorkflowDefinitionGraphNode();
        graphNode.setNamespace(dto.getData().getMeta().getNamespace());
        graphNode.setStepId(dto.getId());
        graphNode.setStepName(dto.getData().getLabel());
        graphNode.setPositionX(dto.getPosition().getX());
        graphNode.setPositionY(dto.getPosition().getY());
        graphNode.setShape(dto.getShape());
        graphNode.setMeta(JacksonUtil.toObject(JacksonUtil.toJsonNode(dto.getData().getMeta()), WorkflowDefinitionGraphNodeMeta.class));
        graphNode.setAttrs(JacksonUtil.toObject(JacksonUtil.toJsonNode(dto.getData().getAttrs()), WorkflowDefinitionGraphNodeAttrs.class));
        return graphNode;
    }

}
