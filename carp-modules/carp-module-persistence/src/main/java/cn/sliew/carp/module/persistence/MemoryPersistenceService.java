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

import cn.sliew.carp.framework.common.model.BaseDTO;
import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.persistence.api.selectors.Selector;
import com.google.common.collect.Iterables;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkNotNull;

public class MemoryPersistenceService<R extends BaseDTO> extends AbstractPersistenceService<Long, R> {

    private final ConcurrentMap<Long, R> map = new ConcurrentHashMap();
    private final BaseResourceVisitor resourceVisitor;

    public MemoryPersistenceService(BaseResourceVisitor resourceVisitor) {
        this.resourceVisitor = resourceVisitor;
    }

    @Override
    public Iterable<R> select(Selector selector) {
        Predicate<R> predicate = (Predicate) selector.accept(resourceVisitor);
        checkNotNull(predicate);
        return Iterables.filter(map.values(), predicate::test);
    }

    @Override
    public PageResult<R> page(PageParam param, Selector selector) {
        return null;
    }

    @Override
    public Optional<R> get(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    protected void doAdd(R resource) {
        map.putIfAbsent(resource.getId(), resource);
    }

    @Override
    protected void doUpdate(Long id, R resource) {
        map.put(id, resource);
    }

    @Override
    protected void doDelete(Long id) {
        map.remove(id);
    }
}
