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

package cn.sliew.carp.module.scheduler.api.executor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultJobHandlerRepository implements JobHandlerRepository {

    private final ConcurrentMap<String, JobHandler> repository = new ConcurrentHashMap<>();

    @Override
    public boolean exists(String name) {
        return repository.containsKey(name);
    }

    @Override
    public JobHandler get(String name) {
        return repository.get(name);
    }

    @Override
    public void register(String name, JobHandler handler) {
        if (exists(name)) {
            throw new RuntimeException(String.format("job handler already exists! name: %s", name));
        }
        repository.put(name, handler);
    }

    @Override
    public JobHandler remove(String name) {
        return repository.remove(name);
    }
}
