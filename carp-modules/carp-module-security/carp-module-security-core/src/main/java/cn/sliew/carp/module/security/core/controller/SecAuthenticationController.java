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

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.security.core.service.SecAuthenticationService;
import cn.sliew.carp.module.security.core.service.SecCaptchaService;
import cn.sliew.carp.module.security.core.service.dto.OnlineUserVO;
import cn.sliew.carp.module.security.core.service.dto.SecCaptchaDTO;
import cn.sliew.carp.module.security.core.service.param.authenticate.LoginParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/security/authentication")
@Tag(name = "权限模块-认证管理")
public class SecAuthenticationController {

    @Autowired
    private SecAuthenticationService secAuthenticationService;
    @Autowired
    private SecCaptchaService secCaptchaService;

    @AnonymousAccess
    @GetMapping("captcha")
    @Operation(summary = "获取验证码", description = "获取验证码")
    public SecCaptchaDTO getCaptcha() {
        return secCaptchaService.getCaptcha();
    }

    @AnonymousAccess
    @PostMapping("login")
    @Operation(summary = "登陆", description = "登陆")
    public OnlineUserVO login(@Valid @RequestBody LoginParam param, HttpServletRequest request, HttpServletResponse response) {
        return secAuthenticationService.login(param, request, response);
    }

    @PostMapping("logout")
    @Operation(summary = "登出", description = "登出")
    public Boolean logout(HttpServletRequest request, HttpServletResponse response) {
        return secAuthenticationService.logout(request, response);
    }

}
