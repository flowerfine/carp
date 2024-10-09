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

package cn.sliew.carp.example.redisson.service;

import cn.sliew.carp.example.redisson.service.job.EchoCallable;
import cn.sliew.carp.example.redisson.service.job.EchoRunner;
import cn.sliew.carp.example.redisson.service.listener.TaskFailureListenerImpl;
import cn.sliew.carp.example.redisson.service.listener.TaskFinishedListenerImpl;
import cn.sliew.carp.example.redisson.service.listener.TaskStartedListenerImpl;
import cn.sliew.carp.example.redisson.service.listener.TaskSuccessListenerImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.redisson.api.options.ExecutorOptions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DistributedExecutorService implements InitializingBean, BeanFactoryAware {

    @Autowired
    private RedissonClient client;
    private BeanFactory beanFactory;

    private RScheduledExecutorService executorService;

    @Override
    public void afterPropertiesSet() throws Exception {
        ExecutorOptions executorOptions = ExecutorOptions.name("");
        executorService = client.getExecutorService(executorOptions);

        WorkerOptions workerOptions = WorkerOptions.defaults()
                .workers(2)
                .beanFactory(beanFactory)
                .taskTimeout(60, TimeUnit.SECONDS)
                .addListener(new TaskSuccessListenerImpl())
                .addListener(new TaskFailureListenerImpl())
                .addListener(new TaskStartedListenerImpl())
                .addListener(new TaskFinishedListenerImpl());
        executorService.registerWorkers(workerOptions);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public Boolean cancel(String taskId) {
        return executorService.cancelTask(taskId);
    }

    public String submitCallable(String input) throws ExecutionException, InterruptedException {
        RExecutorFuture<String> future = executorService.submit(new EchoCallable(input));
        String taskId = future.getTaskId();
        future.toCompletableFuture().whenComplete((result, throwable) -> {
            log.info("异步执行, taskId: {}, input: {}, result: {}", taskId, input, result);
        });
        return future.get();
    }

    public void scheduleRunnable(String input) {
        RScheduledFuture<?> future = executorService.scheduleWithFixedDelay(new EchoRunner(input), 0, 10, TimeUnit.SECONDS);
        String taskId = future.getTaskId();
        // 要转成 CompletableFuture 才正常运行
        future.toCompletableFuture().whenComplete((result, throwable) -> {
            log.info("异步执行, taskId: {}, input: {}, result: {}", taskId, input, result);
        });
    }

    public void scheduleLambda(String input) {
        // lambda 不支持传参数
        // 也能看出 redisson 任务必须实现 Serializable 接口
        RScheduledFuture<?> future = executorService.scheduleWithFixedDelay((Runnable & Serializable)() -> {
            log.info("execute");
        }, 0, 10, TimeUnit.SECONDS);
        String taskId = future.getTaskId();
        future.toCompletableFuture().whenComplete((result, throwable) -> {
            log.info("异步执行, taskId: {}, input: {}, result: {}", taskId, input, result);
        });
    }
}
