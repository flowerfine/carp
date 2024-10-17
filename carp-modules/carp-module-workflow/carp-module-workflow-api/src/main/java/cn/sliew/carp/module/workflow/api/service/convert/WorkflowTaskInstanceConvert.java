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
import cn.sliew.carp.framework.common.dict.workflow.WorkflowTaskInstanceStage;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowTaskInstance;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowTaskInstanceConvert extends BaseConvert<DagStepDTO, WorkflowTaskInstance> {
    WorkflowTaskInstanceConvert INSTANCE = Mappers.getMapper(WorkflowTaskInstanceConvert.class);

    @Override
    default DagStepDTO toDo(WorkflowTaskInstance dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    default WorkflowTaskInstance toDto(DagStepDTO entity) {
        WorkflowTaskInstance dto = new WorkflowTaskInstance();
        BeanUtils.copyProperties(entity, dto);
        dto.setWorkflowInstanceId(entity.getDagInstanceId());
        if (entity.getDagConfigStep() != null) {
            dto.setNode(WorkflowDefinitionGraphNodeConvert.INSTANCE.toDto(entity.getDagConfigStep()));
        }
        if (StringUtils.hasText(entity.getStatus())) {
            dto.setStatus(WorkflowTaskInstanceStage.of(entity.getStatus()));
        }
        return dto;
    }
}
