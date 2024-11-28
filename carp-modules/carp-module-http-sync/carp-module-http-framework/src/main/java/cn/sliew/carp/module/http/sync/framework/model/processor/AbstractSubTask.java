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

package cn.sliew.carp.module.http.sync.framework.model.processor;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.pekko.Done;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.stream.ActorAttributes;
import org.apache.pekko.stream.javadsl.Sink;
import org.apache.pekko.stream.javadsl.Source;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public abstract class AbstractSubTask<Root extends AbstractRootTask, Request, Response> implements SubTask<DefaultJobContext, Root> {

    private final Long subTaskId;
    private final Root rootTask;

    private final String startSyncOffset;
    private final String endSyncOffset;

    public AbstractSubTask(Long subTaskId, Root rootTask, String startSyncOffset, String endSyncOffset) {
        this.subTaskId = subTaskId;
        this.rootTask = rootTask;
        this.startSyncOffset = startSyncOffset;
        this.endSyncOffset = endSyncOffset;
    }

    @Override
    public Long getIdentifier() {
        return subTaskId;
    }

    @Override
    public Root getRootTask() {
        return rootTask;
    }

    @Override
    public String getStartSyncOffset() {
        return startSyncOffset;
    }

    @Override
    public String getEndSyncOffset() {
        return endSyncOffset;
    }

    @Override
    public CompletableFuture<Result> execute(DefaultJobContext context) {
        ActorSystem actorSystem = context.getActorSystem();
        Config config = ConfigFactory.load().getConfig("pekko");

//        System.out.println();
        System.out.println(config.toString());
//        System.out.println();

        Sink<FetchResult<Request, Response>, CompletionStage<Done>> sink = Sink.foreachParallel(10, data -> persistData(context, data.getRequest(), data.getResponse()), actorSystem.executionContext());
        Source<FetchResult<Request, Response>, ?> source = fetch(context);
        CompletionStage completionStage = source
                // 指定 dispatcher
                .withAttributes(ActorAttributes.dispatcher(context.dispatcher()))
                .runWith(sink, actorSystem);
        return completionStage.thenApply(done -> ProcessResult.success(this)).toCompletableFuture();
    }

    protected abstract Source<FetchResult<Request, Response>, ?> fetch(DefaultJobContext context);

    protected abstract Request buildFirstRequest(DefaultJobContext context);

    protected abstract Response requestRemote(DefaultJobContext context, Request request);

    protected abstract void persistData(DefaultJobContext context, Request request, Response response);
}
