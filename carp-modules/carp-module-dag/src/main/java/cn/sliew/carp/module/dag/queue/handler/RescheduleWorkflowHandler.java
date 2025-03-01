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
import cn.sliew.carp.module.workflow.stage.model.resolver.TaskResolver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RescheduleWorkflowHandler extends AbstractDagMessageHandler<Messages.RescheduleWorkflow> {

    @Autowired
    private TaskResolver taskResolver;

    @Override
    public Class<Messages.RescheduleWorkflow> getMessageType() {
        return Messages.RescheduleWorkflow.class;
    }

    @Override
    public void handle(Messages.RescheduleWorkflow message) {
        withWorkflow(message, workflowInstance -> {
            getWorkflowRepository().getStepInstances(workflowInstance.getId()).stream()
                    .filter(stepInstance -> StringUtils.equalsIgnoreCase(stepInstance.getStatus(), ExecutionStatus.RUNNING.name()))
                    .forEach(stepInstance -> {
                        getWorkflowRepository().getStepTaskInstances(stepInstance.getId()).stream()
                                .filter(task -> task.getStatus() == ExecutionStatus.RUNNING)
                                .forEach(task -> {
                                    Messages.RunTask taskMessage = new Messages.RunTask(
                                            message,
                                            stepInstance.getId(),
                                            task.getId(),
                                            taskResolver.getTaskClass(task.getImplementingClass())
                                    );
//                                    queue.ensure(taskMessage, Duration.ZERO);
//                                    queue.reschedule(taskMessage);
                                });
                    });
        });
    }
}
