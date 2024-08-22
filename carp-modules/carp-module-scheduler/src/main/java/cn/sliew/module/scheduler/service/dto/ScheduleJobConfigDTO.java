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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "ScheduleJobConfig", description = "schedule job config")
public class ScheduleJobConfigDTO extends BaseDTO {

    @Schema(description = "任务分组 id")
    private Long jobGroupId;

    @Schema(description = "任务类型")
    private String type;

    @Schema(description = "任务名称")
    private String name;

    @Schema(description = "任务处理器")
    private String handler;

    @Schema(description = "remark")
    private String remark;
}