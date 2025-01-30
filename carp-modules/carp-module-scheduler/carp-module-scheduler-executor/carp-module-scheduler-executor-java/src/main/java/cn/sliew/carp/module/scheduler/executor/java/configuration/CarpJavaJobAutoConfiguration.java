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
package cn.sliew.carp.module.scheduler.executor.java.configuration;

import cn.sliew.carp.module.scheduler.executor.api.executor.DefaultJobThreadRepository;
import cn.sliew.carp.module.scheduler.executor.api.executor.InMemoryJobHandlerFactoryRegistry;
import cn.sliew.carp.module.scheduler.executor.api.executor.JobExecutor;
import cn.sliew.carp.module.scheduler.executor.api.executor.JobHandlerFactoryRegistry;
import cn.sliew.carp.module.scheduler.executor.java.JavaJobExecutor;
import cn.sliew.carp.module.scheduler.executor.java.annotation.CarpJob;
import cn.sliew.carp.module.scheduler.executor.java.handler.bean.BeanJobHandlerFactory;
import cn.sliew.carp.module.scheduler.executor.java.handler.method.MethodJobhandlerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

@ConditionalOnClass(CarpJob.class)
public class CarpJavaJobAutoConfiguration {

    @Bean
    public MethodJobhandlerFactory methodJobhandlerFactory() {
        return new MethodJobhandlerFactory();
    }

    @Bean
    public BeanJobHandlerFactory beanJobHandlerFactory() {
        return new BeanJobHandlerFactory();
    }

    @Bean
    public JobHandlerFactoryRegistry javaJobHandlerFactoryRegistry(MethodJobhandlerFactory methodJobhandlerFactory, BeanJobHandlerFactory beanJobHandlerFactory) {
        JobHandlerFactoryRegistry registry = new InMemoryJobHandlerFactoryRegistry();
        registry.put(methodJobhandlerFactory.getType(), methodJobhandlerFactory);
        registry.put(beanJobHandlerFactory.getType(), beanJobHandlerFactory);
        return registry;
    }

    @Bean
    public JobExecutor javaJobExecutor(JobHandlerFactoryRegistry javaJobHandlerFactoryRegistry) {
        return new JavaJobExecutor(javaJobHandlerFactoryRegistry, new DefaultJobThreadRepository());
    }

}
