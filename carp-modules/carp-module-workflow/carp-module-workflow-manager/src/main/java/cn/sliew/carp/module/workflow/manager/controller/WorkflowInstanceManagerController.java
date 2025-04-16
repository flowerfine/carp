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
package cn.sliew.carp.module.workflow.manager.controller;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.log.web.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.workflow.manager.service.WorkflowInstanceManagerService;
import cn.sliew.carp.module.workflow.manager.service.dto.CarpWorkflowInstanceDTO;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowInstanceAddParam;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowInstancePageParam;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowInstanceUpdateParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebLog
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/workflow/manager/instance")
@Tag(name = "Workflow模块-Instance管理")
public class WorkflowInstanceManagerController {

    @Autowired
    private WorkflowInstanceManagerService workflowInstanceManagerService;

    @GetMapping("page")
    @Operation(summary = "查询-分页", description = "查询-分页")
    public PageResult<CarpWorkflowInstanceDTO> page(@Valid WorkflowInstancePageParam param) {
        return workflowInstanceManagerService.page(param);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询-详情", description = "查询-详情")
    public CarpWorkflowInstanceDTO get(@PathVariable("id") Long id) {
        return workflowInstanceManagerService.get(id);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public boolean add(@Valid @RequestBody WorkflowInstanceAddParam param) {
        return workflowInstanceManagerService.add(param);
    }

    @PostMapping
    @Operation(summary = "更新", description = "更新")
    public boolean update(@Valid @RequestBody WorkflowInstanceUpdateParam param) {
        return workflowInstanceManagerService.update(param);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public boolean delete(@PathVariable("id") Long id) {
        return workflowInstanceManagerService.delete(id);
    }

    @DeleteMapping("batch")
    @Operation(summary = "批量删除", description = "批量删除")
    public boolean deleteBatch(@RequestBody List<Long> ids) {
        return workflowInstanceManagerService.deleteBatch(ids);
    }
}
