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
package cn.sliew.carp.module.dag.queue.handler;

import cn.sliew.carp.framework.exception.ExceptionVO;
import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.dag.util.DagExecutionUtil;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecution;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.stage.model.repository.WorkflowRepository;
import cn.sliew.milky.common.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface DagMessageHandler<M> {

    Class<M> getMessageType();

    void handle(M message);

    default void push(Object message) {
        push(message, Duration.ZERO);
    }

    void push(Object message, Duration delay);

    ExceptionVO handleException(String name, Exception e);

    default void withWorkflow(Messages.WorkflowLevel workflowLevel, Consumer<WorkflowInstance> block) {
        try {
            WorkflowInstance workflowInstance = getWorkflowRepository().get(workflowLevel.getDagId());
            block.accept(workflowInstance);
        } catch (IllegalArgumentException e) { // todo 增加 not found exception
            push(new Messages.InvalidWorkflowId(workflowLevel));
        }
    }


    default void withStep(Messages.StepLevel stepLevel, Consumer<WorkflowStepInstance> block) {
        withWorkflow(stepLevel, dagInstanceDTO -> {
            try {
                WorkflowStepInstance stepInstance = getWorkflowRepository().getStepInstance(stepLevel.getStepId());
                block.accept(stepInstance);
            } catch (IllegalArgumentException e) { // todo 增加 not found exception
                getLog().error("Failed to locate step with id: {}, workflowInstanceId: {}",
                        stepLevel.getStepId(), stepLevel.getDagId(), e);
                push(new Messages.InvalidStepId(stepLevel));
            }
        });
    }

    default void withTask(Messages.TaskLevel taskLevel, BiConsumer<WorkflowStepInstance, TaskExecution> block) {
        withStep(taskLevel, stepInstance -> {
            TaskExecution task = DagExecutionUtil.getTasks(stepInstance, taskLevel.getTaskId());
            if (task == null) {
                getLog().error("InvalidTaskId: Unable to find task {} in step '{}' while processing message {}",
                        taskLevel.getTaskId(), JacksonUtil.toJsonString(stepInstance), taskLevel);
                push(new Messages.InvalidTaskId(taskLevel));
            } else {
                block.accept(stepInstance, task);
            }
        });
    }

    default boolean isCanceled(WorkflowInstance workflowInstance) {
        return false;
    }

    default boolean isComplete(WorkflowInstance workflowInstance) {
        return false;
    }

    default boolean isSkipped(WorkflowStepInstance stepInstance) {
        return false;
    }

    default boolean isManuallySkipped(WorkflowStepInstance stepInstance) {
        return false;
    }

    default Logger getLog() {
        return LoggerFactory.getLogger(getClass());
    }

    WorkflowRepository getWorkflowRepository();

}
