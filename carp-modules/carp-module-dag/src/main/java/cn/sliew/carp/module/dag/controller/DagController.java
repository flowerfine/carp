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

import java.util.Map;

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
        Map<String, Object> inputs = Map.of("foo", "foo-data", "bar", "bar-data");
        Map<String, Map<String, Object>> stepInputs = Map.of(
                "cae1a622-6c96-4cec-81d3-883510c17702", Map.of("url", "url-data", "payload", "payload-data"),
                "2c2cb6c8-794b-4cc1-8258-cd1898912744", Map.of("url", "url-data", "payload", "payload-data"),
                "d82a947b-f414-4273-973a-06f20fe33f0d", Map.of("url", "url-data", "payload", "payload-data"),
                "027db10b-9150-403d-9d11-e4a36c99e1db", Map.of("url", "url-data", "payload", "payload-data")
        );
        dagRunner.start(workflowDefinition, inputs, stepInputs);
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
