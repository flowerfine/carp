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
package cn.sliew.carp.module.workflow.simple.controller;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.framework.log.web.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.workflow.simple.api.WorkflowDefinitionApi;
import cn.sliew.carp.module.workflow.simple.api.request.WorkflowDefinitionRequest;
import cn.sliew.carp.module.workflow.simple.api.request.WorkflowLinkDefinition;
import cn.sliew.carp.module.workflow.simple.api.request.WorkflowStepDefinition;
import cn.sliew.carp.module.workflow.simple.enums.WorkflowStepType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebLog
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/workflow/simple")
@Tag(name = "Workflow模块-SimpleWorkflow管理")
public class SimpleWorkflowController {

    @Autowired
    private WorkflowDefinitionApi workflowDefinitionApi;

    @GetMapping("definition/add")
    @Operation(summary = "新增 definition", description = "新增 definition")
    public Long addDefinition() {
        WorkflowDefinitionRequest request = WorkflowDefinitionRequest.builder()
                .namespace("default")
                .type("WorkFlow")
                .name(UUIDUtil.randomUUId())
                .uuid(UUIDUtil.randomUUId())
                .preStep(WorkflowStepDefinition.builder()
                        .type(WorkflowStepType.STEP)
                        .name("")
                        .uuid(UUIDUtil.randomUUId())
                        .build())
                .stepList(List.of(WorkflowStepDefinition.builder().build()))
                .postStep(WorkflowStepDefinition.builder().build())
                .linkList(List.of(WorkflowLinkDefinition.builder().build()))
                .build();
        return workflowDefinitionApi.create(request);
    }

}
