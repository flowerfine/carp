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
package cn.sliew.carp.module.security.core.service;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.security.core.repository.entity.SecUserRole;
import cn.sliew.carp.module.security.core.service.dto.SecRoleDTO;
import cn.sliew.carp.module.security.core.service.dto.SecUserDTO;
import cn.sliew.carp.module.security.core.service.param.authorize.SecRoleBatchAuthorizeForUserParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecRoleListByUserParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecUserBatchAuthorizeForRoleParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecUserListByRoleParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SecUserRoleService extends IService<SecUserRole> {

    // -------------------------------------------------------------------------------------------
    // role -> user
    // -------------------------------------------------------------------------------------------

    /**
     * 查询角色绑定用户列表
     */
    PageResult<SecUserDTO> listAuthorizedUsersByRoleId(SecUserListByRoleParam param);

    /**
     * 查询角色未绑定用户列表
     */
    PageResult<SecUserDTO> listUnauthorizedUsersByRoleId(SecUserListByRoleParam param);

    /**
     * 批量为角色绑定用户
     */
    void authorize(SecUserBatchAuthorizeForRoleParam param);

    /**
     * 批量为角色解除用户绑定
     */
    void unauthorize(SecUserBatchAuthorizeForRoleParam param);

    void deleteByRoleId(Long roleId);

    // -------------------------------------------------------------------------------------------
    // user -> role
    // -------------------------------------------------------------------------------------------

    /**
     * 查询用户绑定所有角色列表
     */
    List<SecRoleDTO> listAllAuthorizedRolesByUserId(SecRoleListByUserParam param);

    /**
     * 查询用户绑定角色列表
     */
    PageResult<SecRoleDTO> listAuthorizedRolesByUserId(SecRoleListByUserParam param);

    /**
     * 查询用户未绑定角色列表
     */
    PageResult<SecRoleDTO> listUnauthorizedRolesByUserId(SecRoleListByUserParam param);

    /**
     * 批量为用户绑定角色
     */
    void authorize(SecRoleBatchAuthorizeForUserParam param);

    /**
     * 批量为用户解除角色绑定
     */
    void unauthorize(SecRoleBatchAuthorizeForUserParam param);

    void deleteByUserId(Long userId);
}
