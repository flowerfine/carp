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

import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleStatus;
import cn.sliew.carp.framework.common.model.BasePageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.io.Serial;

@Data
@Jacksonized
@SuperBuilder
@RequiredArgsConstructor
public class ScheduleJobInstancePageParam extends BasePageParam {
    @Serial
    private static final long serialVersionUID = 7405602922568411730L;

    @NotNull
    @Schema(description = "任务配置id")
    private final Long jobConfigId;

    @Schema(description = "实例名称")
    private final String name;

    @Schema(description = "状态")
    private final CarpScheduleStatus status;
}
