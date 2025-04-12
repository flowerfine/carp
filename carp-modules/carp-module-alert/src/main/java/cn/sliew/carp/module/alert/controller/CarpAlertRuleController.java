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
package cn.sliew.carp.module.alert.controller;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.log.web.annotation.WebLog;
import cn.sliew.carp.module.alert.service.AlertRuleService;
import cn.sliew.carp.module.alert.service.dto.CarpAlertRuleDTO;
import cn.sliew.carp.module.alert.service.param.AlertRuleAddParam;
import cn.sliew.carp.module.alert.service.param.AlertRulePageParam;
import cn.sliew.carp.module.alert.service.param.AlertRuleUpdateParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebLog
@RestController
@RequestMapping("/api/carp/alert/rule")
@Tag(name = "告警管理-告警规则")
public class CarpAlertRuleController {

    @Autowired
    private AlertRuleService alertRuleService;

    @GetMapping("page")
    @Operation(summary = "查询-分页", description = "查询-分页")
    public PageResult<CarpAlertRuleDTO> page(@Valid AlertRulePageParam param) {
        return alertRuleService.page(param);
    }

    @GetMapping("{id}")
    @Operation(summary = "查询-详情", description = "查询-详情")
    public CarpAlertRuleDTO get(@PathVariable("id") Long id) {
        return alertRuleService.get(id);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public Boolean add(@Valid @RequestBody AlertRuleAddParam param) {
        return alertRuleService.add(param);
    }

    @PostMapping
    @Operation(summary = "更新", description = "更新")
    public Boolean update(@Valid @RequestBody AlertRuleUpdateParam param) {
        return alertRuleService.update(param);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public Boolean delete(@PathVariable("id") Long id) {
        return alertRuleService.delete(id);
    }

    @DeleteMapping("batch")
    @Operation(summary = "批量删除", description = "批量删除")
    public Boolean deleteBatch(@RequestBody List<Long> ids) {
        return alertRuleService.deleteBatch(ids);
    }

}
