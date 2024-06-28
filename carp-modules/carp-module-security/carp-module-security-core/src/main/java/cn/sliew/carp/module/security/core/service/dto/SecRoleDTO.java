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

package cn.sliew.carp.module.security.core.service.dto;

import cn.sliew.carp.framework.common.dict.security.RoleStatus;
import cn.sliew.carp.framework.common.dict.security.RoleType;
import cn.sliew.carp.framework.common.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "角色信息", description = "角色信息表")
public class SecRoleDTO extends BaseDTO {

    @Schema(description = "type")
    private RoleType type;

    @NotBlank
    @Schema(description = "角色编码")
    private String code;

    @NotBlank
    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "order")
    private Integer order;

    @Schema(description = "角色状态")
    private RoleStatus status;

    @Schema(description = "角色备注")
    private String remark;
}