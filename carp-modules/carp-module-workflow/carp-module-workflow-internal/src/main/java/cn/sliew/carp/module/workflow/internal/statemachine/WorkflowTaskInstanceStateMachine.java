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

import cn.sliew.carp.framework.common.dict.workflow.WorkflowTaskInstanceEvent;
import cn.sliew.carp.framework.common.dict.workflow.WorkflowTaskInstanceStage;
import cn.sliew.carp.module.workflow.api.engine.domain.instance.WorkflowTaskInstance;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowTaskInstanceEventDTO;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.publisher.InternalWorkflowTaskInstanceEventPublisher;
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
public class WorkflowTaskInstanceStateMachine implements InitializingBean {

    public static final String CONSUMER_GROUP = "WorkflowTaskInstanceStateMachine";
    public static final String EXECUTOR = "WorkflowTaskInstanceExecute";

    @Autowired
    private InternalWorkflowTaskInstanceEventPublisher statusPublisher;

    private StateMachine<WorkflowTaskInstanceStage, WorkflowTaskInstanceEvent, Pair<Long, Throwable>> stateMachine;

    @Override
    public void afterPropertiesSet() throws Exception {
        StateMachineBuilder<WorkflowTaskInstanceStage, WorkflowTaskInstanceEvent, Pair<Long, Throwable>> builder = StateMachineBuilderFactory.create();

        builder.externalTransition()
                .from(WorkflowTaskInstanceStage.PENDING)
                .to(WorkflowTaskInstanceStage.RUNNING)
                .on(WorkflowTaskInstanceEvent.COMMAND_DEPLOY)
                .perform(doPerform());

        builder.externalTransition()
                .from(WorkflowTaskInstanceStage.RUNNING)
                .to(WorkflowTaskInstanceStage.SUCCESS)
                .on(WorkflowTaskInstanceEvent.PROCESS_SUCCESS)
                .perform(doPerform());
        builder.externalTransition()
                .from(WorkflowTaskInstanceStage.RUNNING)
                .to(WorkflowTaskInstanceStage.FAILURE)
                .on(WorkflowTaskInstanceEvent.PROCESS_FAILURE)
                .perform(doPerform());
        builder.externalTransition()
                .from(WorkflowTaskInstanceStage.RUNNING)
                .to(WorkflowTaskInstanceStage.SUSPEND)
                .on(WorkflowTaskInstanceEvent.COMMAND_SUSPEND)
                .perform(doPerform());
        builder.externalTransition()
                .from(WorkflowTaskInstanceStage.RUNNING)
                .to(WorkflowTaskInstanceStage.TERMINATED)
                .on(WorkflowTaskInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());

        builder.externalTransition()
                .from(WorkflowTaskInstanceStage.SUSPEND)
                .to(WorkflowTaskInstanceStage.RUNNING)
                .on(WorkflowTaskInstanceEvent.COMMAND_RESUME)
                .perform(doPerform());
        builder.externalTransition()
                .from(WorkflowTaskInstanceStage.SUSPEND)
                .to(WorkflowTaskInstanceStage.TERMINATED)
                .on(WorkflowTaskInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());

        this.stateMachine = builder.build(CONSUMER_GROUP);
    }

    private Action<WorkflowTaskInstanceStage, WorkflowTaskInstanceEvent, Pair<Long, Throwable>> doPerform() {
        return (fromState, toState, eventEnum, pair) -> {
            WorkflowTaskInstanceEventDTO eventDTO = new WorkflowTaskInstanceEventDTO(fromState, toState, eventEnum, pair.getLeft(), pair.getRight());
            statusPublisher.publish(eventDTO);
        };
    }

    public void deploy(WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(taskInstance.getStatus(), WorkflowTaskInstanceEvent.COMMAND_DEPLOY, Pair.of(taskInstance.getId(), null));
    }

    public void shutdown(WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(taskInstance.getStatus(), WorkflowTaskInstanceEvent.COMMAND_SHUTDOWN, Pair.of(taskInstance.getId(), null));
    }

    public void suspend(WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(taskInstance.getStatus(), WorkflowTaskInstanceEvent.COMMAND_SUSPEND, Pair.of(taskInstance.getId(), null));
    }

    public void resume(WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(taskInstance.getStatus(), WorkflowTaskInstanceEvent.COMMAND_RESUME, Pair.of(taskInstance.getId(), null));
    }

    public void onSuccess(WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(taskInstance.getStatus(), WorkflowTaskInstanceEvent.PROCESS_SUCCESS, Pair.of(taskInstance.getId(), null));
    }

    public void onFailure(WorkflowTaskInstance taskInstance, Throwable throwable) {
        stateMachine.fireEvent(taskInstance.getStatus(), WorkflowTaskInstanceEvent.PROCESS_FAILURE, Pair.of(taskInstance.getId(), throwable));
    }
}
