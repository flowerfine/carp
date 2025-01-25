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

import cn.sliew.carp.framework.common.dict.security.CarpSecDeptStatus;
import cn.sliew.carp.framework.common.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "sec dept")
public class SecDeptDTO extends BaseDTO {

    private static final long serialVersionUID = -6268620152048177679L;

    @Schema(description = "code")
    private String code;

    @Schema(description = "name")
    private String name;

    @Schema(description = "pid")
    private Long pid;

    @Schema(description = "status")
    private CarpSecDeptStatus status;

    @Schema(description = "remark")
    private String remark;

    @Schema(description = "children")
    private List<SecDeptDTO> children;
}
