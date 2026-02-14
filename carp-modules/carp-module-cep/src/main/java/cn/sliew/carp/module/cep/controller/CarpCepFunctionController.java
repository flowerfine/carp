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
import cn.sliew.carp.module.cep.service.CarpCepFunctionService;
import cn.sliew.carp.module.cep.service.dto.CarpCepFunctionDTO;
import cn.sliew.carp.module.cep.service.param.CepFunctionPageParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebLog
@AnonymousAccess
@RestController
@AllArgsConstructor
@ApiResponseWrapper
@RequestMapping("/api/carp/cep/function")
@Tag(name = "CEP管理-Function管理")
public class CarpCepFunctionController {

    @Autowired
    private CarpCepFunctionService functionService;

    @GetMapping("page")
    @Operation(summary = "查询-分页", description = "查询-分页")
    public PageResult<CarpCepFunctionDTO> page(@Valid CepFunctionPageParam param) {
        return functionService.page(param);
    }

}
