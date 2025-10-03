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

import cn.sliew.carp.framework.common.convert.BaseConvert;
import cn.sliew.carp.module.datasource.service.dto.GravitinoTableDTO;
import org.apache.gravitino.rel.Table;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GravitinoTableConvert extends BaseConvert<Table, GravitinoTableDTO> {
    GravitinoTableConvert INSTANCE = Mappers.getMapper(GravitinoTableConvert.class);

    @Override
    default GravitinoTableDTO toDto(Table entity) {
        GravitinoTableDTO dto = new GravitinoTableDTO();
        dto.setName(entity.name());
        dto.setProperties(entity.properties());
        dto.setComment(entity.comment());
        dto.setColumns(GravitinoColumnConvert.INSTANCE.toDto(Arrays.asList(entity.columns())));
        return dto;
    }

    @Override
    default Table toDo(GravitinoTableDTO dto) {
        throw new UnsupportedOperationException();
    }

}
