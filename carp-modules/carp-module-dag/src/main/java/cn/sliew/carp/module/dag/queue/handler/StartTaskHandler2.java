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

import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecution;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecutionImpl;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.stage.model.resolver.TaskResolver;
import cn.sliew.carp.module.workflow.stage.model.task.SkippableTask;
import cn.sliew.carp.module.workflow.stage.model.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class StartTaskHandler2 extends AbstractDagMessageHandler<Messages.StartTask> {

    @Autowired
    private TaskResolver taskResolver;

    @Override
    public Class<Messages.StartTask> getMessageType() {
        return Messages.StartTask.class;
    }

    @Override
    public void handle(Messages.StartTask message) {
        withTask(message, (stepInstance, task) -> {
            TaskExecutionImpl taskImpl = (TaskExecutionImpl) task;
            if (isTaskEnabled(stepInstance, task)) {
                taskImpl.setStartTime(Instant.now());
                taskImpl.setStatus(ExecutionStatus.RUNNING);
                // todo evaluate expression
                getWorkflowRepository().updateStepTaskInstance(stepInstance, taskImpl);

                push(new Messages.RunTask(message, taskImpl.getId(), getTaskType(taskImpl)));
            } else {
                taskImpl.setStatus(ExecutionStatus.SKIPPED);
                // todo evaluate expression
                getWorkflowRepository().updateStepTaskInstance(stepInstance, taskImpl);
                push(new Messages.CompleteTask(message, ExecutionStatus.SKIPPED));
            }
        });
    }


    private boolean isTaskEnabled(WorkflowStepInstance stepInstance, TaskExecution task) {
        Object taskInstance = getTaskInstance(task);
        if (taskInstance instanceof SkippableTask skippableTask) {
//            boolean enabled = environment.getProperty(
//                    skippableTask.getIsEnabledPropertyName(),
//                    Boolean.class,
//                    true
//            );
//            if (!enabled) {
//                getLog().debug("Skipping task.type={} because {}=false",
//                        getTaskType(task),
//                        skippableTask.getIsEnabledPropertyName()
//                );
//            }
//            return enabled;
        }
        return true;
    }

    private Class<? extends Task> getTaskType(TaskExecution task) {
        return taskResolver.getTaskClass(task.getImplementingClass());
    }

    private Object getTaskInstance(TaskExecution task) {
        return taskResolver.getTask(task.getImplementingClass());
    }
}
