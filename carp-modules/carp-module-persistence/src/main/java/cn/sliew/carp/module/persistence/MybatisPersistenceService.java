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
package cn.sliew.carp.module.persistence;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import cn.sliew.carp.module.persistence.api.selectors.Selector;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Optional;

public class MybatisPersistenceService<R extends BaseAuditDO> extends AbstractPersistenceService<Long, R> {

    private MybatisResourceVisitor<R> resourceVisitor;
    private BaseMapper<R> baseMapper;

    public MybatisPersistenceService(MybatisResourceVisitor<R> resourceVisitor, BaseMapper<R> baseMapper) {
        this.resourceVisitor = resourceVisitor;
        this.baseMapper = baseMapper;
    }

    @Override
    public Iterable<R> select(Selector selector) {
        LambdaQueryWrapper<R> queryWrapper = selector.accept(resourceVisitor);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public PageResult<R> page(PageParam param, Selector selector) {
        LambdaQueryWrapper<R> queryWrapper = selector.accept(resourceVisitor);
        Page<R> page = baseMapper.selectPage(new Page(param.getCurrent(), param.getPageSize()), queryWrapper);
        PageResult pageResult = new PageResult(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(page.getRecords());
        return pageResult;
    }

    @Override
    public Optional<R> get(Long id) {
        return Optional.ofNullable(baseMapper.selectById(id));
    }

    @Override
    protected void doAdd(R resource) {
        baseMapper.insertOrUpdate(resource);
    }

    @Override
    protected void doUpdate(Long id, R resource) {
        resource.setId(id);
        baseMapper.updateById(resource);
    }

    @Override
    protected void doDelete(Long id) {
        baseMapper.deleteById(id);
    }
}
