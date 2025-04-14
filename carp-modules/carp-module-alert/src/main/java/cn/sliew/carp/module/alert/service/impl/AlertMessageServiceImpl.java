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
import cn.sliew.carp.module.alert.repository.entity.CarpAlertMessage;
import cn.sliew.carp.module.alert.repository.mapper.CarpAlertMessageMapper;
import cn.sliew.carp.module.alert.service.AlertMessageService;
import cn.sliew.carp.module.alert.service.dto.CarpAlertMessageDTO;
import cn.sliew.carp.module.alert.service.param.AlertMessagePageParam;
import cn.sliew.carp.module.alert.service.param.AlertMessageReceiveParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

@Service
public class AlertMessageServiceImpl
        extends ServiceImpl<CarpAlertMessageMapper, CarpAlertMessage>
        implements AlertMessageService {

    @Override
    public PageResult<CarpAlertMessageDTO> page(AlertMessagePageParam param) {

        return null;
    }

    @Override
    public CarpAlertMessageDTO get(Long id) {
        return null;
    }

    @Override
    public boolean receive(AlertMessageReceiveParam param) {
        CarpAlertMessage message = new CarpAlertMessage();
        BeanUtils.copyProperties(param, message);
        message.setCount(1L);
        LambdaQueryWrapper<CarpAlertMessage> queryWrapper = Wrappers.lambdaQuery(CarpAlertMessage.class)
                .eq(CarpAlertMessage::getNamespace, param.getNamespace())
                .eq(CarpAlertMessage::getRuleId, param.getRuleId())
                .eq(CarpAlertMessage::getFingerprint, param.getFingerprint());
        CarpAlertMessage entity = getOne(queryWrapper, false);
        if (Objects.nonNull(entity)) {
            message.setCount(entity.getCount() + 1);
            return updateById(message);
        } else {
            return save(entity);
        }
    }


    @Override
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean deleteBatch(Collection<Long> ids) {
        return removeBatchByIds(ids);
    }

    @Override
    public boolean deleteByRule(String namespace, String ruleId) {
        LambdaUpdateWrapper<CarpAlertMessage> updateWrapper = Wrappers.lambdaUpdate(CarpAlertMessage.class)
                .eq(CarpAlertMessage::getNamespace, namespace)
                .eq(CarpAlertMessage::getRuleId, ruleId);
        return remove(updateWrapper);
    }
}
