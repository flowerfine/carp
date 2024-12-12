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
package cn.sliew.carp.module.persistence.api;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.persistence.api.selectors.Selector;

import java.util.Optional;
import java.util.function.Function;

public interface PersistenceService<ID, R> {

    Iterable<R> select(Selector selector);

    PageResult<R> page(PageParam param, Selector selector);

    Optional<R> get(ID id);

    default R getOrThrow(ID id) {
        return get(id).orElseThrow(() -> new IllegalStateException("resource not found"));
    }

    R add(R resource);

    R update(ID id, Function<R, R> updateFn);

    Optional<R> delete(ID id);

    void addListener(PersistenceListener<R> listener);
}
