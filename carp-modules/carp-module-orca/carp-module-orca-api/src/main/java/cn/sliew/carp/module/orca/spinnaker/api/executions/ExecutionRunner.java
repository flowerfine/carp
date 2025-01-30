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
package cn.sliew.carp.module.orca.spinnaker.api.executions;

import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;

public interface ExecutionRunner {

    void start(PipelineExecution execution);

    default void restart(PipelineExecution execution, Long stageId) {
        throw new UnsupportedOperationException();
    }

    default void reschedule(PipelineExecution execution) {
        throw new UnsupportedOperationException();
    }

    default void unpause(PipelineExecution execution) {
        throw new UnsupportedOperationException();
    }

    default void cancel(PipelineExecution execution, String reason) {
        throw new UnsupportedOperationException();
    }
}
