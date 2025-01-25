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
package cn.sliew.carp.module.orca.spinnaker.orca.queue.pending;

import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class InMemoryPendingExecutionService implements PendingExecutionService {

    private final Map<Long, Deque<Message>> pending = new ConcurrentHashMap<>();

    @Override
    public void enqueue(Long pipelineConfigId, Message message) {
        pendingFor(pipelineConfigId).addLast(message);
    }

    @Override
    public Message popOldest(Long pipelineConfigId) {
        Deque<Message> deque = pendingFor(pipelineConfigId);
        if (CollectionUtils.isEmpty(deque)) {
            return null;
        } else {
            return deque.removeFirst();
        }
    }

    @Override
    public Message popNewest(Long pipelineConfigId) {
        Deque<Message> deque = pendingFor(pipelineConfigId);
        if (CollectionUtils.isEmpty(deque)) {
            return null;
        } else {
            return deque.removeLast();
        }
    }

    @Override
    public void purge(Long pipelineConfigId, Consumer<Message> callback) {
        Deque<Message> deque = pendingFor(pipelineConfigId);
        while (CollectionUtils.isNotEmpty(deque)) {
            callback.accept(deque.removeFirst());
        }
    }

    @Override
    public int depth(Long pipelineConfigId) {
        return pendingFor(pipelineConfigId).size();
    }

    @Override
    public List<Long> pendingIds() {
        return pending.keySet().stream().toList();
    }

    private Deque<Message> pendingFor(Long pipelineConfigId) {
        return pending.computeIfAbsent(pipelineConfigId, key -> new LinkedList<>());
    }
}
