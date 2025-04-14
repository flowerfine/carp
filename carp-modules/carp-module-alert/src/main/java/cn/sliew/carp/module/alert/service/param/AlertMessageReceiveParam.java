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
package cn.sliew.carp.module.alert.service.param;

import cn.sliew.carp.framework.common.dict.alert.CarpAlertStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;

@Getter
@Builder
@Jacksonized
@Schema(name = "CarpAlertMessage", description = "alert message")
public class AlertMessageReceiveParam {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "命名空间")
    private String namespace;

    @NotBlank
    @Schema(description = "规则id")
    private String ruleId;

    @NotBlank
    @Schema(description = "资源类型")
    private String resourceType;

    @NotBlank
    @Schema(description = "资源id")
    private String resourceId;

    @NotBlank
    @Schema(description = "告警消息fingerprint")
    private String fingerprint;

    @NotNull
    @Schema(description = "告警消息状态")
    private CarpAlertStatus status;

    @NotNull
    @Schema(description = "告警时间")
    private Date startTime;

    @Schema(description = "恢复时间")
    private Date endTime;

    @NotBlank
    private String summary;

    @NotBlank
    private String description;

    @NotBlank
    private String source;
}
