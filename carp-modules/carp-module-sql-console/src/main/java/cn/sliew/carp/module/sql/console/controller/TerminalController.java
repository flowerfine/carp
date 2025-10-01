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

package cn.sliew.carp.module.sql.console.controller;

import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.sql.console.catalog.SqlExample;
import cn.sliew.carp.module.sql.console.service.model.LatestSessionInfo;
import cn.sliew.carp.module.sql.console.service.model.LogInfo;
import cn.sliew.carp.module.sql.console.service.model.SessionInfo;
import cn.sliew.carp.module.sql.console.service.model.SqlResult;
import cn.sliew.carp.module.sql.console.terminal.TerminalManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/sql-console/terminal")
@Tag(name = "SQL控制台模块-Terminal管理")
public class TerminalController {

    private TerminalManager terminalManager;

    @GetMapping("examples")
    @Operation(summary = "SQL Example 列表查询", description = "SQL Example 列表查询")
    public List<String> getExamples() {
        return Arrays.stream(SqlExample.values())
                .map(SqlExample::getName)
                .collect(Collectors.toList());
    }

    @GetMapping("examples/{name}")
    @Operation(summary = "SQL Example 详情查询", description = "SQL Example 详情查询")
    public String getSqlExamples(@PathVariable("name") String exampleName) {
        for (SqlExample example : SqlExample.values()) {
            if (example.getName().equals(exampleName)) {
                return example.getSql();
            }
        }
        throw new IllegalArgumentException("can not get example name : " + exampleName);
    }

    @PostMapping("{catalog}/sql")
    @Operation(summary = "SQL执行", description = "SQL执行")
    public SessionInfo executeScript(@CookieValue(value = "JSESSIONID", required = false) String terminalId,
                                     @PathVariable("catalog") String catalog,
                                     @RequestBody Map<String, String> bodyParams) {
        String sql = bodyParams.get("sql");
        if (terminalId == null) {
            terminalId = UUID.randomUUID().toString();
        }
        String sessionId = terminalManager.executeScript(terminalId, catalog, sql);
        return new SessionInfo(sessionId);
    }

    @GetMapping("sql/{sessionId}/logs")
    @Operation(summary = "SQL日志", description = "SQL日志")
    public LogInfo getLogs(@PathVariable("sessionId") String sessionId) {
        return terminalManager.getExecutionLog(sessionId);
    }

    @GetMapping("sql/{sessionId}/result")
    @Operation(summary = "SQL结果", description = "SQL日志")
    public List<SqlResult> getSqlResult(@PathVariable("sessionId") String sessionId) {
        return terminalManager.getExecutionResults(sessionId);
    }

    @DeleteMapping("sql/{sessionId}")
    @Operation(summary = "中止SQL", description = "中止SQL")
    public void stopSql(@PathVariable("sessionId") String sessionId) {
        terminalManager.cancelExecution(sessionId);
    }

    @GetMapping("sql/latest")
    @Operation(summary = "最新的SQL信息", description = "最新的SQL信息")
    public LatestSessionInfo getLatestInfo(@CookieValue("JSESSIONID") String terminalId) {
        return terminalManager.getLastSessionInfo(terminalId);
    }
}
