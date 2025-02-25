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

import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.dag.util.DagExecutionUtil;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.task.TaskExecution;
import cn.sliew.carp.module.workflow.stage.model.task.TaskExecutionImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class CompleteTaskHandler2 extends AbstractDagMessageHandler<Messages.CompleteTask> {

    @Override
    public Class<Messages.CompleteTask> getMessageType() {
        return Messages.CompleteTask.class;
    }

    @Override
    public void handle(Messages.CompleteTask message) {
        withTask(message, (dagStepDTO, task) -> {
//            TaskExecutionImpl taskImpl = (TaskExecutionImpl) task;
//            taskImpl.setStatus(message.getStatus());
//            taskImpl.setEndTime(clock.instant());
//            StageExecution mergedContextStage = withMergedContext(stage);

            if (StringUtils.equalsIgnoreCase(message.getStatus(), ExecutionStatus.REDIRECT.name())) {
//                handleRedirect(mergedContextStage);
                handleRedirect(message, dagStepDTO);
            } else {
//                getRepository().storeStage(mergedContextStage);

                if (isManuallySkipped(dagStepDTO)) {
//                   push(new Messages.SkipStep(stage.getTopLevelStage()));
                   push(new Messages.SkipStep(dagStepDTO));
                } else if (shouldCompleteStage(task, message.getStatus(), message.getOriginalStatus())) {
                    push(new Messages.CompleteStep(message));
                } else {
//                    TaskExecution nextTask = DagExecutionUtil.nextTask(mergedContextStage, task);
                    TaskExecution nextTask = DagExecutionUtil.nextTask(dagStepDTO, task);
                    if (nextTask == null) {
                        push(new Messages.NoDownstreamTasks(message));
                    } else {
                        push(new Messages.StartTask(message, nextTask.getId()));
                    }
                }

//                publisher.publishEvent(new TaskComplete(this, mergedContextStage, task));
            }
        });
    }


    private boolean shouldCompleteStage(TaskExecution task, ExecutionStatus status, ExecutionStatus originalStatus) {
        if (task.isStageEnd()) {
            // last task in the stage
            return true;
        }

        if (Objects.equals(originalStatus, ExecutionStatus.FAILED_CONTINUE)) {
            // the task explicitly returned FAILED_CONTINUE and _should_ run subsequent tasks
            return false;
        }

        // the task _should not_ run subsequent tasks
        return status.isHalt();
    }

    private void handleRedirect(Messages.CompleteTask message, DagStepDTO dagStepDTO) {
        List<TaskExecutionImpl> tasks = DagExecutionUtil.getTasks(dagStepDTO);
        int start = -1;
        int end = -1;

        for (int i = 0; i < tasks.size(); i++) {
            TaskExecution task = tasks.get(i);
            if (task.isLoopStart()) {
                start = i;
            }
            if (task.isLoopEnd()) {
                end = i;
            }
        }

        for (int i = start; i <= end; i++) {
            TaskExecutionImpl task = (TaskExecutionImpl) tasks.get(i);
            task.setEndTime(null);
            task.setStatus(ExecutionStatus.NOT_STARTED);
        }

//        getRepository().storeStage(stage);
        push(new Messages.StartTask(message, tasks.get(start).getId()));
    }
}
