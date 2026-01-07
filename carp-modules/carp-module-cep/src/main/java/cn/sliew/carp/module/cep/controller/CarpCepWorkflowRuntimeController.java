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
package cn.sliew.carp.module.cep.controller;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.log.web.annotation.WebLog;
import cn.sliew.carp.module.cep.service.CarpCepWorkflowRuntimeService;
import cn.sliew.carp.module.cep.service.dto.runtime.*;
import cn.sliew.carp.module.cep.service.param.runtime.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * workflow runtime 接口，遵从的是三方的 http 协议，不是本项目的。
 * 因此不对接口返回的结果设置统一格式
 */
@WebLog
@AnonymousAccess
@RestController
@RequestMapping("/api")
@Tag(name = "CEP管理-Workflow Runtime接口")
public class CarpCepWorkflowRuntimeController {

    @Autowired
    private CarpCepWorkflowRuntimeService workflowRuntimeService;

    @GetMapping("info")
    @Operation(summary = "查询-ServerInfo", description = "查询-ServerInfo")
    public ServerInfo getInfo() {
        return workflowRuntimeService.getInfo();
    }

    @GetMapping("/task/result")
    @Operation(summary = "查询-运行结果", description = "查询-运行结果")
    public TaskResultDTO getResult(@Valid TaskResultParam param) {
        return workflowRuntimeService.getResult(param);
    }

    @GetMapping("/task/report")
    @Operation(summary = "查询-运行报告", description = "查询-运行报告")
    public TaskReportDTO getReport(@Valid TaskReportParam param) {
        return workflowRuntimeService.getReport(param);
    }

    @PostMapping("/task/validate")
    @Operation(summary = "任务-验证", description = "任务-验证")
    public TaskValidateDTO validate(@Valid @RequestBody TaskValidateParam param) {
        return workflowRuntimeService.validate(param);
    }

    @PostMapping("/task/run")
    @Operation(summary = "任务-运行", description = "任务-运行")
    public TaskRunDTO run(@Valid @RequestBody TaskRunParam param) {
        return workflowRuntimeService.run(param);
    }

    @PutMapping("/task/cancel")
    @Operation(summary = "任务-取消", description = "任务-取消")
    public TaskCancelDTO cancel(@Valid @RequestBody TaskCancelParam param) {
        return workflowRuntimeService.cancel(param);
    }
}
