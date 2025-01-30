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
package cn.sliew.carp.module.scheduler.executor.java.handler.method;

import cn.sliew.carp.module.scheduler.executor.api.executor.JobContext;
import cn.sliew.carp.module.scheduler.executor.api.executor.JobHandler;
import cn.sliew.carp.module.scheduler.executor.api.executor.entity.job.JobExecutionResult;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

public class MethodJobHandler implements JobHandler {

    private final Object target;
    private final Method method;
    private Method initMethod;
    private Method destroyMethod;

    public MethodJobHandler(Object target, Method method, Method initMethod, Method destroyMethod) {
        this.target = target;
        this.method = method;
        this.initMethod = initMethod;
        this.destroyMethod = destroyMethod;
    }

    @Override
    public JobExecutionResult init(JobContext context) {
        if (Objects.isNull(initMethod)) {
            return null;
        }

        try {
            Object result = ReflectionUtils.invokeMethod(initMethod, target, context);
            if (Objects.nonNull(result) && result instanceof JobExecutionResult) {
                return (JobExecutionResult) result;
            }
        } catch (Exception e) {
            return JobExecutionResult.failed("-1", "init method failed", e);
        }
        return null;
    }

    @Override
    public void destroy(JobContext context) {
        if (Objects.isNull(destroyMethod)) {
            return;
        }
        try {
            ReflectionUtils.invokeMethod(destroyMethod, target, context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JobExecutionResult execute(JobContext context) {
        try {
            Object result = ReflectionUtils.invokeMethod(method, target, context);
            if (Objects.nonNull(result) && result instanceof JobExecutionResult) {
                return (JobExecutionResult) result;
            }
            return JobExecutionResult.failed("-1", "job method not return JobExecutionResult", null);
        } catch (Exception e) {
            return JobExecutionResult.failed("-1", "job method failed", e);
        }
    }
}
