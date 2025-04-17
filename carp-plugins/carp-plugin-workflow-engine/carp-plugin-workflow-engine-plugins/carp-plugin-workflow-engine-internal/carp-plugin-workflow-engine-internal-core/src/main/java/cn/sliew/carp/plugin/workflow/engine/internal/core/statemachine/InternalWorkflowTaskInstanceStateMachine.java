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
package cn.sliew.carp.plugin.workflow.engine.internal.core.statemachine;

import cn.sliew.carp.plugin.workflow.engine.internal.api.engine.dispatch.publisher.WorkflowTaskInstanceEventPublisher;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.enums.CarpWorkflowTaskInstanceEvent;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.enums.CarpWorkflowTaskInstanceState;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.plugin.workflow.engine.internal.domain.instance.WorkflowTaskInstance;
import cn.sliew.carp.plugin.workflow.engine.internal.core.engine.dispatch.event.InternalWorkflowTaskInstanceStatusEvent;
import cn.sliew.carp.plugin.workflow.engine.internal.core.engine.dispatch.event.task.FailureTaskDTO;
import cn.sliew.carp.plugin.workflow.engine.internal.core.engine.dispatch.event.task.InternalWorkflowTaskInstanceStatusEventBuilder;
import cn.sliew.carp.plugin.workflow.engine.internal.core.engine.dispatch.event.task.WorkflowTaskInstanceEventDTO;
import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class InternalWorkflowTaskInstanceStateMachine implements InitializingBean {

    public static final String CONSUMER_GROUP = "InternalWorkflowTaskInstanceStateMachine";
    public static final String EXECUTOR = "InternalWorkflowTaskInstanceExecute";

    private WorkflowTaskInstanceEventPublisher publisher;

    private StateMachine<CarpWorkflowTaskInstanceState, CarpWorkflowTaskInstanceEvent, InternalWorkflowTaskInstanceStatusEventBuilder> stateMachine;

    public InternalWorkflowTaskInstanceStateMachine(WorkflowTaskInstanceEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StateMachineBuilder<CarpWorkflowTaskInstanceState, CarpWorkflowTaskInstanceEvent, InternalWorkflowTaskInstanceStatusEventBuilder> builder = StateMachineBuilderFactory.create();

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

        builder.internalTransition()
                .within(CarpWorkflowTaskInstanceState.RUNNING)
                .on(CarpWorkflowTaskInstanceEvent.COMMAND_RUN)
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
        log.debug("Cola StateMachine: {}\n{}", stateMachine.getMachineId(), stateMachine.accept(new DAGMermaidVisitor()));
    }

    private Action<CarpWorkflowTaskInstanceState, CarpWorkflowTaskInstanceEvent, InternalWorkflowTaskInstanceStatusEventBuilder> doPerform() {
        return (fromState, toState, eventEnum, builder) -> {
            InternalWorkflowTaskInstanceStatusEvent eventDTO = builder.build(fromState, toState, eventEnum);
            publisher.publish(eventDTO);
        };
    }

    public void deploy(WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance) {
        InternalWorkflowTaskInstanceStatusEventBuilder builder =
                (fromState, toState, eventEnum) ->
                        new WorkflowTaskInstanceEventDTO(fromState, toState, eventEnum, stepInstance, taskInstance);
        stateMachine.fireEvent(CarpWorkflowTaskInstanceState.of(taskInstance.getStatus()), CarpWorkflowTaskInstanceEvent.COMMAND_DEPLOY, builder);
    }

    public void run(CarpWorkflowTaskInstanceState state, InternalWorkflowTaskInstanceStatusEventBuilder builder) {
        stateMachine.fireEvent(state, CarpWorkflowTaskInstanceEvent.COMMAND_RUN, builder);
    }

    public void shutdown(WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance) {
        InternalWorkflowTaskInstanceStatusEventBuilder builder =
                (fromState, toState, eventEnum) ->
                        new WorkflowTaskInstanceEventDTO(fromState, toState, eventEnum, stepInstance, taskInstance);
        stateMachine.fireEvent(CarpWorkflowTaskInstanceState.of(taskInstance.getStatus()), CarpWorkflowTaskInstanceEvent.COMMAND_SHUTDOWN, builder);
    }

    public void onSuccess(WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance) {
        InternalWorkflowTaskInstanceStatusEventBuilder builder =
                (fromState, toState, eventEnum) ->
                        new WorkflowTaskInstanceEventDTO(fromState, toState, eventEnum, stepInstance, taskInstance);
        stateMachine.fireEvent(CarpWorkflowTaskInstanceState.of(taskInstance.getStatus()), CarpWorkflowTaskInstanceEvent.PROCESS_SUCCESS, builder);
    }

    public void onFailure(WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance, Throwable throwable) {
        InternalWorkflowTaskInstanceStatusEventBuilder builder =
                (fromState, toState, eventEnum) ->
                        new FailureTaskDTO(fromState, toState, eventEnum, stepInstance, taskInstance, throwable);
        stateMachine.fireEvent(CarpWorkflowTaskInstanceState.of(taskInstance.getStatus()), CarpWorkflowTaskInstanceEvent.PROCESS_FAILURE, builder);
    }
}
