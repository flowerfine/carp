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
package cn.sliew.carp.module.orca.spinnaker.orca.core.events;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.annotation.Nonnull;
import java.time.Instant;

/**
 * Events emitted at various stages in the lifecycle of an execution.
 */
@Getter
public abstract class ExecutionEvent extends ApplicationEvent {

    private final ExecutionType executionType;
    private final Long executionId;

    protected ExecutionEvent(
            @Nonnull Object source, @Nonnull ExecutionType executionType, @Nonnull Long executionId) {
        super(source);
        this.executionType = executionType;
        this.executionId = executionId;
    }

    public final @Nonnull Instant timestamp() {
        return Instant.ofEpochMilli(super.getTimestamp());
    }
}
