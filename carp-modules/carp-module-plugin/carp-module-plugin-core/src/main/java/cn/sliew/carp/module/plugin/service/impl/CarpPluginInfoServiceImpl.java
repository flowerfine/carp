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
import cn.sliew.carp.framework.common.dict.plugin.CarpPluginType;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.module.plugin.repository.entity.CarpPluginInfo;
import cn.sliew.carp.module.plugin.repository.mapper.CarpPluginInfoMapper;
import cn.sliew.carp.module.plugin.service.CarpPluginInfoService;
import cn.sliew.carp.module.plugin.service.convert.CarpPluginInfoConvert;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginInfoDTO;
import cn.sliew.carp.module.plugin.service.param.CarpPluginInfoAddParam;
import cn.sliew.carp.module.plugin.service.param.CarpPluginInfoPageParam;
import cn.sliew.carp.module.plugin.service.param.CarpPluginInfoUpdateParam;
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
public class CarpPluginInfoServiceImpl extends ServiceImpl<CarpPluginInfoMapper, CarpPluginInfo> implements CarpPluginInfoService {

    @Override
    public PageResult<CarpPluginInfoDTO> page(CarpPluginInfoPageParam param) {
        Page<CarpPluginInfo> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<CarpPluginInfo> queryChainWrapper = Wrappers.lambdaQuery(CarpPluginInfo.class)
                .eq(param.getType() != null, CarpPluginInfo::getType, param.getType())
                .like(StringUtils.hasText(param.getProvider()), CarpPluginInfo::getProvider, param.getProvider())
                .like(StringUtils.hasText(param.getName()), CarpPluginInfo::getName, param.getName())
                .orderByAsc(CarpPluginInfo::getId);
        Page<CarpPluginInfo> carpPluginInfoPage = page(page, queryChainWrapper);
        PageResult<CarpPluginInfoDTO> pageResult = new PageResult<>(carpPluginInfoPage.getCurrent(), carpPluginInfoPage.getSize(), carpPluginInfoPage.getTotal());
        pageResult.setRecords(CarpPluginInfoConvert.INSTANCE.toDto(carpPluginInfoPage.getRecords()));
        return pageResult;
    }

    @Override
    public List<CarpPluginInfoDTO> list(CarpPluginInfoPageParam param) {
        LambdaQueryWrapper<CarpPluginInfo> queryChainWrapper = Wrappers.lambdaQuery(CarpPluginInfo.class)
                .eq(param.getType() != null, CarpPluginInfo::getType, param.getType())
                .like(StringUtils.hasText(param.getProvider()), CarpPluginInfo::getProvider, param.getProvider())
                .like(StringUtils.hasText(param.getName()), CarpPluginInfo::getName, param.getName())
                .orderByAsc(CarpPluginInfo::getId);
        List<CarpPluginInfo> entities = list(queryChainWrapper);
        return CarpPluginInfoConvert.INSTANCE.toDto(entities);
    }

    @Override
    public CarpPluginInfoDTO get(Long id) {
        CarpPluginInfo entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("plugin info not exists for id: " + id));
        return CarpPluginInfoConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(CarpPluginInfoAddParam param) {
        CarpPluginInfo entity = BeanUtil.copyProperties(param, CarpPluginInfo.class);
        entity.setType(CarpPluginType.INTERNAL);
        return save(entity);
    }

    @Override
    public boolean update(CarpPluginInfoUpdateParam param) {
        CarpPluginInfo entity = BeanUtil.copyProperties(param, CarpPluginInfo.class);
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
