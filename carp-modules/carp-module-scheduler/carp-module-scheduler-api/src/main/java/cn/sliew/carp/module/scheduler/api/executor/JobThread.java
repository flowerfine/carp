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

import cn.sliew.carp.module.scheduler.api.executor.entity.job.JobExecutionResult;
import cn.sliew.milky.common.concurrent.CallableWrapper;
import cn.sliew.milky.common.exception.Rethrower;

import java.util.Objects;

import static cn.sliew.milky.common.check.Ensures.checkNotNull;

public class JobThread implements CallableWrapper<JobExecutionResult> {

    private final JobHandler jobHandler;
    private final JobContext context;

    private JobExecutionResult result;

    public JobThread(JobHandler jobHandler, JobContext context) {
        this.jobHandler = checkNotNull(jobHandler);
        this.context = checkNotNull(context);
    }

    public String getJobId() {
        return context.getJobId();
    }

    public String getJobInstanceId() {
        return context.getJobInstanceId();
    }

    @Override
    public void onBefore() throws Exception {
        result = jobHandler.init(context);
    }

    @Override
    public JobExecutionResult doCall() throws Exception {
        if (Objects.nonNull(result)) {
            return result;
        }
        return jobHandler.execute(context);
    }

    @Override
    public void onFailure(Exception e) {
        Rethrower.throwAs(e);
    }

    @Override
    public void onFinal() {
        jobHandler.destroy(context);
    }
}
