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
package cn.sliew.carp.module.cep.service.impl;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.framework.mybatis.util.PageUtil;
import cn.sliew.carp.module.cep.repository.entity.CarpCepWorkflow;
import cn.sliew.carp.module.cep.repository.mapper.CarpCepWorkflowMapper;
import cn.sliew.carp.module.cep.service.CarpCepWorkflowService;
import cn.sliew.carp.module.cep.service.convert.CepWorkflowConvert;
import cn.sliew.carp.module.cep.service.dto.CarpCepWorkflowDTO;
import cn.sliew.carp.module.cep.service.param.CepWorkflowAddParam;
import cn.sliew.carp.module.cep.service.param.CepWorkflowPageParam;
import cn.sliew.carp.module.cep.service.param.CepWorkflowUpdateParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class CarpCepWorkflowServiceImpl
        extends ServiceImpl<CarpCepWorkflowMapper, CarpCepWorkflow>
        implements CarpCepWorkflowService {

    @Override
    public PageResult<CarpCepWorkflowDTO> page(CepWorkflowPageParam param) {
        Page<CarpCepWorkflow> page = PageUtil.buildPageParam(param);
        LambdaQueryWrapper<CarpCepWorkflow> queryWrapper = Wrappers.lambdaQuery(CarpCepWorkflow.class)
                .eq(StringUtils.isNotBlank(param.getNamespace()), CarpCepWorkflow::getNamespace, param.getNamespace())
                .like(StringUtils.isNotBlank(param.getName()), CarpCepWorkflow::getName, param.getName())
                .orderByDesc(CarpCepWorkflow::getId);
        Page<CarpCepWorkflow> carpCepWorkflowPage = page(page, queryWrapper);
        return PageUtil.buildPageResult(carpCepWorkflowPage, CepWorkflowConvert.INSTANCE::toDto);
    }

    @Override
    public CarpCepWorkflowDTO get(Long id) {
        CarpCepWorkflow entity = getById(id);
        checkNotNull(entity, "cep workflow not exists for id: " + id);
        return CepWorkflowConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(CepWorkflowAddParam param) {
        CarpCepWorkflow entity = new CarpCepWorkflow();
        BeanUtils.copyProperties(param, entity);
        entity.setUuid(UUIDUtil.randomUUId());
        entity.setType("user");
        return save(entity);
    }

    @Override
    public boolean update(CepWorkflowUpdateParam param) {
        CarpCepWorkflow entity = new CarpCepWorkflow();
        BeanUtils.copyProperties(param, entity);
        return updateById(entity);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
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
