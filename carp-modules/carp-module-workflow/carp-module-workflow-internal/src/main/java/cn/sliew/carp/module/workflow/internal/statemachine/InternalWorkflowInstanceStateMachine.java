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

import cn.sliew.carp.module.workflow.api.engine.dispatch.publisher.WorkflowInstanceEventPublisher;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowInstanceEvent;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.InternalWorkflowInstanceStatusEvent;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.workflow.InternalWorkflowInstanceStatusEventBuilder;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.workflow.WorkflowInstanceEventDTO;
import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class InternalWorkflowInstanceStateMachine implements InitializingBean {

    public static final String CONSUMER_GROUP = "InternalWorkflowInstanceStateMachine";
    public static final String EXECUTOR = "InternalWorkflowInstanceExecute";

    private WorkflowInstanceEventPublisher publisher;

    private StateMachine<CarpWorkflowInstanceState, CarpWorkflowInstanceEvent, InternalWorkflowInstanceStatusEventBuilder> stateMachine;

    public InternalWorkflowInstanceStateMachine(WorkflowInstanceEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StateMachineBuilder<CarpWorkflowInstanceState, CarpWorkflowInstanceEvent, InternalWorkflowInstanceStatusEventBuilder> builder = StateMachineBuilderFactory.create();

        builder.externalTransition()
                .from(CarpWorkflowInstanceState.PENDING)
                .to(CarpWorkflowInstanceState.PENDING)
                .on(CarpWorkflowInstanceEvent.COMMAND_INIT)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowInstanceState.PENDING)
                .to(CarpWorkflowInstanceState.RUNNING)
                .on(CarpWorkflowInstanceEvent.COMMAND_DEPLOY)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowInstanceState.PENDING)
                .to(CarpWorkflowInstanceState.SUSPEND)
                .on(CarpWorkflowInstanceEvent.COMMAND_SUSPEND)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowInstanceState.PENDING)
                .to(CarpWorkflowInstanceState.SHUTDOWN)
                .on(CarpWorkflowInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowInstanceState.PENDING)
                .to(CarpWorkflowInstanceState.SUCCESS)
                .on(CarpWorkflowInstanceEvent.PROCESS_SUCCESS)
                .perform(doPerform());

        builder.externalTransition()
                .from(CarpWorkflowInstanceState.RUNNING)
                .to(CarpWorkflowInstanceState.SUCCESS)
                .on(CarpWorkflowInstanceEvent.PROCESS_SUCCESS)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowInstanceState.RUNNING)
                .to(CarpWorkflowInstanceState.FAILURE)
                .on(CarpWorkflowInstanceEvent.PROCESS_FAILURE)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowInstanceState.RUNNING)
                .to(CarpWorkflowInstanceState.SUSPEND)
                .on(CarpWorkflowInstanceEvent.COMMAND_SUSPEND)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowInstanceState.RUNNING)
                .to(CarpWorkflowInstanceState.SHUTDOWN)
                .on(CarpWorkflowInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());
        builder.internalTransition()
                .within(CarpWorkflowInstanceState.RUNNING)
                .on(CarpWorkflowInstanceEvent.PROCESS_STEP_CHANGE)
                .perform(doPerform());

        builder.externalTransition()
                .from(CarpWorkflowInstanceState.SUSPEND)
                .to(CarpWorkflowInstanceState.RUNNING)
                .on(CarpWorkflowInstanceEvent.COMMAND_RESUME)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowInstanceState.SUSPEND)
                .to(CarpWorkflowInstanceState.SHUTDOWN)
                .on(CarpWorkflowInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());

        this.stateMachine = builder.build(CONSUMER_GROUP);
    }

    private Action<CarpWorkflowInstanceState, CarpWorkflowInstanceEvent, InternalWorkflowInstanceStatusEventBuilder> doPerform() {
        return (fromState, toState, eventEnum, builder) -> {
            InternalWorkflowInstanceStatusEvent eventDTO = builder.build(fromState, toState, eventEnum);
            publisher.publish(eventDTO);
        };
    }

    public void init(InternalWorkflowInstanceStatusEventBuilder builder) {
        stateMachine.fireEvent(CarpWorkflowInstanceState.PENDING, CarpWorkflowInstanceEvent.COMMAND_INIT, builder);
    }

    public void deploy(WorkflowInstance instance) {
        InternalWorkflowInstanceStatusEventBuilder builder =
                (state, nextState, event) ->
                        new WorkflowInstanceEventDTO(state, nextState, event, instance);
        stateMachine.fireEvent(CarpWorkflowInstanceState.of(instance.getStatus()), CarpWorkflowInstanceEvent.COMMAND_DEPLOY, builder);
    }

    public void shutdown(WorkflowInstance instance) {
        InternalWorkflowInstanceStatusEventBuilder builder =
                (state, nextState, event) ->
                        new WorkflowInstanceEventDTO(state, nextState, event, instance);
        stateMachine.fireEvent(CarpWorkflowInstanceState.of(instance.getStatus()), CarpWorkflowInstanceEvent.COMMAND_SHUTDOWN, builder);
    }

    public void suspend(WorkflowInstance instance) {
        InternalWorkflowInstanceStatusEventBuilder builder =
                (state, nextState, event) ->
                        new WorkflowInstanceEventDTO(state, nextState, event, instance);
        stateMachine.fireEvent(CarpWorkflowInstanceState.of(instance.getStatus()), CarpWorkflowInstanceEvent.COMMAND_SUSPEND, builder);
    }

    public void resume(WorkflowInstance instance) {
        InternalWorkflowInstanceStatusEventBuilder builder =
                (state, nextState, event) ->
                        new WorkflowInstanceEventDTO(state, nextState, event, instance);
        stateMachine.fireEvent(CarpWorkflowInstanceState.of(instance.getStatus()), CarpWorkflowInstanceEvent.COMMAND_RESUME, builder);
    }

    public void onStepChange(WorkflowInstance instance) {
        InternalWorkflowInstanceStatusEventBuilder builder =
                (state, nextState, event) ->
                        new WorkflowInstanceEventDTO(state, nextState, event, instance);
        stateMachine.fireEvent(CarpWorkflowInstanceState.of(instance.getStatus()), CarpWorkflowInstanceEvent.PROCESS_STEP_CHANGE, builder);
    }

    public void onSuccess(WorkflowInstance instance) {
        InternalWorkflowInstanceStatusEventBuilder builder =
                (state, nextState, event) ->
                        new WorkflowInstanceEventDTO(state, nextState, event, instance);
        stateMachine.fireEvent(CarpWorkflowInstanceState.of(instance.getStatus()), CarpWorkflowInstanceEvent.PROCESS_SUCCESS, builder);
    }

    public void onFailure(WorkflowInstance instance, Throwable throwable) {
        InternalWorkflowInstanceStatusEventBuilder builder =
                (state, nextState, event) ->
                        new WorkflowInstanceEventDTO(state, nextState, event, instance, throwable);
        stateMachine.fireEvent(CarpWorkflowInstanceState.of(instance.getStatus()), CarpWorkflowInstanceEvent.PROCESS_FAILURE, builder);
    }
}
