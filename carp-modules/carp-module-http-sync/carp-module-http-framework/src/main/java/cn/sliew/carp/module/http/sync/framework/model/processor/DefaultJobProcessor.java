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

import cn.sliew.carp.module.http.sync.framework.model.job.JobInfo;
import cn.sliew.carp.module.http.sync.framework.model.job.JobLogLevel;
import cn.sliew.milky.common.exception.Rethrower;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class DefaultJobProcessor<Context extends JobContext, Root extends RootTask, Sub extends SubTask>
        implements JobProcessor<Context, Root, Sub> {

    private final Context context;

    public DefaultJobProcessor(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public List<Sub> map(Root rootTask) {
        return rootTask.split(getContext());
    }

    @Override
    public CompletableFuture<Result> process(Sub subTask) {
        return subTask.execute(context);
    }

    @Override
    public Result reduce(Result result) {
        JobInfo jobInfo = context.jobInfo();
        SubTask subTask = result.getSubTask();
        if (result.isSuccess() == false) {
            if (context.logLevel() == JobLogLevel.SIMPLE
                    || context.logLevel() == JobLogLevel.COMPLEX
                    || context.logLevel() == JobLogLevel.FULL) {
                context.log(log, Level.ERROR, "{}-{}, 子任务处理失败, 子任务详情: {}, 失败信息: {}",
                        subTask.getRootTask().getIdentifier(), subTask.getIdentifier(),
                        JacksonUtil.toJsonString(subTask),
                        result.getMessage(), result.getThrowable());
            }
            if (result.getThrowable() != null) {
                Rethrower.throwAs(result.getThrowable());
            }
            throw new RuntimeException(result.getMessage());
        }

        if (context.logLevel() == JobLogLevel.COMPLEX
                || context.logLevel() == JobLogLevel.FULL) {
            context.log(log, Level.DEBUG, "{}-{}, 子任务处理成功, 子任务详情: {}",
                    subTask.getRootTask().getIdentifier(), subTask.getIdentifier(),
                    JacksonUtil.toJsonString(subTask));
        }
        context.syncOffsetManager().updateSyncOffset(context, subTask.getEndSyncOffset());
        return ProcessResult.success(result.getSubTask());
    }
}
