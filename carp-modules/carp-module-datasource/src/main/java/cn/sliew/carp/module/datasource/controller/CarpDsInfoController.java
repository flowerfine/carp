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

import cn.sliew.carp.framework.common.dict.datasource.CarpDataSourceType;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.log.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.service.CarpDsInfoService;
import cn.sliew.carp.module.datasource.service.dto.DsInfoDTO;
import cn.sliew.carp.module.datasource.service.param.DsInfoListParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebLog
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/datasource/info")
@Tag(name = "数据源模块-数据源管理")
public class CarpDsInfoController {

    @Autowired
    private CarpDsInfoService carpDsInfoService;

    @GetMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public PageResult<DsInfoDTO> list(@Valid DsInfoListParam param) {
        return carpDsInfoService.list(param);
    }

    @GetMapping("{type}")
    @Operation(summary = "查询所有", description = "查询指定数据源类型下所有数据源")
    public List<DsInfoDTO> listByType(@PathVariable("type") CarpDataSourceType type) {
        return carpDsInfoService.listByType(type);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询详情", description = "查询详情")
    public DsInfoDTO get(@PathVariable("id") Long id) {
        return carpDsInfoService.selectOne(id, false);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public boolean add(@Valid @RequestBody DataSourceInfo dataSource) {
        return carpDsInfoService.add(dataSource);
    }

    @PostMapping("{id}")
    @Operation(summary = "更新", description = "更新")
    public boolean update(@PathVariable("id") Long id, @Valid @RequestBody DataSourceInfo dataSource) {
        return carpDsInfoService.update(id, dataSource);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public boolean delete(@PathVariable("id") Long id) {
        return carpDsInfoService.deleteById(id);
    }

    @DeleteMapping("batch")
    @Operation(summary = "批量删除", description = "批量删除")
    public boolean deleteBatch(@RequestBody List<Long> ids) {
        return carpDsInfoService.deleteBatch(ids);
    }

}
