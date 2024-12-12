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

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.datasource.service.CarpGravitinoMetalakeService;
import cn.sliew.carp.module.datasource.service.dto.GravitinoCatalogDTO;
import cn.sliew.carp.module.datasource.service.dto.GravitinoMetalakeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AnonymousAccess
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/datasource/gravitino/metalake")
@Tag(name = "数据源模块-Gravitino-MetaLake管理")
public class CarpGravitinoMetalakeController {

    @Autowired
    private CarpGravitinoMetalakeService carpGravitinoMetalakeService;

    @GetMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public PageResult<GravitinoMetalakeDTO> page(@Valid PageParam param) {
        return carpGravitinoMetalakeService.page(param);
    }

    @GetMapping("{metalake}/catalogs")
    @Operation(summary = "查询 catalogs", description = "查询 catalogs")
    public List<GravitinoCatalogDTO> listCatalog(@PathVariable("metalake") String metalake) {
        return carpGravitinoMetalakeService.listCatalogs(metalake);
    }
}
