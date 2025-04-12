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
package cn.sliew.carp.module.alert.service.dto;

import cn.sliew.carp.framework.common.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
@Schema(name = "CarpAlertLog", description = "alert log")
public class CarpAlertLogDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "消息版本号")
    private String version;

    @Schema(description = "分组key")
    private String groupKey;

    @Schema(description = "分组labels")
    private Map<String, String> groupLabels;

    @Schema(description = "接收者")
    private String receiver;

    @Schema(description = "通用labels")
    private Map<String, String> commonLabels;

    @Schema(description = "通用annotations")
    private Map<String, String> commonAnnotations;

    @Schema(description = "告警消息fingerprint")
    private String fingerprint;

    @Schema(description = "告警消息状态")
    private String status;

    @Schema(description = "告警消息labels")
    private Map<String, String> labels;

    @Schema(description = "告警消息annotations")
    private Map<String, String> annotations;

    @Schema(description = "告警时间")
    private Long startsAt;

    @Schema(description = "恢复时间")
    private Long endsAt;
}
