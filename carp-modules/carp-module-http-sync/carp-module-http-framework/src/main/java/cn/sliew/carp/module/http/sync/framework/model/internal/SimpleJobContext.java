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

package cn.sliew.carp.module.http.sync.framework.model.internal;

import cn.sliew.carp.module.http.sync.framework.model.SplitManager;
import cn.sliew.carp.module.http.sync.framework.model.SyncOffsetJobContext;
import cn.sliew.carp.module.http.sync.framework.model.SyncOffsetManager;
import lombok.Setter;
import org.apache.pekko.actor.typed.ActorSystem;

import java.util.Optional;

@Setter
public class SimpleJobContext implements SyncOffsetJobContext {

    private String group;
    private String job;
    private String subJob;
    private String account;
    private String subAccount;

    private ActorSystem actorSystem;

    private int subTaskParallelism;
    private int subTaskBatchSize;
    private SplitManager splitManager;

    private SyncOffsetManager syncOffsetManager;
    private String initialSyncOffset;
    private String finalSyncOffset;

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public String getJob() {
        return job;
    }

    @Override
    public Optional<String> getSubJob() {
        return Optional.ofNullable(subJob);
    }

    @Override
    public Optional<String> getAccount() {
        return Optional.ofNullable(account);
    }

    @Override
    public Optional<String> getSubAccount() {
        return Optional.ofNullable(subAccount);
    }

    @Override
    public ActorSystem getActorSystem() {
        return actorSystem;
    }

    @Override
    public SplitManager getSplitManager() {
        return splitManager;
    }

    @Override
    public SyncOffsetManager getSyncOffsetManager() {
        return syncOffsetManager;
    }

    @Override
    public String getInitialSyncOffset() {
        return initialSyncOffset;
    }

    @Override
    public String getFinalSyncOffset() {
        return finalSyncOffset;
    }
}
