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
package cn.sliew.carp.module.workflow.stage.model.domain.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.framework.dag.service.dto.DagConfigLinkDTO;
import cn.sliew.carp.module.workflow.stage.model.domain.definition.WorkflowDefinitionGraphEdge;
import cn.sliew.carp.module.workflow.stage.model.domain.definition.WorkflowDefinitionGraphEdgeAttrs;
import cn.sliew.carp.module.workflow.stage.model.domain.definition.WorkflowDefinitionGraphEdgeMeta;
import cn.sliew.milky.common.util.JacksonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowDefinitionGraphEdgeConvert extends BaseConvert<DagConfigLinkDTO, WorkflowDefinitionGraphEdge> {
    WorkflowDefinitionGraphEdgeConvert INSTANCE = Mappers.getMapper(WorkflowDefinitionGraphEdgeConvert.class);

    @Override
    default DagConfigLinkDTO toDo(WorkflowDefinitionGraphEdge dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    default WorkflowDefinitionGraphEdge toDto(DagConfigLinkDTO entity) {
        WorkflowDefinitionGraphEdge dto = new WorkflowDefinitionGraphEdge();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getLinkMeta() != null) {
            dto.setMeta(JacksonUtil.toObject(entity.getLinkMeta(), WorkflowDefinitionGraphEdgeMeta.class));
        }
        if (entity.getLinkAttrs() != null) {
            dto.setAttrs(JacksonUtil.toObject(entity.getLinkAttrs(), WorkflowDefinitionGraphEdgeAttrs.class));
        }
        return dto;
    }
}
