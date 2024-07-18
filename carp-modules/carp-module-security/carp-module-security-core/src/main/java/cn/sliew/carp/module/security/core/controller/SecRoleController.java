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

package cn.sliew.carp.module.security.core.controller;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.security.core.service.SecRoleService;
import cn.sliew.carp.module.security.core.service.dto.SecRoleDTO;
import cn.sliew.carp.module.security.core.service.param.SecRoleAddParam;
import cn.sliew.carp.module.security.core.service.param.SecRoleListParam;
import cn.sliew.carp.module.security.core.service.param.SecRoleUpdateParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/security/role")
@Tag(name = "权限模块-角色管理")
public class SecRoleController {

    @Autowired
    private SecRoleService secRoleService;

    @GetMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public PageResult<SecRoleDTO> list(@Valid SecRoleListParam param) {
        return secRoleService.list(param);
    }

    @GetMapping
    @Operation(summary = "查询所有", description = "查询所有")
    public List<SecRoleDTO> listAll(@Valid SecRoleListParam param) {
        return secRoleService.listAll(param);
    }

    @GetMapping("{id}")
    @Operation(summary = "查询详情", description = "查询详情")
    public SecRoleDTO get(@PathVariable("id") Long id) {
        return secRoleService.get(id);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public Boolean add(@Valid @RequestBody SecRoleAddParam param) {
        return secRoleService.add(param);
    }

    @PostMapping
    @Operation(summary = "更新", description = "更新")
    public Boolean update(@Valid @RequestBody SecRoleUpdateParam param) {
        return secRoleService.update(param);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public Boolean delete(@PathVariable("id") Long id) {
        return secRoleService.removeById(id);
    }

    @DeleteMapping("batch")
    @Operation(summary = "批量删除", description = "批量删除")
    public Boolean deleteBatch(@RequestBody List<Long> ids) {
        return secRoleService.removeByIds(ids);
    }
}
