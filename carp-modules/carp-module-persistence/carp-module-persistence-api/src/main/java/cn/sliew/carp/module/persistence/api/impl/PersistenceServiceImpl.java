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

package cn.sliew.carp.module.persistence.api.impl;

import cn.sliew.carp.module.persistence.api.BlobStore;
import cn.sliew.carp.module.persistence.api.PersistenceService;
import cn.sliew.carp.module.persistence.api.ResourceMapper;
import cn.sliew.carp.module.persistence.api.ResourceSerDe;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PersistenceServiceImpl<R> implements PersistenceService<R> {

    private ResourceSerDe serDe;
    private Class<R> resourceClazz;
    private ResourceMapper<R> resourceMapper;
    private BlobStore blobStore;

    @Override
    public ResourceSerDe getSerDe() {
        return serDe;
    }

    @Override
    public Optional<List<R>> list() {
        List<byte[]> list = blobStore.list();
        return Optional.ofNullable(list).map(blobs -> {
            return blobs.stream()
                    .map(blob -> getSerDe().deserialize(blob, resourceClazz))
                    .collect(Collectors.toList());
        });
    }

    @Override
    public Optional<R> get(Long id) {
        byte[] bytes = blobStore.get(id);
        return Optional.of(bytes).map(blob -> getSerDe().deserialize(blob, resourceClazz));
    }

    @Override
    public R add(R resource) {
        Long id = resourceMapper.id(resource);
        blobStore.add(id, getSerDe().serialize(resource));
        return resource;
    }

    @Override
    public R update(Long id, Function<R, R> updateFn) {
        R updated = get(id).map(updateFn).get();
        blobStore.update(id, getSerDe().serialize(updated));
        return updated;
    }

    @Override
    public Optional<R> delete(Long id) {
        Optional<R> optional = get(id);
        if (optional.isPresent()) {
            blobStore.delete(id);
        }
        return optional;
    }
}
