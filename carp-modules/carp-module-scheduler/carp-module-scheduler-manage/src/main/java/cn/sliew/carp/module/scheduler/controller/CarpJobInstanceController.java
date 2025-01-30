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

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.log.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.scheduler.executor.api.scheduler.JobScheduler;
import cn.sliew.carp.module.scheduler.service.ScheduleJobInstanceService;
import cn.sliew.carp.module.scheduler.service.dto.ScheduleJobInstanceDTO;
import cn.sliew.carp.module.scheduler.service.param.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebLog
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/schedule/instance")
@Tag(name = "调度管理-实例管理")
public class CarpJobInstanceController {

    @Autowired
    private ScheduleJobInstanceService scheduleJobInstanceService;
    @Autowired(required = false)
    private JobScheduler jobScheduler;

    @GetMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public PageResult<ScheduleJobInstanceDTO> list(@Valid ScheduleJobInstancePageParam param) {
        return scheduleJobInstanceService.list(param);
    }

    @GetMapping
    @Operation(summary = "查询所有", description = "查询所有")
    public List<ScheduleJobInstanceDTO> listAll(@Valid ScheduleJobInstanceListParam param) {
        return scheduleJobInstanceService.listAll(param);
    }

    @GetMapping("{id}")
    @Operation(summary = "查询详情", description = "查询详情")
    public ScheduleJobInstanceDTO get(@PathVariable("id") Long id) {
        return scheduleJobInstanceService.get(id);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public Boolean add(@Valid @RequestBody ScheduleJobInstanceAddParam param) {
        return scheduleJobInstanceService.add(param);
    }

    @PostMapping
    @Operation(summary = "更新", description = "更新")
    public Boolean update(@Valid @RequestBody ScheduleJobInstanceUpdateParam param) {
        return scheduleJobInstanceService.update(param);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public Boolean delete(@PathVariable("id") Long id) {
        return scheduleJobInstanceService.delete(id);
    }

    @DeleteMapping("batch")
    @Operation(summary = "批量删除", description = "批量删除")
    public Boolean deleteBatch(@RequestBody List<Long> ids) {
        return scheduleJobInstanceService.deleteBatch(ids);
    }

    @PostMapping("manage/execute")
    @Operation(summary = "执行一次", description = "执行一次")
    public void execute(@Valid @RequestBody ScheduleJobManageParam param) {
        jobScheduler.execute(param.getJobInstanceId());
    }

    @PostMapping("manage/schedule")
    @Operation(summary = "启动调度", description = "启动调度")
    public void schedule(@Valid @RequestBody ScheduleJobManageParam param) {
        jobScheduler.schedule(param.getJobInstanceId());
    }

    @PostMapping("manage/unschedule")
    @Operation(summary = "取消调度", description = "取消调度")
    public void unschedule(@Valid @RequestBody ScheduleJobManageParam param) {
        jobScheduler.unschedule(param.getJobInstanceId());
    }

    @PostMapping("manage/suspend")
    @Operation(summary = "暂停", description = "暂停")
    public void suspend(@Valid @RequestBody ScheduleJobManageParam param) {
        jobScheduler.suspend(param.getJobInstanceId());
    }

    @PostMapping("manage/resume")
    @Operation(summary = "恢复", description = "恢复")
    public void resume(@Valid @RequestBody ScheduleJobManageParam param) {
        jobScheduler.resume(param.getJobInstanceId());
    }

}
