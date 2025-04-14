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

import cn.sliew.carp.framework.common.dict.alert.CarpAlertStatus;
import cn.sliew.carp.framework.common.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(name = "CarpAlertMessage", description = "alert message")
public class CarpAlertMessageDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "命名空间")
    private String namespace;

    @Schema(description = "规则id")
    private String ruleId;

    @Schema(description = "规则名称")
    private String ruleName;

    @Schema(description = "资源类型")
    private String resourceType;

    @Schema(description = "资源id")
    private String resourceId;

    @Schema(description = "告警消息fingerprint")
    private String fingerprint;

    @Schema(description = "告警消息状态")
    private CarpAlertStatus status;

    @Schema(description = "告警时间")
    private Date startTime;

    @Schema(description = "恢复时间")
    private Date endTime;

    @Schema(description = "告警次数")
    private Long count;

    private String summary;

    private String description;

    private String source;
}
