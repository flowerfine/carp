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

import cn.sliew.carp.module.application.vela.api.v1.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RbacApi {

    @GetMapping(value = "/api/v1/roles")
    ResponseEntity<V1ListRolesResponse> listPlatformRoles();

    @PostMapping(value = "/api/v1/roles")
    ResponseEntity<V1RoleBase> createPlatformRole(@RequestBody V1CreateRoleRequest body);

    @PutMapping(value = "/api/v1/roles/{roleName}")
    ResponseEntity<V1RoleBase> updatePlatformRole(@PathVariable("roleName") String roleName, @RequestBody V1UpdateRoleRequest body);

    @DeleteMapping(value = "/api/v1/roles/{roleName}")
    ResponseEntity<V1EmptyResponse> deletePlatformRole(@PathVariable("roleName") String roleName);

    @GetMapping(value = "/api/v1/permissions")
    ResponseEntity<List<V1PermissionBase>> listPlatformPermissions();

    @PostMapping(value = "/api/v1/permissions")
    ResponseEntity<V1PermissionBase> createPlatformPermission(@RequestBody V1CreatePermissionRequest body);

    @DeleteMapping(value = "/api/v1/permissions/{permissionName}")
    ResponseEntity<V1EmptyResponse> deletePlatformPermission(@PathVariable("permissionName") String permissionName);

}
