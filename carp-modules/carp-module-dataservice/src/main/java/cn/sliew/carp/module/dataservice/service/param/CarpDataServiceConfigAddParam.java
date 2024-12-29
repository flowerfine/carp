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
package cn.sliew.carp.module.dataservice.service.param;

import cn.sliew.carp.framework.common.dict.common.CarpYesOrNo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarpDataServiceConfigAddParam {

    @NotNull
    @Schema(description = "分组id")
    private Long groupId;

    @NotNull
    @Schema(description = "类型")
    private String type;

    @NotBlank
    @Schema(description = "name")
    private String name;

    @NotBlank
    @Schema(description = "uri path")
    private String httpPath;

    @NotBlank
    @Schema(description = "http method")
    private String httpMethod;

    @Schema(description = "http content type")
    private String httpContentType;

    @NotNull
    @Schema(description = "data source id")
    private Long dsId;

    @Schema(description = "query type")
    private String queryType;

    @Schema(description = "query script")
    private String queryScript;

    @Schema(description = "query page enabled")
    private CarpYesOrNo queryPageEnabled;

    @Schema(description = "object、array")
    private String queryResultType;

    @Schema(description = "status, disabled or enabled")
    private CarpYesOrNo status;

    @Schema(description = "备注")
    private String remark;
}
