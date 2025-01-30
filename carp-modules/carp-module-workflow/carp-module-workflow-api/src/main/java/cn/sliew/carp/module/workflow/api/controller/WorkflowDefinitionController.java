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
package cn.sliew.carp.module.workflow.api.controller;

import cn.sliew.carp.framework.log.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinition;
import cn.sliew.carp.module.workflow.api.service.WorkflowDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@WebLog
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/workflow/definition")
@Tag(name = "Workflow模块-Definition管理")
public class WorkflowDefinitionController {

    @Autowired
    private WorkflowDefinitionService workflowDefinitionService;

    @GetMapping("{id}")
    @Operation(summary = "查询详情", description = "查询详情")
    public WorkflowDefinition get(@PathVariable("id") Long id) {
        return workflowDefinitionService.get(id);
    }

    @GetMapping("{id}/graph")
    @Operation(summary = "查询详情-图", description = "查询详情-图")
    public WorkflowDefinition getGraph(@PathVariable("id") Long id) {
        return workflowDefinitionService.getGraph(id);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public Long add() {
        return null;
    }
}
