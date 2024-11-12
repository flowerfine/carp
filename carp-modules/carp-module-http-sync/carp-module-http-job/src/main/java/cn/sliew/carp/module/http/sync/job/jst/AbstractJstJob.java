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

package cn.sliew.carp.module.http.sync.job.jst;

import cn.sliew.carp.module.http.sync.framework.model.AbstractJob;
import cn.sliew.carp.module.http.sync.framework.model.RootTask;
import cn.sliew.carp.module.http.sync.framework.model.internal.SimpleJobContext;
import cn.sliew.carp.module.http.sync.job.enums.JstJob;
import cn.sliew.carp.module.http.sync.job.repository.entity.jst.JstAuth;
import cn.sliew.carp.module.http.sync.job.repository.mapper.jst.JstAuthMapper;
import cn.sliew.carp.module.http.sync.job.task.jst.AbstractJstRootTask;
import cn.sliew.milky.common.util.JacksonUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.SpawnProtocol;

import static cn.sliew.milky.common.check.Ensures.checkState;

public abstract class AbstractJstJob extends AbstractJob {

    private final JstAuthMapper jstAuthMapper;

    public AbstractJstJob(ActorSystem<SpawnProtocol.Command> actorSystem, JstAuthMapper jstAuthMapper) {
        super(actorSystem);
        this.jstAuthMapper = jstAuthMapper;
    }

    @Override
    public String getJobName() {
        return String.format("%s.%s.%s", getJstJob().getGroup().getGroup(), getJstJob().getApi().getApi(), getJstJob().getType().getType());
    }

    @Override
    protected SimpleJobContext buildJobContext() {
        return new SimpleJobContext();
    }

    @Override
    protected RootTask buildRootTask(Object param) {
        JstJobParam jstJobParam = JacksonUtil.parseJsonString((String) param, JstJobParam.class);
        LambdaQueryWrapper<JstAuth> queryWrapper = Wrappers.lambdaQuery(JstAuth.class)
                .eq(JstAuth::getAppKey, jstJobParam.getAppKey())
                .eq(JstAuth::getCompany, jstJobParam.getCompany());

        JstAuth jstAuth = jstAuthMapper.selectOne(queryWrapper);
        checkState(jstAuth != null);

        return buildJstRootTask(jstAuth);
    }

    protected abstract JstJob getJstJob();

    protected abstract AbstractJstRootTask buildJstRootTask(JstAuth jstAuth);
}
