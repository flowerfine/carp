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
import cn.sliew.carp.framework.dag.service.dto.DagConfigDTO;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinition;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinitionAttrs;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinitionMeta;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowParamOption;
import cn.sliew.milky.common.util.JacksonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowDefinitionConvert extends BaseConvert<DagConfigDTO, WorkflowDefinition> {
    WorkflowDefinitionConvert INSTANCE = Mappers.getMapper(WorkflowDefinitionConvert.class);

    @Override
    default DagConfigDTO toDo(WorkflowDefinition dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    default WorkflowDefinition toDto(DagConfigDTO entity) {
        WorkflowDefinition dto = new WorkflowDefinition();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getDagMeta() != null) {
            dto.setMeta(JacksonUtil.toObject(entity.getDagMeta(), WorkflowDefinitionMeta.class));
        }
        if (entity.getDagAttrs() != null) {
            dto.setAttrs(JacksonUtil.toObject(entity.getDagAttrs(), WorkflowDefinitionAttrs.class));
        }
        if (entity.getInputOptions() != null) {
            dto.setInputOptions(JacksonUtil.parseJsonArray(entity.getInputOptions().toString(), WorkflowParamOption.class));
        }
        if (entity.getOutputOptions() != null) {
            dto.setOutputOptions(JacksonUtil.parseJsonArray(entity.getOutputOptions().toString(), WorkflowParamOption.class));
        }
        return dto;
    }
}
