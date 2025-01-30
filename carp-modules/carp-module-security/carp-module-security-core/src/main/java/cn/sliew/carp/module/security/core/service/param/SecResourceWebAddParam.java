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

import cn.sliew.carp.framework.common.dict.security.CarpSecResourceStatus;
import cn.sliew.carp.framework.common.dict.security.CarpSecResourceWebType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SecResourceWebAddParam {

    @NotNull
    @Schema(description = "资源类型")
    private CarpSecResourceWebType type;

    @NotNull
    @Schema(description = "上级资源id")
    private Long pid;

    @NotBlank
    @Schema(description = "资源值")
    private String value;

    @NotBlank
    @Schema(description = "资源名称")
    private String label;

    @NotBlank
    @Schema(description = "路由路径")
    private String path;

    @Schema(description = "order")
    private Integer order;

    @NotNull
    @Schema(description = "资源状态")
    private CarpSecResourceStatus status;

    @Schema(description = "备注")
    private String remark;
}
