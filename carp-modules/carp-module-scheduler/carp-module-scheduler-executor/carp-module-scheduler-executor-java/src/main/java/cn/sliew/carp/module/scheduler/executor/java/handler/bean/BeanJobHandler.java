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
package cn.sliew.carp.module.scheduler.executor.java.handler.bean;

import cn.sliew.carp.module.scheduler.executor.api.executor.JobContext;
import cn.sliew.carp.module.scheduler.executor.api.executor.JobHandler;
import cn.sliew.carp.module.scheduler.executor.api.executor.entity.job.JobExecutionResult;

public class BeanJobHandler implements JobHandler {

    private final JobHandler bean;

    public BeanJobHandler(JobHandler bean) {
        this.bean = bean;
    }

    @Override
    public JobExecutionResult init(JobContext context) {
        return bean.init(context);
    }

    @Override
    public JobExecutionResult execute(JobContext context) {
        return bean.execute(context);
    }

    @Override
    public void destroy(JobContext context) {
        bean.destroy(context);
    }
}
