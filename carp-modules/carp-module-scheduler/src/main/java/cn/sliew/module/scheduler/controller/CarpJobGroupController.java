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

package cn.sliew.module.scheduler.controller;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.module.scheduler.service.ScheduleJobGroupService;
import cn.sliew.module.scheduler.service.dto.ScheduleJobGroupDTO;
import cn.sliew.module.scheduler.service.param.ScheduleJobGroupAddParam;
import cn.sliew.module.scheduler.service.param.ScheduleJobGroupPageParam;
import cn.sliew.module.scheduler.service.param.ScheduleJobGroupUpdateParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/schedule/group")
@Tag(name = "调度管理-分组管理")
public class CarpJobGroupController {

    @Autowired
    private ScheduleJobGroupService scheduleJobGroupService;

    @GetMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public PageResult<ScheduleJobGroupDTO> list(@Valid ScheduleJobGroupPageParam param) {
        return scheduleJobGroupService.list(param);
    }

    @GetMapping
    @Operation(summary = "查询所有", description = "查询所有")
    public List<ScheduleJobGroupDTO> listAll() {
        return scheduleJobGroupService.listAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "查询详情", description = "查询详情")
    public ScheduleJobGroupDTO get(@PathVariable("id") Long id) {
        return scheduleJobGroupService.get(id);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public Boolean add(@Valid @RequestBody ScheduleJobGroupAddParam param) {
        return scheduleJobGroupService.add(param);
    }

    @PostMapping
    @Operation(summary = "更新", description = "更新")
    public Boolean update(@Valid @RequestBody ScheduleJobGroupUpdateParam param) {
        return scheduleJobGroupService.update(param);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public Boolean delete(@PathVariable("id") Long id) {
        return scheduleJobGroupService.delete(id);
    }

    @DeleteMapping("batch")
    @Operation(summary = "批量删除", description = "批量删除")
    public Boolean deleteBatch(@RequestBody List<Long> ids) {
        return scheduleJobGroupService.deleteBatch(ids);
    }

}
