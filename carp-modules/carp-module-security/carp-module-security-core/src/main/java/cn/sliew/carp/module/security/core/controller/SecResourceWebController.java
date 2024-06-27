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

import cn.sliew.carp.module.security.core.service.SecResourceWebService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carp/security/resource/web")
@Tag(name = "权限模块-资源-WEB管理")
public class SecResourceWebController {

    @Autowired
    private SecResourceWebService secResourceWebService;

    @GetMapping
    @ApiOperationSupport(order = 1)
    @SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
    @Operation(summary = "分页查询", description = "分页查询")
    public ResponseEntity list() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @ApiOperationSupport(order = 2)
    @Operation(summary = "新增", description = "新增")
    public ResponseEntity add() {
        return ResponseEntity.ok().build();
    }

}
