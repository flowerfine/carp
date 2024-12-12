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

import cn.sliew.carp.module.persistence.api.PersistenceListener;
import cn.sliew.carp.module.persistence.api.PersistenceService;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractPersistenceService<ID, R> implements PersistenceService<ID, R> {

    private final List<PersistenceListener<R>> listeners = Lists.newArrayList();

    @Override
    public void addListener(PersistenceListener<R> listener) {
        listeners.add(checkNotNull(listener, "listener is null"));
    }

    @Override
    public R add(R resource) {
        doAdd(resource);
        onAdded(resource);
        return resource;
    }

    @Override
    public R update(ID id, Function<R, R> updateFn) {
        R before = getOrThrow(id);
        R after = updateFn.apply(before);
        doUpdate(id, after);
        onUpdated(before, after);
        return after;
    }

    @Override
    public Optional<R> delete(ID id) {
        R deleted = getOrThrow(id);
        doDelete(id);
        onDeleted(deleted);
        return Optional.of(deleted);
    }

    private void onAdded(R created) {
        for (PersistenceListener<R> listener : listeners) {
            listener.afterAdded(created);
        }
    }

    private void onUpdated(R before, R after) {
        for (PersistenceListener<R> listener : listeners) {
            listener.afterUpdated(before, after);
        }
    }

    private void onDeleted(R resource) {
        for (PersistenceListener<R> listener : listeners) {
            listener.afterDeleted(resource);
        }
    }

    protected abstract void doAdd(R resource);

    protected abstract void doUpdate(ID id, R resource);

    protected abstract void doDelete(ID id);
}
