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
package cn.sliew.carp.module.scheduler.service.impl;

import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleJobType;
import cn.sliew.carp.module.scheduler.api.dict.CarpScheduleExecuteType;
import cn.sliew.carp.module.scheduler.api.executor.JobExecutorManager;
import cn.sliew.carp.module.scheduler.service.ScheduleJobExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleJobExecutorServiceImpl implements ScheduleJobExecutorService {

    @Autowired
    private JobExecutorManager jobExecutorManager;

    @Override
    public List<CarpScheduleJobType> getTypes() {
        return jobExecutorManager.listTypes();
    }

    @Override
    public List<CarpScheduleExecuteType> getExecutorTypes(CarpScheduleJobType jobType) {
        return jobExecutorManager.listExecutorTypes(jobType);
    }
}
