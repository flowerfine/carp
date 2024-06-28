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
import cn.sliew.carp.module.security.core.service.SecUserService;
import cn.sliew.carp.module.security.core.service.dto.SecUserDTO;
import cn.sliew.carp.module.security.core.service.param.SecUserAddParam;
import cn.sliew.carp.module.security.core.service.param.SecUserListParam;
import cn.sliew.carp.module.security.core.service.param.SecUserUpdateParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carp/security/user")
@Tag(name = "权限模块-用户管理")
public class SecUserController {

    @Autowired
    private SecUserService secUserService;

    @GetMapping("page")
    @ApiOperationSupport(order = 1)
    @Operation(summary = "分页查询", description = "分页查询")
    public PageResult<SecUserDTO> list(@Valid SecUserListParam param) {
        return secUserService.list(param);
    }

    @GetMapping
    @ApiOperationSupport(order = 2)
    @Operation(summary = "查询所有", description = "查询所有")
    public List<SecUserDTO> listAll(@Valid SecUserListParam param) {
        return secUserService.listAll(param);
    }

    @GetMapping("{id}")
    @ApiOperationSupport(order = 3)
    @Operation(summary = "查询详情", description = "查询详情")
    public SecUserDTO get(@PathVariable("id") Long id) {
        return secUserService.get(id);
    }

    @PutMapping
    @ApiOperationSupport(order = 4)
    @Operation(summary = "新增", description = "新增")
    public Boolean add(@Valid SecUserAddParam param) {
        return secUserService.add(param);
    }

    @PostMapping
    @ApiOperationSupport(order = 5)
    @Operation(summary = "更新", description = "新增")
    public Boolean update(@Valid SecUserUpdateParam param) {
        return secUserService.update(param);
    }

    @DeleteMapping("{id}")
    @ApiOperationSupport(order = 6)
    @Operation(summary = "删除", description = "删除")
    public Boolean delete(@PathVariable("id") Long id) {
        return secUserService.removeById(id);
    }

    @DeleteMapping("batch")
    @ApiOperationSupport(order = 7)
    @Operation(summary = "批量删除", description = "批量删除")
    public Boolean deleteBatch(@RequestBody List<Long> ids) {
        return secUserService.removeByIds(ids);
    }
}
