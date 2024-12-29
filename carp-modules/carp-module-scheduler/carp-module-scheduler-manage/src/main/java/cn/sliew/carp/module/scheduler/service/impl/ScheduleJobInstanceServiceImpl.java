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

import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleStatus;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.module.scheduler.repository.entity.ScheduleJobInstance;
import cn.sliew.carp.module.scheduler.repository.entity.ScheduleJobInstanceVO;
import cn.sliew.carp.module.scheduler.repository.mapper.ScheduleJobInstanceMapper;
import cn.sliew.carp.module.scheduler.service.ScheduleJobInstanceService;
import cn.sliew.carp.module.scheduler.service.convert.ScheduleJobInstanceConvert;
import cn.sliew.carp.module.scheduler.service.dto.ScheduleJobInstanceDTO;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobInstanceAddParam;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobInstanceListParam;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobInstancePageParam;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobInstanceUpdateParam;
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
public class ScheduleJobInstanceServiceImpl extends ServiceImpl<ScheduleJobInstanceMapper, ScheduleJobInstance> implements ScheduleJobInstanceService {

    @Override
    public PageResult<ScheduleJobInstanceDTO> list(ScheduleJobInstancePageParam param) {
        Page<ScheduleJobInstance> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<ScheduleJobInstance> queryChainWrapper = Wrappers.lambdaQuery(ScheduleJobInstance.class)
                .eq(ScheduleJobInstance::getJobConfigId, param.getJobConfigId())
                .like(StringUtils.hasText(param.getName()), ScheduleJobInstance::getName, param.getName())
                .eq(StringUtils.hasText(param.getStatus()), ScheduleJobInstance::getStatus, param.getStatus())
                .orderByAsc(ScheduleJobInstance::getId);
        Page<ScheduleJobInstance> scheduleJobInstancePage = page(page, queryChainWrapper);
        PageResult<ScheduleJobInstanceDTO> pageResult = new PageResult<>(scheduleJobInstancePage.getCurrent(), scheduleJobInstancePage.getSize(), scheduleJobInstancePage.getTotal());
        pageResult.setRecords(ScheduleJobInstanceConvert.INSTANCE.toDto(scheduleJobInstancePage.getRecords()));
        return pageResult;
    }

    @Override
    public List<ScheduleJobInstanceDTO> listAll(ScheduleJobInstanceListParam param) {
        LambdaQueryWrapper<ScheduleJobInstance> queryChainWrapper = Wrappers.lambdaQuery(ScheduleJobInstance.class)
                .eq(ScheduleJobInstance::getJobConfigId, param.getJobConfigId())
                .eq(StringUtils.hasText(param.getStatus()), ScheduleJobInstance::getStatus, param.getStatus())
                .orderByAsc(ScheduleJobInstance::getId);
        List<ScheduleJobInstance> list = list(queryChainWrapper);
        return ScheduleJobInstanceConvert.INSTANCE.toDto(list);
    }

    @Override
    public ScheduleJobInstanceDTO get(Long id) {
        ScheduleJobInstanceVO entity = baseMapper.get(id);
        checkNotNull(entity, "schedule job instance not exists for id: " + id);
        return ScheduleJobInstanceConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(ScheduleJobInstanceAddParam param) {
        ScheduleJobInstance entity = new ScheduleJobInstance();
        BeanUtils.copyProperties(param, entity);
        entity.setStatus(CarpScheduleStatus.RUNNING);
        return save(entity);
    }

    @Override
    public boolean update(ScheduleJobInstanceUpdateParam param) {
        ScheduleJobInstance entity = new ScheduleJobInstance();
        BeanUtils.copyProperties(param, entity);
        return updateById(entity);
    }

    @Override
    public boolean updateStatus(Long id, CarpScheduleStatus status) {
        ScheduleJobInstance entity = new ScheduleJobInstance();
        entity.setId(id);
        entity.setStatus(status);
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
