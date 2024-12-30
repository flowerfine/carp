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

package cn.sliew.carp.module.scheduler.service.param;

import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleEngineType;
import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleJobType;
import cn.sliew.carp.module.scheduler.api.dict.CarpScheduleExecuteType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScheduleJobConfigAddParam {

    @NotNull
    @Schema(description = "任务分组 id")
    private Long jobGroupId;

    @NotNull
    @Schema(description = "引擎类型")
    private CarpScheduleEngineType engineType;

    @NotNull
    @Schema(description = "任务类型")
    private CarpScheduleJobType jobType;

    @NotNull
    @Schema(description = "执行类型")
    private CarpScheduleExecuteType executeType;

    @NotBlank
    @Schema(description = "任务名称")
    private String name;

    @NotBlank
    @Schema(description = "任务处理器")
    private String handler;

    @Schema(description = "remark")
    private String remark;
}
