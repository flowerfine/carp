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

package cn.sliew.carp.module.scheduler.api.executor.handler.method;

import cn.hutool.extra.spring.SpringUtil;
import cn.sliew.carp.framework.common.dict.schedule.ScheduleExecuteType;
import cn.sliew.carp.module.scheduler.api.annotation.CarpJob;
import cn.sliew.carp.module.scheduler.api.annotation.CarpJobHandler;
import cn.sliew.carp.module.scheduler.api.executor.AbstractJobHandlerFactory;
import cn.sliew.carp.module.scheduler.api.executor.JobContext;
import cn.sliew.carp.module.scheduler.api.executor.JobHandler;
import cn.sliew.carp.module.scheduler.api.executor.JobHandlerFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class MethodJobhandlerFactory extends AbstractJobHandlerFactory implements JobHandlerFactory, SmartInitializingSingleton {

    private ConcurrentMap<String, CarpJobMethod> registry = new ConcurrentHashMap<>();

    @Override
    public String getType() {
        return ScheduleExecuteType.METHOD.getValue();
    }

    @Override
    protected JobHandler create(String jobHandler) {
        if (registry.containsKey(jobHandler) == false) {
            throw new IllegalStateException("Unknown job handler: " + jobHandler);
        }
        CarpJobMethod carpJobMethod = registry.get(jobHandler);
        Object bean = carpJobMethod.getBean();
        Method method = carpJobMethod.getMethod();
        CarpJobHandler carpJobHandler = carpJobMethod.getCarpJobHandler();

        // todo 校验方法的返回参数是否是 JobExecutionResult
        Method initMethod = ReflectionUtils.findMethod(bean.getClass(), carpJobHandler.initMethod(), JobContext.class);
        if (Objects.nonNull(initMethod)) {
            ReflectionUtils.makeAccessible(initMethod);
        }
        Method destroyMethod = ReflectionUtils.findMethod(bean.getClass(), carpJobHandler.destroyMethod(), JobContext.class);
        if (Objects.nonNull(destroyMethod)) {
            ReflectionUtils.makeAccessible(destroyMethod);
        }
        return new MethodJobHandler(bean, method, initMethod, destroyMethod);
    }

    @Override
    public void afterSingletonsInstantiated() {
        scanBean();
    }

    private void scanBean() {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        // xxl-job scan too many beans spend too much time
//        String[] beanNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        // carp 增加了 @CarpJob。事先扫描 @CarpJob 类或 JobHandler 接口至 spring applicationcontext 中
        // 相比 xxl-job 增加了限制，类上必须添加 @CarpJob 方法
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(CarpJob.class);
        for (String beanName : beanNames) {
            Object bean = null;
            Lazy onBean = applicationContext.findAnnotationOnBean(beanName, Lazy.class);
            if (onBean != null) {
                log.debug("@CarpJobHandler annotation scan, skip @Lazy bean: {}", beanName);
            } else {
                bean = applicationContext.getBean(beanName);
            }
            if (Objects.nonNull(bean)) {
                findCarpJob(bean, beanName);
            }
        }
    }

    private void findCarpJob(Object bean, String beanName) {
        Map<Method, CarpJobHandler> annotatedMethods = null;
        try {
            annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                    new MethodIntrospector.MetadataLookup<CarpJobHandler>() {
                        @Override
                        public CarpJobHandler inspect(Method method) {
                            return AnnotatedElementUtils.findMergedAnnotation(method, CarpJobHandler.class);
                        }
                    });
        } catch (Throwable ex) {
            log.error("@CarpJobHandler method jobhandler resolve error for bean: {}", beanName, ex);
        }

        if (CollectionUtils.isEmpty(annotatedMethods) == false) {
            for (Map.Entry<Method, CarpJobHandler> entry : annotatedMethods.entrySet()) {
                register(beanName, bean, entry.getKey(), entry.getValue());
            }
        }
    }

    private void register(String beanName, Object bean, Method method, CarpJobHandler carpJobHandler) {
        if (registry.containsKey(carpJobHandler.value())) {
            throw new IllegalStateException("@CarpJobHandler annotation already exists for job: " + carpJobHandler.value() + " on bean: " + beanName);
        }
        registry.put(carpJobHandler.value(), new CarpJobMethod(bean, method, carpJobHandler));
    }

    @Getter
    @AllArgsConstructor
    private static class CarpJobMethod {
        private final Object bean;
        private final Method method;
        private final CarpJobHandler carpJobHandler;
    }

}
