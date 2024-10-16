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

package cn.sliew.carp.module.plugin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sliew.carp.framework.common.dict.common.YesOrNo;
import cn.sliew.carp.framework.common.dict.plugin.PluginType;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.module.plugin.repository.entity.CarpPlugin;
import cn.sliew.carp.module.plugin.repository.mapper.CarpPluginMapper;
import cn.sliew.carp.module.plugin.service.PluginManageService;
import cn.sliew.carp.module.plugin.service.convert.CarpPluginConvert;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginDTO;
import cn.sliew.carp.module.plugin.service.param.CarpPluginAddParam;
import cn.sliew.carp.module.plugin.service.param.CarpPluginListParam;
import cn.sliew.carp.module.plugin.service.param.CarpPluginUpdateParam;
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
public class PluginManageServiceImpl extends ServiceImpl<CarpPluginMapper, CarpPlugin> implements PluginManageService {

    @Override
    public PageResult<CarpPluginDTO> list(CarpPluginListParam param) {
        Page<CarpPlugin> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<CarpPlugin> queryChainWrapper = Wrappers.lambdaQuery(CarpPlugin.class)
                .like(StringUtils.hasText(param.getName()), CarpPlugin::getName, param.getName())
                .eq(param.getType() != null, CarpPlugin::getType, param.getType())
                .eq(param.getStatus() != null, CarpPlugin::getStatus, param.getStatus())
                .orderByAsc(CarpPlugin::getId);
        Page<CarpPlugin> secRolePage = page(page, queryChainWrapper);
        PageResult<CarpPluginDTO> pageResult = new PageResult<>(secRolePage.getCurrent(), secRolePage.getSize(), secRolePage.getTotal());
        pageResult.setRecords(CarpPluginConvert.INSTANCE.toDto(secRolePage.getRecords()));
        return pageResult;
    }

    @Override
    public List<CarpPluginDTO> listAll() {
        LambdaQueryWrapper<CarpPlugin> queryChainWrapper = Wrappers.lambdaQuery(CarpPlugin.class)
                .orderByAsc(CarpPlugin::getId);
        List<CarpPlugin> entities = list(queryChainWrapper);
        return CarpPluginConvert.INSTANCE.toDto(entities);
    }

    @Override
    public CarpPluginDTO get(Long id) {
        CarpPlugin entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("plugin not exists for id: " + id));
        return CarpPluginConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(CarpPluginAddParam param) {
        CarpPlugin entity = BeanUtil.copyProperties(param, CarpPlugin.class);
        entity.setType(PluginType.INTERNAL);
        entity.setStatus(YesOrNo.NO);
        return save(entity);
    }

    @Override
    public boolean update(CarpPluginUpdateParam param) {
        CarpPlugin entity = BeanUtil.copyProperties(param, CarpPlugin.class);
        return updateById(entity);
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
