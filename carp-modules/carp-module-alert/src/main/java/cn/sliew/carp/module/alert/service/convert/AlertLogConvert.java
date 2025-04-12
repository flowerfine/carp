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
package cn.sliew.carp.module.alert.service.convert;

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.module.alert.repository.entity.CarpAlertLog;
import cn.sliew.carp.module.alert.service.dto.CarpAlertLogDTO;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Map;

@Mapper
public interface AlertLogConvert extends BaseConvert<CarpAlertLog, CarpAlertLogDTO> {
    AlertLogConvert INSTANCE = Mappers.getMapper(AlertLogConvert.class);

    @Override
    default CarpAlertLogDTO toDto(CarpAlertLog entity) {
        CarpAlertLogDTO dto = new CarpAlertLogDTO();
        BeanUtils.copyProperties(entity, dto);
        if (StringUtils.hasText(entity.getGroupLabels())) {
            dto.setGroupLabels(JacksonUtil.parseJsonString(entity.getGroupLabels(), new TypeReference<Map<String, String>>() {}));
        }
        if (StringUtils.hasText(entity.getCommonLabels())) {
            dto.setCommonLabels(JacksonUtil.parseJsonString(entity.getCommonLabels(), new TypeReference<Map<String, String>>() {}));
        }
        if (StringUtils.hasText(entity.getCommonAnnotations())) {
            dto.setCommonAnnotations(JacksonUtil.parseJsonString(entity.getCommonAnnotations(), new TypeReference<Map<String, String>>() {}));
        }
        if (StringUtils.hasText(entity.getLabels())) {
            dto.setLabels(JacksonUtil.parseJsonString(entity.getLabels(), new TypeReference<Map<String, String>>() {}));
        }
        if (StringUtils.hasText(entity.getAnnotations())) {
            dto.setAnnotations(JacksonUtil.parseJsonString(entity.getAnnotations(), new TypeReference<Map<String, String>>() {}));
        }
        return dto;
    }

    @Override
    default CarpAlertLog toDo(CarpAlertLogDTO dto) {
        CarpAlertLog entity = new CarpAlertLog();
        BeanUtils.copyProperties(dto, entity);
        if (CollectionUtils.isEmpty(dto.getGroupLabels()) == false) {
            entity.setGroupLabels(JacksonUtil.toJsonString(dto.getGroupLabels()));
        }
        if (CollectionUtils.isEmpty(dto.getCommonLabels()) == false) {
            entity.setCommonLabels(JacksonUtil.toJsonString(dto.getCommonLabels()));
        }
        if (CollectionUtils.isEmpty(dto.getCommonAnnotations()) == false) {
            entity.setCommonAnnotations(JacksonUtil.toJsonString(dto.getCommonAnnotations()));
        }
        if (CollectionUtils.isEmpty(dto.getLabels()) == false) {
            entity.setLabels(JacksonUtil.toJsonString(dto.getLabels()));
        }
        if (CollectionUtils.isEmpty(dto.getAnnotations()) == false) {
            entity.setAnnotations(JacksonUtil.toJsonString(dto.getAnnotations()));
        }
        return entity;
    }
}
