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
import cn.sliew.carp.module.security.core.repository.entity.SecDept;
import cn.sliew.carp.module.security.core.repository.mapper.SecDeptMapper;
import cn.sliew.carp.module.security.core.service.SecDeptService;
import cn.sliew.carp.module.security.core.service.convert.SecDeptConvert;
import cn.sliew.carp.module.security.core.service.dto.SecDeptDTO;
import cn.sliew.carp.module.security.core.service.param.SecDeptListParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Service
public class SecDeptServiceImpl extends ServiceImpl<SecDeptMapper, SecDept> implements SecDeptService {

    @Override
    public PageResult<SecDeptDTO> page(SecDeptListParam param) {
        Page<SecDept> page = new Page<>(param.getCurrent(), param.getPageSize());
        LambdaQueryWrapper<SecDept> queryChainWrapper = Wrappers.lambdaQuery(SecDept.class)
                .eq(SecDept::getPid, param.getPid() != null ? param.getPid() : 0L)
                .eq(StringUtils.hasText(param.getCode()), SecDept::getCode, param.getCode())
                .like(StringUtils.hasText(param.getName()), SecDept::getName, param.getName())
                .orderByAsc(SecDept::getId);
        Page<SecDept> secDeptPage = page(page, queryChainWrapper);
        PageResult<SecDeptDTO> pageResult = new PageResult<>(secDeptPage.getCurrent(), secDeptPage.getSize(), secDeptPage.getTotal());
        pageResult.setRecords(SecDeptConvert.INSTANCE.toDto(secDeptPage.getRecords()));
        pageResult.getRecords().forEach(dto -> recurse(dto, param));
        return pageResult;
    }

    private void recurse(SecDeptDTO secDeptDTO, SecDeptListParam param) {
        SecDeptListParam copyParam = BeanUtil.copyProperties(param, SecDeptListParam.class);
        copyParam.setPid(secDeptDTO.getId());
        List<SecDeptDTO> children = listAll(copyParam);
        if (CollectionUtils.isEmpty(children) == false) {
            secDeptDTO.setChildren(children);
            children.forEach(child -> recurse(child, param));
        }
    }

    @Override
    public List<SecDeptDTO> listAll(SecDeptListParam param) {
        LambdaQueryWrapper<SecDept> queryChainWrapper = Wrappers.lambdaQuery(SecDept.class)
                .eq(SecDept::getPid, param.getPid() != null ? param.getPid() : 0L)
                .eq(StringUtils.hasText(param.getCode()), SecDept::getCode, param.getCode())
                .like(StringUtils.hasText(param.getName()), SecDept::getName, param.getName())
                .orderByAsc(SecDept::getId);
        List<SecDept> entities = list(queryChainWrapper);
        return SecDeptConvert.INSTANCE.toDto(entities);
    }

    @Override
    public SecDeptDTO get(Long id) {
        SecDept entity = getOptById(id).orElseThrow(() -> new IllegalArgumentException("dept not exists for id: " + id));
        return SecDeptConvert.INSTANCE.toDto(entity);
    }

    @Override
    public boolean add(SecDeptDTO param) {
        SecDept entity = BeanUtil.copyProperties(param, SecDept.class);
        return save(entity);
    }

    @Override
    public boolean update(SecDeptDTO param) {
        SecDept entity = BeanUtil.copyProperties(param, SecDept.class);
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
