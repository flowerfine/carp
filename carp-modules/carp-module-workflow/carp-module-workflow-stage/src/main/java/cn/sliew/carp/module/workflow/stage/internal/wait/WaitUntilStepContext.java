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
package cn.sliew.carp.module.workflow.stage.internal.wait;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.Instant;

public final class WaitUntilStepContext {

    private final Long epochMillis;
    private final Instant startTime;

    @JsonCreator
    public WaitUntilStepContext(
            @JsonProperty("epochMillis") @Nullable Long epochMillis,
            @JsonProperty("startTime") @Nullable Instant startTime) {
        this.epochMillis = epochMillis;
        this.startTime = startTime;
    }

    public WaitUntilStepContext(@Nonnull Long epochMillis) {
        this(epochMillis, null);
    }

    public @Nullable Long getEpochMillis() {
        return epochMillis;
    }

    public @Nullable Instant getStartTime() {
        return startTime;
    }
}