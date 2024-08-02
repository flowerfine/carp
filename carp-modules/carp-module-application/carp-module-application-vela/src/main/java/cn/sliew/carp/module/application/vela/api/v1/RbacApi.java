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
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public interface RbacApi {

    @RequestMapping(value = "/api/v1/permissions",
            produces = "application/json",
            consumes = "application/xml",
            method = RequestMethod.POST)
    ResponseEntity<V1PermissionBase> createPlatformPermission(@ApiParam(value = "", required = true) @Valid @RequestBody V1CreatePermissionRequest body);

    @RequestMapping(value = "/api/v1/roles",
            produces = "application/json",
            consumes = "application/xml",
            method = RequestMethod.POST)
    ResponseEntity<V1RoleBase> createPlatformRole(@ApiParam(value = "", required = true) @Valid @RequestBody V1CreateRoleRequest body);


    @RequestMapping(value = "/api/v1/permissions/{permissionName}",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.DELETE)
    ResponseEntity<V1EmptyResponse> deletePlatformPermission(@ApiParam(value = "identifier of the permission", required = true) @PathVariable("permissionName") String permissionName);


    @RequestMapping(value = "/api/v1/roles/{roleName}",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.DELETE)
    ResponseEntity<V1EmptyResponse> deletePlatformRole(@ApiParam(value = "identifier of the role", required = true) @PathVariable("roleName") String roleName);


    @RequestMapping(value = "/api/v1/permissions",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.GET)
    ResponseEntity<List<V1PermissionBase>> listPlatformPermissions();


    @RequestMapping(value = "/api/v1/roles",
            produces = "application/json",
            consumes = "",
            method = RequestMethod.GET)
    ResponseEntity<V1ListRolesResponse> listPlatformRoles();


    @RequestMapping(value = "/api/v1/roles/{roleName}",
            produces = "application/json",
            consumes = "application/xml",
            method = RequestMethod.PUT)
    ResponseEntity<V1RoleBase> updatePlatformRole(@ApiParam(value = "identifier of the role", required = true) @PathVariable("roleName") String roleName, @ApiParam(value = "", required = true) @Valid @RequestBody V1UpdateRoleRequest body);


}
