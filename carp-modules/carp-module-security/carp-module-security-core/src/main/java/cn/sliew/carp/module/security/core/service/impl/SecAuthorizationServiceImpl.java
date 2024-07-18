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

package cn.sliew.carp.module.security.core.service.impl;

import cn.sliew.carp.module.security.core.service.SecAuthorizationService;
import cn.sliew.carp.module.security.core.service.SecResourceWebRoleService;
import cn.sliew.carp.module.security.core.service.SecUserRoleService;
import cn.sliew.carp.module.security.core.service.dto.SecRoleDTO;
import cn.sliew.carp.module.security.core.service.dto.SecUserDTO;
import cn.sliew.carp.module.security.core.service.param.authorize.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecAuthorizationServiceImpl implements SecAuthorizationService {

    @Autowired
    private SecResourceWebRoleService secResourceWebRoleService;
    @Autowired
    private SecUserRoleService secUserRoleService;

    @Override
    public Page<SecRoleDTO> listAuthorizedRolesByResourceWebId(SecRoleListByResourceWebParam param) {
        return secResourceWebRoleService.listAuthorizedRolesByResourceWebId(param);
    }

    @Override
    public Page<SecRoleDTO> listUnauthorizedRolesByResourceWebId(SecRoleListByResourceWebParam param) {
        return secResourceWebRoleService.listUnauthorizedRolesByResourceWebId(param);
    }

    @Override
    public void authorize(SecRoleBatchAuthorizeForResourceWebParam param) {
        secResourceWebRoleService.authorize(param);
    }

    @Override
    public void unauthorize(SecRoleBatchAuthorizeForResourceWebParam param) {
        secResourceWebRoleService.unauthorize(param);
    }

    @Override
    public List listResourceWebsByRoleId(SecResourceWebListByRoleParam param) {
        return secResourceWebRoleService.listResourceWebsByRoleId(param);
    }

    @Override
    public void authorize(SecResourceWebBatchAuthorizeForRoleParam param) {
        secResourceWebRoleService.authorize(param);
    }

    @Override
    public void unauthorize(SecResourceWebBatchAuthorizeForRoleParam param) {
        secResourceWebRoleService.unauthorize(param);
    }

    @Override
    public Page<SecUserDTO> listAuthorizedUsersByRoleId(SecUserListByRoleParam param) {
        return secUserRoleService.listAuthorizedUsersByRoleId(param);
    }

    @Override
    public Page<SecUserDTO> listUnauthorizedUsersByRoleId(SecUserListByRoleParam param) {
        return secUserRoleService.listUnauthorizedUsersByRoleId(param);
    }

    @Override
    public void authorize(SecUserBatchAuthorizeForRoleParam param) {
        secUserRoleService.authorize(param);
    }

    @Override
    public void unauthorize(SecUserBatchAuthorizeForRoleParam param) {
        secUserRoleService.unauthorize(param);
    }

    @Override
    public List<SecRoleDTO> listAuthorizedRolesByUserId(SecRoleListByUserParam param) {
        return secUserRoleService.listAuthorizedRolesByUserId(param);
    }

    @Override
    public List<SecRoleDTO> listUnauthorizedRolesByUserId(SecRoleListByUserParam param) {
        return secUserRoleService.listUnauthorizedRolesByUserId(param);
    }

    @Override
    public void authorize(SecRoleBatchAuthorizeForUserParam param) {
        secUserRoleService.authorize(param);
    }

    @Override
    public void unauthorize(SecRoleBatchAuthorizeForUserParam param) {
        secUserRoleService.unauthorize(param);
    }
}
