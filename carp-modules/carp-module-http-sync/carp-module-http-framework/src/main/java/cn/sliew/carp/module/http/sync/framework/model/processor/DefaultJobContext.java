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

package cn.sliew.carp.module.http.sync.framework.model.processor;

import cn.sliew.carp.framework.spring.util.LogUtil;
import cn.sliew.carp.module.http.sync.framework.model.JobSetting;
import cn.sliew.carp.module.http.sync.framework.model.job.JobInfo;
import cn.sliew.carp.module.http.sync.framework.model.job.JobLogLevel;
import cn.sliew.carp.module.http.sync.framework.model.manager.LockManager;
import cn.sliew.carp.module.http.sync.framework.model.manager.SplitManager;
import cn.sliew.carp.module.http.sync.framework.model.manager.SyncOffsetManager;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.pekko.actor.typed.ActorSystem;
import org.slf4j.Logger;
import org.slf4j.event.Level;

import java.util.function.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultJobContext implements JobContext {

    private JobSetting setting;
    private ActorSystem actorSystem;
    private MeterRegistry meterRegistry;
    private Function<JobSetting, SyncOffsetManager> syncOffsetManagerSupplier;
    private Function<JobSetting, SplitManager> splitManagerSupplier;
    private Function<JobSetting, LockManager> lockManagerSupplier;

    @Override
    public JobSetting jobSetting() {
        return setting;
    }

    @Override
    public JobInfo jobInfo() {
        return setting.getJobInfo();
    }

    @Override
    public JobLogLevel logLevel() {
        return setting.getLogLevel();
    }

    @Override
    public String dispatcher() {
        return setting.getDispatcher();
    }

    @Override
    public ActorSystem actorSystem() {
        return actorSystem;
    }

    @Override
    public MeterRegistry meterRegistry() {
        return meterRegistry;
    }

    @Override
    public SyncOffsetManager syncOffsetManager() {
        return syncOffsetManagerSupplier.apply(getSetting());
    }

    @Override
    public SplitManager splitManager() {
        return splitManagerSupplier.apply(getSetting());
    }

    @Override
    public LockManager lockManager() {
        return lockManagerSupplier.apply(getSetting());
    }

    @Override
    public void log(Logger log, Level level, String message, Object... params) {
        String appendMessage = "group: {}, job: {}, subJob: {}, account: {}, subAccount: {}, " + message;
        LogUtil.log(log, level, appendMessage, appendParams(params));
    }

    private Object[] appendParams(Object... params) {
        JobInfo jobInfo = jobInfo();
        Object[] jobParams = new Object[]{jobInfo.getGroup(), jobInfo.getJob(),
                jobInfo.getSubJob().orElse("null"),
                jobInfo.getAccount().orElse("null"),
                jobInfo.getSubAccount().orElse("null")
        };
        return ArrayUtils.addAll(jobParams, params);
    }
}
