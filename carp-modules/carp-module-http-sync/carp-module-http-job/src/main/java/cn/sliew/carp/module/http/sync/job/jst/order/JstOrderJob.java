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

package cn.sliew.carp.module.http.sync.job.jst.order;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.sliew.carp.module.http.sync.framework.model.JobSetting;
import cn.sliew.carp.module.http.sync.framework.model.job.JobLogLevel;
import cn.sliew.carp.module.http.sync.framework.model.manager.LockManager;
import cn.sliew.carp.module.http.sync.framework.repository.mapper.JobSyncOffsetMapper;
import cn.sliew.carp.module.http.sync.job.enums.JstJob;
import cn.sliew.carp.module.http.sync.job.jst.AbstractJstJob;
import cn.sliew.carp.module.http.sync.job.remote.JstRemoteService;
import cn.sliew.carp.module.http.sync.job.repository.entity.jst.JstAuth;
import cn.sliew.carp.module.http.sync.job.repository.mapper.jst.JstAuthMapper;
import cn.sliew.carp.module.http.sync.job.repository.mapper.jst.JstOrderMapper;
import cn.sliew.carp.module.http.sync.job.task.jst.AbstractJstRootTask;
import cn.sliew.carp.module.http.sync.job.task.jst.order.JstOrderRootTask;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.SpawnProtocol;
import org.springframework.stereotype.Component;

import java.util.Date;

import static cn.sliew.milky.common.check.Ensures.checkNotNull;

@Component
public class JstOrderJob extends AbstractJstJob {

    private final JstOrderMapper jstOrderMapper;

    public JstOrderJob(ActorSystem<SpawnProtocol.Command> actorSystem, MeterRegistry meterRegistry, JobSyncOffsetMapper jobSyncOffsetMapper, LockManager lockManager, JstRemoteService jstRemoteService, JstAuthMapper jstAuthMapper, JstOrderMapper jstOrderMapper) {
        super(actorSystem, meterRegistry, jobSyncOffsetMapper, lockManager, jstRemoteService, jstAuthMapper);
        this.jstOrderMapper = checkNotNull(jstOrderMapper);
    }

    @Override
    protected JstJob getJstJob() {
        return JstJob.NORMAL_ORDERS_SINGLE_QUERY;
    }

    @Override
    public JobSetting getSetting(String param) {
        // 一月之前
        Date initSyncOffset = DateUtil.offsetMonth(DateUtil.date(), -1);
        // 一小时之前
        Date finalSyncOffset = DateUtil.offsetHour(DateUtil.date(), -1);
        return JobSetting.builder()
                .jobInfo(getJobInfo(param))
                .logLevel(JobLogLevel.COMPLEX)
                .initSyncOffset(DateUtil.format(initSyncOffset, DatePattern.NORM_DATETIME_PATTERN))
                .finalSyncOffset(DateUtil.format(finalSyncOffset, DatePattern.NORM_DATETIME_PATTERN))
                .parallelism(2)
                .batchSize(100)
                .build();
    }

    @Override
    protected AbstractJstRootTask buildJstRootTask(JstAuth jstAuth) {
        return new JstOrderRootTask(System.currentTimeMillis(), jstRemoteService, jstAuth, jstOrderMapper);
    }
}
