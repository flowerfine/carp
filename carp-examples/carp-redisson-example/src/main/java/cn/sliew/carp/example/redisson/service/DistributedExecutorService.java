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

import cn.sliew.carp.example.redisson.service.listener.TaskFailureListenerImpl;
import cn.sliew.carp.example.redisson.service.listener.TaskFinishedListenerImpl;
import cn.sliew.carp.example.redisson.service.listener.TaskStartedListenerImpl;
import cn.sliew.carp.example.redisson.service.listener.TaskSuccessListenerImpl;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.redisson.api.WorkerOptions;
import org.redisson.api.options.ExecutorOptions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
}
