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

import cn.sliew.carp.module.workflow.api.engine.dispatch.publisher.WorkflowStepInstanceEventPublisher;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowStepInstanceEvent;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowStepInstanceState;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowTaskInstance;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowStepInstanceEventDTO;
import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public class InternalWorkflowStepInstanceStateMachine implements InitializingBean {

    public static final String CONSUMER_GROUP = "InternalWorkflowStepInstanceStateMachine";
    public static final String EXECUTOR = "InternalWorkflowStepInstanceExecute";

    private WorkflowStepInstanceEventPublisher publisher;

    private StateMachine<CarpWorkflowStepInstanceState, CarpWorkflowStepInstanceEvent, Triple<WorkflowStepInstance, WorkflowTaskInstance, Throwable>> stateMachine;

    public InternalWorkflowStepInstanceStateMachine(WorkflowStepInstanceEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        StateMachineBuilder<CarpWorkflowStepInstanceState, CarpWorkflowStepInstanceEvent, Triple<WorkflowStepInstance, WorkflowTaskInstance, Throwable>> builder = StateMachineBuilderFactory.create();

        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.PENDING)
                .to(CarpWorkflowStepInstanceState.RUNNING)
                .on(CarpWorkflowStepInstanceEvent.COMMAND_DEPLOY)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.PENDING)
                .to(CarpWorkflowStepInstanceState.SUSPEND)
                .on(CarpWorkflowStepInstanceEvent.COMMAND_SUSPEND)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.PENDING)
                .to(CarpWorkflowStepInstanceState.SHUTDOWN)
                .on(CarpWorkflowStepInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.PENDING)
                .to(CarpWorkflowStepInstanceState.SKIP)
                .on(CarpWorkflowStepInstanceEvent.COMMAND_SKIP)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.PENDING)
                .to(CarpWorkflowStepInstanceState.SKIP)
                .on(CarpWorkflowStepInstanceEvent.PROCESS_SKIP_CAUSE_BY_FAILURE)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.PENDING)
                .to(CarpWorkflowStepInstanceState.SKIP)
                .on(CarpWorkflowStepInstanceEvent.PROCESS_SKIP_CAUSE_BY_SHUTDOWNED)
                .perform(doPerform());

        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.RUNNING)
                .to(CarpWorkflowStepInstanceState.SUCCESS)
                .on(CarpWorkflowStepInstanceEvent.PROCESS_SUCCESS)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.RUNNING)
                .to(CarpWorkflowStepInstanceState.FAILURE)
                .on(CarpWorkflowStepInstanceEvent.PROCESS_FAILURE)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.RUNNING)
                .to(CarpWorkflowStepInstanceState.SUSPEND)
                .on(CarpWorkflowStepInstanceEvent.COMMAND_SUSPEND)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.RUNNING)
                .to(CarpWorkflowStepInstanceState.SHUTDOWN)
                .on(CarpWorkflowStepInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());
        builder.internalTransition()
                .within(CarpWorkflowStepInstanceState.RUNNING)
                .on(CarpWorkflowStepInstanceEvent.PROCESS_TASK_CHANGE)
                .perform(doPerform());

        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.SUSPEND)
                .to(CarpWorkflowStepInstanceState.RUNNING)
                .on(CarpWorkflowStepInstanceEvent.COMMAND_RESUME)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowStepInstanceState.SUSPEND)
                .to(CarpWorkflowStepInstanceState.SHUTDOWN)
                .on(CarpWorkflowStepInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());

        this.stateMachine = builder.build(CONSUMER_GROUP);
    }

    private Action<CarpWorkflowStepInstanceState, CarpWorkflowStepInstanceEvent, Triple<WorkflowStepInstance, WorkflowTaskInstance, Throwable>> doPerform() {
        return (fromState, toState, eventEnum, triple) -> {
            WorkflowStepInstanceEventDTO eventDTO = new WorkflowStepInstanceEventDTO(triple.getLeft(), triple.getMiddle(), fromState, toState, eventEnum, triple.getRight());
            publisher.publish(eventDTO);
        };
    }


    public void deploy(WorkflowStepInstance instance) {
        stateMachine.fireEvent(CarpWorkflowStepInstanceState.of(instance.getStatus()), CarpWorkflowStepInstanceEvent.COMMAND_DEPLOY, Triple.of(instance, null,null));
    }

    public void shutdown(WorkflowStepInstance instance) {
        stateMachine.fireEvent(CarpWorkflowStepInstanceState.of(instance.getStatus()), CarpWorkflowStepInstanceEvent.COMMAND_SHUTDOWN, Triple.of(instance, null,null));
    }

    public void suspend(WorkflowStepInstance instance) {
        stateMachine.fireEvent(CarpWorkflowStepInstanceState.of(instance.getStatus()), CarpWorkflowStepInstanceEvent.COMMAND_SUSPEND, Triple.of(instance, null,null));
    }

    public void resume(WorkflowStepInstance instance) {
        stateMachine.fireEvent(CarpWorkflowStepInstanceState.of(instance.getStatus()), CarpWorkflowStepInstanceEvent.COMMAND_RESUME, Triple.of(instance, null,null));
    }

    public void skip(WorkflowStepInstance instance) {
        stateMachine.fireEvent(CarpWorkflowStepInstanceState.of(instance.getStatus()), CarpWorkflowStepInstanceEvent.COMMAND_RESUME, Triple.of(instance, null,null));
    }

    public void onUpstreamShutdown(WorkflowStepInstance instance) {
        stateMachine.fireEvent(CarpWorkflowStepInstanceState.of(instance.getStatus()), CarpWorkflowStepInstanceEvent.PROCESS_SKIP_CAUSE_BY_SHUTDOWNED,Triple.of(instance, null,null));
    }

    public void onUpstreamFailure(WorkflowStepInstance instance) {
        stateMachine.fireEvent(CarpWorkflowStepInstanceState.of(instance.getStatus()), CarpWorkflowStepInstanceEvent.PROCESS_SKIP_CAUSE_BY_FAILURE, Triple.of(instance, null,null));
    }

    public void onTaskChange(WorkflowStepInstance instance, WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(CarpWorkflowStepInstanceState.of(instance.getStatus()), CarpWorkflowStepInstanceEvent.PROCESS_TASK_CHANGE, Triple.of(instance, taskInstance,null));
    }

    public void onSuccess(WorkflowStepInstance instance) {
        stateMachine.fireEvent(CarpWorkflowStepInstanceState.of(instance.getStatus()), CarpWorkflowStepInstanceEvent.PROCESS_SUCCESS, Triple.of(instance, null,null));
    }

    public void onFailure(WorkflowStepInstance instance, Throwable throwable) {
        stateMachine.fireEvent(CarpWorkflowStepInstanceState.of(instance.getStatus()), CarpWorkflowStepInstanceEvent.PROCESS_FAILURE, Triple.of(instance, null,null));
    }

}
