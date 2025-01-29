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
import cn.sliew.carp.framework.common.dict.security.CarpSecDeptStatus;
import cn.sliew.carp.framework.common.dict.security.CarpSecUserType;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.module.security.core.repository.entity.SecUser;
import cn.sliew.carp.module.security.core.repository.mapper.SecUserMapper;
import cn.sliew.carp.module.security.core.service.SecDeptService;
import cn.sliew.carp.module.security.core.service.SecUserRoleService;
import cn.sliew.carp.module.security.core.service.SecUserService;
import cn.sliew.carp.module.security.core.service.convert.SecUserConvert;
import cn.sliew.carp.module.security.core.service.dto.SecDeptDTO;
import cn.sliew.carp.module.security.core.service.dto.SecUserDTO;
import cn.sliew.carp.module.security.core.service.param.SecDeptListParam;
import cn.sliew.carp.module.security.core.service.param.SecUserAddParam;
import cn.sliew.carp.module.security.core.service.param.SecUserListParam;
import cn.sliew.carp.module.security.core.service.param.SecUserUpdateParam;
import cn.sliew.carp.module.security.core.util.PasswordUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SecUserServiceImpl extends ServiceImpl<SecUserMapper, SecUser> implements SecUserService {

    @Autowired
    private SecUserRoleService secUserRoleService;
    @Autowired
    private SecDeptService secDeptService;

    @Override
    public PageResult<SecUserDTO> page(SecUserListParam param) {
        Page<SecUser> page = new Page<>(param.getCurrent(), param.getPageSize());
        List<Long> childDeptIds = recurse(param.getDeptId());
        Page<SecUser> secUserPage = baseMapper.list(page, param, childDeptIds);
        PageResult<SecUserDTO> pageResult = new PageResult<>(secUserPage.getCurrent(), secUserPage.getSize(), secUserPage.getTotal());
        pageResult.setRecords(SecUserConvert.INSTANCE.toDto(secUserPage.getRecords()));
        return pageResult;
    }

    private List<Long> recurse(Long pid) {
        SecDeptListParam secDeptListParam = SecDeptListParam.builder()
                .pid(pid)
                .status(CarpSecDeptStatus.ENABLED)
                .build();
        List<Long> deptIds = Lists.newArrayList();
        deptIds.add(pid);
        List<SecDeptDTO> secDeptDTOS = secDeptService.listAll(secDeptListParam);
        secDeptDTOS.stream().map(SecDeptDTO::getId)
                .flatMap(childDeptId -> recurse(childDeptId).stream())
                .forEach(deptIds::add);
        return deptIds;
    }

    @Override
    public List<SecUserDTO> listAll(SecUserListParam param) {

        List<SecUser> entities = baseMapper.list(param);
        return SecUserConvert.INSTANCE.toDto(entities);
    }

    @Override
    public SecUserDTO get(Long id) {
        SecUser entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("user not exists for id: " + id));
        return SecUserConvert.INSTANCE.toDto(entity);
    }

    @Override
    public Optional<SecUserDTO> getByUserName(String userName) {
        // fixme LambdaQueryChainWrapper 不支持 getOne
        LambdaQueryWrapper<SecUser> queryChainWrapper = Wrappers.lambdaQuery(SecUser.class)
                .eq(SecUser::getUserName, userName);

        Optional<SecUser> optional = getOneOpt(queryChainWrapper);
        return optional.map(user -> SecUserConvert.INSTANCE.toDto(user));
    }

    @Override
    public boolean add(SecUserAddParam param) {
        SecUser entity = BeanUtil.copyProperties(param, SecUser.class);
        entity.setType(CarpSecUserType.CUSTOM);
        entity.setSalt(RandomStringUtils.randomAlphanumeric(32));
        entity.setPassword(PasswordUtil.digestPassword(param.getPassword(), entity.getSalt()));
        return save(entity);
    }

    @Override
    public boolean update(SecUserUpdateParam param) {
        SecUser entity = BeanUtil.copyProperties(param, SecUser.class);
        return saveOrUpdate(entity);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean delete(Long id) {
        secUserRoleService.deleteByUserId(id);
        // todo 关联部门
        return removeById(id);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean deleteBatch(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids) == false) {
            ids.forEach(this::delete);
        }
        return true;
    }
}
