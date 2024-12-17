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
import cn.sliew.carp.module.dataservice.repository.entity.CarpDataServiceGroup;
import cn.sliew.carp.module.dataservice.repository.mapper.CarpDataServiceGroupMapper;
import cn.sliew.carp.module.dataservice.service.CarpDataServiceGroupService;
import cn.sliew.carp.module.dataservice.service.convert.CarpDataServiceGroupConvert;
import cn.sliew.carp.module.dataservice.service.dto.CarpDataServiceGroupDTO;
import cn.sliew.carp.module.dataservice.service.param.CarpDataServiceGroupAddParam;
import cn.sliew.carp.module.dataservice.service.param.CarpDataServiceGroupPageParam;
import cn.sliew.carp.module.dataservice.service.param.CarpDataServiceGroupUpdateParam;
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
public class CarpDataServiceGroupServiceImpl
        extends ServiceImpl<CarpDataServiceGroupMapper, CarpDataServiceGroup>
        implements CarpDataServiceGroupService {

    @Override
    public PageResult<CarpDataServiceGroupDTO> page(CarpDataServiceGroupPageParam param) {
        Page<CarpDataServiceGroup> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<CarpDataServiceGroup> queryChainWrapper = Wrappers.lambdaQuery(CarpDataServiceGroup.class)
                .eq(StringUtils.hasText(param.getNamespace()), CarpDataServiceGroup::getNamespace, param.getNamespace())
                .like(StringUtils.hasText(param.getName()), CarpDataServiceGroup::getName, param.getName())
                .eq(StringUtils.hasText(param.getCode()), CarpDataServiceGroup::getCode, param.getCode())
                .orderByAsc(CarpDataServiceGroup::getId);
        Page<CarpDataServiceGroup> carpDataServiceGroupPage = page(page, queryChainWrapper);
        PageResult<CarpDataServiceGroupDTO> pageResult = new PageResult<>(carpDataServiceGroupPage.getCurrent(), carpDataServiceGroupPage.getSize(), carpDataServiceGroupPage.getTotal());
        pageResult.setRecords(CarpDataServiceGroupConvert.INSTANCE.toDto(carpDataServiceGroupPage.getRecords()));
        return pageResult;
    }

    @Override
    public List<CarpDataServiceGroupDTO> list(CarpDataServiceGroupPageParam param) {
        LambdaQueryWrapper<CarpDataServiceGroup> queryChainWrapper = Wrappers.lambdaQuery(CarpDataServiceGroup.class)
                .eq(StringUtils.hasText(param.getNamespace()), CarpDataServiceGroup::getNamespace, param.getNamespace())
                .like(StringUtils.hasText(param.getName()), CarpDataServiceGroup::getName, param.getName())
                .eq(StringUtils.hasText(param.getCode()), CarpDataServiceGroup::getCode, param.getCode())
                .orderByAsc(CarpDataServiceGroup::getId);
        List<CarpDataServiceGroup> entities = list(queryChainWrapper);
        return CarpDataServiceGroupConvert.INSTANCE.toDto(entities);
    }

    @Override
    public CarpDataServiceGroupDTO get(Long id) {
        CarpDataServiceGroup entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("carp dataservice group not exists for id: " + id));
        return CarpDataServiceGroupConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(CarpDataServiceGroupAddParam param) {
        CarpDataServiceGroup entity = BeanUtil.copyProperties(param, CarpDataServiceGroup.class);
        return save(entity);
    }

    @Override
    public boolean update(CarpDataServiceGroupUpdateParam param) {
        CarpDataServiceGroup entity = BeanUtil.copyProperties(param, CarpDataServiceGroup.class);
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
