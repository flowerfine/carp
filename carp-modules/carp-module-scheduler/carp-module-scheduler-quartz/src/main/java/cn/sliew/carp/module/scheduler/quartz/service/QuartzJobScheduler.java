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

import cn.sliew.carp.framework.common.dict.schedule.ScheduleStatus;
import cn.sliew.carp.module.scheduler.api.scheduler.JobScheduler;
import cn.sliew.carp.module.scheduler.quartz.service.listener.QuartzJobListener;
import cn.sliew.carp.module.scheduler.quartz.service.listener.QuartzSchedulerListener;
import cn.sliew.carp.module.scheduler.quartz.service.listener.QuartzTriggerListener;
import cn.sliew.carp.module.scheduler.service.ScheduleJobInstanceService;
import cn.sliew.carp.module.scheduler.service.dto.ScheduleJobInstanceDTO;
import cn.sliew.milky.common.exception.Rethrower;
import org.quartz.*;
import org.quartz.impl.matchers.EverythingMatcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzJobScheduler implements JobScheduler, InitializingBean {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleJobInstanceService scheduleJobInstanceService;
    @Autowired
    private QuartzSchedulerListener schedulerListener;
    @Autowired
    private QuartzJobListener jobListener;
    @Autowired
    private QuartzTriggerListener triggerListener;

    @Override
    public void afterPropertiesSet() throws Exception {
        ListenerManager listenerManager = scheduler.getListenerManager();
        listenerManager.addSchedulerListener(schedulerListener);
        listenerManager.addJobListener(jobListener, EverythingMatcher.allJobs());
        listenerManager.addTriggerListener(triggerListener, EverythingMatcher.allTriggers());
    }

    @Override
    public boolean exists(Long jobInstanceId) {
        try {
            ScheduleJobInstanceDTO jobInstanceDTO = scheduleJobInstanceService.get(jobInstanceId);
            JobKey jobKey = QuartzUtil.getJobKey(jobInstanceDTO);
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            Rethrower.throwAs(e);
            return false;
        }
    }

    @Override
    public void execute(Long jobInstanceId) {
        try {
            ScheduleJobInstanceDTO jobInstanceDTO = scheduleJobInstanceService.get(jobInstanceId);
            JobKey jobKey = QuartzUtil.getJobKey(jobInstanceDTO);
            if (scheduler.checkExists(jobKey) == true) {
                return;
            }
            JobDataMap dataMap = QuartzUtil.buildDataMap(jobInstanceDTO);
            JobDetail job = JobBuilder.newJob(QuartzJobHandler.class)
                    .withIdentity(jobKey)
                    .usingJobData(dataMap)
                    .storeDurably()
                    .build();
            Trigger trigger = QuartzUtil.getTriggerOnce(jobInstanceDTO);
            scheduler.scheduleJob(job, trigger);
            updateScheduelStatus(jobInstanceId, ScheduleStatus.RUNNING);
        } catch (SchedulerException e) {
            Rethrower.throwAs(e);
        }
    }

    @Override
    public void schedule(Long jobInstanceId) {
        try {
            ScheduleJobInstanceDTO jobInstanceDTO = scheduleJobInstanceService.get(jobInstanceId);
            JobKey jobKey = QuartzUtil.getJobKey(jobInstanceDTO);
            if (scheduler.checkExists(jobKey) == true) {
                return;
            }
            JobDataMap dataMap = QuartzUtil.buildDataMap(jobInstanceDTO);
            JobDetail job = JobBuilder.newJob(QuartzJobHandler.class)
                    .withIdentity(jobKey)
                    .usingJobData(dataMap)
                    .storeDurably()
                    .build();
            Trigger trigger = QuartzUtil.getTrigger(jobInstanceDTO);
            scheduler.scheduleJob(job, trigger);
            updateScheduelStatus(jobInstanceId, ScheduleStatus.RUNNING);
        } catch (SchedulerException e) {
            Rethrower.throwAs(e);
        }
    }

    @Override
    public void unschedule(Long jobInstanceId) {
        try {
            ScheduleJobInstanceDTO jobInstanceDTO = scheduleJobInstanceService.get(jobInstanceId);
            JobKey jobKey = QuartzUtil.getJobKey(jobInstanceDTO);
            if (scheduler.checkExists(jobKey) == false) {
                return;
            }
            scheduler.deleteJob(jobKey);
            updateScheduelStatus(jobInstanceId, ScheduleStatus.STOP);
        } catch (SchedulerException e) {
            Rethrower.throwAs(e);
        }
    }

    @Override
    public void suspend(Long jobInstanceId) {
        try {
            ScheduleJobInstanceDTO jobInstanceDTO = scheduleJobInstanceService.get(jobInstanceId);
            JobKey jobKey = QuartzUtil.getJobKey(jobInstanceDTO);
            if (scheduler.checkExists(jobKey) == false) {
                return;
            }
            scheduler.pauseJob(jobKey);
            updateScheduelStatus(jobInstanceId, ScheduleStatus.STOP);
        } catch (SchedulerException e) {
            Rethrower.throwAs(e);
        }
    }

    @Override
    public void resume(Long jobInstanceId) {
        try {
            ScheduleJobInstanceDTO jobInstanceDTO = scheduleJobInstanceService.get(jobInstanceId);
            JobKey jobKey = QuartzUtil.getJobKey(jobInstanceDTO);
            if (scheduler.checkExists(jobKey) == false) {
                return;
            }
            scheduler.resumeJob(jobKey);
            updateScheduelStatus(jobInstanceId, ScheduleStatus.STOP);
        } catch (SchedulerException e) {
            Rethrower.throwAs(e);
        }
    }

    private void updateScheduelStatus(Long id, ScheduleStatus status) {
        scheduleJobInstanceService.updateStatus(id, status);
    }
}
