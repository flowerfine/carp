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
package cn.sliew.carp.module.workflow.manager.service.impl;

import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.framework.crud.service.impl.AbstractCrudService;
import cn.sliew.carp.framework.mybatis.util.PageUtil;
import cn.sliew.carp.module.workflow.manager.repository.entity.CarpWorkflowInstance;
import cn.sliew.carp.module.workflow.manager.repository.mapper.CarpWorkflowInstanceMapper;
import cn.sliew.carp.module.workflow.manager.service.WorkflowInstanceManagerService;
import cn.sliew.carp.module.workflow.manager.service.convert.CarpWorkflowInstanceConvert;
import cn.sliew.carp.module.workflow.manager.service.dto.CarpWorkflowInstanceDTO;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowInstanceAddParam;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowInstancePageParam;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowInstanceUpdateParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static cn.sliew.milky.common.check.Ensures.checkState;

@Service
public class WorkflowInstanceManagerServiceImpl
        extends AbstractCrudService<CarpWorkflowInstanceMapper, CarpWorkflowInstance,
        CarpWorkflowInstanceDTO, WorkflowInstancePageParam, WorkflowInstanceAddParam, WorkflowInstanceUpdateParam>
        implements WorkflowInstanceManagerService {

    @Override
    public PageResult<CarpWorkflowInstanceDTO> page(WorkflowInstancePageParam param) {
        Page<CarpWorkflowInstance> page = PageUtil.buildPageParam(param);
        LambdaQueryWrapper<CarpWorkflowInstance> queryWrapper = Wrappers.lambdaQuery(CarpWorkflowInstance.class)
                .eq(CarpWorkflowInstance::getNamespace, param.getNamespace())
                .eq(Objects.nonNull(param.getWorkflowDefinitionId()), CarpWorkflowInstance::getWorkflowDefinitionId, param.getWorkflowDefinitionId())
                .eq(StringUtils.hasText(param.getUuid()), CarpWorkflowInstance::getUuid, param.getUuid())
                .eq(StringUtils.hasText(param.getStatus()), CarpWorkflowInstance::getStatus, param.getStatus());

        Page<CarpWorkflowInstance> carpWorkflowInstancePage = page(page, queryWrapper);
        return PageUtil.buildPageResult(carpWorkflowInstancePage, CarpWorkflowInstanceConvert.INSTANCE::toDto);
    }

    @Override
    public CarpWorkflowInstanceDTO get(Long id) {
        CarpWorkflowInstance entity = getById(id);
        checkState(entity != null, () -> "workflow instance not exists for id: " + id);
        return CarpWorkflowInstanceConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(WorkflowInstanceAddParam param) {
        CarpWorkflowInstance entity = new CarpWorkflowInstance();
        BeanUtils.copyProperties(param, entity);
        entity.setUuid(UUIDUtil.randomUUId());
        if (Objects.nonNull(param.getParams())) {
            entity.setParams(param.getParams().toString());
        }
        return save(entity);
    }

    @Override
    public boolean update(WorkflowInstanceUpdateParam param) {
        CarpWorkflowInstance entity = new CarpWorkflowInstance();
        BeanUtils.copyProperties(param, entity);
        if (Objects.nonNull(param.getParams())) {
            entity.setParams(param.getParams().toString());
        }
        return updateById(entity);
    }
}
