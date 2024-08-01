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

package cn.sliew.carp.module.application.vela.controller;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.application.vela.api.v1.VelaAuthClientV1;
import cn.sliew.carp.module.application.vela.api.v1.model.AuthLoginParam;
import cn.sliew.carp.module.application.vela.api.v1.model.AuthLoginResult;
import cn.sliew.carp.module.application.vela.api.v1.model.AuthRefreshTokenResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/application/vela")
@Tag(name = "应用模块-Vela-权限管理")
public class VelaAuthController {

    private static final URI uri = URI.create("http://129.204.156.150:8000");

    @Autowired
    private VelaAuthClientV1 clientV1;

    @AnonymousAccess
    @PostMapping("login")
    @Operation(summary = "登陆", description = "登陆")
    public AuthLoginResult login(@RequestBody AuthLoginParam param) {
        return clientV1.login(uri, param);
    }

    @AnonymousAccess
    @GetMapping("refreshToken")
    @Operation(summary = "刷新授权", description = "刷新授权")
    public AuthRefreshTokenResult refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return clientV1.refreshToken(uri, refreshToken);
    }

}
