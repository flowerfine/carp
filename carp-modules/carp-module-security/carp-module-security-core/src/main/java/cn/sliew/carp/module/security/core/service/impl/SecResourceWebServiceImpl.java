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
package cn.sliew.carp.module.security.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.DataSourceConstants;
import cn.sliew.carp.module.security.core.repository.entity.SecResourceWeb;
import cn.sliew.carp.module.security.core.repository.mapper.SecResourceWebMapper;
import cn.sliew.carp.module.security.core.service.SecResourceWebRoleService;
import cn.sliew.carp.module.security.core.service.SecResourceWebService;
import cn.sliew.carp.module.security.core.service.convert.SecResourceWebConvert;
import cn.sliew.carp.module.security.core.service.dto.SecResourceWebDTO;
import cn.sliew.carp.module.security.core.service.param.SecResourceWebAddParam;
import cn.sliew.carp.module.security.core.service.param.SecResourceWebListParam;
import cn.sliew.carp.module.security.core.service.param.SecResourceWebUpdateParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Service
public class SecResourceWebServiceImpl extends ServiceImpl<SecResourceWebMapper, SecResourceWeb> implements SecResourceWebService {

    @Autowired
    private SecResourceWebRoleService secResourceWebRoleService;

    @Override
    public PageResult<SecResourceWebDTO> page(SecResourceWebListParam param) {
        Page<SecResourceWeb> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<SecResourceWeb> queryChainWrapper = Wrappers.lambdaQuery(SecResourceWeb.class)
                .eq(SecResourceWeb::getPid, param.getPid() != null ? param.getPid() : 0L)
                .like(StringUtils.hasText(param.getLabel()), SecResourceWeb::getLabel, param.getLabel())
                .orderByAsc(SecResourceWeb::getOrder, SecResourceWeb::getId);
        Page<SecResourceWeb> secResourceWebPage = page(page, queryChainWrapper);
        PageResult<SecResourceWebDTO> pageResult = new PageResult<>(secResourceWebPage.getCurrent(), secResourceWebPage.getSize(), secResourceWebPage.getTotal());
        pageResult.setRecords(SecResourceWebConvert.INSTANCE.toDto(secResourceWebPage.getRecords()));
        pageResult.getRecords().forEach(dto -> recurse(dto, param.getLabel()));
        return pageResult;
    }

    private void recurse(SecResourceWebDTO resourceWebDTO, String label) {
        List<SecResourceWebDTO> children = listByPid(resourceWebDTO.getId(), label);
        if (CollectionUtils.isEmpty(children) == false) {
            resourceWebDTO.setChildren(children);
            children.forEach(child -> recurse(child, label));
        }
    }

    @Override
    public List<SecResourceWebDTO> listAll(SecResourceWebListParam param) {
        LambdaQueryWrapper<SecResourceWeb> queryChainWrapper = Wrappers.lambdaQuery(SecResourceWeb.class)
                .eq(param.getPid() != null, SecResourceWeb::getPid, param.getPid())
                .like(StringUtils.hasText(param.getLabel()), SecResourceWeb::getLabel, param.getLabel())
                .orderByAsc(SecResourceWeb::getOrder, SecResourceWeb::getId);
        List<SecResourceWeb> entities = list(queryChainWrapper);
        return SecResourceWebConvert.INSTANCE.toDto(entities);
    }

    @Override
    public List<SecResourceWebDTO> listByPid(Long pid, String label) {
        LambdaQueryWrapper<SecResourceWeb> queryWrapper = Wrappers.lambdaQuery(SecResourceWeb.class)
                .eq(SecResourceWeb::getPid, pid)
                .like(StringUtils.hasText(label), SecResourceWeb::getLabel, label);
        List<SecResourceWeb> list = list(queryWrapper);
        return SecResourceWebConvert.INSTANCE.toDto(list);
    }

    @Override
    public SecResourceWebDTO get(Long id) {
        SecResourceWeb entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("resource web not exists for id: " + id));
        return SecResourceWebConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(SecResourceWebAddParam param) {
        SecResourceWeb entity = BeanUtil.copyProperties(param, SecResourceWeb.class);
        return save(entity);
    }

    @Override
    public boolean update(SecResourceWebUpdateParam param) {
        SecResourceWeb entity = BeanUtil.copyProperties(param, SecResourceWeb.class);
        return saveOrUpdate(entity);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean delete(Long id) {
        SecResourceWebListParam param = new SecResourceWebListParam();
        param.setPid(id);
        List<SecResourceWebDTO> children = listAll(param);
        if (CollectionUtils.isEmpty(children) == false) {
            children.forEach(child -> delete(child.getId()));
        }
        // 处理关联资源
        secResourceWebRoleService.deleteByResourceWebId(id);
        return removeById(id);
    }

    @Transactional(rollbackFor = {Exception.class}, transactionManager = DataSourceConstants.TRANSACTION_MANAGER_FACTORY)
    @Override
    public boolean deleteBatch(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids) == false) {
            ids.forEach(this::delete);
        }
        return true;
    }
}
