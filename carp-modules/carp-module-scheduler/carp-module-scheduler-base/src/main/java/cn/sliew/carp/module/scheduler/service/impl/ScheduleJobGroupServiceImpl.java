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

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.module.scheduler.repository.entity.ScheduleJobGroup;
import cn.sliew.carp.module.scheduler.repository.mapper.ScheduleJobGroupMapper;
import cn.sliew.carp.module.scheduler.service.ScheduleJobGroupService;
import cn.sliew.carp.module.scheduler.service.convert.ScheduleJobGroupConvert;
import cn.sliew.carp.module.scheduler.service.dto.ScheduleJobGroupDTO;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobGroupAddParam;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobGroupPageParam;
import cn.sliew.carp.module.scheduler.service.param.ScheduleJobGroupUpdateParam;
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

@Service
public class ScheduleJobGroupServiceImpl extends ServiceImpl<ScheduleJobGroupMapper, ScheduleJobGroup> implements ScheduleJobGroupService {

    @Override
    public PageResult<ScheduleJobGroupDTO> list(ScheduleJobGroupPageParam param) {
        Page<ScheduleJobGroup> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<ScheduleJobGroup> queryChainWrapper = Wrappers.lambdaQuery(ScheduleJobGroup.class)
                .like(StringUtils.hasText(param.getNamespace()), ScheduleJobGroup::getNamespace, param.getNamespace())
                .like(StringUtils.hasText(param.getName()), ScheduleJobGroup::getName, param.getName())
                .orderByAsc(ScheduleJobGroup::getId);
        Page<ScheduleJobGroup> scheduleJobGroupPage = page(page, queryChainWrapper);
        PageResult<ScheduleJobGroupDTO> pageResult = new PageResult<>(scheduleJobGroupPage.getCurrent(), scheduleJobGroupPage.getSize(), scheduleJobGroupPage.getTotal());
        pageResult.setRecords(ScheduleJobGroupConvert.INSTANCE.toDto(scheduleJobGroupPage.getRecords()));
        return pageResult;
    }

    @Override
    public List<ScheduleJobGroupDTO> listAll() {
        LambdaQueryWrapper<ScheduleJobGroup> queryChainWrapper = Wrappers.lambdaQuery(ScheduleJobGroup.class)
                .orderByAsc(ScheduleJobGroup::getId);
        List<ScheduleJobGroup> list = list(queryChainWrapper);
        return ScheduleJobGroupConvert.INSTANCE.toDto(list);
    }

    @Override
    public ScheduleJobGroupDTO get(Long id) {
        ScheduleJobGroup entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("schedule job group not exists for id: " + id));
        return ScheduleJobGroupConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(ScheduleJobGroupAddParam param) {
        ScheduleJobGroup entity = new ScheduleJobGroup();
        BeanUtils.copyProperties(param, entity);
        return save(entity);
    }

    @Override
    public boolean update(ScheduleJobGroupUpdateParam param) {
        ScheduleJobGroup entity = new ScheduleJobGroup();
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
