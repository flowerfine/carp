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
package cn.sliew.carp.module.dag.queue.handler;

import cn.sliew.carp.framework.common.serder.SerDer;
import cn.sliew.carp.framework.common.serder.jdk.JdkSerDerFactory;
import cn.sliew.carp.framework.exception.ExceptionHandler;
import cn.sliew.carp.framework.exception.ExceptionVO;
import cn.sliew.carp.module.dag.dispatch.InternalDagInstanceDispatcher;
import cn.sliew.carp.module.queue.api.Message;
import cn.sliew.carp.module.queue.api.Queue;
import cn.sliew.carp.module.queue.api.QueueFactory;
import cn.sliew.carp.module.workflow.stage.model.repository.WorkflowRepository;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.redisson.api.WorkerOptions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

public abstract class AbstractDagMessageHandler<M> implements DagMessageHandler<M>, InitializingBean, BeanFactoryAware {

    private BeanFactory beanFactory;
    protected RScheduledExecutorService executorService;

    @Autowired
    private WorkflowRepository workflowRepository;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private QueueFactory queueFactory;
    @Autowired(required = false)
    private List<ExceptionHandler> exceptionHandlers;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService = redissonClient.getExecutorService("dag-message-handler");
        executorService.registerWorkers(WorkerOptions.defaults().workers(20).beanFactory(beanFactory));
    }

    @Override
    public void push(Object event, Duration delay) {
        Queue queue = queueFactory.get(InternalDagInstanceDispatcher.TOPIC);
        SerDer serDer = JdkSerDerFactory.INSTANCE.getInstance();
        Message message = Message.builder()
                .topic(queue.getName())
                .body(serDer.serialize(event))
                .build();
        queue.push(message, delay);
    }

    @Override
    public ExceptionVO handleException(String name, Exception e) {
        return exceptionHandlers.stream()
                .filter(handler -> handler.support(e))
                .findFirst()
                .map(handler -> handler.handle(name != null ? name : "unspecified", e))
                .orElse(null);
    }

    @Override
    public WorkflowRepository getWorkflowRepository() {
        return workflowRepository;
    }
}
