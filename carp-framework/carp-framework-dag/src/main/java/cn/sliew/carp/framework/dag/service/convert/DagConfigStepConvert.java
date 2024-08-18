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

package cn.sliew.carp.framework.dag.service.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.framework.dag.repository.entity.DagConfigStep;
import cn.sliew.carp.framework.dag.service.dto.DagConfigStepDTO;
import cn.sliew.milky.common.util.JacksonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DagConfigStepConvert extends BaseConvert<DagConfigStep, DagConfigStepDTO> {
    DagConfigStepConvert INSTANCE = Mappers.getMapper(DagConfigStepConvert.class);

    @Override
    default DagConfigStep toDo(DagConfigStepDTO dto) {
        DagConfigStep entity = new DagConfigStep();
        BeanUtils.copyProperties(dto, entity);
        if (dto.getStyle() != null) {
            entity.setStyle(dto.getStyle().toString());
        }
        if (dto.getStepMeta() != null) {
            entity.setStepMeta(dto.getStepMeta().toString());
        }
        if (dto.getStepAttrs() != null) {
            entity.setStepAttrs(dto.getStepAttrs().toString());
        }
        return entity;
    }

    @Override
    default DagConfigStepDTO toDto(DagConfigStep entity) {
        DagConfigStepDTO dto = new DagConfigStepDTO();
        BeanUtils.copyProperties(entity, dto);
        if (StringUtils.hasText(entity.getStyle())) {
            dto.setStyle(JacksonUtil.toJsonNode(entity.getStyle()));
        }
        if (StringUtils.hasText(entity.getStepMeta())) {
            dto.setStepMeta(JacksonUtil.toJsonNode(entity.getStepMeta()));
        }
        if (StringUtils.hasText(entity.getStepAttrs())) {
            dto.setStepAttrs(JacksonUtil.toJsonNode(entity.getStepAttrs()));
        }
        return dto;
    }
}
