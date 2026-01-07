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

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.log.web.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.cep.service.CarpCepWorkflowService;
import cn.sliew.carp.module.cep.service.dto.CarpCepWorkflowDTO;
import cn.sliew.carp.module.cep.service.param.CepWorkflowAddParam;
import cn.sliew.carp.module.cep.service.param.CepWorkflowPageParam;
import cn.sliew.carp.module.cep.service.param.CepWorkflowUpdateParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebLog
@AnonymousAccess
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/cep/workflow")
@Tag(name = "CEP管理-Workflow管理")
public class CarpCepWorkflowController {

    @Autowired
    private CarpCepWorkflowService cepWorkflowService;

    @GetMapping("page")
    @Operation(summary = "查询-分页", description = "查询-分页")
    public PageResult<CarpCepWorkflowDTO> page(@Valid CepWorkflowPageParam param) {
        return cepWorkflowService.page(param);
    }

    @GetMapping("{id}")
    @Operation(summary = "查询-详情", description = "查询-详情")
    public CarpCepWorkflowDTO get(@PathVariable("id") Long id) {
        return cepWorkflowService.get(id);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public Boolean add(@Valid @RequestBody CepWorkflowAddParam param) {
        return cepWorkflowService.add(param);
    }

    @PostMapping
    @Operation(summary = "更新", description = "更新")
    public Boolean update(@Valid @RequestBody CepWorkflowUpdateParam param) {
        return cepWorkflowService.update(param);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public Boolean delete(@PathVariable("id") Long id) {
        return cepWorkflowService.delete(id);
    }

    @DeleteMapping("batch")
    @Operation(summary = "批量删除", description = "批量删除")
    public Boolean deleteBatch(@RequestBody List<Long> ids) {
        return cepWorkflowService.deleteBatch(ids);
    }

}
