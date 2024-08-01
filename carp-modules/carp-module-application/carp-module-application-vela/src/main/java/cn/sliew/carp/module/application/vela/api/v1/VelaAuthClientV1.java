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

package cn.sliew.carp.module.application.vela.api.v1;

import cn.sliew.carp.module.application.vela.api.v1.model.auth.AuthLoginParam;
import cn.sliew.carp.module.application.vela.api.v1.model.auth.AuthLoginResult;
import cn.sliew.carp.module.application.vela.api.v1.model.auth.AuthRefreshTokenResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;

@FeignClient(value = "VelaAuthClient", url = "EMPTY")
public interface VelaAuthClientV1 {

    @PostMapping(value = "api/v1/auth/login", headers = {HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE})
    AuthLoginResult login(URI uri, @RequestBody AuthLoginParam param);

    @GetMapping(value = "api/v1/auth/refresh_token", headers = {HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE})
    AuthRefreshTokenResult refreshToken(URI uri, @RequestHeader("RefreshToken") String refreshToken);
}
