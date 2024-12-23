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

package cn.sliew.carp.module.datasource.controller;

import cn.sliew.carp.framework.log.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.datasource.service.CarpDsCategoryService;
import cn.sliew.carp.module.datasource.service.dto.DsCategoryDTO;
import cn.sliew.carp.module.datasource.service.dto.DsTypeDTO;
import cn.sliew.carp.module.datasource.service.param.DsTypeListParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebLog
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/datasource/category")
@Tag(name = "数据源模块-分类管理")
public class CarpDsCategoryController {

    @Autowired
    private CarpDsCategoryService carpDsCategoryService;

    @GetMapping
    @Operation(summary = "查询分类", description = "查询分类")
    public List<DsCategoryDTO> listAll() {
        return carpDsCategoryService.listAll();
    }

    @GetMapping("type")
    @Operation(summary = "查询数据源类型", description = "查询数据源类型")
    public List<DsTypeDTO> listTypes(@Valid DsTypeListParam param) {
        return carpDsCategoryService.listTypes(param);
    }

}
