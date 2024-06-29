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

import cn.sliew.carp.module.plugin.service.PluginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pf4j.PluginDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/carp/plugin")
@Tag(name = "插件模块-插件管理")
public class PluginController {

    @Autowired
    private PluginService pluginService;

    @GetMapping
    @Operation(summary = "查询所有", description = "查询所有")
    public List<PluginDescriptor> listAll() {
        return pluginService.listAll();
    }

    @GetMapping("{pluginId}")
    @Operation(summary = "查询详情", description = "查询详情")
    public PluginDescriptor get(@PathVariable("pluginId") String pluginId) {
        return pluginService.get(pluginId);
    }

    @GetMapping("test")
    @Operation(summary = "测试一下", description = "测试一下")
    public void test() {
        pluginService.testExtension();
    }
}
