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
package cn.sliew.carp.module.scheduler.service.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.module.scheduler.repository.entity.ScheduleJobInstance;
import cn.sliew.carp.module.scheduler.service.dto.ScheduleJobInstanceDTO;
import cn.sliew.milky.common.util.JacksonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Mapper
public interface ScheduleJobInstanceConvert extends BaseConvert<ScheduleJobInstance, ScheduleJobInstanceDTO> {
    ScheduleJobInstanceConvert INSTANCE = Mappers.getMapper(ScheduleJobInstanceConvert.class);

    @Override
    default ScheduleJobInstanceDTO toDto(ScheduleJobInstance entity) {
        ScheduleJobInstanceDTO dto = new ScheduleJobInstanceDTO();
        BeanUtils.copyProperties(entity, dto);
        if (StringUtils.hasText(entity.getProps())) {
            dto.setProps(JacksonUtil.toJsonNode(entity.getProps()));
        }
        if (Objects.nonNull(entity.getJobConfig())) {
            dto.setJobConfig(ScheduleJobConfigConvert.INSTANCE.toDto(entity.getJobConfig()));
        }
        return dto;
    }

    @Override
    default ScheduleJobInstance toDo(ScheduleJobInstanceDTO dto) {
        ScheduleJobInstance entity = new ScheduleJobInstance();
        BeanUtils.copyProperties(dto, entity);
        if (dto.getProps() != null) {
            entity.setProps(dto.getProps().toString());
        }
        if (Objects.nonNull(dto.getJobConfig())) {
            entity.setJobConfigId(dto.getJobConfig().getId());
        }
        return entity;
    }
}
