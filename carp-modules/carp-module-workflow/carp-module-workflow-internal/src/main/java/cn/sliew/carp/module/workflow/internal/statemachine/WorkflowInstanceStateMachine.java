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

import cn.sliew.carp.framework.common.dict.workflow.WorkflowInstanceEvent;
import cn.sliew.carp.framework.common.dict.workflow.WorkflowInstanceState;
import cn.sliew.carp.module.workflow.api.engine.dispatch.publisher.WorkflowInstanceEventPublisher;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowInstanceEventDTO;
import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkflowInstanceStateMachine implements InitializingBean {

    public static final String CONSUMER_GROUP = "WorkflowInstanceStateMachine";
    public static final String EXECUTOR = "WorkflowInstanceExecute";

    @Autowired
    private WorkflowInstanceEventPublisher publisher;

    private StateMachine<WorkflowInstanceState, WorkflowInstanceEvent, Pair<Long, Throwable>> stateMachine;

    @Override
    public void afterPropertiesSet() throws Exception {
        StateMachineBuilder<WorkflowInstanceState, WorkflowInstanceEvent, Pair<Long, Throwable>> builder = StateMachineBuilderFactory.create();

        builder.externalTransition()
                .from(WorkflowInstanceState.PENDING)
                .to(WorkflowInstanceState.RUNNING)
                .on(WorkflowInstanceEvent.COMMAND_DEPLOY)
                .perform(doPerform());
        builder.externalTransition()
                .from(WorkflowInstanceState.PENDING)
                .to(WorkflowInstanceState.SUCCESS)
                .on(WorkflowInstanceEvent.PROCESS_SUCCESS)
                .perform(doPerform());

        builder.internalTransition()
                .within(WorkflowInstanceState.RUNNING)
                .on(WorkflowInstanceEvent.PROCESS_TASK_CHANGE)
                .perform(doPerform());
        builder.externalTransition()
                .from(WorkflowInstanceState.RUNNING)
                .to(WorkflowInstanceState.SUCCESS)
                .on(WorkflowInstanceEvent.PROCESS_SUCCESS)
                .perform(doPerform());
        builder.externalTransition()
                .from(WorkflowInstanceState.RUNNING)
                .to(WorkflowInstanceState.FAILURE)
                .on(WorkflowInstanceEvent.PROCESS_FAILURE)
                .perform(doPerform());
        builder.externalTransition()
                .from(WorkflowInstanceState.RUNNING)
                .to(WorkflowInstanceState.SUSPEND)
                .on(WorkflowInstanceEvent.COMMAND_SUSPEND)
                .perform(doPerform());
        builder.externalTransition()
                .from(WorkflowInstanceState.RUNNING)
                .to(WorkflowInstanceState.TERMINATED)
                .on(WorkflowInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());

        builder.externalTransition()
                .from(WorkflowInstanceState.SUSPEND)
                .to(WorkflowInstanceState.RUNNING)
                .on(WorkflowInstanceEvent.COMMAND_RESUME)
                .perform(doPerform());
        builder.externalTransition()
                .from(WorkflowInstanceState.SUSPEND)
                .to(WorkflowInstanceState.TERMINATED)
                .on(WorkflowInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());

        this.stateMachine = builder.build(CONSUMER_GROUP);
    }

    private Action<WorkflowInstanceState, WorkflowInstanceEvent, Pair<Long, Throwable>> doPerform() {
        return (fromState, toState, eventEnum, pair) -> {
            WorkflowInstanceEventDTO eventDTO = new WorkflowInstanceEventDTO(fromState, toState, eventEnum, pair.getLeft(), pair.getRight());
            publisher.publish(eventDTO);
        };
    }


    public void deploy(WorkflowInstance instance) {
        stateMachine.fireEvent(instance.getStatus(), WorkflowInstanceEvent.COMMAND_DEPLOY, Pair.of(instance.getId(), null));
    }

    public void shutdown(WorkflowInstance instance) {
        stateMachine.fireEvent(instance.getStatus(), WorkflowInstanceEvent.COMMAND_SHUTDOWN, Pair.of(instance.getId(), null));
    }

    public void suspend(WorkflowInstance instance) {
        stateMachine.fireEvent(instance.getStatus(), WorkflowInstanceEvent.COMMAND_SUSPEND, Pair.of(instance.getId(), null));
    }

    public void resume(WorkflowInstance instance) {
        stateMachine.fireEvent(instance.getStatus(), WorkflowInstanceEvent.COMMAND_RESUME, Pair.of(instance.getId(), null));
    }

    public void onTaskChange(WorkflowInstance instance) {
        stateMachine.fireEvent(instance.getStatus(), WorkflowInstanceEvent.PROCESS_TASK_CHANGE, Pair.of(instance.getId(), null));
    }

    public void onSuccess(WorkflowInstance instance) {
        stateMachine.fireEvent(instance.getStatus(), WorkflowInstanceEvent.PROCESS_SUCCESS, Pair.of(instance.getId(), null));
    }

    public void onFailure(WorkflowInstance instance, Throwable throwable) {
        stateMachine.fireEvent(instance.getStatus(), WorkflowInstanceEvent.PROCESS_FAILURE, Pair.of(instance.getId(), throwable));
    }
}
