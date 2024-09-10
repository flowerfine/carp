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

package cn.sliew.module.scheduler.service.dto;

import cn.sliew.carp.framework.common.model.BaseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(name = "ScheduleJobInstance", description = "schedule job instance")
public class ScheduleJobInstanceDTO extends BaseDTO {
    
    @Schema(description = "任务配置")
    private ScheduleJobConfigDTO jobConfig;

    @Schema(description = "实例名称")
    private String name;

    @Schema(description = "CRON表达式")
    private String cron;

    @Schema(description = "Time Zone")
    private String timezone;

    @Schema(description = "开始时间")
    private Date startTime;

    @Schema(description = "结束时间")
    private Date endTime;

    @Schema(description = "属性")
    private JsonNode props;

    @Schema(description = "参数")
    private String params;

    @Schema(description = "超时时间（毫秒）")
    private Long timeout;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "remark")
    private String remark;
}
