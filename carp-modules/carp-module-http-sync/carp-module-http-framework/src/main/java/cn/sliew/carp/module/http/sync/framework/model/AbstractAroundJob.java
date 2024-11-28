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

import cn.sliew.carp.module.http.sync.framework.model.processor.DefaultJobContext;
import cn.sliew.carp.module.http.sync.framework.model.processor.JobContext;
import org.apache.pekko.Done;
import org.apache.pekko.japi.Pair;
import org.apache.pekko.stream.UniqueKillSwitch;

import java.util.concurrent.CompletionStage;

public abstract class AbstractAroundJob implements Job {

    protected JobContext context;

    @Override
    public void process(String param) {
        buildJobContext(param);
        execute(param);
    }

    private void execute(Object param) {
        if (doExecuteBefore(param)) {
            Pair<UniqueKillSwitch, CompletionStage<Done>> pair = doExecuteAsync(param);
            pair.second().toCompletableFuture().whenCompleteAsync((unused, throwable) -> doExecuteAfter(param));
            handleAsyncResult(param, pair);
        }
    }

    protected boolean doExecuteBefore(Object param) {
        return true;
    }

    protected abstract Pair<UniqueKillSwitch, CompletionStage<Done>> doExecuteAsync(Object param);

    protected void doExecuteAfter(Object param) {

    }

    protected abstract void handleAsyncResult(Object param, Pair<UniqueKillSwitch, CompletionStage<Done>> pair);

    protected abstract DefaultJobContext buildJobContext(String param);
}
