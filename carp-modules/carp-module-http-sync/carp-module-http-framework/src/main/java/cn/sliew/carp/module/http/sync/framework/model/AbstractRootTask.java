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
import org.apache.pekko.japi.Pair;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRootTask<Sub extends AbstractSubTask> implements RootTask<SimpleJobContext, Sub> {

    private Long rootTaskId;

    public AbstractRootTask(Long rootTaskId) {
        this.rootTaskId = rootTaskId;
    }

    @Override
    public Long getIdentifier() {
        return rootTaskId;
    }

    @Override
    public List<Sub> split(SimpleJobContext context) {
        SyncOffsetManager syncOffsetManager = context.getSyncOffsetManager();
        JobSyncOffset syncOffset = syncOffsetManager.getSyncOffset(context);

        SplitManager splitManager = context.getSplitManager();
        Optional<Duration> optional = splitManager.getGradients().stream()
                .filter(gradient -> splitManager.supportSplit(syncOffset.getSyncOffset(), context.getFinalSyncOffset(), gradient))
                .findFirst();
        Duration gradient = null;
        if (optional.isEmpty()) {
            boolean backupSupport = splitManager.supportSplit(syncOffset.getSyncOffset(), context.getFinalSyncOffset(), splitManager.getBackoffGradient());
            if (backupSupport) {
                gradient = splitManager.getBackoffGradient();
            }
        } else {
            gradient = optional.get();
        }
        if (gradient == null) {
            return Collections.emptyList();
        }
        List<Pair<String, String>> splits = context.getSplitManager().split(syncOffset.getSyncOffset(), context.getFinalSyncOffset(), gradient, context.getSubTaskBatchSize());

        List<Sub> subs = new ArrayList<>();
        for (int i = 0; i < splits.size(); i++) {
            Pair<String, String> pair = splits.get(i);
            subs.add(build(Long.valueOf(i), pair.first(), pair.second()));
        }
        return subs;
    }
}
