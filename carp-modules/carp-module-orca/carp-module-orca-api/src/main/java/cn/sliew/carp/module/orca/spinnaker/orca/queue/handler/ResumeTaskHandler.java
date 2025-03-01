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
package cn.sliew.carp.module.orca.spinnaker.orca.queue.handler;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.Task;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.TaskResolver;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class ResumeTaskHandler implements OrcaMessageHandler<Messages.ResumeTask> {

    @Getter
    private final Queue queue;
    @Getter
    private final ExecutionRepository repository;
    private final TaskResolver taskResolver;

    public ResumeTaskHandler(
            Queue queue,
            ExecutionRepository repository,
            TaskResolver taskResolver) {
        this.queue = queue;
        this.repository = repository;
        this.taskResolver = taskResolver;
    }

    @Override
    public Class<Messages.ResumeTask> getMessageType() {
        return Messages.ResumeTask.class;
    }

    @Override
    public void handle(Messages.ResumeTask message) {
        withStage(message, stage -> {
            ((StageExecutionImpl) stage).setStatus(ExecutionStatus.RUNNING);
            stage.getTasks().stream()
                    .filter(task -> task.getStatus() == ExecutionStatus.PAUSED)
                    .forEach(task -> {
                        ((TaskExecutionImpl) task).setStatus(ExecutionStatus.RUNNING);
                        queue.push(new Messages.RunTask(message, getTaskType(task)));
                    });
            repository.storeStage(stage);
        });
    }

    private Class<? extends Task> getTaskType(TaskExecution task) {
        return taskResolver.getTaskClass(task.getImplementingClass());
    }
}
