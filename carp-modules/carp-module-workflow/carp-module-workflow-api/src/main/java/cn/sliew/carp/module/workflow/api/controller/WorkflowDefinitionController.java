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

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.dag.service.param.DagSimplePageParam;
import cn.sliew.carp.framework.dag.x6.dnd.DndDTO;
import cn.sliew.carp.framework.log.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.workflow.api.engine.domain.definition.WorkflowDefinition;
import cn.sliew.carp.module.workflow.api.service.WorkflowDagService;
import cn.sliew.carp.module.workflow.api.service.WorkflowDefinitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebLog
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/workflow/definition")
@Tag(name = "Workflow模块-Definition管理")
public class WorkflowDefinitionController {

    @Autowired
    private WorkflowDefinitionService workflowDefinitionService;
    @Autowired
    private WorkflowDagService workflowDagService;

    @GetMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public PageResult<WorkflowDefinition> page(@Valid DagSimplePageParam param) {
        return workflowDefinitionService.page(param);
    }

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

    @GetMapping("/dag/dnd")
    @Operation(summary = "查询DAG节点元信息", description = "后端统一返回节点信息")
    public List<DndDTO> loadNodeMeta() {
        return workflowDagService.getDnds();
    }

}
