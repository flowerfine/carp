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

package cn.sliew.carp.module.workflow.internal.executor;

import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowExecuteType;
import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowTaskInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class WorkflowInstanceExecutorManager implements InitializingBean, DisposableBean {

    @Autowired
    private List<WorkflowInstanceExecutor> executors;

    private Map<CarpWorkflowExecuteType, WorkflowInstanceExecutor> registry = new HashMap<>();
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(executors) == false) {
            executors.stream().forEach(handler -> registry.put(handler.getExecuteType(), handler));
        }
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(5);
        executor.setCorePoolSize(1);
        executor.setThreadNamePrefix("workflow-instance-execute-thread-pool-");
        executor.initialize();
        taskExecutor = executor;
    }

    @Override
    public void destroy() throws Exception {
        if (taskExecutor != null) {
            taskExecutor.shutdown();
        }
    }

    public CompletableFuture execute(CarpWorkflowExecuteType executeType, WorkflowInstance instance, DAG<WorkflowTaskInstance> dag) {
        if (registry.containsKey(executeType) == false) {
            throw new RuntimeException("unknown workflow instance execute type: "
                    + executeType.getLabel() + "[" + executeType.getValue() + "]");
        }

        WorkflowInstanceExecutor handler = registry.get(executeType);
        return CompletableFuture.runAsync(() -> handler.execute(instance, dag), taskExecutor)
                .whenComplete((unused, throwable) -> {
                    if (throwable != null) {
                        log.error("workflow instance execute failed", throwable);
                    }
                });
    }
}
