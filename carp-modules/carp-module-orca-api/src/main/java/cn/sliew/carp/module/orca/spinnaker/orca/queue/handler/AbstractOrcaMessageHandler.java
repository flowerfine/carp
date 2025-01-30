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
package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Clock;

public abstract class AbstractOrcaMessageHandler<M extends Message> implements OrcaMessageHandler<M> {

    private final Queue queue;
    private final ExecutionRepository repository;
    protected final ApplicationEventPublisher publisher;
    protected final Clock clock;

    public AbstractOrcaMessageHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock) {
        this.queue = queue;
        this.repository = repository;
        this.publisher = publisher;
        this.clock = clock;
    }

    @Override
    public Queue getQueue() {
        return queue;
    }

    @Override
    public ExecutionRepository getRepository() {
        return repository;
    }

}
