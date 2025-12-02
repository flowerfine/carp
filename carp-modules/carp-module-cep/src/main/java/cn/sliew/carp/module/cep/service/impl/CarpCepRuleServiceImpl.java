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
package cn.sliew.carp.module.cep.service.impl;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.framework.mybatis.util.PageUtil;
import cn.sliew.carp.module.cep.repository.entity.CarpCepRule;
import cn.sliew.carp.module.cep.repository.mapper.CarpCepRuleMapper;
import cn.sliew.carp.module.cep.service.CarpCepRuleService;
import cn.sliew.carp.module.cep.service.convert.CepRuleConvert;
import cn.sliew.carp.module.cep.service.dto.CarpCepRuleDTO;
import cn.sliew.carp.module.cep.service.param.CepRuleAddParam;
import cn.sliew.carp.module.cep.service.param.CepRulePageParam;
import cn.sliew.carp.module.cep.service.param.CepRuleUpdateParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class CarpCepRuleServiceImpl
        extends ServiceImpl<CarpCepRuleMapper, CarpCepRule>
        implements CarpCepRuleService {

    @Override
    public PageResult<CarpCepRuleDTO> page(CepRulePageParam param) {
        Page<CarpCepRule> page = PageUtil.buildPageParam(param);
        LambdaQueryWrapper<CarpCepRule> queryWrapper = Wrappers.lambdaQuery(CarpCepRule.class)
                .eq(CarpCepRule::getNamespace, param.getNamespace())
                .like(StringUtils.isNotBlank(param.getName()), CarpCepRule::getName, param.getName())
                .orderByDesc(CarpCepRule::getId);
        Page<CarpCepRule> carpAlertLogPage = page(page, queryWrapper);
        return PageUtil.buildPageResult(carpAlertLogPage, CepRuleConvert.INSTANCE::toDto);
    }

    @Override
    public CarpCepRuleDTO get(Long id) {
        CarpCepRule entity = getById(id);
        checkNotNull(entity, "cep rule not exists for id: " + id);
        return CepRuleConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(CepRuleAddParam param) {
        CarpCepRule entity = new CarpCepRule();
        BeanUtils.copyProperties(param, entity);
        entity.setUuid(UUIDUtil.randomUUId());
        entity.setType("user");
        return save(entity);
    }

    @Override
    public boolean update(CepRuleUpdateParam param) {
        CarpCepRule entity = new CarpCepRule();
        BeanUtils.copyProperties(param, entity);
        return updateById(entity);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean deleteBatch(Collection<Long> ids) {
        return removeBatchByIds(ids);
    }
}
