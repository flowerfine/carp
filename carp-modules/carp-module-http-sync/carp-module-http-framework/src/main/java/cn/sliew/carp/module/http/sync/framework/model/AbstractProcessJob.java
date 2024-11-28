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

import cn.sliew.carp.module.http.sync.framework.model.job.JobInfo;
import cn.sliew.carp.module.http.sync.framework.model.manager.DefaultSplitManager;
import cn.sliew.carp.module.http.sync.framework.model.manager.LockManager;
import cn.sliew.carp.module.http.sync.framework.model.manager.SplitManager;
import cn.sliew.carp.module.http.sync.framework.model.manager.SyncOffsetManager;
import cn.sliew.carp.module.http.sync.framework.model.processor.*;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.pekko.Done;
import org.apache.pekko.NotUsed;
import org.apache.pekko.actor.typed.ActorSystem;
import org.apache.pekko.actor.typed.SpawnProtocol;
import org.apache.pekko.event.Logging;
import org.apache.pekko.japi.Pair;
import org.apache.pekko.stream.*;
import org.apache.pekko.stream.javadsl.*;

import java.util.concurrent.CompletionStage;

public abstract class AbstractProcessJob extends AbstractAroundJob {

    protected final ActorSystem<SpawnProtocol.Command> actorSystem;
    protected final MeterRegistry meterRegistry;

    public AbstractProcessJob(ActorSystem<SpawnProtocol.Command> actorSystem, MeterRegistry meterRegistry) {
        this.actorSystem = actorSystem;
        this.meterRegistry = meterRegistry;
    }

    @Override
    protected Pair<UniqueKillSwitch, CompletionStage<Done>> doExecuteAsync(Object param) {
        JobSetting setting = getSetting((String) param);
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
                                    int concurrency = setting.getParallelism();
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

        return source.via(subTasks)
                .log(getJobName(setting.getJobInfo()))
                .withAttributes(
                        Attributes.createLogLevels(
                                getElementLogLevel(), // onElement
                                Logging.InfoLevel(),  // onFinish
                                Logging.ErrorLevel()  // onFailure
                        )
                )
                .toMat(Sink.foreach(result -> processor.reduce(result)), Keep.both())
                .run(actorSystem);
    }

    protected String getJobName(JobInfo jobInfo) {
        if (jobInfo.getSubJob().isPresent()) {
            return String.format("%s.%s.%s", jobInfo.getGroup(), jobInfo.getJob(), jobInfo.getSubJob().get());
        }
        return String.format("%s.%s", jobInfo.getGroup(), jobInfo.getJob());
    }

    private int getElementLogLevel() {
        return switch (context.logLevel()) {
            case FULL, COMPLEX -> Logging.DebugLevel();
            default -> Logging.InfoLevel();
        };
    }

    @Override
    protected DefaultJobContext buildJobContext(String param) {
        return DefaultJobContext.builder()
                .setting(getSetting(param))
                .actorSystem(actorSystem)
                .meterRegistry(meterRegistry)
                .splitManagerSupplier(setting -> buildSplitManager(setting))
                .syncOffsetManagerSupplier(setting -> buildSyncOffsetManager(setting))
                .lockManagerSupplier(setting -> buildLockManager(setting))
                .build();
    }

    protected SplitManager buildSplitManager(JobSetting setting) {
        return new DefaultSplitManager(setting);
    }

    protected abstract SyncOffsetManager buildSyncOffsetManager(JobSetting setting);

    protected abstract LockManager buildLockManager(JobSetting setting);

    protected JobProcessor buildJobProcessor(JobContext context) {
        return new DefaultJobProcessor(context);
    }

    protected abstract RootTask buildRootTask(Object param);
}
