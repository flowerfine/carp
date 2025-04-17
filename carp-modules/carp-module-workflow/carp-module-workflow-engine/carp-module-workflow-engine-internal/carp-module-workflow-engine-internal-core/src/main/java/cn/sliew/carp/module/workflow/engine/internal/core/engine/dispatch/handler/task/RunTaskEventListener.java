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
package cn.sliew.carp.module.workflow.engine.internal.core.engine.dispatch.handler.task;

import cn.hutool.core.lang.func.Consumer3;
import cn.sliew.carp.framework.common.lock.LockRunResult;
import cn.sliew.carp.framework.common.lock.RetriableLockAndRunExecutor;
import cn.sliew.carp.framework.common.util.KeyUtil;
import cn.sliew.carp.framework.dag.service.DagStepService;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.workflow.engine.internal.core.util.DagExecutionUtil;
import cn.sliew.carp.module.workflow.engine.internal.domain.enums.CarpWorkflowTaskInstanceEvent;
import cn.sliew.carp.module.workflow.engine.internal.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.engine.internal.domain.instance.WorkflowTaskInstance;
import cn.sliew.carp.module.workflow.engine.internal.core.engine.dispatch.event.task.RunTaskDTO;
import cn.sliew.carp.module.workflow.engine.internal.stage.model.resolver.TaskResolver;
import cn.sliew.carp.module.workflow.engine.internal.stage.model.task.Task;
import cn.sliew.carp.module.workflow.engine.internal.stage.model.task.TaskExecutionInterceptor;
import cn.sliew.carp.module.workflow.engine.internal.stage.model.task.TaskResult;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Slf4j
@Component
public class RunTaskEventListener extends AbstractTaskEventListener<RunTaskDTO> {

    @Autowired
    private DagStepService dagStepService;
    @Autowired(required = false)
    private List<TaskExecutionInterceptor> taskExecutionInterceptors;
    @Autowired
    private RetriableLockAndRunExecutor lockAndRunExecutor;
    @Autowired
    private TaskResolver taskResolver;

    @Override
    public CarpWorkflowTaskInstanceEvent getType() {
        return CarpWorkflowTaskInstanceEvent.COMMAND_RUN;
    }

    @Override
    protected CompletableFuture<?> handleEventAsync(RunTaskDTO event) {
        return CompletableFuture.runAsync(() -> run(event)).toCompletableFuture();
    }

    private void run(RunTaskDTO event) {
        withLocking(event, () -> {
            withTask(event, (origStep, taskInstance, task) -> {
                withLoggingContext(origStep, taskInstance, (stepInstance) -> {
                    TaskResult taskResult = null;
                    Exception taskException = null;
                    try {
                        stepInstance = DagExecutionUtil.beforeTask(taskExecutionInterceptors, stepInstance, task);
                        taskResult = executeTask(event, stepInstance, taskInstance, task);
                        handleTaskResult(event, stepInstance, taskInstance, task, taskResult);
                    } catch (Exception e) {
                        taskException = e;
                        handleTaskException(event, stepInstance, taskInstance, task, e);
                    } finally {
                        DagExecutionUtil.finallyTask(taskExecutionInterceptors, stepInstance, task, taskResult, taskException);
                    }
                });
            });
        });
    }

    /**
     * todo 迁移到 Context 中
     */
    private void withLocking(RunTaskDTO message, Runnable action) {
        String lockKey = KeyUtil.buildKey("workflow", message.getNamespace(), message.getType(), message.getWorkflowInstanceId(), message.getStepId());
        LockRunResult<Void> lockRunResult = lockAndRunExecutor.execute(action, lockKey);
        if (!lockRunResult.isLockAcquired()) {
            log.warn("Workflow Step (namespace: {}, type: {}, workflowInstanceId: {}, stepId: {}) fail to obtain lock for task. Pushing original message back to queue",
                    message.getNamespace(), message.getType(), message.getWorkflowInstanceId(), message.getStepId());
            // fixme 需增加重试次数，不然会无限重试下去
            stateMachine.run(message.getState(), (state, nextState, event) -> message);
        }
    }

    private void withTask(RunTaskDTO message, Consumer3<WorkflowStepInstance, WorkflowTaskInstance, Task> consumer) {
        WorkflowStepInstance stepInstance = workflowInstanceService.getStep(message.getStepId());
        WorkflowTaskInstance taskInstance = workflowInstanceService.getTask(message.getTaskId());
        Task task = getTaskInstance(taskInstance);
        consumer.accept(stepInstance, taskInstance, task);
    }

    private void withLoggingContext(WorkflowStepInstance stepInstance, WorkflowTaskInstance taskInstance, Consumer<WorkflowStepInstance> action) {
        try {
            MDC.put("stepType", stepInstance.getNode().getStepName());
            MDC.put("taskType", taskInstance.getImplementingClass());
            if (Objects.nonNull(taskInstance.getStartTime())) {
                MDC.put("taskStartTime", taskInstance.getStartTime().toString());
            }
            action.accept(stepInstance);
        } finally {
            MDC.remove("stepType");
            MDC.remove("taskType");
            MDC.remove("taskStartTime");
        }
    }

    private TaskResult executeTask(RunTaskDTO message,
                                   WorkflowStepInstance stepInstance,
                                   WorkflowTaskInstance taskInstance,
                                   Task task) throws Exception {
        TaskResult result = task.execute(stepInstance, taskInstance);
        return DagExecutionUtil.afterTask(taskExecutionInterceptors, stepInstance, task, result);
    }


    private void handleTaskResult(RunTaskDTO message,
                                  WorkflowStepInstance stepInstance,
                                  WorkflowTaskInstance taskModel,
                                  Task task,
                                  TaskResult result) {
        switch (result.getStatus()) {
            case SUCCEEDED:
                processTaskOutput(stepInstance, result);
                stateMachine.onSuccess(stepInstance, taskModel);
                break;
            default:
                processTaskOutput(stepInstance, result);
                throw new IllegalStateException("Unhandled task status " + result.getStatus());
        }
    }


    private void handleTaskException(RunTaskDTO message,
                                     WorkflowStepInstance stepInstance,
                                     WorkflowTaskInstance taskModel,
                                     Task task,
                                     Exception e) {
        stateMachine.onFailure(stepInstance, taskModel, e);
    }


    private void processTaskOutput(WorkflowStepInstance stepInstance, TaskResult result) {
        Map<String, ?> outputs = result.getOutputs();
        if (MapUtils.isNotEmpty(result.getContext()) || MapUtils.isNotEmpty(outputs)) {
            if (MapUtils.isNotEmpty(result.getContext())) {
                stepInstance.getContext().putAll(result.getContext());
            }
            if (MapUtils.isNotEmpty(outputs)) {
                stepInstance.getOutputs().putAll(outputs);
            }

            DagStepDTO stepUpdateParam = new DagStepDTO();
            stepUpdateParam.setId(stepInstance.getId());
            stepUpdateParam.setOutputs(JacksonUtil.toJsonNode(stepInstance.getOutputs()));
            stepUpdateParam.setContext(JacksonUtil.toJsonNode(stepInstance.getContext()));
            dagStepService.update(stepUpdateParam);
        }
    }

    private Task getTaskInstance(WorkflowTaskInstance task) {
        return taskResolver.getTask(task.getImplementingClass());
    }
}
