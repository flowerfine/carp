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
import cn.sliew.carp.framework.dag.service.DagConfigService;
import cn.sliew.carp.framework.dag.service.dto.DagConfigDTO;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.framework.mybatis.util.PageUtil;
import cn.sliew.carp.plugin.workflow.engine.api.dict.CarpWorkflowEngineType;
import cn.sliew.carp.module.workflow.manager.repository.entity.CarpWorkflowDefinition;
import cn.sliew.carp.module.workflow.manager.repository.mapper.CarpWorkflowDefinitionMapper;
import cn.sliew.carp.module.workflow.manager.service.WorkflowDefinitionService;
import cn.sliew.carp.module.workflow.manager.service.convert.CarpWorkflowDefinitionConvert;
import cn.sliew.carp.module.workflow.manager.service.dto.CarpWorkflowDefinitionDTO;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowDefinitionAddParam;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowDefinitionPageParam;
import cn.sliew.carp.module.workflow.manager.service.param.WorkflowDefinitionUpdateParam;
import cn.sliew.milky.common.util.JacksonUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static cn.sliew.milky.common.check.Ensures.checkState;

@Service
public class WorkflowDefinitionServiceImpl
        extends AbstractCrudService<CarpWorkflowDefinitionMapper, CarpWorkflowDefinition,
        CarpWorkflowDefinitionDTO, WorkflowDefinitionPageParam, WorkflowDefinitionAddParam, WorkflowDefinitionUpdateParam>
        implements WorkflowDefinitionService {

    @Autowired
    private DagConfigService dagConfigService;

    @Override
    public PageResult<CarpWorkflowDefinitionDTO> page(WorkflowDefinitionPageParam param) {
        Page<CarpWorkflowDefinition> page = PageUtil.buildPageParam(param);
        LambdaQueryWrapper<CarpWorkflowDefinition> queryWrapper = Wrappers.lambdaQuery(CarpWorkflowDefinition.class)
                .eq(CarpWorkflowDefinition::getNamespace, param.getNamespace())
                .like(StringUtils.hasText(param.getName()), CarpWorkflowDefinition::getName, param.getName())
                .eq(StringUtils.hasText(param.getUuid()), CarpWorkflowDefinition::getUuid, param.getUuid())
                .eq(Objects.nonNull(param.getEngine()), CarpWorkflowDefinition::getEngine, param.getEngine())
                .orderByDesc(CarpWorkflowDefinition::getId);

        Page<CarpWorkflowDefinition> carpWorkflowDefinitionPage = page(page, queryWrapper);
        return PageUtil.buildPageResult(carpWorkflowDefinitionPage, CarpWorkflowDefinitionConvert.INSTANCE::toDto);
    }

    @Override
    public List<CarpWorkflowDefinitionDTO> list(WorkflowDefinitionPageParam param) {
        LambdaQueryWrapper<CarpWorkflowDefinition> queryWrapper = Wrappers.lambdaQuery(CarpWorkflowDefinition.class)
                .eq(CarpWorkflowDefinition::getNamespace, param.getNamespace())
                .like(StringUtils.hasText(param.getName()), CarpWorkflowDefinition::getName, param.getName())
                .eq(StringUtils.hasText(param.getUuid()), CarpWorkflowDefinition::getUuid, param.getUuid())
                .eq(Objects.nonNull(param.getEngine()), CarpWorkflowDefinition::getEngine, param.getEngine())
                .orderByDesc(CarpWorkflowDefinition::getId);
        List<CarpWorkflowDefinition> list = list(queryWrapper);
        return CarpWorkflowDefinitionConvert.INSTANCE.toDto(list);
    }

    @Override
    public CarpWorkflowDefinitionDTO get(Long id) {
        CarpWorkflowDefinition entity = getById(id);
        checkState(entity != null, () -> "workflow definition not exists for id: " + id);
        return CarpWorkflowDefinitionConvert.INSTANCE.toDto(entity);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean add(WorkflowDefinitionAddParam param) {
        CarpWorkflowDefinition entity = new CarpWorkflowDefinition();
        BeanUtils.copyProperties(param, entity);
        entity.setUuid(UUIDUtil.randomUUId());
        if (param.getEngine() == CarpWorkflowEngineType.INTERNAL) {
            ObjectNode body;
            if (Objects.nonNull(param.getBody())) {
                body = (ObjectNode) param.getBody();
            } else {
                body = JacksonUtil.createObjectNode();
            }

            DagConfigDTO dagConfigDTO = new DagConfigDTO();
            dagConfigDTO.setNamespace(dagConfigDTO.getNamespace());
            dagConfigDTO.setType("WorkFlow");
            dagConfigDTO.setName(param.getName());
            Long dagConfigId = dagConfigService.add(dagConfigDTO);
            body.put("dagConfigId", dagConfigId);
            param.setBody(body);
        }
        if (Objects.nonNull(param.getBody())) {
            entity.setBody(param.getBody().toString());
        }

        return save(entity);
    }

    @Override
    public boolean update(WorkflowDefinitionUpdateParam param) {
        CarpWorkflowDefinition entity = new CarpWorkflowDefinition();
        BeanUtils.copyProperties(param, entity);
        if (Objects.nonNull(param.getBody())) {
            entity.setBody(param.getBody().toString());
        }
        return updateById(entity);
    }
}
