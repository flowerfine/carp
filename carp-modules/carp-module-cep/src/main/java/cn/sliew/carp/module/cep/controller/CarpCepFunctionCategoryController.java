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
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.cep.service.CarpCepFunctionCategoryService;
import cn.sliew.carp.module.cep.service.dto.CarpCepFunctionCategoryDTO;
import cn.sliew.carp.module.cep.service.param.CepFunctionCategoryListParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebLog
@AnonymousAccess
@RestController
@AllArgsConstructor
@ApiResponseWrapper
@RequestMapping("/api/carp/cep/function/category")
@Tag(name = "CEP管理-Function分类管理")
public class CarpCepFunctionCategoryController {

    @Autowired
    private CarpCepFunctionCategoryService functionCategoryService;

    @GetMapping("listAll")
    @Operation(summary = "查询-所有", description = "查询-所有")
    public List<CarpCepFunctionCategoryDTO> listAll(@Valid CepFunctionCategoryListParam param) {
        return functionCategoryService.listAll(param);
    }

}
