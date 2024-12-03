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

    @Override
    public void process(String param) {
        execute(buildJobContext(param), param);
    }

    private void execute(JobContext context, Object param) {
        if (doExecuteBefore(context, param)) {
            Pair<UniqueKillSwitch, CompletionStage<Done>> pair = doExecuteAsync(context, param);
            pair.second().toCompletableFuture().whenCompleteAsync((unused, throwable) -> doExecuteAfter(context, param));
            handleAsyncResult(context, param, pair);
        }
    }

    protected boolean doExecuteBefore(JobContext context, Object param) {
        return true;
    }

    protected abstract Pair<UniqueKillSwitch, CompletionStage<Done>> doExecuteAsync(JobContext context, Object param);

    protected void doExecuteAfter(JobContext context, Object param) {

    }

    protected abstract void handleAsyncResult(JobContext context, Object param, Pair<UniqueKillSwitch, CompletionStage<Done>> pair);

    protected abstract DefaultJobContext buildJobContext(String param);
}
