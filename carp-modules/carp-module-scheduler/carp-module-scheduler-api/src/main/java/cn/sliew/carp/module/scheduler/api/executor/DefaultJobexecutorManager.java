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

import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleEngineType;
import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleJobType;
import cn.sliew.carp.module.scheduler.api.dict.CarpScheduleExecuteType;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

public class DefaultJobexecutorManager implements JobExecutorManager {

    private List<JobExecutor> executors;

    public DefaultJobexecutorManager(List<JobExecutor> executors) {
        checkArgument(CollectionUtils.isNotEmpty(executors), "executors not allowed empty");
        this.executors = executors;
    }

    @Override
    public List<CarpScheduleEngineType> listEngines() {
        return Arrays.asList(CarpScheduleEngineType.values());
    }

    @Override
    public Set<CarpScheduleJobType> listTypes(CarpScheduleEngineType engineType) {
        return executors.stream()
                .filter(executor -> CollectionUtils.containsAny(executor.getEngines(), engineType))
                .map(JobExecutor::getType).collect(Collectors.toSet());
    }

    @Override
    public List<CarpScheduleExecuteType> listExecutorTypes(CarpScheduleEngineType engineType, CarpScheduleJobType jobType) {
        return Optional.of(getExecutor(engineType, jobType))
                .map(JobExecutor::getSupportExecuteTypes)
                .orElseThrow();
    }

    @Override
    public JobExecutor getExecutor(CarpScheduleEngineType engineType, CarpScheduleJobType jobType) {
        return executors.stream()
                .filter(executor -> CollectionUtils.containsAny(executor.getEngines(), engineType))
                .filter(executor -> Objects.equals(executor.getType(), jobType))
                .findFirst().orElseThrow();
    }
}
