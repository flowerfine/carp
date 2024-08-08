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

package cn.sliew.carp.module.datasource.modal.file;

import cn.sliew.carp.framework.common.dict.datasource.DataSourceType;
import cn.sliew.carp.module.datasource.modal.AbstractDataSource;
import cn.sliew.carp.module.datasource.service.dto.DsInfoDTO;
import cn.sliew.carp.module.datasource.service.dto.DsTypeDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class HDFSDataSource extends AbstractDataSource {

    @Schema(description = "hdfs hdfs-site.xml path")
    private String hdfsSitePath;

    @NotBlank
    @Schema(description = "hdfs fs.defaultFS property")
    private String fsDefaultFS;

    @Override
    public DataSourceType getType() {
        return DataSourceType.HDFS;
    }

    @Override
    public DsInfoDTO toDsInfo() {
        DsInfoDTO dto = new DsInfoDTO();
        BeanUtils.copyProperties(this, dto);
        DsTypeDTO dsType = new DsTypeDTO();
        dsType.setId(getDsTypeId());
        dsType.setType(getType());
        dto.setDsType(dsType);
        Map<String, Object> props = new HashMap<>();
        props.put("fsDefaultFS", fsDefaultFS);
        if (StringUtils.hasText(hdfsSitePath)) {
            props.put("hdfsSitePath", hdfsSitePath);
        }
        dto.setProps(props);
        return dto;
    }
}
