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
import cn.sliew.carp.module.workflow.manager.service.WorkflowDefinitionService;
import cn.sliew.carp.module.workflow.manager.service.dto.CarpWorkflowDefinitionDTO;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowDefinitionAddParam;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowDefinitionPageParam;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowDefinitionUpdateParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebLog
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/workflow/definition")
@Tag(name = "Workflow模块-Definition管理")
public class WorkflowDefinitionController {

    @Autowired
    private WorkflowDefinitionService workflowDefinitionService;

    @GetMapping("page")
    @Operation(summary = "查询-分页", description = "查询-分页")
    public PageResult<CarpWorkflowDefinitionDTO> page(@Valid WorkflowDefinitionPageParam param) {
        return workflowDefinitionService.page(param);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询-详情", description = "查询-详情")
    public CarpWorkflowDefinitionDTO get(@PathVariable("id") Long id) {
        return workflowDefinitionService.get(id);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public boolean add(@Valid @RequestBody WorkflowDefinitionAddParam param) {
        return workflowDefinitionService.add(param);
    }

    @PostMapping
    @Operation(summary = "更新", description = "更新")
    public boolean update(@Valid @RequestBody WorkflowDefinitionUpdateParam param) {
        return workflowDefinitionService.update(param);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public boolean delete(@PathVariable("id") Long id) {
        return workflowDefinitionService.delete(id);
    }

    @DeleteMapping("batch")
    @Operation(summary = "批量删除", description = "批量删除")
    public boolean deleteBatch(@RequestBody List<Long> ids) {
        return workflowDefinitionService.deleteBatch(ids);
    }
}
