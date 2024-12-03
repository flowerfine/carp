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

package cn.sliew.carp.module.http.sync.framework.model.manager;

import cn.sliew.carp.module.http.sync.framework.model.JobSetting;
import cn.sliew.carp.module.http.sync.framework.model.job.JobInfo;
import cn.sliew.carp.module.http.sync.framework.model.processor.DefaultJobContext;
import cn.sliew.carp.module.http.sync.framework.repository.entity.JobSyncOffset;
import cn.sliew.carp.module.http.sync.framework.repository.mapper.JobSyncOffsetMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.Objects;

import static cn.sliew.milky.common.check.Ensures.checkNotNull;

public class DefaultSyncOffsetManager implements SyncOffsetManager<DefaultJobContext> {

    private JobSetting setting;
    private JobSyncOffsetMapper jobSyncOffsetMapper;

    public DefaultSyncOffsetManager(JobSetting setting, JobSyncOffsetMapper jobSyncOffsetMapper) {
        this.setting = checkNotNull(setting);
        this.jobSyncOffsetMapper = jobSyncOffsetMapper;
    }

    @Override
    public String initSyncOffset() {
        return setting.getInitSyncOffset();
    }

    @Override
    public String finalSyncOffset() {
        return setting.getFinalSyncOffset();
    }

    @Override
    public JobSyncOffset getSyncOffset(DefaultJobContext context) {
        LambdaQueryWrapper<JobSyncOffset> queryWrapper = buildQueryWrapper(setting.getJobInfo());
        JobSyncOffset syncOffset = jobSyncOffsetMapper.selectOne(queryWrapper);
        if (Objects.nonNull(syncOffset)) {
            if (needResetSyncOffset(syncOffset)) {
                resetSyncOffset(context);
                return getSyncOffset(context);
            } else {
                return syncOffset;
            }
        } else {
            initSyncOffset(context);
            return getSyncOffset(context);
        }
    }

    @Override
    public void initSyncOffset(DefaultJobContext context) {
        JobInfo jobInfo = setting.getJobInfo();
        JobSyncOffset record = new JobSyncOffset();
        record.setGroup(jobInfo.getGroup());
        record.setJob(jobInfo.getJob());
        jobInfo.getSubJob().ifPresent(subJob -> record.setSubJob(subJob));
        jobInfo.getAccount().ifPresent(account -> record.setAccount(account));
        jobInfo.getSubAccount().ifPresent(subAccount -> record.setSubAccount(subAccount));
        record.setSyncOffset(initSyncOffset());
        record.setCreator("sync-offset-manager");
        record.setEditor("sync-offset-manager");
        jobSyncOffsetMapper.insert(record);
    }

    @Override
    public void updateSyncOffset(DefaultJobContext context, String syncOffset) {
        JobSyncOffset jobSyncOffset = getSyncOffset(context);
        LambdaQueryWrapper<JobSyncOffset> queryWrapper = buildQueryWrapper(setting.getJobInfo());

        JobInfo jobInfo = setting.getJobInfo();
        JobSyncOffset record = new JobSyncOffset();
        record.setGroup(jobInfo.getGroup());
        record.setJob(jobInfo.getJob());
        jobInfo.getSubJob().ifPresent(subJob -> record.setSubJob(subJob));
        jobInfo.getAccount().ifPresent(account -> record.setAccount(account));
        jobInfo.getSubAccount().ifPresent(subAccount -> record.setSubAccount(subAccount));
        record.setLastSyncOffset(jobSyncOffset.getSyncOffset());
        record.setSyncOffset(syncOffset);
        record.setEditor("sync-offset-manager");
        jobSyncOffsetMapper.update(record, queryWrapper);
    }

    @Override
    public boolean needResetSyncOffset(JobSyncOffset syncOffset) {
        return initSyncOffset().compareTo(syncOffset.getSyncOffset()) > 0;
    }

    @Override
    public void resetSyncOffset(DefaultJobContext context) {
        LambdaQueryWrapper<JobSyncOffset> queryWrapper = buildQueryWrapper(setting.getJobInfo());
        jobSyncOffsetMapper.delete(queryWrapper);
        initSyncOffset(context);
    }

    protected LambdaQueryWrapper<JobSyncOffset> buildQueryWrapper(JobInfo jobInfo) {
        LambdaQueryWrapper<JobSyncOffset> queryWrapper = Wrappers.lambdaQuery(JobSyncOffset.class)
                .eq(JobSyncOffset::getGroup, jobInfo.getGroup())
                .eq(JobSyncOffset::getJob, jobInfo.getJob());
        if (jobInfo.getSubJob().isPresent()) {
            queryWrapper.eq(JobSyncOffset::getSubJob, jobInfo.getSubJob().get());
        }
        if (jobInfo.getAccount().isPresent()) {
            queryWrapper.eq(JobSyncOffset::getAccount, jobInfo.getAccount().get());
        }
        if (jobInfo.getSubAccount().isPresent()) {
            queryWrapper.eq(JobSyncOffset::getSubAccount, jobInfo.getSubAccount().get());
        }
        return queryWrapper;
    }
}
