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

import cn.sliew.carp.module.application.vela.api.v1.model.security.V1DetailUserResponse;
import cn.sliew.carp.module.application.vela.api.v1.model.security.V1ListUserResponse;
import cn.sliew.carp.module.application.vela.api.v1.model.security.V1UserBase;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.*;
import cn.sliew.carp.module.application.vela.config.VelaFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@FeignClient(value = "VelaUsersApi", url = "EMPTY", configuration = VelaFeignConfig.class)
public interface UsersApi {

    @GetMapping(value = "/api/v1/users")
    ResponseEntity<V1ListUserResponse> listUser(URI uri, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("alias") String alias);

    @GetMapping(value = "/api/v1/users/{username}")
    ResponseEntity<V1DetailUserResponse> detailUser(URI uri, @PathVariable("username") String username);

    @PostMapping(value = "/api/v1/users")
    ResponseEntity<V1UserBase> createUser(URI uri, @RequestBody V1CreateUserRequest body);

    @PutMapping(value = "/api/v1/users/{username}")
    ResponseEntity<V1UserBase> updateUser(URI uri, @PathVariable("username") String username, @RequestBody V1UpdateUserRequest body);

    @DeleteMapping(value = "/api/v1/users/{username}")
    ResponseEntity<V1EmptyResponse> deleteUser(URI uri, @PathVariable("username") String username);

    @GetMapping(value = "/api/v1/users/{username}/disable")
    ResponseEntity<V1EmptyResponse> disableUser(URI uri, @PathVariable("username") String username);

    @GetMapping(value = "/api/v1/users/{username}/enable")
    ResponseEntity<V1EmptyResponse> enableUser(URI uri, @PathVariable("username") String username);
}
