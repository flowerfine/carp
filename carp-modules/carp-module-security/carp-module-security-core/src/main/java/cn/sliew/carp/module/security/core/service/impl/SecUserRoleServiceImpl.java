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

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.security.core.repository.entity.SecRole;
import cn.sliew.carp.module.security.core.repository.entity.SecUser;
import cn.sliew.carp.module.security.core.repository.entity.SecUserRole;
import cn.sliew.carp.module.security.core.repository.mapper.SecUserRoleMapper;
import cn.sliew.carp.module.security.core.service.SecUserRoleService;
import cn.sliew.carp.module.security.core.service.convert.SecRoleConvert;
import cn.sliew.carp.module.security.core.service.convert.SecUserConvert;
import cn.sliew.carp.module.security.core.service.dto.SecRoleDTO;
import cn.sliew.carp.module.security.core.service.dto.SecUserDTO;
import cn.sliew.carp.module.security.core.service.param.authorize.SecRoleBatchAuthorizeForUserParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecRoleListByUserParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecUserBatchAuthorizeForRoleParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecUserListByRoleParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecUserRoleServiceImpl extends ServiceImpl<SecUserRoleMapper, SecUserRole> implements SecUserRoleService {

    @Override
    public PageResult<SecUserDTO> listAuthorizedUsersByRoleId(SecUserListByRoleParam param) {
        Page page = new Page(param.getCurrent(), param.getPageSize());
        Page<SecUser> secUserPage = baseMapper.selectRelatedUsersByRole(page, param.getRoleId(), param.getStatus(), param.getUserName());
        PageResult<SecUserDTO> result = new PageResult<>(secUserPage.getCurrent(), secUserPage.getSize(), secUserPage.getTotal());
        List<SecUserDTO> secUserDTOS = SecUserConvert.INSTANCE.toDto(secUserPage.getRecords());
        result.setRecords(secUserDTOS);
        return result;
    }

    @Override
    public PageResult<SecUserDTO> listUnauthorizedUsersByRoleId(SecUserListByRoleParam param) {
        Page page = new Page(param.getCurrent(), param.getPageSize());
        Page<SecUser> secUserPage = baseMapper.selectUnrelatedUsersByRole(page, param.getRoleId(), param.getStatus(), param.getUserName());
        PageResult<SecUserDTO> result = new PageResult<>(secUserPage.getCurrent(), secUserPage.getSize(), secUserPage.getTotal());
        List<SecUserDTO> secUserDTOS = SecUserConvert.INSTANCE.toDto(secUserPage.getRecords());
        result.setRecords(secUserDTOS);
        return result;
    }

    @Override
    public void authorize(SecUserBatchAuthorizeForRoleParam param) {
        List<SecUserRole> entities = new ArrayList<>();
        for (Long userId : param.getUserIds()) {
            SecUserRole record = new SecUserRole();
            record.setUserId(userId);
            record.setRoleId(param.getRoleId());
            entities.add(record);
        }
        saveBatch(entities);
    }

    @Override
    public void unauthorize(SecUserBatchAuthorizeForRoleParam param) {
        for (Long userId : param.getUserIds()) {
            LambdaQueryWrapper<SecUserRole> queryWrapper = Wrappers.lambdaQuery(SecUserRole.class)
                    .eq(SecUserRole::getUserId, userId)
                    .eq(SecUserRole::getRoleId, param.getRoleId());
            baseMapper.delete(queryWrapper);
        }
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        LambdaUpdateWrapper<SecUserRole> updateWrapper = Wrappers.lambdaUpdate(SecUserRole.class)
                .eq(SecUserRole::getRoleId, roleId);
        baseMapper.delete(updateWrapper);
    }

    @Override
    public List<SecRoleDTO> listAllAuthorizedRolesByUserId(SecRoleListByUserParam param) {
        List<SecRole> secRoleList = baseMapper.selectRelatedRolesByUser(param.getUserId(), null, param.getName());
        return SecRoleConvert.INSTANCE.toDto(secRoleList);
    }

    @Override
    public PageResult<SecRoleDTO> listAuthorizedRolesByUserId(SecRoleListByUserParam param) {
        Page page = new Page(param.getCurrent(), param.getPageSize());
        Page<SecRole> secRolePage = baseMapper.selectRelatedRolesByUser(page, param.getUserId(), null, param.getName());
        PageResult<SecRoleDTO> result = new PageResult<>(secRolePage.getCurrent(), secRolePage.getSize(), secRolePage.getTotal());
        List<SecRoleDTO> secRoleDTOS = SecRoleConvert.INSTANCE.toDto(secRolePage.getRecords());
        result.setRecords(secRoleDTOS);
        return result;
    }

    @Override
    public PageResult<SecRoleDTO> listUnauthorizedRolesByUserId(SecRoleListByUserParam param) {
        Page page = new Page(param.getCurrent(), param.getPageSize());
        Page<SecRole> secRolePage = baseMapper.selectUnrelatedRolesByUser(page, param.getUserId(), null, param.getName());
        PageResult<SecRoleDTO> result = new PageResult<>(secRolePage.getCurrent(), secRolePage.getSize(), secRolePage.getTotal());
        List<SecRoleDTO> secRoleDTOS = SecRoleConvert.INSTANCE.toDto(secRolePage.getRecords());
        result.setRecords(secRoleDTOS);
        return result;
    }

    @Override
    public void authorize(SecRoleBatchAuthorizeForUserParam param) {
        List<SecUserRole> entities = new ArrayList<>();
        for (Long roleId : param.getRoleIds()) {
            SecUserRole record = new SecUserRole();
            record.setUserId(param.getUserId());
            record.setRoleId(roleId);
            entities.add(record);
        }
        saveBatch(entities);
    }

    @Override
    public void unauthorize(SecRoleBatchAuthorizeForUserParam param) {
        for (Long roleId : param.getRoleIds()) {
            LambdaQueryWrapper<SecUserRole> queryWrapper = Wrappers.lambdaQuery(SecUserRole.class)
                    .eq(SecUserRole::getUserId, param.getUserId())
                    .eq(SecUserRole::getRoleId, roleId);
            baseMapper.delete(queryWrapper);
        }
    }

    @Override
    public void deleteByUserId(Long userId) {
        LambdaUpdateWrapper<SecUserRole> updateWrapper = Wrappers.lambdaUpdate(SecUserRole.class)
                .eq(SecUserRole::getUserId, userId);
        baseMapper.delete(updateWrapper);
    }
}
