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

import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowTaskInstanceEvent;
import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowTaskInstanceStage;
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

    private StateMachine<CarpWorkflowTaskInstanceStage, CarpWorkflowTaskInstanceEvent, Pair<Long, Throwable>> stateMachine;

    @Override
    public void afterPropertiesSet() throws Exception {
        StateMachineBuilder<CarpWorkflowTaskInstanceStage, CarpWorkflowTaskInstanceEvent, Pair<Long, Throwable>> builder = StateMachineBuilderFactory.create();

        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceStage.PENDING)
                .to(CarpWorkflowTaskInstanceStage.RUNNING)
                .on(CarpWorkflowTaskInstanceEvent.COMMAND_DEPLOY)
                .perform(doPerform());

        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceStage.RUNNING)
                .to(CarpWorkflowTaskInstanceStage.SUCCESS)
                .on(CarpWorkflowTaskInstanceEvent.PROCESS_SUCCESS)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceStage.RUNNING)
                .to(CarpWorkflowTaskInstanceStage.FAILURE)
                .on(CarpWorkflowTaskInstanceEvent.PROCESS_FAILURE)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceStage.RUNNING)
                .to(CarpWorkflowTaskInstanceStage.SUSPEND)
                .on(CarpWorkflowTaskInstanceEvent.COMMAND_SUSPEND)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceStage.RUNNING)
                .to(CarpWorkflowTaskInstanceStage.TERMINATED)
                .on(CarpWorkflowTaskInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());

        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceStage.SUSPEND)
                .to(CarpWorkflowTaskInstanceStage.RUNNING)
                .on(CarpWorkflowTaskInstanceEvent.COMMAND_RESUME)
                .perform(doPerform());
        builder.externalTransition()
                .from(CarpWorkflowTaskInstanceStage.SUSPEND)
                .to(CarpWorkflowTaskInstanceStage.TERMINATED)
                .on(CarpWorkflowTaskInstanceEvent.COMMAND_SHUTDOWN)
                .perform(doPerform());

        this.stateMachine = builder.build(CONSUMER_GROUP);
    }

    private Action<CarpWorkflowTaskInstanceStage, CarpWorkflowTaskInstanceEvent, Pair<Long, Throwable>> doPerform() {
        return (fromState, toState, eventEnum, pair) -> {
            WorkflowTaskInstanceEventDTO eventDTO = new WorkflowTaskInstanceEventDTO(fromState, toState, eventEnum, pair.getLeft(), pair.getRight());
            statusPublisher.publish(eventDTO);
        };
    }

    public void deploy(WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(taskInstance.getStatus(), CarpWorkflowTaskInstanceEvent.COMMAND_DEPLOY, Pair.of(taskInstance.getId(), null));
    }

    public void shutdown(WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(taskInstance.getStatus(), CarpWorkflowTaskInstanceEvent.COMMAND_SHUTDOWN, Pair.of(taskInstance.getId(), null));
    }

    public void suspend(WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(taskInstance.getStatus(), CarpWorkflowTaskInstanceEvent.COMMAND_SUSPEND, Pair.of(taskInstance.getId(), null));
    }

    public void resume(WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(taskInstance.getStatus(), CarpWorkflowTaskInstanceEvent.COMMAND_RESUME, Pair.of(taskInstance.getId(), null));
    }

    public void onSuccess(WorkflowTaskInstance taskInstance) {
        stateMachine.fireEvent(taskInstance.getStatus(), CarpWorkflowTaskInstanceEvent.PROCESS_SUCCESS, Pair.of(taskInstance.getId(), null));
    }

    public void onFailure(WorkflowTaskInstance taskInstance, Throwable throwable) {
        stateMachine.fireEvent(taskInstance.getStatus(), CarpWorkflowTaskInstanceEvent.PROCESS_FAILURE, Pair.of(taskInstance.getId(), throwable));
    }
}
