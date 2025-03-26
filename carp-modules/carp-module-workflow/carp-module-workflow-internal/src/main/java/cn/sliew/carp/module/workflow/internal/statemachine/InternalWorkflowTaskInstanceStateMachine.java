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
package cn.sliew.carp.module.workflow.internal.statemachine;

import cn.sliew.carp.module.workflow.api.engine.dispatch.publisher.WorkflowTaskInstanceEventPublisher;
import cn.sliew.carp.module.workflow.api.enums.CarpWorkflowTaskInstanceEvent;
import cn.sliew.carp.module.workflow.api.enums.CarpWorkflowTaskInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowTaskInstance;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowTaskInstanceEventDTO;
import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class InternalWorkflowTaskInstanceStateMachine implements InitializingBean {

    public static final String CONSUMER_GROUP = "InternalWorkflowTaskInstanceStateMachine";
    public static final String EXECUTOR = "InternalWorkflowTaskInstanceExecute";

    private WorkflowTaskInstanceEventPublisher publisher;

    private StateMachine<CarpWorkflowTaskInstanceState, CarpWorkflowTaskInstanceEvent, Triple<WorkflowStepInstance, WorkflowTaskInstance, Throwable>> stateMachine;

    public InternalWorkflowTaskInstanceStateMachine(WorkflowTaskInstanceEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StateMachineBuilder<CarpWorkflowTaskInstanceState, CarpWorkflowTaskInstanceEvent, Triple<WorkflowStepInstance, WorkflowTaskInstance, Throwable>> builder = StateMachineBuilderFactory.create();

        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceState.PENDING)
                .to(CarpWorkflowTaskInstanceState.RUNNING)
                .on(CarpWorkflowTaskInstanceEvent.COMMAND_DEPLOY)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceState.PENDING)
                .to(CarpWorkflowTaskInstanceState.SHUTDOWN)
                .on(CarpWorkflowTaskInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());

        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceState.RUNNING)
                .to(CarpWorkflowTaskInstanceState.SUCCESS)
                .on(CarpWorkflowTaskInstanceEvent.PROCESS_SUCCESS)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceState.RUNNING)
                .to(CarpWorkflowTaskInstanceState.FAILURE)
                .on(CarpWorkflowTaskInstanceEvent.PROCESS_FAILURE)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceState.RUNNING)
                .to(CarpWorkflowTaskInstanceState.SHUTDOWN)
                .on(CarpWorkflowTaskInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());

        this.stateMachine = builder.build(CONSUMER_GROUP);
    }

    private Action<CarpWorkflowTaskInstanceState, CarpWorkflowTaskInstanceEvent, Triple<WorkflowStepInstance, WorkflowTaskInstance, Throwable>> doPerform() {
        return (fromState, toState, eventEnum, triple) -> {
            WorkflowTaskInstanceEventDTO eventDTO = new WorkflowTaskInstanceEventDTO(triple.getLeft(), triple.getMiddle(), fromState, toState, eventEnum, triple.getRight());
            publisher.publish(eventDTO);
        };
    }

    public void deploy(WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(CarpWorkflowTaskInstanceState.of(taskInstance.getStatus()), CarpWorkflowTaskInstanceEvent.COMMAND_DEPLOY, Triple.of(stepInstance, taskInstance, null));
    }

    public void shutdown(WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(CarpWorkflowTaskInstanceState.of(taskInstance.getStatus()), CarpWorkflowTaskInstanceEvent.COMMAND_SHUTDOWN, Triple.of(stepInstance, taskInstance, null));
    }

    public void onSuccess(WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(CarpWorkflowTaskInstanceState.of(taskInstance.getStatus()), CarpWorkflowTaskInstanceEvent.PROCESS_SUCCESS, Triple.of(stepInstance, taskInstance, null));
    }

    public void onFailure(WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance, Throwable throwable) {
        stateMachine.fireEvent(CarpWorkflowTaskInstanceState.of(taskInstance.getStatus()), CarpWorkflowTaskInstanceEvent.PROCESS_FAILURE, Triple.of(stepInstance, taskInstance, throwable));
    }
}
