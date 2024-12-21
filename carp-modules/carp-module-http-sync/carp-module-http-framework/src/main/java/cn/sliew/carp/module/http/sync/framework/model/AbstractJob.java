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

import cn.sliew.carp.module.http.sync.framework.model.manager.DefaultSyncOffsetManager;
import cn.sliew.carp.module.http.sync.framework.model.manager.LockManager;
import cn.sliew.carp.module.http.sync.framework.model.manager.SyncOffsetManager;
import cn.sliew.carp.module.http.sync.framework.repository.mapper.JobSyncOffsetMapper;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.SpawnProtocol;

import static cn.sliew.milky.common.check.Ensures.checkNotNull;

public abstract class AbstractJob extends AbstractLockJob {

    private final JobSyncOffsetMapper jobSyncOffsetMapper;
    private final LockManager lockManager;

    public AbstractJob(ActorSystem<SpawnProtocol.Command> actorSystem, MeterRegistry meterRegistry, JobSyncOffsetMapper jobSyncOffsetMapper, LockManager lockManager) {
        super(actorSystem, meterRegistry);
        this.jobSyncOffsetMapper = checkNotNull(jobSyncOffsetMapper);
        this.lockManager = checkNotNull(lockManager);
    }

    @Override
    protected SyncOffsetManager buildSyncOffsetManager(JobSetting setting) {
        return new DefaultSyncOffsetManager(setting, jobSyncOffsetMapper);
    }

    @Override
    protected LockManager buildLockManager(JobSetting setting) {
        return lockManager;
    }
}
