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
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.SkippableTask;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.Task;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageDefinitionBuilderFactory;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.TaskResolver;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.core.events.TaskComplete;
import cn.sliew.carp.module.orca.spinnaker.orca.core.events.TaskStarted;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.util.ContextParameterProcessor;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Slf4j
@Component
public class StartTaskHandler extends AbstractOrcaMessageHandler<Messages.StartTask> implements ExpressionAware {

    private final TaskResolver taskResolver;
    private final StageDefinitionBuilderFactory stageDefinitionBuilderFactory;
    private final ContextParameterProcessor contextParameterProcessor;

    public StartTaskHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock,
            TaskResolver taskResolver,
            StageDefinitionBuilderFactory stageDefinitionBuilderFactory,
            ContextParameterProcessor contextParameterProcessor) {
        super(queue, repository, publisher, clock);
        this.taskResolver = taskResolver;
        this.stageDefinitionBuilderFactory = stageDefinitionBuilderFactory;
        this.contextParameterProcessor = contextParameterProcessor;
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public Class<Messages.StartTask> getMessageType() {
        return Messages.StartTask.class;
    }

    @Override
    public StageDefinitionBuilderFactory getStageDefinitionBuilderFactory() {
        return stageDefinitionBuilderFactory;
    }

    @Override
    public ContextParameterProcessor getContextParameterProcessor() {
        return contextParameterProcessor;
    }

    @Override
    public void handle(Messages.StartTask message) {
        withTask(message, (stage, task) -> {
            TaskExecutionImpl taskImpl = (TaskExecutionImpl) task;
            if (isTaskEnabled(taskImpl)) {
                taskImpl.setStatus(ExecutionStatus.RUNNING);
                taskImpl.setStartTime(clock.instant());

                StageExecution mergedContextStage = withMergedContext(stage);
                getRepository().storeStage(mergedContextStage);

                getQueue().push(new Messages.RunTask(message, taskImpl.getId(), getTaskType(taskImpl)));
                publisher.publishEvent(new TaskStarted(this, mergedContextStage, taskImpl));
            } else {
                taskImpl.setStatus(ExecutionStatus.SKIPPED);

                StageExecution mergedContextStage = withMergedContext(stage);
                getRepository().storeStage(mergedContextStage);

                getQueue().push(new Messages.CompleteTask(message, ExecutionStatus.SKIPPED));
                publisher.publishEvent(new TaskComplete(this, mergedContextStage, taskImpl));
            }
        });
    }

    private boolean isTaskEnabled(TaskExecution task) {
        Object taskInstance = getTaskInstance(task);
        if (taskInstance instanceof SkippableTask) {
//            SkippableTask skippableTask = (SkippableTask) taskInstance;
//            boolean enabled = environment.getProperty(
//                    skippableTask.getIsEnabledPropertyName(),
//                    Boolean.class,
//                    true
//            );
//            if (!enabled) {
//                log.debug("Skipping task.type={} because {}=false",
//                        getTaskType(task),
//                        skippableTask.getIsEnabledPropertyName()
//                );
//            }
//            return enabled;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Task> getTaskType(TaskExecution task) {
        return taskResolver.getTaskClass(task.getImplementingClass());
    }

    private Object getTaskInstance(TaskExecution task) {
        return taskResolver.getTask(task.getImplementingClass());
    }
}