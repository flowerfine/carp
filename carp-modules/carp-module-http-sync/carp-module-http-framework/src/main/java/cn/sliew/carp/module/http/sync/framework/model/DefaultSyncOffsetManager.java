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

package cn.sliew.carp.module.http.sync.framework.model;

import cn.sliew.carp.module.http.sync.framework.model.internal.SimpleJobContext;
import cn.sliew.carp.module.http.sync.framework.repository.entity.JobSyncOffset;
import cn.sliew.carp.module.http.sync.framework.repository.mapper.JobSyncOffsetMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Component;

@Component
public class DefaultSyncOffsetManager implements SyncOffsetManager<SimpleJobContext> {

    private JobSyncOffsetMapper jobSyncOffsetMapper;

    public DefaultSyncOffsetManager(JobSyncOffsetMapper jobSyncOffsetMapper) {
        this.jobSyncOffsetMapper = jobSyncOffsetMapper;
    }

    @Override
    public JobSyncOffset getSyncOffset(SimpleJobContext context) {
        LambdaQueryWrapper<JobSyncOffset> queryWrapper = buildQueryWrapper(context);
        JobSyncOffset syncOffset = jobSyncOffsetMapper.selectOne(queryWrapper);
        if (syncOffset != null) {
            return syncOffset;
        }
        initSyncOffset(context);

        return getSyncOffset(context);
    }

    @Override
    public void initSyncOffset(SimpleJobContext context) {
        JobSyncOffset record = new JobSyncOffset();
        record.setGroup(context.getGroup());
        record.setJob(context.getJob());
        context.getSubJob().ifPresent(subJob -> record.setSubJob(subJob));
        record.setSyncOffset(context.getInitialSyncOffset());
        record.setCreator("sync-offset-manager");
        record.setEditor("sync-offset-manager");
        jobSyncOffsetMapper.insert(record);
    }

    @Override
    public void updateSyncOffset(SimpleJobContext context, String syncOffset) {
        JobSyncOffset jobSyncOffset = getSyncOffset(context);
        LambdaQueryWrapper<JobSyncOffset> queryWrapper = buildQueryWrapper(context);

        JobSyncOffset record = new JobSyncOffset();
        record.setGroup(context.getGroup());
        record.setJob(context.getJob());
        context.getSubJob().ifPresent(subJob -> record.setSubJob(subJob));
        record.setLastSyncOffset(jobSyncOffset.getSyncOffset());
        record.setSyncOffset(syncOffset);
        record.setEditor("sync-offset-manager");
        jobSyncOffsetMapper.update(record, queryWrapper);
    }

    @Override
    public void resetSyncOffset(SimpleJobContext context) {
        LambdaQueryWrapper<JobSyncOffset> queryWrapper = buildQueryWrapper(context);
        jobSyncOffsetMapper.delete(queryWrapper);
        initSyncOffset(context);
    }

    protected LambdaQueryWrapper<JobSyncOffset> buildQueryWrapper(SimpleJobContext context) {
        LambdaQueryWrapper<JobSyncOffset> queryWrapper = Wrappers.lambdaQuery(JobSyncOffset.class)
                .eq(JobSyncOffset::getGroup, context.getGroup())
                .eq(JobSyncOffset::getJob, context.getJob());
        if (context.getSubJob().isPresent()) {
            queryWrapper.eq(JobSyncOffset::getSubJob, context.getSubJob().get());
        }
        if (context.getAccount().isPresent()) {
            queryWrapper.eq(JobSyncOffset::getAccount, context.getAccount().get());
        }
        if (context.getSubAccount().isPresent()) {
            queryWrapper.eq(JobSyncOffset::getSubAccount, context.getSubAccount().get());
        }
        return queryWrapper;
    }
}
