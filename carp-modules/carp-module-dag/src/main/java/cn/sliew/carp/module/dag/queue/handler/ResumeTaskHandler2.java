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
import cn.sliew.carp.module.workflow.stage.model.resolver.TaskResolver;
import cn.sliew.carp.module.workflow.stage.model.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResumeTaskHandler2 extends AbstractDagMessageHandler<Messages.ResumeTask> {

    @Autowired
    private TaskResolver taskResolver;

    @Override
    public Class<Messages.ResumeTask> getMessageType() {
        return Messages.ResumeTask.class;
    }

    @Override
    public void handle(Messages.ResumeTask message) {
        withStep(message, workflowStepInstance -> {
            getWorkflowRepository().getStepTaskInstances(workflowStepInstance.getId()).stream()
                    .filter(task -> task.getStatus() == ExecutionStatus.PAUSED)
                    .forEach(task -> {
                        task.setStatus(ExecutionStatus.RUNNING);
                        getWorkflowRepository().updateStepTaskInstance(workflowStepInstance, task);
                        push(new Messages.RunTask(message, getTaskType(task)));
                    });
        });
    }

    private Class<? extends Task> getTaskType(TaskExecution task) {
        return taskResolver.getTaskClass(task.getImplementingClass());
    }
}
