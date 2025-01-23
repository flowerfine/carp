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
package cn.sliew.carp.module.orca.spinnaker.orca.core.listeners;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import org.springframework.core.Ordered;

import javax.annotation.Nonnull;

public interface ExecutionListener extends Ordered, Comparable<ExecutionListener> {

    default void beforeInitialPersist(@Nonnull PipelineExecution execution) {

    }

    default void beforeExecution(@Nonnull Persister persister, @Nonnull PipelineExecution execution) {

    }

    default void afterExecution(
            @Nonnull Persister persister,
            @Nonnull PipelineExecution execution,
            @Nonnull ExecutionStatus executionStatus,
            boolean wasSuccessful) {

    }

    default int getOrder() {
        return 0;
    }

    @Override
    default int compareTo(@Nonnull ExecutionListener o) {
        return o.getOrder() - getOrder();
    }
}
