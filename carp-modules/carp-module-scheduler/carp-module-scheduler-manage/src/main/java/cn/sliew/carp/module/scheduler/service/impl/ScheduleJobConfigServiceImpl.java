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
package cn.sliew.carp.module.scheduler.service.impl;

import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleType;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.module.scheduler.repository.entity.ScheduleJobConfig;
import cn.sliew.carp.module.scheduler.repository.entity.ScheduleJobConfigVO;
import cn.sliew.carp.module.scheduler.repository.mapper.ScheduleJobConfigMapper;
import cn.sliew.carp.module.scheduler.service.ScheduleJobConfigService;
import cn.sliew.carp.module.scheduler.service.convert.ScheduleJobConfigConvert;
import cn.sliew.carp.module.scheduler.service.dto.ScheduleJobConfigDTO;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobConfigAddParam;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobConfigListParam;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobConfigPageParam;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobConfigUpdateParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class ScheduleJobConfigServiceImpl extends ServiceImpl<ScheduleJobConfigMapper, ScheduleJobConfig> implements ScheduleJobConfigService {

    @Override
    public PageResult<ScheduleJobConfigDTO> list(ScheduleJobConfigPageParam param) {
        Page<ScheduleJobConfig> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<ScheduleJobConfig> queryChainWrapper = Wrappers.lambdaQuery(ScheduleJobConfig.class)
                .eq(ScheduleJobConfig::getJobGroupId, param.getJobGroupId())
                .eq(param.getType() != null, ScheduleJobConfig::getType, param.getType())
                .eq(param.getEngineType() != null, ScheduleJobConfig::getEngineType, param.getEngineType())
                .eq(param.getJobType() != null, ScheduleJobConfig::getJobType, param.getJobType())
                .like(StringUtils.hasText(param.getName()), ScheduleJobConfig::getName, param.getName())
                .like(StringUtils.hasText(param.getHandler()), ScheduleJobConfig::getHandler, param.getHandler())
                .orderByAsc(ScheduleJobConfig::getId);
        Page<ScheduleJobConfig> scheduleJobConfigPage = page(page, queryChainWrapper);
        PageResult<ScheduleJobConfigDTO> pageResult = new PageResult<>(scheduleJobConfigPage.getCurrent(), scheduleJobConfigPage.getSize(), scheduleJobConfigPage.getTotal());
        pageResult.setRecords(ScheduleJobConfigConvert.INSTANCE.toDto(scheduleJobConfigPage.getRecords()));
        return pageResult;
    }

    @Override
    public List<ScheduleJobConfigDTO> listAll(ScheduleJobConfigListParam param) {
        LambdaQueryWrapper<ScheduleJobConfig> queryChainWrapper = Wrappers.lambdaQuery(ScheduleJobConfig.class)
                .eq(ScheduleJobConfig::getJobGroupId, param.getJobGroupId())
                .eq(param.getType() != null, ScheduleJobConfig::getType, param.getType())
                .eq(param.getEngineType() != null, ScheduleJobConfig::getEngineType, param.getEngineType())
                .eq(param.getJobType() != null, ScheduleJobConfig::getJobType, param.getJobType())
                .orderByAsc(ScheduleJobConfig::getId);
        List<ScheduleJobConfig> list = list(queryChainWrapper);
        return ScheduleJobConfigConvert.INSTANCE.toDto(list);
    }

    @Override
    public ScheduleJobConfigDTO get(Long id) {
        ScheduleJobConfigVO entity = baseMapper.get(id);
        checkNotNull(entity, "schedule job config not exists for id: " + id);
        return ScheduleJobConfigConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(ScheduleJobConfigAddParam param) {
        ScheduleJobConfig entity = new ScheduleJobConfig();
        BeanUtils.copyProperties(param, entity);
        entity.setType(CarpScheduleType.USER);
        return save(entity);
    }

    @Override
    public boolean update(ScheduleJobConfigUpdateParam param) {
        ScheduleJobConfig entity = new ScheduleJobConfig();
        BeanUtils.copyProperties(param, entity);
        return updateById(entity);
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
