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

package cn.sliew.carp.module.plugin.controller;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.module.plugin.service.PluginService;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginDTO;
import cn.sliew.carp.module.plugin.service.param.CarpPluginAddParam;
import cn.sliew.carp.module.plugin.service.param.CarpPluginListParam;
import cn.sliew.carp.module.plugin.service.param.CarpPluginUpdateParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AnonymousAccess
@RestController
@RequestMapping("/api/carp/plugin")
@Tag(name = "插件模块-插件管理")
public class PluginController {

    @Autowired
    private PluginService pluginService;

    @GetMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public PageResult<CarpPluginDTO> list(@Valid CarpPluginListParam param) {
        return pluginService.list(param);
    }

    @GetMapping
    @Operation(summary = "查询所有", description = "查询所有")
    public List<CarpPluginDTO> listAll() {
        return pluginService.listAll();
    }

    @GetMapping("{id}")
    @Operation(summary = "查询详情", description = "查询详情")
    public CarpPluginDTO get(@PathVariable("id") Long id) {
        return pluginService.get(id);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public Boolean add(@Valid @RequestBody CarpPluginAddParam param) {
        return pluginService.add(param);
    }

    @PostMapping
    @Operation(summary = "更新", description = "更新")
    public Boolean update(@Valid @RequestBody CarpPluginUpdateParam param) {
        return pluginService.update(param);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public Boolean delete(@PathVariable("id") Long id) {
        return pluginService.delete(id);
    }

    @DeleteMapping("batch")
    @Operation(summary = "批量删除", description = "批量删除")
    public Boolean deleteBatch(@RequestBody List<Long> ids) {
        return pluginService.deleteBatch(ids);
    }
}
