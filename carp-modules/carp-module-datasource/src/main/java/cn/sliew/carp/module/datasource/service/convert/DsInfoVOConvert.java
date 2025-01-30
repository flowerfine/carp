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
package cn.sliew.carp.module.datasource.service.convert;

import cn.sliew.carp.framework.common.codec.CodecUtil;
import cn.sliew.carp.framework.common.collection.PropValuePair;
import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.module.datasource.repository.entity.DsInfoVO;
import cn.sliew.carp.module.datasource.service.dto.DsInfoDTO;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DsInfoVOConvert extends BaseConvert<DsInfoVO, DsInfoDTO> {
    DsInfoVOConvert INSTANCE = Mappers.getMapper(DsInfoVOConvert.class);

    @Override
    default DsInfoDTO toDto(DsInfoVO entity) {
        DsInfoDTO dto = new DsInfoDTO();
        BeanUtils.copyProperties(entity, dto);
        if (StringUtils.hasText(entity.getProps())) {
            String jsonProps = CodecUtil.decodeFromBase64(entity.getProps());
            dto.setProps(JacksonUtil.parseJsonString(jsonProps, new TypeReference<Map<String, Object>>() {}));
        }
        if (StringUtils.hasText(entity.getAdditionalProps())) {
            String jsonAdditionalProps = CodecUtil.decodeFromBase64(entity.getAdditionalProps());
            dto.setAdditionalProps(JacksonUtil.parseJsonArray(jsonAdditionalProps, PropValuePair.class));
        }
        dto.setDsType(DsTypeConvert.INSTANCE.toDto(entity.getDsType()));
        return dto;
    }

    @Override
    default DsInfoVO toDo(DsInfoDTO dto) {
        DsInfoVO record = new DsInfoVO();
        BeanUtils.copyProperties(dto, record);
        if (CollectionUtils.isEmpty(dto.getProps()) == false) {
            String jsonProps = JacksonUtil.toJsonString(dto.getProps());
            record.setProps(CodecUtil.encodeToBase64(jsonProps));
        }
        if (CollectionUtils.isEmpty(dto.getAdditionalProps()) == false) {
            String jsonAdditionalProps = JacksonUtil.toJsonString(dto.getAdditionalProps());
            record.setAdditionalProps(CodecUtil.encodeToBase64(jsonAdditionalProps));
        }
        record.setDsType(DsTypeConvert.INSTANCE.toDo(dto.getDsType()));
        return record;
    }
}
