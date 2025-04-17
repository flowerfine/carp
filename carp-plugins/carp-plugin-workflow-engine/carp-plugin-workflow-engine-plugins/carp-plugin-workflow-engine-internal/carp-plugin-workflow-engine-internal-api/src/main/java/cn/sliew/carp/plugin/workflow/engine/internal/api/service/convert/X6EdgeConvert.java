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
package cn.sliew.carp.plugin.workflow.engine.internal.api.service.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.framework.dag.x6.dnd.X6EdgeDTO;
import cn.sliew.carp.framework.dag.x6.dnd.X6EdgeDataDTO;
import cn.sliew.carp.framework.dag.x6.dnd.X6EdgePortDTO;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.definition.WorkflowDefinitionGraphEdge;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.definition.WorkflowDefinitionGraphEdgeAttrs;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.definition.WorkflowDefinitionGraphEdgeMeta;
import cn.sliew.milky.common.util.JacksonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface X6EdgeConvert extends BaseConvert<WorkflowDefinitionGraphEdge, X6EdgeDTO> {
    X6EdgeConvert INSTANCE = Mappers.getMapper(X6EdgeConvert.class);

    @Override
    default X6EdgeDTO toDto(WorkflowDefinitionGraphEdge entity) {
        return X6EdgeDTO.builder()
                .id(entity.getLinkId())
                .shape(entity.getShape())
                .source(X6EdgePortDTO.builder()
                        .cell(entity.getFromStepId())
                        .port(entity.getFromStepId() + "_out")
                        .build())
                .target(X6EdgePortDTO.builder()
                        .cell(entity.getToStepId())
                        .port(entity.getToStepId() + "_in")
                        .build())
                .data(X6EdgeDataDTO.builder()
                        .label(entity.getLinkName())
                        .meta(entity.getMeta())
                        .attrs(entity.getAttrs())
                        .build())
                .build();
    }

    @Override
    default WorkflowDefinitionGraphEdge toDo(X6EdgeDTO dto) {
        WorkflowDefinitionGraphEdge graphEdge = new WorkflowDefinitionGraphEdge();
        graphEdge.setNamespace(dto.getData().getMeta().getNamespace());
        graphEdge.setLinkId(dto.getId());
        graphEdge.setLinkName(dto.getData().getLabel());
        graphEdge.setFromStepId(dto.getSource().getCell());
        graphEdge.setToStepId(dto.getTarget().getCell());
        graphEdge.setShape(dto.getShape());

        graphEdge.setMeta(JacksonUtil.toObject(JacksonUtil.toJsonNode(dto.getData().getMeta()), WorkflowDefinitionGraphEdgeMeta.class));
        graphEdge.setAttrs(JacksonUtil.toObject(JacksonUtil.toJsonNode(dto.getData().getAttrs()), WorkflowDefinitionGraphEdgeAttrs.class));
        return graphEdge;
    }

}
