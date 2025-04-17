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
package cn.sliew.carp.module.workflow.engine.internal.api.controller;

import cn.sliew.carp.framework.dag.x6.dnd.X6GraphDTO;
import cn.sliew.carp.framework.log.web.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.workflow.engine.internal.api.service.ServerlessWorkflowService;
import cn.sliew.carp.module.workflow.engine.internal.api.service.dto.dnd.DndGroupDTO;
import cn.sliew.carp.module.workflow.engine.internal.api.service.param.ServerlessWorkflowExecuteParam;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebLog
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/workflow/serverless-workflow")
@Tag(name = "Workflow模块-ServerlessWorkflow管理")
public class ServerlessWorkflowController {

    @Autowired
    private ServerlessWorkflowService serverlessWorkflowService;

    @GetMapping("dnds")
    @Operation(summary = "查询节点元信息", description = "后端统一返回节点信息")
    public List<DndGroupDTO> loadNodeMeta() {
        return serverlessWorkflowService.getDnds();
    }

    @PostMapping("workflow")
    @Operation(summary = "转换 DAG 为 Workflow", description = "转换 DAG 为 Workflow")
    public String convertToWorkflow(@Valid @RequestBody X6GraphDTO x6GraphDTO) {
        return serverlessWorkflowService.convertDagToWorkflow(x6GraphDTO);
    }

    @PostMapping("execute")
    @Operation(summary = "执行 DAG", description = "执行 DAG")
    public JsonNode execute(@Valid @RequestBody ServerlessWorkflowExecuteParam param) {
        return serverlessWorkflowService.execute(param);
    }

}
