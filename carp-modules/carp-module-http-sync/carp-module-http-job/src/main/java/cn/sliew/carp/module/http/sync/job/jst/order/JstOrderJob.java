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

import cn.sliew.carp.module.http.sync.framework.model.SplitManager;
import cn.sliew.carp.module.http.sync.framework.model.SyncOffsetManager;
import cn.sliew.carp.module.http.sync.framework.model.internal.SimpleJobContext;
import cn.sliew.carp.module.http.sync.job.enums.JstJob;
import cn.sliew.carp.module.http.sync.job.jst.AbstractJstJob;
import cn.sliew.carp.module.http.sync.job.remote.JstRemoteService;
import cn.sliew.carp.module.http.sync.job.repository.entity.jst.JstAuth;
import cn.sliew.carp.module.http.sync.job.repository.mapper.jst.JstAuthMapper;
import cn.sliew.carp.module.http.sync.job.repository.mapper.jst.JstOrderMapper;
import cn.sliew.carp.module.http.sync.job.task.jst.AbstractJstRootTask;
import cn.sliew.carp.module.http.sync.job.task.jst.order.JstOrderRootTask;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.SpawnProtocol;
import org.springframework.stereotype.Component;

@Component
public class JstOrderJob extends AbstractJstJob {

    private final JstOrderMapper jstOrderMapper;

    public JstOrderJob(ActorSystem<SpawnProtocol.Command> actorSystem, SyncOffsetManager syncOffsetManager, SplitManager splitManager, JstRemoteService jstRemoteService, JstAuthMapper jstAuthMapper, JstOrderMapper jstOrderMapper) {
        super(actorSystem, syncOffsetManager, splitManager, jstRemoteService, jstAuthMapper);
        this.jstOrderMapper = jstOrderMapper;
    }

    @Override
    protected JstJob getJstJob() {
        return JstJob.NORMAL_ORDERS_SINGLE_QUERY;
    }

    @Override
    protected SimpleJobContext buildJobContext() {
        SimpleJobContext jobContext = super.buildJobContext();
        jobContext.setInitialSyncOffset("2024-01-01 00:00:00");
        jobContext.setFinalSyncOffset("2024-11-11 00:00:00");

        jobContext.setSubTaskParallelism(2);
        jobContext.setSubTaskBatchSize(1);
        return jobContext;
    }

    @Override
    protected AbstractJstRootTask buildJstRootTask(JstAuth jstAuth) {
        return new JstOrderRootTask(System.currentTimeMillis(), jstRemoteService, jstAuth, jstOrderMapper);
    }
}
