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

package cn.sliew.carp.module.application.vela.controller.security;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.application.vela.api.v1.model.security.*;
import cn.sliew.carp.module.application.vela.api.v1.security.AuthenticationApi;
import cn.sliew.carp.module.application.vela.config.VelaFeignConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AnonymousAccess
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/application/vela/auth")
@Tag(name = "应用模块-Vela-认证管理")
public class VelaAuthController {

    @Autowired
    private AuthenticationApi authenticationApi;

    @GetMapping("login_type")
    @Operation(summary = "登陆类型", description = "登陆类型")
    public ResponseEntity<V1GetLoginTypeResponse> getLoginType() {
        return authenticationApi.getLoginType(VelaFeignConfig.VELA_URI);
    }

    @GetMapping("user_info")
    @Operation(summary = "登陆用户信息", description = "登陆用户信息")
    public ResponseEntity<V1LoginUserInfoResponse> getLoginUserInfo() {
        return authenticationApi.getLoginUserInfo(VelaFeignConfig.VELA_URI);
    }

    @PostMapping("login")
    @Operation(summary = "登陆", description = "登陆")
    public ResponseEntity<V1LoginResponse> login(@RequestBody V1LoginRequest request) {
        ResponseEntity<V1LoginResponse> responseEntity = authenticationApi.login(VelaFeignConfig.VELA_URI, request);
        VelaFeignConfig.VelaOpenAPIAuthInterceptor.setToken(responseEntity.getBody().getAccessToken());
        return responseEntity;
    }

    @GetMapping("refreshToken")
    @Operation(summary = "刷新授权", description = "刷新授权")
    public ResponseEntity<V1RefreshTokenResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        ResponseEntity<V1RefreshTokenResponse> responseEntity = authenticationApi.refreshToken(VelaFeignConfig.VELA_URI, refreshToken);
        VelaFeignConfig.VelaOpenAPIAuthInterceptor.setToken(responseEntity.getBody().getAccessToken());
        return responseEntity;
    }

    @GetMapping(value = "admin_configured")
    @Operation(summary = "是否配置 Admin 用户", description = "是否配置 Admin 用户")
    public ResponseEntity<V1AdminConfiguredResponse> adminConfigured() {
        return authenticationApi.adminConfigured(VelaFeignConfig.VELA_URI);
    }

    @PutMapping(value = "init_admin")
    @Operation(summary = "配置 Admin 用户", description = "配置 Admin 用户")
    public ResponseEntity<V1InitAdminResponse> configureAdmin(@RequestBody V1InitAdminRequest request) {
        return authenticationApi.configureAdmin(VelaFeignConfig.VELA_URI, request);
    }

    @GetMapping(value = "dex_config")
    @Operation(summary = "dex配置", description = "dex配置")
    public ResponseEntity<V1DexConfigResponse> getDexConfig() {
        return authenticationApi.getDexConfig(VelaFeignConfig.VELA_URI);
    }

}
