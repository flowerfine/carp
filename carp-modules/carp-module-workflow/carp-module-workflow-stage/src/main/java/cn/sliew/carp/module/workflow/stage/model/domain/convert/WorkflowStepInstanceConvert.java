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
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepContext;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowStepInstanceConvert extends BaseConvert<DagStepDTO, WorkflowStepInstance> {
    WorkflowStepInstanceConvert INSTANCE = Mappers.getMapper(WorkflowStepInstanceConvert.class);

    @Override
    default DagStepDTO toDo(WorkflowStepInstance dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    default WorkflowStepInstance toDto(DagStepDTO entity) {
        WorkflowStepInstance dto = new WorkflowStepInstance();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getDagInstance() != null) {
            dto.setWorkflowInstance(WorkflowInstanceConvert.INSTANCE.toDto(entity.getDagInstance()));
        }
        if (entity.getDagConfigStep() != null) {
            dto.setNode(WorkflowDefinitionGraphNodeConvert.INSTANCE.toDto(entity.getDagConfigStep()));
        }
        if (Objects.nonNull(entity.getInputs())
                && entity.getInputs().isNull() == false
                && entity.getInputs().isEmpty() == false) {
            dto.setInputs(JacksonUtil.toObject(entity.getInputs(), new TypeReference<Map<String, Object>>() {
            }));
        } else {
            dto.setInputs(Maps.newHashMap());
        }
        if (Objects.nonNull(entity.getOutputs())
                && entity.getOutputs().isNull() == false
                && entity.getOutputs().isEmpty() == false) {
            dto.setOutputs(JacksonUtil.toObject(entity.getOutputs(), new TypeReference<Map<String, Object>>() {
            }));
        } else {
            dto.setOutputs(Maps.newHashMap());
        }
        dto.setContext(new WorkflowStepContext(dto));
        return dto;
    }
}
