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
package cn.sliew.carp.module.workflow.domain.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.framework.dag.service.dto.DagStepTaskDTO;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowTaskInstance;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowStepTaskInstanceConvert extends BaseConvert<DagStepTaskDTO, WorkflowTaskInstance> {
    WorkflowStepTaskInstanceConvert INSTANCE = Mappers.getMapper(WorkflowStepTaskInstanceConvert.class);

    @Override
    default DagStepTaskDTO toDo(WorkflowTaskInstance dto) {
        DagStepTaskDTO entity = new DagStepTaskDTO();
        BeanUtils.copyProperties(dto, entity);
        entity.setDagInstanceId(dto.getWorkflowInstanceId());
        entity.setDagStepId(dto.getStepId());
        if (Objects.nonNull(dto.getStartTime())) {
            entity.setStartTime(Date.from(dto.getStartTime()));
        }
        if (Objects.nonNull(dto.getEndTime())) {
            entity.setEndTime(Date.from(dto.getEndTime()));
        }
        return entity;
    }

    @Override
    default WorkflowTaskInstance toDto(DagStepTaskDTO entity) {
        WorkflowTaskInstance dto = new WorkflowTaskInstance();
        BeanUtils.copyProperties(entity, dto);
        dto.setWorkflowInstanceId(entity.getDagInstanceId());
        dto.setStepId(entity.getDagStepId());
        if (Objects.nonNull(entity.getStartTime())) {
            dto.setStartTime(entity.getStartTime().toInstant());
        }
        if (Objects.nonNull(entity.getEndTime())) {
            dto.setEndTime(entity.getEndTime().toInstant());
        }
        return dto;
    }
}
