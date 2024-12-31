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

package cn.sliew.carp.module.scheduler.controller;

import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleEngineType;
import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleJobType;
import cn.sliew.carp.framework.log.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.scheduler.api.dict.CarpScheduleExecuteType;
import cn.sliew.carp.module.scheduler.service.ScheduleJobExecutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@WebLog
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/schedule/executor")
@Tag(name = "调度管理-执行器管理")
public class CarpJobExecutorController {

    @Autowired
    private ScheduleJobExecutorService scheduleJobExecutorService;

    @GetMapping("engines")
    @Operation(summary = "引擎", description = "引擎")
    public List<CarpScheduleEngineType> getEngines() {
        return scheduleJobExecutorService.getEngines();
    }

    @GetMapping("{engine}/types")
    @Operation(summary = "类型", description = "类型")
    public Set<CarpScheduleJobType> getTypes(@PathVariable("engine") CarpScheduleEngineType engineType) {
        return scheduleJobExecutorService.getTypes(engineType);
    }

    @GetMapping("{engine}/{type}/executors")
    @Operation(summary = "执行器", description = "执行器")
    public List<CarpScheduleExecuteType> getExecutorTypes(@PathVariable("engine") CarpScheduleEngineType engineType, @PathVariable("type") CarpScheduleJobType jobType) {
        return scheduleJobExecutorService.getExecutorTypes(engineType, jobType);
    }

}
