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
package cn.sliew.carp.plugin.workflow.engine.temporal;

import cn.sliew.carp.framework.pf4j.api.PluginComponent;
import cn.sliew.milky.common.exception.Rethrower;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.temporal.client.WorkflowClient;
import io.temporal.client.schedules.ScheduleClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Slf4j
@PluginComponent
@RequiredArgsConstructor
public class DefaultWorkflowClientFactory implements WorkflowClientFactory {

    private final LoadingCache<String, WorkflowClient> workflowClientCache = CacheBuilder.newBuilder()
            .expireAfterAccess(Duration.ofMinutes(30L))
            .build(new CacheLoader<>() {
                @Override
                public WorkflowClient load(String key) throws Exception {
                    return TemporalUtil.createWorkflowClient(properties.getHost(), key);
                }
            });

    private final LoadingCache<String, ScheduleClient> scheduleClientCache = CacheBuilder.newBuilder()
            .expireAfterAccess(Duration.ofMinutes(30L))
            .build(new CacheLoader<>() {
                @Override
                public ScheduleClient load(String key) throws Exception {
                    return TemporalUtil.createScheduleClient(properties.getHost(), key);
                }
            });

    private final TemporalProperties properties;

    @Override
    public WorkflowClient getWorkflowClient(String namespace) {
        try {
            return workflowClientCache.get(namespace);
        } catch (ExecutionException e) {
            log.error("Get Temporal WorkflowClient error, host: {}, namespace: {}"
                    , properties.getHost(), namespace);
            Rethrower.throwAs(e);
            return null;
        }
    }

    @Override
    public ScheduleClient getScheduleClient(String namespace) {
        try {
            return scheduleClientCache.get(namespace);
        } catch (ExecutionException e) {
            log.error("Get Temporal ScheduleClient error, host: {}, namespace: {}"
                    , properties.getHost(), namespace);
            Rethrower.throwAs(e);
            return null;
        }
    }
}
