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

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.sliew.carp.framework.common.model.BasePageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.framework.mybatis.util.PageUtil;
import cn.sliew.carp.module.alert.model.webhook.WebhookAlert;
import cn.sliew.carp.module.alert.model.webhook.WebhookAlertList;
import cn.sliew.carp.module.alert.repository.entity.CarpAlertLog;
import cn.sliew.carp.module.alert.repository.mapper.CarpAlertLogMapper;
import cn.sliew.carp.module.alert.service.AlertLogService;
import cn.sliew.carp.module.alert.service.convert.AlertLogConvert;
import cn.sliew.carp.module.alert.service.dto.CarpAlertLogDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class AlertLogServiceImpl
        extends ServiceImpl<CarpAlertLogMapper, CarpAlertLog>
        implements AlertLogService {

    @Override
    public PageResult<CarpAlertLogDTO> page(BasePageParam param) {
        Page<CarpAlertLog> page = PageUtil.buildBasePageParam(param);
        LambdaQueryWrapper<CarpAlertLog> queryWrapper = Wrappers.lambdaQuery(CarpAlertLog.class)
                .orderByDesc(CarpAlertLog::getId);
        Page<CarpAlertLog> carpAlertLogPage = page(page, queryWrapper);
        return PageUtil.buildPageResult(carpAlertLogPage, AlertLogConvert.INSTANCE::toDto);
    }

    @Override
    public CarpAlertLogDTO get(Long id) {
        CarpAlertLog entity = getById(id);
        checkNotNull(entity, "alert log not exists for id: " + id);
        return AlertLogConvert.INSTANCE.toDto(entity);
    }

    @Override
    public void add(CarpAlertLogDTO param) {
        CarpAlertLog entity = AlertLogConvert.INSTANCE.toDo(param);
        save(entity);
    }

    @Override
    public void addAlerts(WebhookAlertList alertList) {
        if (CollectionUtils.isEmpty(alertList.getAlerts())) {
            return;
        }
        for (WebhookAlert alert : alertList.getAlerts()) {
            CarpAlertLogDTO dto = new CarpAlertLogDTO();
            dto.setVersion(alertList.getVersion());
            dto.setGroupKey(alertList.getGroupKey());
            dto.setGroupLabels(alertList.getGroupLabels());
            dto.setReceiver(alertList.getReceiver());
            dto.setCommonLabels(alertList.getCommonLabels());
            dto.setCommonAnnotations(alertList.getCommonAnnotations());
            dto.setFingerprint(alert.getFingerprint());
            dto.setStatus(alert.getStatus());
            dto.setLabels(alert.getLabels());
            dto.setAnnotations(alert.getAnnotations());
            dto.setStartsAt(LocalDateTimeUtil.toEpochMilli(alert.getStartsAt()));
            dto.setEndsAt(LocalDateTimeUtil.toEpochMilli(alert.getEndsAt()));
            add(dto);
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
}
