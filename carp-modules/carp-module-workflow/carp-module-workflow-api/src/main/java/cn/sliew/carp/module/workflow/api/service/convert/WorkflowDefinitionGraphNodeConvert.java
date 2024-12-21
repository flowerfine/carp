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
package cn.sliew.carp.module.workflow.api.service.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.framework.dag.service.dto.DagConfigStepDTO;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinitionGraphNode;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinitionGraphNodeAttrs;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinitionGraphNodeMeta;
import cn.sliew.milky.common.util.JacksonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowDefinitionGraphNodeConvert extends BaseConvert<DagConfigStepDTO, WorkflowDefinitionGraphNode> {
    WorkflowDefinitionGraphNodeConvert INSTANCE = Mappers.getMapper(WorkflowDefinitionGraphNodeConvert.class);

    @Override
    default DagConfigStepDTO toDo(WorkflowDefinitionGraphNode dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    default WorkflowDefinitionGraphNode toDto(DagConfigStepDTO entity) {
        WorkflowDefinitionGraphNode dto = new WorkflowDefinitionGraphNode();
        BeanUtils.copyProperties(entity, dto);
        dto.setWorkflowDefinitionId(entity.getDagId());
        if (entity.getStepMeta() != null) {
            dto.setMeta(JacksonUtil.toObject(entity.getStepMeta(), WorkflowDefinitionGraphNodeMeta.class));
        }
        if (entity.getStepAttrs() != null) {
            dto.setAttrs(JacksonUtil.toObject(entity.getStepAttrs(), WorkflowDefinitionGraphNodeAttrs.class));
        }
        return dto;
    }
}
