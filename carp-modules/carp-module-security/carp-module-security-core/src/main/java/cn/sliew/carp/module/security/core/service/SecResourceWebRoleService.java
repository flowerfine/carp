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
import cn.sliew.carp.module.security.core.repository.entity.SecResourceWebRole;
import cn.sliew.carp.module.security.core.service.dto.SecResourceWebDTO;
import cn.sliew.carp.module.security.core.service.dto.SecResourceWebWithAuthorizeDTO;
import cn.sliew.carp.module.security.core.service.dto.SecRoleDTO;
import cn.sliew.carp.module.security.core.service.param.authorize.SecResourceWebBatchAuthorizeForRoleParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecResourceWebListByRoleParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecRoleBatchAuthorizeForResourceWebParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecRoleListByResourceWebParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SecResourceWebRoleService extends IService<SecResourceWebRole> {

    // -------------------------------------------------------------------------------------------
    // resource-web -> role
    // -------------------------------------------------------------------------------------------

    /**
     * 查询 资源-web 绑定角色列表
     */
    PageResult<SecRoleDTO> listAuthorizedRolesByResourceWebId(SecRoleListByResourceWebParam param);

    /**
     * 查询 资源-web 未绑定角色列表
     */
    PageResult<SecRoleDTO> listUnauthorizedRolesByResourceWebId(SecRoleListByResourceWebParam param);

    /**
     * 批量为 资源-web 绑定角色
     */
    void authorize(SecRoleBatchAuthorizeForResourceWebParam param);

    /**
     * 批量为 资源-web 解除角色绑定
     */
    void unauthorize(SecRoleBatchAuthorizeForResourceWebParam param);

    void deleteByResourceWebId(Long resourceWebId);

    // -------------------------------------------------------------------------------------------
    // role -> resource-web
    // -------------------------------------------------------------------------------------------

    /**
     * 查询所有 资源-web 和指定角色绑定状态
     */
    List<SecResourceWebWithAuthorizeDTO> listResourceWebsByRoleId(SecResourceWebListByRoleParam param);

    /**
     * 查询角色绑定 资源-web 列表
     */
    List<SecResourceWebDTO> listAuthorizedResourceWebsByRoleId(SecResourceWebListByRoleParam param);

    /**
     * 批量为角色绑定 资源-web
     */
    void authorize(SecResourceWebBatchAuthorizeForRoleParam param);

    /**
     * 批量为角色解除 资源-web 绑定
     */
    void unauthorize(SecResourceWebBatchAuthorizeForRoleParam param);

    void deleteByRoleId(Long roleId);
}
