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
package cn.sliew.carp.module.workflow.internal.configuration;

import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.DagLinkService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.module.queue.api.QueueFactory;
import cn.sliew.carp.module.workflow.api.engine.dispatch.publisher.WorkflowInstanceEventPublisher;
import cn.sliew.carp.module.workflow.api.engine.dispatch.publisher.WorkflowStepInstanceEventPublisher;
import cn.sliew.carp.module.workflow.api.engine.dispatch.publisher.WorkflowTaskInstanceEventPublisher;
import cn.sliew.carp.module.workflow.api.manager.WorkflowInstanceManager;
import cn.sliew.carp.module.workflow.api.manager.WorkflowStepInstanceManager;
import cn.sliew.carp.module.workflow.api.manager.WorkflowTaskInstanceManager;
import cn.sliew.carp.module.workflow.api.service.WorkflowInstanceService;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.publisher.InternalWorkflowInstanceEventPublisher;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.publisher.InternalWorkflowStepInstanceEventPublisher;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.publisher.InternalWorkflowTaskInstanceEventPublisher;
import cn.sliew.carp.module.workflow.internal.manager.InternalWorkflowInstanceManager;
import cn.sliew.carp.module.workflow.internal.manager.InternalWorkflowStepInstanceManager;
import cn.sliew.carp.module.workflow.internal.manager.InternalWorkflowTaskInstanceManager;
import cn.sliew.carp.module.workflow.internal.statemachine.InternalWorkflowInstanceStateMachine;
import cn.sliew.carp.module.workflow.internal.statemachine.InternalWorkflowStepInstanceStateMachine;
import cn.sliew.carp.module.workflow.internal.statemachine.InternalWorkflowTaskInstanceStateMachine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class InternalWorkflowRuntimeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(WorkflowInstanceEventPublisher.class)
    public InternalWorkflowInstanceEventPublisher workflowInstanceEventPublisher(QueueFactory queueFactory) {
        return new InternalWorkflowInstanceEventPublisher(queueFactory);
    }

    @Bean
    @ConditionalOnMissingBean(WorkflowStepInstanceEventPublisher.class)
    public InternalWorkflowStepInstanceEventPublisher workflowStepInstanceEventPublisher(QueueFactory queueFactory) {
        return new InternalWorkflowStepInstanceEventPublisher(queueFactory);
    }

    @Bean
    @ConditionalOnMissingBean(WorkflowTaskInstanceEventPublisher.class)
    public InternalWorkflowTaskInstanceEventPublisher workflowTaskInstanceEventPublisher(QueueFactory queueFactory) {
        return new InternalWorkflowTaskInstanceEventPublisher(queueFactory);
    }

    @Bean
    public InternalWorkflowInstanceStateMachine internalWorkflowInstanceStateMachine(WorkflowInstanceEventPublisher publisher) {
        return new InternalWorkflowInstanceStateMachine(publisher);
    }

    @Bean
    public InternalWorkflowStepInstanceStateMachine internalWorkflowStepInstanceStateMachine(WorkflowStepInstanceEventPublisher publisher) {
        return new InternalWorkflowStepInstanceStateMachine(publisher);
    }

    @Bean
    public InternalWorkflowTaskInstanceStateMachine internalWorkflowTaskInstanceStateMachine(WorkflowTaskInstanceEventPublisher publisher) {
        return new InternalWorkflowTaskInstanceStateMachine(publisher);
    }

    @Bean
    @ConditionalOnMissingBean(WorkflowInstanceManager.class)
    public InternalWorkflowInstanceManager internalWorkflowInstanceManager(
            DagInstanceService dagInstanceService,
            DagStepService dagStepService,
            DagLinkService dagLinkService,
            WorkflowInstanceService workflowInstanceService,
            InternalWorkflowInstanceStateMachine stateMachine) {
        return new InternalWorkflowInstanceManager(
                dagInstanceService, dagStepService, dagLinkService, workflowInstanceService, stateMachine);
    }

    @Bean
    @ConditionalOnMissingBean(WorkflowStepInstanceManager.class)
    public InternalWorkflowStepInstanceManager internalWorkflowStepInstanceManager(
            WorkflowInstanceService workflowInstanceService,
            InternalWorkflowStepInstanceStateMachine stateMachine) {
        return new InternalWorkflowStepInstanceManager(workflowInstanceService, stateMachine);
    }

    @Bean
    @ConditionalOnMissingBean(WorkflowTaskInstanceManager.class)
    public InternalWorkflowTaskInstanceManager internalWorkflowTaskInstanceManager(
            WorkflowInstanceService workflowInstanceService,
            InternalWorkflowTaskInstanceStateMachine stateMachine) {
        return new InternalWorkflowTaskInstanceManager(workflowInstanceService, stateMachine);
    }
}
