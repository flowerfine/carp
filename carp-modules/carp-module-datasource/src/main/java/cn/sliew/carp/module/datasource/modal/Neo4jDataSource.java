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

package cn.sliew.carp.module.datasource.modal;

import cn.sliew.carp.framework.common.codec.CodecUtil;
import cn.sliew.carp.framework.common.dict.datasource.DataSourceType;
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

import static cn.sliew.milky.common.check.Ensures.checkState;

@Data
@EqualsAndHashCode(callSuper = true)
public class Neo4jDataSource extends AbstractDataSource {

    @NotBlank
    @Schema(description = "uri")
    private String uri;

    @Schema(description = "username")
    private String username;

    @Schema(description = "password")
    private String password;

    @Schema(description = "Bearer Token")
    private String bearerToken;

    @Schema(description = "Kerberos Ticket")
    private String kerberosTicket;

    @Override
    public DataSourceType getType() {
        return DataSourceType.NEO4J;
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
        props.put("uri", uri);
        if (StringUtils.hasText(username)) {
            checkState(StringUtils.hasText(password), () -> "password must provide where username specified");
            props.put("username", username);
            props.put("password", CodecUtil.encrypt(password));
        }
        if (StringUtils.hasText(bearerToken)) {
            props.put("bearerToken", bearerToken);
        }
        if (StringUtils.hasText(kerberosTicket)) {
            props.put("kerberosTicket", kerberosTicket);
        }
        dto.setProps(props);
        return dto;
    }
}
