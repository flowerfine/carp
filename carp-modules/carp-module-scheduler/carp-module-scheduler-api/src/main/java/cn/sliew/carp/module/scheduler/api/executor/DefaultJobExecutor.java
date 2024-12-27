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

import cn.hutool.core.bean.BeanUtil;
import cn.sliew.carp.framework.common.dict.schedule.ScheduleExecuteType;
import cn.sliew.carp.module.scheduler.api.executor.entity.ScheduleResponse;
import cn.sliew.carp.module.scheduler.api.executor.entity.trigger.TriggerParam;
import cn.sliew.milky.common.exception.Rethrower;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class DefaultJobExecutor implements JobExecutor {

    private JobHandlerFactoryRegistry jobHandlerFactoryRegistry;
    private JobThreadRepository jobThreadRepository;

    public DefaultJobExecutor(JobHandlerFactoryRegistry jobHandlerFactoryRegistry, JobThreadRepository jobThreadRepository) {
        this.jobHandlerFactoryRegistry = jobHandlerFactoryRegistry;
        this.jobThreadRepository = jobThreadRepository;
    }

    @Override
    public ScheduleResponse execute(TriggerParam param) {
        if (jobHandlerFactoryRegistry.exist(param.getExecuteType()) == false) {
            return new ScheduleResponse("-1", "unknown execute type: " + param.getExecuteType());
        }
        JobHandlerFactory jobHandlerFactory = jobHandlerFactoryRegistry.get(ScheduleExecuteType.of(param.getExecuteType()));

        JobHandler jobHandler = jobHandlerFactory.newInstance(param.getJobHandler());
        JobContext context = BeanUtil.copyProperties(param, JobContext.class);

        JobThread jobThread = new JobThread(jobHandler, context);

        jobThreadRepository.save(jobThread);

        CompletableFuture.supplyAsync(() -> {
                    try {
                        return jobThread.call();
                    } catch (Exception e) {
                        Rethrower.throwAs(e);
                        return null;
                    }
                })
                .whenComplete(((result, throwable) -> {
                    if (throwable != null) {
                        log.error(throwable.getMessage(), throwable);
                    } else {
                        log.info("{}", result);
                    }
                    jobThreadRepository.remove(jobThread.getJobId(), jobThread.getJobInstanceId());
                }));

        // 执行 jobThread，然后推送
        return ScheduleResponse.SUCCESS;
    }
}
