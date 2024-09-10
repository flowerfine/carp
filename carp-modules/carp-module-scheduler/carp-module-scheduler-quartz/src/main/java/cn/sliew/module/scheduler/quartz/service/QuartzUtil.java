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

package cn.sliew.module.scheduler.quartz.service;

import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.module.scheduler.service.dto.ScheduleJobInstanceDTO;
import org.quartz.*;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.TimeZone;

public enum QuartzUtil {
    ;

    /**
     * schedule job and group
     */
    private static final String JOB_PREFIX = "job";
    private static final String JOB_GROUP_PREFIX = "jobGrp";
    private static final String TRIGGER_PREFIX = "trigger";
    private static final String TRIGGER_GROUP_PREFIX = "triggerGrp";

    public static final String JOB_INSTANCE_ATTR = "CARP_JOB_INSTANCE_ATTR";

    public static JobDataMap buildDataMap(ScheduleJobInstanceDTO instance) {
        ScheduleJobInstanceDTO copy = new ScheduleJobInstanceDTO();
        BeanUtils.copyProperties(instance, copy);
        copy.setStatus(null);
        return new JobDataMap(Map.of(JOB_INSTANCE_ATTR, JacksonUtil.toJsonString(copy)));
    }

    public static JobKey getJobKey(ScheduleJobInstanceDTO instance) {
        String jobGroup = String.format("%s_%s", JOB_GROUP_PREFIX, instance.getJobConfig().getJobGroup().getNamespace());
        String jobName = String.format("%s_%s", JOB_PREFIX, instance.getJobConfig().getName());
        return new JobKey(jobName, jobGroup);
    }

    public static TriggerKey getTriggerKey(ScheduleJobInstanceDTO instance) {
        String triggerGroup = String.format("%s_%s", TRIGGER_GROUP_PREFIX, instance.getJobConfig().getJobGroup().getNamespace());
        String triggerName = String.format("%s_%s", TRIGGER_PREFIX, instance.getId());
        return TriggerKey.triggerKey(triggerName, triggerGroup);
    }

    private static ScheduleBuilder buildSchedule(ScheduleJobInstanceDTO instance) {
        return CronScheduleBuilder
                .cronSchedule(instance.getCron())
                .inTimeZone(TimeZone.getTimeZone(instance.getTimezone()));
    }

    public static Trigger getTrigger(ScheduleJobInstanceDTO instance) {
        return TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(instance))
                .startAt(instance.getStartTime())
                .endAt(instance.getEndTime())
                .withSchedule(buildSchedule(instance))
                .build();
    }

    public static Trigger getTriggerOnce(ScheduleJobInstanceDTO instance) {
        return TriggerBuilder.newTrigger()
                .withIdentity(getTriggerKey(instance))
                .startAt(instance.getStartTime())
                .endAt(instance.getEndTime())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(1))
                .build();
    }

}
