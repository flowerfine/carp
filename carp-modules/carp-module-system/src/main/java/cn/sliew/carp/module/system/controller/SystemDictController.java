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

package cn.sliew.carp.module.system.controller;

import cn.sliew.carp.framework.common.dict.DictDefinition;
import cn.sliew.carp.framework.common.dict.DictInstance;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.system.service.SysDictDefinitionService;
import cn.sliew.carp.module.system.service.SysDictInstanceService;
import cn.sliew.carp.module.system.service.param.SysDictDefinitionParam;
import cn.sliew.carp.module.system.service.param.SysDictInstanceParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/system/dict")
@Tag(name = "系统模块-字典管理")
public class SystemDictController {

    @Autowired
    private SysDictDefinitionService sysDictDefinitionService;
    @Autowired
    private SysDictInstanceService sysDictInstanceService;

    @GetMapping("/definition/page")
    @Operation(summary = "分页查询数据字典类型", description = "分页查询数据字典类型")
    public PageResult<DictDefinition> listDefinition(@Valid SysDictDefinitionParam param) {
        return sysDictDefinitionService.listByPage(param);
    }

    @GetMapping("/definition/all")
    @Operation(summary = "查询所有数据字典类型", description = "查询所有数据字典类型")
    public Collection<DictDefinition> listDefinitionAll() {
        return sysDictDefinitionService.selectAll();
    }

    @GetMapping("/definition/instance/page")
    @Operation(summary = "分页查询数据字典", description = "分页查询数据字典")
    public PageResult<DictInstance> listDict(SysDictInstanceParam param) {
        return sysDictInstanceService.listByPage(param);
    }

    @GetMapping("/definition/instance")
    @Operation(summary = "查询数据字典", description = "根据字典类型code查询数据字典")
    public Collection<DictInstance> listDictByDefinition(@RequestParam("dictDefinitionCode") String code) {
        return sysDictInstanceService.selectByDefinition(code);
    }

}
