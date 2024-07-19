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

import cn.sliew.carp.module.security.core.repository.entity.SecResourceWeb;
import cn.sliew.carp.module.security.core.repository.entity.SecResourceWebRole;
import cn.sliew.carp.module.security.core.repository.entity.SecResourceWebVO;
import cn.sliew.carp.module.security.core.repository.entity.SecRole;
import cn.sliew.carp.module.security.core.repository.mapper.SecResourceWebRoleMapper;
import cn.sliew.carp.module.security.core.service.SecResourceWebRoleService;
import cn.sliew.carp.module.security.core.service.convert.SecResourceWebConvert;
import cn.sliew.carp.module.security.core.service.convert.SecResourceWebWithAuthorizeConvert;
import cn.sliew.carp.module.security.core.service.convert.SecRoleConvert;
import cn.sliew.carp.module.security.core.service.dto.SecResourceWebDTO;
import cn.sliew.carp.module.security.core.service.dto.SecResourceWebWithAuthorizeDTO;
import cn.sliew.carp.module.security.core.service.dto.SecRoleDTO;
import cn.sliew.carp.module.security.core.service.param.authorize.SecResourceWebBatchAuthorizeForRoleParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecResourceWebListByRoleParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecRoleBatchAuthorizeForResourceWebParam;
import cn.sliew.carp.module.security.core.service.param.authorize.SecRoleListByResourceWebParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SecResourceWebRoleServiceImpl extends ServiceImpl<SecResourceWebRoleMapper, SecResourceWebRole> implements SecResourceWebRoleService {

    @Override
    public Page<SecRoleDTO> listAuthorizedRolesByResourceWebId(SecRoleListByResourceWebParam param) {
        Page page = new Page(param.getCurrent(), param.getPageSize());
        Page<SecRole> secRolePage = baseMapper.selectRelatedRolesByWebResource(page, param.getResourceWebId(), param.getStatus(), param.getName());
        Page<SecRoleDTO> result = new Page<>(secRolePage.getCurrent(), secRolePage.getSize(), secRolePage.getTotal());
        List<SecRoleDTO> secRoleDTOS = SecRoleConvert.INSTANCE.toDto(secRolePage.getRecords());
        result.setRecords(secRoleDTOS);
        return result;
    }

    @Override
    public Page<SecRoleDTO> listUnauthorizedRolesByResourceWebId(SecRoleListByResourceWebParam param) {
        Page page = new Page(param.getCurrent(), param.getPageSize());
        Page<SecRole> secRolePage = baseMapper.selectUnrelatedRolesByWebResource(page, param.getResourceWebId(), param.getStatus(), param.getName());
        Page<SecRoleDTO> result = new Page<>(secRolePage.getCurrent(), secRolePage.getSize(), secRolePage.getTotal());
        List<SecRoleDTO> secRoleDTOS = SecRoleConvert.INSTANCE.toDto(secRolePage.getRecords());
        result.setRecords(secRoleDTOS);
        return result;
    }

    @Override
    public void authorize(SecRoleBatchAuthorizeForResourceWebParam param) {
        for (Long roleId : param.getRoleIds()) {
            SecResourceWebRole record = new SecResourceWebRole();
            record.setResourceWebId(param.getResourceWebId());
            record.setRoleId(roleId);
            baseMapper.insert(record);
        }
    }

    @Override
    public void unauthorize(SecRoleBatchAuthorizeForResourceWebParam param) {
        for (Long roleId : param.getRoleIds()) {
            LambdaQueryWrapper<SecResourceWebRole> queryWrapper = Wrappers.lambdaQuery(SecResourceWebRole.class)
                    .eq(SecResourceWebRole::getResourceWebId, param.getResourceWebId())
                    .eq(SecResourceWebRole::getRoleId, roleId);
            baseMapper.delete(queryWrapper);
        }
    }

    @Override
    public List<SecResourceWebWithAuthorizeDTO> listResourceWebsByRoleId(SecResourceWebListByRoleParam param) {
        List<SecResourceWebVO> secResourceWebVOS = baseMapper.selectAllResourceWebWithAuthorizeStatus(param.getRoleId(), 0L);
        List<SecResourceWebWithAuthorizeDTO> result = SecResourceWebWithAuthorizeConvert.INSTANCE.toDto(secResourceWebVOS);
        result.forEach(dto -> recurse(param.getRoleId(), dto));
        return result;
    }

    @Override
    public List<SecResourceWebDTO> listAuthorizedResourceWebsByRoleId(SecResourceWebListByRoleParam param) {
        List<SecResourceWeb> secResourceWebS = baseMapper.selectRelatedWebResourceByRole(param.getRoleId());
        return SecResourceWebConvert.INSTANCE.toDto(secResourceWebS);
    }

    private void recurse(Long roleId, SecResourceWebWithAuthorizeDTO resourceWebDTO) {
        List<SecResourceWebWithAuthorizeDTO> children = listResourceWebsByRoleIdAndPid(roleId, resourceWebDTO.getId());
        if (CollectionUtils.isEmpty(children) == false) {
            resourceWebDTO.setChildren(children);
            children.forEach(child -> recurse(roleId, child));
        }
    }

    private List<SecResourceWebWithAuthorizeDTO> listResourceWebsByRoleIdAndPid(Long roleId, Long pid) {
        List<SecResourceWebVO> secResourceWebVOS = baseMapper.selectAllResourceWebWithAuthorizeStatus(roleId, pid);
        return SecResourceWebWithAuthorizeConvert.INSTANCE.toDto(secResourceWebVOS);
    }

    @Override
    public void authorize(SecResourceWebBatchAuthorizeForRoleParam param) {
        for (Long resourceWebId : param.getResourceWebIds()) {
            SecResourceWebRole record = new SecResourceWebRole();
            record.setResourceWebId(resourceWebId);
            record.setRoleId(param.getRoleId());
            baseMapper.insert(record);
        }
    }

    @Override
    public void unauthorize(SecResourceWebBatchAuthorizeForRoleParam param) {
        for (Long resourceWebId : param.getResourceWebIds()) {
            LambdaQueryWrapper<SecResourceWebRole> queryWrapper = Wrappers.lambdaQuery(SecResourceWebRole.class)
                    .eq(SecResourceWebRole::getResourceWebId, resourceWebId)
                    .eq(SecResourceWebRole::getRoleId, param.getRoleId());
            baseMapper.delete(queryWrapper);
        }
    }
}
