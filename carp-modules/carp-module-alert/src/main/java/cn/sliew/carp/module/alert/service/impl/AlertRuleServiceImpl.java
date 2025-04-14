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
package cn.sliew.carp.module.alert.service.impl;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.framework.mybatis.util.PageUtil;
import cn.sliew.carp.module.alert.repository.entity.CarpAlertRule;
import cn.sliew.carp.module.alert.repository.mapper.CarpAlertRuleMapper;
import cn.sliew.carp.module.alert.service.AlertMessageService;
import cn.sliew.carp.module.alert.service.AlertRuleService;
import cn.sliew.carp.module.alert.service.convert.AlertRuleConvert;
import cn.sliew.carp.module.alert.service.dto.CarpAlertRuleDTO;
import cn.sliew.carp.module.alert.service.param.AlertMessageReceiveParam;
import cn.sliew.carp.module.alert.service.param.AlertRuleAddParam;
import cn.sliew.carp.module.alert.service.param.AlertRulePageParam;
import cn.sliew.carp.module.alert.service.param.AlertRuleUpdateParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class AlertRuleServiceImpl
        extends ServiceImpl<CarpAlertRuleMapper, CarpAlertRule>
        implements AlertRuleService {

    @Autowired
    private AlertMessageService alertMessageService;

    @Override
    public PageResult<CarpAlertRuleDTO> page(AlertRulePageParam param) {
        Page<CarpAlertRule> page = PageUtil.buildPageParam(param);
        LambdaQueryWrapper<CarpAlertRule> queryWrapper = Wrappers.lambdaQuery(CarpAlertRule.class)
                .eq(CarpAlertRule::getNamespace, param.getNamespace())
                .like(StringUtils.hasText(param.getName()), CarpAlertRule::getName, param.getName())
                .eq(StringUtils.hasText(param.getUuid()), CarpAlertRule::getUuid, param.getUuid());
        Page<CarpAlertRule> carpAlertRulePage = page(page, queryWrapper);
        return PageUtil.buildPageResult(carpAlertRulePage, AlertRuleConvert.INSTANCE::toDto);
    }

    @Override
    public CarpAlertRuleDTO get(Long id) {
        CarpAlertRule entity = getById(id);
        checkNotNull(entity, "alert rule not exists for id: " + id);
        return AlertRuleConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(AlertRuleAddParam param) {
        CarpAlertRule entity = new CarpAlertRule();
        BeanUtils.copyProperties(param, entity);
        if (save(entity)) {
            AlertMessageReceiveParam receiveParam = AlertMessageReceiveParam.builder().build();
            alertMessageService.receive(receiveParam);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(AlertRuleUpdateParam param) {
        CarpAlertRule entity = new CarpAlertRule();
        BeanUtils.copyProperties(param, entity);
        return updateById(entity);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean delete(Long id) {
        CarpAlertRuleDTO ruleDTO = get(id);
        removeById(id);
        return alertMessageService.deleteByRule(ruleDTO.getNamespace(), ruleDTO.getUuid());
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean deleteBatch(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return true;
        }
        for (Long id : ids) {
            delete(id);
        }
        return true;
    }
}
