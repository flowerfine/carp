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

import cn.sliew.carp.framework.common.dict.common.YesOrNo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 资源-web
 */
@Data
@Schema(name = "SecResourceWebWithAuthorize对象", description = "资源-web 相关授权状态")
public class SecResourceWebWithAuthorizeDTO extends SecResourceWebDTO {

    @Schema(description = "下级资源")
    private List<SecResourceWebWithAuthorizeDTO> children;

    @Schema(description = "授权状态")
    private YesOrNo authorized;
}
