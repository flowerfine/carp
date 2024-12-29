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
import cn.sliew.carp.framework.common.dict.security.CarpSecUserType;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.module.security.core.repository.entity.SecUser;
import cn.sliew.carp.module.security.core.repository.mapper.SecUserMapper;
import cn.sliew.carp.module.security.core.service.SecUserService;
import cn.sliew.carp.module.security.core.service.convert.SecUserConvert;
import cn.sliew.carp.module.security.core.service.dto.SecUserDTO;
import cn.sliew.carp.module.security.core.service.param.SecUserAddParam;
import cn.sliew.carp.module.security.core.service.param.SecUserListParam;
import cn.sliew.carp.module.security.core.service.param.SecUserUpdateParam;
import cn.sliew.carp.module.security.core.util.PasswordUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SecUserServiceImpl extends ServiceImpl<SecUserMapper, SecUser> implements SecUserService {

    @Override
    public PageResult<SecUserDTO> list(SecUserListParam param) {
        Page<SecUser> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<SecUser> queryChainWrapper = Wrappers.lambdaQuery(SecUser.class)
                .like(StringUtils.hasText(param.getUserName()), SecUser::getUserName, param.getUserName())
                .like(StringUtils.hasText(param.getNickName()), SecUser::getNickName, param.getNickName())
                .eq(StringUtils.hasText(param.getEmail()), SecUser::getEmail, param.getEmail())
                .eq(StringUtils.hasText(param.getPhone()), SecUser::getPhone, param.getPhone())
                .eq(param.getType() != null, SecUser::getType, param.getType())
                .eq(param.getStatus() != null, SecUser::getStatus, param.getStatus())
                .orderByAsc(SecUser::getOrder, SecUser::getId);
        Page<SecUser> secUserPage = page(page, queryChainWrapper);
        PageResult<SecUserDTO> pageResult = new PageResult<>(secUserPage.getCurrent(), secUserPage.getSize(), secUserPage.getTotal());
        pageResult.setRecords(SecUserConvert.INSTANCE.toDto(secUserPage.getRecords()));
        return pageResult;
    }

    @Override
    public List<SecUserDTO> listAll(SecUserListParam param) {
        LambdaQueryWrapper<SecUser> queryChainWrapper = Wrappers.lambdaQuery(SecUser.class)
                .like(StringUtils.hasText(param.getUserName()), SecUser::getUserName, param.getUserName())
                .like(StringUtils.hasText(param.getNickName()), SecUser::getNickName, param.getNickName())
                .eq(StringUtils.hasText(param.getEmail()), SecUser::getEmail, param.getEmail())
                .eq(StringUtils.hasText(param.getPhone()), SecUser::getPhone, param.getPhone())
                .eq(param.getType() != null, SecUser::getType, param.getType())
                .eq(param.getStatus() != null, SecUser::getStatus, param.getStatus())
                .orderByAsc(SecUser::getOrder, SecUser::getId);
        List<SecUser> entities = list(queryChainWrapper);
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
