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

import cn.sliew.carp.framework.common.model.ResponseVO;
import cn.sliew.carp.module.security.core.service.SecAuthorizationService;
import cn.sliew.carp.module.security.core.service.dto.SecResourceWebWithAuthorizeDTO;
import cn.sliew.carp.module.security.core.service.dto.SecRoleDTO;
import cn.sliew.carp.module.security.core.service.dto.SecUserDTO;
import cn.sliew.carp.module.security.core.service.param.authorize.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carp/security/authorization")
@Tag(name = "权限模块-授权管理")
public class SecAuthorizationController {

    @Autowired
    private SecAuthorizationService secAuthorizationService;

    @GetMapping("resource-web/authorized-roles")
    @Operation(summary = "查询 资源-web 绑定角色列表", description = "查询 资源-web 绑定角色列表")
    public ResponseEntity<Page<SecRoleDTO>> listAuthorizedRolesByResourceWebId(@Valid SecRoleListByResourceWebParam param) {
        Page<SecRoleDTO> result = secAuthorizationService.listAuthorizedRolesByResourceWebId(param);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("resource-web/unauthorized-roles")
    @Operation(summary = "查询 资源-web 未绑定角色列表", description = "查询 资源-web 未绑定角色列表")
    public ResponseEntity<Page<SecRoleDTO>> listUnauthorizedRolesByResourceWebId(@Valid SecRoleListByResourceWebParam param) {
        Page<SecRoleDTO> result = secAuthorizationService.listUnauthorizedRolesByResourceWebId(param);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("resource-web/roles")
    @Operation(summary = "批量为 资源-web 绑定角色", description = "批量为 资源-web 绑定角色")
    public ResponseEntity<ResponseVO> authorize(@Valid @RequestBody SecRoleBatchAuthorizeForResourceWebParam param) {
        secAuthorizationService.authorize(param);
        return new ResponseEntity<>(ResponseVO.success(), HttpStatus.OK);
    }

    @DeleteMapping("resource-web/roles")
    @Operation(summary = "批量为 资源-web 解除角色绑定", description = "批量为 资源-web 解除角色绑定")
    public ResponseEntity<ResponseVO> unauthorize(@Valid @RequestBody SecRoleBatchAuthorizeForResourceWebParam param) {
        secAuthorizationService.unauthorize(param);
        return new ResponseEntity<>(ResponseVO.success(), HttpStatus.OK);
    }

    @GetMapping("role/resource-webs")
    @Operation(summary = "查询所有 资源-web 和指定角色绑定状态", description = "查询所有 资源-web 和指定角色绑定状态")
    public ResponseEntity<List<SecResourceWebWithAuthorizeDTO>> listResourceWebsByRole(@Valid SecResourceWebListByRoleParam param) {
        List<SecResourceWebWithAuthorizeDTO> result = secAuthorizationService.listResourceWebsByRoleId(param);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("role/resource-webs")
    @Operation(summary = "批量为角色绑定 资源-web", description = "批量为角色绑定 资源-web")
    public ResponseEntity<ResponseVO> authorize(@Valid @RequestBody SecResourceWebBatchAuthorizeForRoleParam param) {
        secAuthorizationService.authorize(param);
        return new ResponseEntity<>(ResponseVO.success(), HttpStatus.OK);
    }

    @DeleteMapping("role/resource-webs")
    @Operation(summary = "批量为角色解除 资源-web 绑定", description = "批量为角色解除 资源-web 绑定")
    public ResponseEntity<ResponseVO> unauthorize(@Valid @RequestBody SecResourceWebBatchAuthorizeForRoleParam param) {
        secAuthorizationService.unauthorize(param);
        return new ResponseEntity<>(ResponseVO.success(), HttpStatus.OK);
    }

    @GetMapping("role/authorized-users")
    @Operation(summary = "查询角色绑定用户列表", description = "查询角色绑定用户列表")
    public ResponseEntity<Page<SecUserDTO>> listAuthorizedUsersByRoleId(@Valid SecUserListByRoleParam param) {
        Page<SecUserDTO> result = secAuthorizationService.listAuthorizedUsersByRoleId(param);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("role/unauthorized-users")
    @Operation(summary = "查询角色未绑定用户列表", description = "查询角色未绑定用户列表")
    public ResponseEntity<Page<SecUserDTO>> listUnauthorizedUsersByRoleId(@Valid SecUserListByRoleParam param) {
        Page<SecUserDTO> result = secAuthorizationService.listUnauthorizedUsersByRoleId(param);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("role/users")
    @Operation(summary = "批量为角色绑定用户", description = "批量为角色绑定用户")
    public ResponseEntity<ResponseVO> authorize(@Valid @RequestBody SecUserBatchAuthorizeForRoleParam param) {
        secAuthorizationService.authorize(param);
        return new ResponseEntity<>(ResponseVO.success(), HttpStatus.OK);
    }

    @DeleteMapping("role/users")
    @Operation(summary = "批量为角色解除用户绑定", description = "批量为角色解除用户绑定")
    public ResponseEntity<ResponseVO> unauthorize(@Valid @RequestBody SecUserBatchAuthorizeForRoleParam param) {
        secAuthorizationService.unauthorize(param);
        return new ResponseEntity<>(ResponseVO.success(), HttpStatus.OK);
    }

    @GetMapping("user/authorized-roles")
    @Operation(summary = "查询用户绑定角色列表", description = "查询用户绑定角色列表")
    public ResponseEntity<List<SecRoleDTO>> listAuthorizedRolesByUserId(@Valid SecRoleListByUserParam param) {
        List<SecRoleDTO> result = secAuthorizationService.listAuthorizedRolesByUserId(param);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("user/unauthorized-roles")
    @Operation(summary = "查询用户未绑定角色列表", description = "查询用户未绑定角色列表")
    public ResponseEntity<List<SecRoleDTO>> listUnauthorizedRolesByUserId(@Valid SecRoleListByUserParam param) {
        List<SecRoleDTO> result = secAuthorizationService.listUnauthorizedRolesByUserId(param);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("user/roles")
    @Operation(summary = "批量为用户绑定角色", description = "批量为用户绑定角色")
    public ResponseEntity<ResponseVO> authorize(@Valid @RequestBody SecRoleBatchAuthorizeForUserParam param) {
        secAuthorizationService.authorize(param);
        return new ResponseEntity<>(ResponseVO.success(), HttpStatus.OK);
    }

    @DeleteMapping("user/roles")
    @Operation(summary = "批量为用户解除角色绑定", description = "批量为用户解除角色绑定")
    public ResponseEntity<ResponseVO> unauthorize(@Valid @RequestBody SecRoleBatchAuthorizeForUserParam param) {
        secAuthorizationService.unauthorize(param);
        return new ResponseEntity<>(ResponseVO.success(), HttpStatus.OK);
    }

}
