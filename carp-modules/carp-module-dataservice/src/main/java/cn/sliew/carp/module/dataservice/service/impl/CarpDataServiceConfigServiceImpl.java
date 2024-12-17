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
package cn.sliew.carp.module.dataservice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.module.dataservice.repository.entity.CarpDataServiceConfig;
import cn.sliew.carp.module.dataservice.repository.mapper.CarpDataServiceConfigMapper;
import cn.sliew.carp.module.dataservice.service.CarpDataServiceConfigService;
import cn.sliew.carp.module.dataservice.service.convert.CarpDataServiceConfigConvert;
import cn.sliew.carp.module.dataservice.service.dto.CarpDataServiceConfigDTO;
import cn.sliew.carp.module.dataservice.service.param.CarpDataServiceConfigAddParam;
import cn.sliew.carp.module.dataservice.service.param.CarpDataServiceConfigPageParam;
import cn.sliew.carp.module.dataservice.service.param.CarpDataServiceConfigUpdateParam;
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
public class CarpDataServiceConfigServiceImpl
        extends ServiceImpl<CarpDataServiceConfigMapper, CarpDataServiceConfig>
        implements CarpDataServiceConfigService {

    @Override
    public PageResult<CarpDataServiceConfigDTO> page(CarpDataServiceConfigPageParam param) {
        Page<CarpDataServiceConfig> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<CarpDataServiceConfig> queryChainWrapper = Wrappers.lambdaQuery(CarpDataServiceConfig.class)
                .eq(CarpDataServiceConfig::getGroupId, param.getGroupId())
                .eq(StringUtils.hasText(param.getType()), CarpDataServiceConfig::getType, param.getType())
                .like(StringUtils.hasText(param.getName()), CarpDataServiceConfig::getName, param.getName())
                .orderByAsc(CarpDataServiceConfig::getId);
        Page<CarpDataServiceConfig> carpDataServiceConfigPage = page(page, queryChainWrapper);
        PageResult<CarpDataServiceConfigDTO> pageResult = new PageResult<>(carpDataServiceConfigPage.getCurrent(), carpDataServiceConfigPage.getSize(), carpDataServiceConfigPage.getTotal());
        pageResult.setRecords(CarpDataServiceConfigConvert.INSTANCE.toDto(carpDataServiceConfigPage.getRecords()));
        return pageResult;
    }

    @Override
    public List<CarpDataServiceConfigDTO> list(CarpDataServiceConfigPageParam param) {
        LambdaQueryWrapper<CarpDataServiceConfig> queryChainWrapper = Wrappers.lambdaQuery(CarpDataServiceConfig.class)
                .eq(CarpDataServiceConfig::getGroupId, param.getGroupId())
                .eq(StringUtils.hasText(param.getType()), CarpDataServiceConfig::getType, param.getType())
                .like(StringUtils.hasText(param.getName()), CarpDataServiceConfig::getName, param.getName())
                .orderByAsc(CarpDataServiceConfig::getId);
        List<CarpDataServiceConfig> entities = list(queryChainWrapper);
        return CarpDataServiceConfigConvert.INSTANCE.toDto(entities);
    }

    @Override
    public CarpDataServiceConfigDTO get(Long id) {
        CarpDataServiceConfig entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("carp dataservice config not exists for id: " + id));
        return CarpDataServiceConfigConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(CarpDataServiceConfigAddParam param) {
        CarpDataServiceConfig entity = BeanUtil.copyProperties(param, CarpDataServiceConfig.class);
        return save(entity);
    }

    @Override
    public boolean update(CarpDataServiceConfigUpdateParam param) {
        CarpDataServiceConfig entity = BeanUtil.copyProperties(param, CarpDataServiceConfig.class);
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
