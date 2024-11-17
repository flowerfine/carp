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

package cn.sliew.carp.module.scheduler.quartz.service;

import cn.sliew.carp.module.scheduler.api.executor.JobExecutor;
import cn.sliew.carp.module.scheduler.api.executor.entity.trigger.TriggerParam;
import cn.sliew.carp.module.scheduler.service.ScheduleJobInstanceService;
import cn.sliew.carp.module.scheduler.service.dto.ScheduleJobInstanceDTO;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StringUtils;

@Slf4j
public class QuartzJobHandler extends QuartzJobBean {

    @Autowired
    private JobExecutor jobExecutor;
    @Autowired
    private ScheduleJobInstanceService scheduleJobInstanceService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getMergedJobDataMap();
        Long jobInstanceId = dataMap.getLong(QuartzUtil.JOB_INSTANCE_ATTR);

        ScheduleJobInstanceDTO scheduleJobInstanceDTO = scheduleJobInstanceService.get(jobInstanceId);

        TriggerParam triggerParam = new TriggerParam();
        triggerParam.setJobId(scheduleJobInstanceDTO.getJobConfig().getId().toString());
        triggerParam.setJobInstanceId(scheduleJobInstanceDTO.getId().toString());

        // todo jobType & jobHandler

        if (StringUtils.hasText(scheduleJobInstanceDTO.getParams())) {
            triggerParam.setParams(JacksonUtil.toMap(JacksonUtil.toJsonNode(scheduleJobInstanceDTO.getParams())));
        }
        triggerParam.setFireTime(context.getFireTime());
        triggerParam.setTriggerTime(context.getScheduledFireTime());
        System.out.println("triggerParam: "+JacksonUtil.toJsonString(triggerParam));
//        jobExecutor.execute(triggerParam);
    }
}
