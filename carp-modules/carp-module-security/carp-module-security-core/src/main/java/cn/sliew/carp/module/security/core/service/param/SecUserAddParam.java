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

package cn.sliew.carp.module.security.core.service.param;

import cn.sliew.carp.framework.common.dict.security.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SecUserAddParam {

    @NotBlank
    @Pattern(regexp = "\\w+$")
    @Schema(description = "用户名")
    private String userName;

    @NotBlank
    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "头像")
    private String avatar;

    @Email
    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机")
    private String phone;

    @NotBlank
    @Schema(description = "密码")
    private String password;

    @Schema(description = "排序")
    private Integer order;

    @Schema(description = "status")
    private UserStatus status;

    @Schema(description = "备注")
    private String remark;
}
