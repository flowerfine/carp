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

package cn.sliew.carp.module.application.vela.api.v1.security;

import cn.sliew.carp.module.application.vela.api.v1.model.security.*;
import cn.sliew.carp.module.application.vela.config.VelaFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@FeignClient(value = "VelaAuthenticationApi", url = "EMPTY", configuration = VelaFeignConfig.class)
public interface AuthenticationApi {

    @GetMapping(value = "/api/v1/auth/login_type")
    ResponseEntity<V1GetLoginTypeResponse> getLoginType(URI uri);

    @GetMapping(value = "/api/v1/auth/user_info")
    ResponseEntity<V1LoginUserInfoResponse> getLoginUserInfo(URI uri);

    @GetMapping(value = "/api/v1/auth/refresh_token")
    ResponseEntity<V1RefreshTokenResponse> refreshToken(URI uri, @RequestHeader("RefreshToken") String refreshToken);

    @PostMapping(value = "/api/v1/auth/login")
    ResponseEntity<V1LoginResponse> login(URI uri, @RequestBody V1LoginRequest body);

    @GetMapping(value = "/api/v1/auth/admin_configured")
    ResponseEntity<V1AdminConfiguredResponse> adminConfigured(URI uri);

    @PutMapping(value = "/api/v1/auth/init_admin")
    ResponseEntity<V1InitAdminResponse> configureAdmin(URI uri, @RequestBody V1InitAdminRequest body);

    @GetMapping(value = "/api/v1/auth/dex_config")
    ResponseEntity<V1DexConfigResponse> getDexConfig(URI uri);
}
