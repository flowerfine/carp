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

package cn.sliew.carp.module.scheduler.api.configuration;

import cn.sliew.carp.module.scheduler.api.annotation.CarpJob;
import cn.sliew.carp.module.scheduler.api.executor.JobHandler;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Set;

public class ClassPathCarpJobScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathCarpJobScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            logger.warn(String.format("No @CarpJob definition was found. Please check your configuration."));
        }
        return beanDefinitions;
    }

    /**
     * Configures parent scanner to search for the right interfaces.
     * It can search just for those annotated with the {@link CarpJob}
     */
    public void registerFilters() {
        addIncludeFilter(new AnnotationTypeFilter(CarpJob.class, true, true));
        addIncludeFilter(new AssignableTypeFilter(JobHandler.class));

        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }
}
