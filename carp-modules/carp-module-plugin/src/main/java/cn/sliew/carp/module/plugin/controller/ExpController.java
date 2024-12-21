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

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.module.plugin.service.ExpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AnonymousAccess
@RestController
@RequestMapping("/api/carp/plugin/exp")
@Tag(name = "插件模块-Exp管理")
public class ExpController {

    @Autowired
    private ExpService expService;

    @GetMapping("page")
    @Operation(summary = "分页查询", description = "分页查询")
    public PageResult<String> list(@Valid PageParam param) {
        return expService.page(param);
    }

    @GetMapping
    @Operation(summary = "查询所有", description = "查询所有")
    public List<String> list() {
        return expService.list();
    }

}
