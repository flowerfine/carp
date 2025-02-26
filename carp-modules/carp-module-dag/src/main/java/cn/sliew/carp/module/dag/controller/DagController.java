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
package cn.sliew.carp.module.dag.controller;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.dag.service.DagConfigComplexService;
import cn.sliew.carp.framework.dag.service.DagInstanceComplexService;
import cn.sliew.carp.framework.dag.service.dto.DagConfigDTO;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.framework.dag.service.param.DagConfigSimplePageParam;
import cn.sliew.carp.framework.dag.service.param.DagInstanceSimplePageParam;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.dag.model.DagRunner;
import cn.sliew.carp.module.workflow.stage.model.domain.convert.WorkflowDefinitionConvert;
import cn.sliew.carp.module.workflow.stage.model.domain.definition.WorkflowDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@AnonymousAccess
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/dag")
@Tag(name = "Dag模块-Dag测试")
public class DagController {

    @Autowired
    private DagConfigComplexService dagConfigComplexService;
    @Autowired
    private DagInstanceComplexService dagInstanceComplexService;
    @Autowired
    private DagRunner dagRunner;

    @PutMapping("add")
    @Operation(summary = "新增", description = "新增")
    public void add(@RequestParam("dagConfigId") Long dagConfigId) {
        DagConfigDTO dagConfigDTO = dagConfigComplexService.selectSimpleOne(dagConfigId);
        WorkflowDefinition workflowDefinition = WorkflowDefinitionConvert.INSTANCE.toDto(dagConfigDTO);
        dagRunner.start(workflowDefinition);
    }

    @GetMapping("configs")
    @Operation(summary = "列表查询 config", description = "列表查询 config")
    public PageResult<DagConfigDTO> listConfigs(@Valid DagConfigSimplePageParam param) {
        return dagConfigComplexService.page(param);
    }

    @GetMapping("instances")
    @Operation(summary = "列表查询 instance", description = "列表查询 instance")
    public PageResult<DagInstanceDTO> listInstances(@Valid DagInstanceSimplePageParam param) {
        return dagInstanceComplexService.page(param);
    }
}
