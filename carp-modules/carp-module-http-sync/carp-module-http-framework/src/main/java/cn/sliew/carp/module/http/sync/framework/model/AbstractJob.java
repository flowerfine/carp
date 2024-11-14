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

import cn.sliew.carp.module.http.sync.framework.model.internal.ProcessResult;
import cn.sliew.carp.module.http.sync.framework.model.internal.SimpleJobContext;
import cn.sliew.milky.common.exception.Rethrower;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pekko.Done;
import org.apache.pekko.NotUsed;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.SpawnProtocol;
import org.apache.pekko.japi.Pair;
import org.apache.pekko.stream.*;
import org.apache.pekko.stream.javadsl.*;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractJob implements Job {

    protected ActorSystem<SpawnProtocol.Command> actorSystem;
    protected SyncOffsetManager syncOffsetManager;
    protected SplitManager splitManager;

    public AbstractJob(ActorSystem<SpawnProtocol.Command> actorSystem, SyncOffsetManager syncOffsetManager, SplitManager splitManager) {
        this.actorSystem = actorSystem;
        this.syncOffsetManager = syncOffsetManager;
        this.splitManager = splitManager;
    }

    @Override
    public void process(String param) {
        doExecute(param);
    }

    protected void doExecute(Object param) {
        SimpleJobContext context = buildJobContext();
        JobProcessor processor = buildJobProcessor(context);
        RootTask rootTask = buildRootTask(param);

        Source<SubTask, UniqueKillSwitch> source = Source.single(rootTask)
                .mapConcat(root -> processor.map(root))
                .viaMat(KillSwitches.single(), Keep.right());

        Flow<SubTask, ProcessResult, NotUsed> process = Flow.<SubTask>create()
                .map(subTask -> processor.process(subTask)).mapAsync(1, future -> future);

        Flow<SubTask, ProcessResult, NotUsed> subTasks =
                Flow.fromGraph(
                        GraphDSL.create(
                                b -> {
                                    int concurrency = context.getSubTaskParallelism();
                                    UniformFanOutShape<SubTask, SubTask> partition =
                                            b.add(Partition.create(concurrency, subTask -> Math.toIntExact(subTask.getIdentifier()) % concurrency));
                                    UniformFanInShape<ProcessResult, ProcessResult> merge =
                                            b.add(MergeSequence.create(concurrency, result -> result.getSubTask().getIdentifier()));

                                    for (int i = 0; i < concurrency; i++) {
                                        b.from(partition.out(i))
                                                .via(b.add(process.async()))
                                                .viaFanIn(merge);
                                    }

                                    return FlowShape.of(partition.in(), merge.out());
                                }));

        Pair<UniqueKillSwitch, CompletionStage<Done>> pair = source.via(subTasks)
                .log(getJobName())
                .toMat(Sink.foreach(result -> processor.reduce(result)), Keep.both())
                .run(actorSystem);
        UniqueKillSwitch killSwitch = pair.first();
        try {
            pair.second().toCompletableFuture().get(1, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error("job 执行异常, job: {}, param: {}", getJobName(), JacksonUtil.toJsonString(param));
            killSwitch.abort(e);
            Rethrower.throwAs(e);
        }
    }

    public abstract String getJobName();

    protected SimpleJobContext buildJobContext() {
        SimpleJobContext jobContext = new SimpleJobContext();
        jobContext.setActorSystem(actorSystem);
        jobContext.setSyncOffsetManager(syncOffsetManager);
        jobContext.setSplitManager(splitManager);
        return jobContext;
    }

    protected JobProcessor buildJobProcessor(SimpleJobContext context) {
        return new DefaultJobProcessor(context);
    }

    protected abstract RootTask buildRootTask(Object param);
}
