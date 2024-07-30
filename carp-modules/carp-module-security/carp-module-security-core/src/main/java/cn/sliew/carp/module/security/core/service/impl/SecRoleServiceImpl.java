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

import cn.hutool.core.bean.BeanUtil;
import cn.sliew.carp.framework.common.dict.security.RoleType;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.module.security.core.repository.entity.SecRole;
import cn.sliew.carp.module.security.core.repository.mapper.SecRoleMapper;
import cn.sliew.carp.module.security.core.service.SecRoleService;
import cn.sliew.carp.module.security.core.service.convert.SecRoleConvert;
import cn.sliew.carp.module.security.core.service.dto.SecRoleDTO;
import cn.sliew.carp.module.security.core.service.param.SecRoleAddParam;
import cn.sliew.carp.module.security.core.service.param.SecRoleListParam;
import cn.sliew.carp.module.security.core.service.param.SecRoleUpdateParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Service
public class SecRoleServiceImpl extends ServiceImpl<SecRoleMapper, SecRole> implements SecRoleService {

    @Override
    public PageResult<SecRoleDTO> list(SecRoleListParam param) {
        Page<SecRole> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<SecRole> queryChainWrapper = Wrappers.lambdaQuery(SecRole.class)
                .like(StringUtils.hasText(param.getName()), SecRole::getName, param.getName())
                .eq(param.getType() != null, SecRole::getType, param.getType())
                .eq(param.getStatus() != null, SecRole::getStatus, param.getStatus())
                .orderByAsc(SecRole::getOrder, SecRole::getId);
        Page<SecRole> secRolePage = page(page, queryChainWrapper);
        PageResult<SecRoleDTO> pageResult = new PageResult<>(secRolePage.getCurrent(), secRolePage.getSize(), secRolePage.getTotal());
        pageResult.setRecords(SecRoleConvert.INSTANCE.toDto(secRolePage.getRecords()));
        return pageResult;
    }

    @Override
    public List<SecRoleDTO> listAll(SecRoleListParam param) {
        LambdaQueryWrapper<SecRole> queryChainWrapper = Wrappers.lambdaQuery(SecRole.class)
                .like(StringUtils.hasText(param.getName()), SecRole::getName, param.getName())
                .eq(param.getType() != null, SecRole::getType, param.getType())
                .eq(param.getStatus() != null, SecRole::getStatus, param.getStatus())
                .orderByAsc(SecRole::getOrder, SecRole::getId);
        List<SecRole> entities = list(queryChainWrapper);
        return SecRoleConvert.INSTANCE.toDto(entities);
    }

    @Override
    public SecRoleDTO get(Long id) {
        SecRole entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("role not exists for id: " + id));
        return SecRoleConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(SecRoleAddParam param) {
        SecRole entity = BeanUtil.copyProperties(param, SecRole.class);
        entity.setType(RoleType.CUSTOM);
        return save(entity);
    }

    @Override
    public boolean update(SecRoleUpdateParam param) {
        SecRole entity = BeanUtil.copyProperties(param, SecRole.class);
        return saveOrUpdate(entity);
    }

    @Override
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean deleteBatch(Collection<Long> ids) {
        return removeByIds(ids);
    }
}
