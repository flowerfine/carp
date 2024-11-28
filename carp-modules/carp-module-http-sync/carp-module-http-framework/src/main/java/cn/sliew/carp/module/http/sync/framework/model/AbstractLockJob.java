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

package cn.sliew.carp.module.http.sync.framework.model;

import cn.sliew.carp.module.http.sync.framework.model.job.JobInfo;
import cn.sliew.carp.module.http.sync.framework.model.job.JobLogLevel;
import cn.sliew.carp.module.http.sync.framework.model.manager.LockManager;
import cn.sliew.milky.common.util.JacksonUtil;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.pekko.Done;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.SpawnProtocol;
import org.apache.pekko.japi.Pair;
import org.apache.pekko.stream.UniqueKillSwitch;
import org.slf4j.event.Level;

import java.util.concurrent.CompletionStage;

@Slf4j
public abstract class AbstractLockJob extends AbstractProcessJob {

    public AbstractLockJob(ActorSystem<SpawnProtocol.Command> actorSystem, MeterRegistry meterRegistry) {
        super(actorSystem, meterRegistry);
    }

    @Override
    protected boolean doExecuteBefore(Object param) {
        LockManager lockManager = context.lockManager();
        JobInfo jobInfo = context.jobInfo();
        boolean lock = lockManager.lock(jobInfo);
        if (context.logLevel() == JobLogLevel.COMPLEX || context.logLevel() == JobLogLevel.FULL) {
            if (lock) {
                context.log(log, Level.DEBUG, "ttl: {}, 获取执行锁成功, param: {}",
                        DurationFormatUtils.formatDurationHMS(lockManager.lockReleaseTime(jobInfo)), JacksonUtil.toJsonString(param));
            } else {
                context.log(log, Level.DEBUG, "ttl: {}, 获取执行锁失败, param: {}",
                        DurationFormatUtils.formatDurationHMS(lockManager.lockReleaseTime(jobInfo)), JacksonUtil.toJsonString(param));
            }
        }
        return lock;
    }

    @Override
    protected void doExecuteAfter(Object param) {
        LockManager lockManager = context.lockManager();
        JobInfo jobInfo = context.jobInfo();
        boolean unlock = lockManager.unlock(jobInfo);
        if (context.logLevel() == JobLogLevel.COMPLEX || context.logLevel() == JobLogLevel.FULL) {
            if (unlock) {
                context.log(log, Level.DEBUG, "释放执行锁成功, param: {}", JacksonUtil.toJsonString(param));
            } else {
                context.log(log, Level.DEBUG, "ttl: {}, 释放执行锁失败, param: {}",
                        DurationFormatUtils.formatDurationHMS(lockManager.lockReleaseTime(jobInfo)), JacksonUtil.toJsonString(param));
            }
        }
    }

    @Override
    protected void handleAsyncResult(Object param, Pair<UniqueKillSwitch, CompletionStage<Done>> pair) {

    }
}
