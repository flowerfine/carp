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
package cn.sliew.carp.module.orca.spinnaker.api.model.task;

import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;

import java.time.Duration;

/**
 * A retryable task defines its backoff period (the period between delays) and its timeout (the
 * total period of the task)
 */
public interface RetryableTask extends Task {

    Duration getBackoffPeriod();

    Duration getTimeout();

    default Duration getDynamicTimeout(DagStepDTO stage) {
        return getTimeout();
    }

    default Duration getDynamicBackoffPeriod(Duration taskDuration) {
        return getBackoffPeriod();
    }

    default Duration getDynamicBackoffPeriod(DagStepDTO stage, Duration taskDuration) {
        return getDynamicBackoffPeriod(taskDuration);
    }
}
