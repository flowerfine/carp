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

import cn.hutool.extra.spring.SpringUtil;
import cn.sliew.carp.framework.dag.service.DagInstanceComplexService;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.framework.exception.ExceptionVO;
import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.module.workflow.stage.model.task.TaskExecution;
import cn.sliew.module.workflow.stage.model.task.TaskExecutionImpl;
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

    default void withDag(Messages.DagLevel dagLevel, Consumer<DagInstanceDTO> block) {
        try {
            DagInstanceComplexService dagInstanceComplexService = SpringUtil.getBean(DagInstanceComplexService.class);
            DagInstanceDTO execution = dagInstanceComplexService.selectSimpleOne(dagLevel.getDagId());
            block.accept(execution);
        } catch (IllegalArgumentException e) { // todo 增加 not found exception
            push(new Messages.InvalidExecutionId(dagLevel));
        }
    }


    default void withStep(Messages.StepLevel stepLevel, Consumer<DagStepDTO> block) {
        withDag(stepLevel, dagInstanceDTO -> {
            try {
                DagStepService dagStepService = SpringUtil.getBean(DagStepService.class);
                DagStepDTO stepDTO = dagStepService.get(stepLevel.getStepId());
                block.accept(stepDTO);
            } catch (IllegalArgumentException e) {
                getLog().error("Failed to locate step with id: {}, dagInstanceId: {}",
                        stepLevel.getStepId(), stepLevel.getDagId(), e);
                push(new Messages.InvalidStageId(stepLevel));
            }
        });
    }

    default void withTask(Messages.TaskLevel taskLevel, BiConsumer<DagStepDTO, TaskExecution> block) {
        withStep(taskLevel, step -> {

            // todo 解析 task
//            TaskExecution task = step.taskById(taskLevel.getTaskId());
            TaskExecutionImpl task = new TaskExecutionImpl();
            task.setId(taskLevel.getTaskId());
            if (task == null) {
                getLog().error("InvalidTaskId: Unable to find task {} in step '{}' while processing message {}",
                        taskLevel.getTaskId(),
                        JacksonUtil.toJsonString(step),
                        taskLevel);
                push(new Messages.InvalidTaskId(taskLevel));
            } else {
                block.accept(step, task);
            }
        });
    }

    default boolean isCanceled(DagInstanceDTO dagInstanceDTO) {
        return false;
    }

    default boolean isComplete(DagInstanceDTO dagInstanceDTO) {
        return false;
    }

    default boolean isSkipped(DagStepDTO dagStepDTO) {
        return false;
    }

    default boolean isManuallySkipped(DagStepDTO dagStepDTO) {
        return false;
    }

    default Logger getLog() {
        return LoggerFactory.getLogger(getClass());
    }

}
