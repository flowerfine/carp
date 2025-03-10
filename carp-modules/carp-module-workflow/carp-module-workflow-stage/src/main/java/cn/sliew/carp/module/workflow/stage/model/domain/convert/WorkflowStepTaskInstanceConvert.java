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
import cn.sliew.carp.framework.dag.service.dto.DagStepTaskDTO;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecutionImpl;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowStepTaskInstanceConvert extends BaseConvert<DagStepTaskDTO, TaskExecutionImpl> {
    WorkflowStepTaskInstanceConvert INSTANCE = Mappers.getMapper(WorkflowStepTaskInstanceConvert.class);

    @Override
    default DagStepTaskDTO toDo(TaskExecutionImpl dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    default TaskExecutionImpl toDto(DagStepTaskDTO entity) {
        TaskExecutionImpl dto = new TaskExecutionImpl();
        BeanUtils.copyProperties(entity, dto);
        if (StringUtils.hasText(entity.getStatus())) {
            dto.setStatus(ExecutionStatus.valueOf(entity.getStatus()));
        }
        if (Objects.nonNull(entity.getStartTime())) {
            dto.setStartTime(entity.getStartTime().toInstant());
        }
        if (Objects.nonNull(entity.getEndTime())) {
            dto.setEndTime(entity.getEndTime().toInstant());
        }
        return dto;
    }
}
