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
package cn.sliew.carp.module.scheduler.executor.api.executor;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Collection;

public class DefaultJobThreadRepository implements JobThreadRepository {

    private final Table<String, String, JobThread> repository = HashBasedTable.create();

    @Override
    public Collection<JobThread> getAll() {
        return repository.values();
    }

    @Override
    public Collection<JobThread> getByJobId(String jobId) {
        return repository.row(jobId).values();
    }

    @Override
    public JobThread get(String jobId, String jobInstanceId) {
        return repository.get(jobId, jobInstanceId);
    }

    @Override
    public boolean exists(String jobId, String jobInstanceId) {
        return repository.contains(jobId, jobInstanceId);
    }

    @Override
    public void save(JobThread thread) {
        repository.put(thread.getJobId(), thread.getJobInstanceId(), thread);
    }

    @Override
    public JobThread remove(String jobId, String jobInstanceId) {
        return repository.remove(jobId, jobInstanceId);
    }
}
