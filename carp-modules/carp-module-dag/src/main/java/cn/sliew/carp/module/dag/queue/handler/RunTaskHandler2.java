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

import cn.hutool.core.lang.func.Consumer3;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.framework.exception.ExceptionVO;
import cn.sliew.carp.module.dag.exceptions.StepTimeoutException;
import cn.sliew.carp.module.dag.lock.RetriableLock;
import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.dag.util.DagExecutionUtil;
import cn.sliew.carp.module.workflow.stage.model.task.*;
import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.resolver.NoSuchTaskException;
import cn.sliew.carp.module.workflow.stage.model.resolver.TaskResolver;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

@Component
public class RunTaskHandler2 extends AbstractDagMessageHandler<Messages.RunTask> {

    @Autowired(required = false)
    private List<TaskExecutionInterceptor> taskExecutionInterceptors;
    @Autowired
    @Qualifier("carpRetriableLock")
    private RetriableLock retriableLock;
    @Autowired
    private TaskResolver taskResolver;

    @Override
    public Class<Messages.RunTask> getMessageType() {
        return Messages.RunTask.class;
    }

    @Override
    public void handle(Messages.RunTask message) {
        withLocking(message, () -> {
            withTask(message, (origStep, taskModel, task) -> {
                withLoggingContext(origStep, taskModel, (dagStepDTO) -> {
                    long thisInvocationStartTimeMs = System.currentTimeMillis();
                    TaskResult taskResult = null;
                    Exception taskException = null;

                    try {
                        dagStepDTO = DagExecutionUtil.beforeTask(taskExecutionInterceptors, dagStepDTO, task);

                        if (isCanceled(dagStepDTO.getDagInstance())) {
                            handleCanceledExecution(message, dagStepDTO, taskModel, task);
                        } else if (isComplete(dagStepDTO.getDagInstance())) {
                            push(new Messages.CompleteTask(message, ExecutionStatus.CANCELED));
                        } else if (StringUtils.equalsIgnoreCase(dagStepDTO.getDagInstance().getStatus(), ExecutionStatus.PAUSED.name())) {
                            push(new Messages.PauseTask(message));
                        } else if (isManuallySkipped(dagStepDTO)) {
                            push(new Messages.CompleteTask(message, ExecutionStatus.SKIPPED));
                        } else {
                            taskResult = executeTask(message, dagStepDTO, taskModel, task);
                            handleTaskResult(message, dagStepDTO, taskModel, task, taskResult, thisInvocationStartTimeMs);
                        }
                    } catch (Exception e) {
                        taskException = e;
                        handleTaskException(message, dagStepDTO, taskModel, task, e, thisInvocationStartTimeMs);
                    } finally {
                        DagExecutionUtil.finallyTask(taskExecutionInterceptors, dagStepDTO, task, taskResult, taskException);
                    }
                });
            });
        });
    }

    private void withLocking(Messages.RunTask message, Runnable action) {
        RetriableLock.RetriableLockOptions lockOptions = new RetriableLock.RetriableLockOptions(
                String.format("carp:dag:%s:%s:%d:%d",
                        message.getNamespace(), message.getType(), message.getDagId(), message.getStepId()));
        Boolean lockAcquired = retriableLock.lock(lockOptions, action);
        if (!lockAcquired) {
            getLog().warn("Dag Step (namespace: {}, type: {}, dagId: {}, stepId: {}) fail to obtain lock for task. Pushing original message back to queue",
                    message.getNamespace(), message.getType(), message.getDagId(), message.getStepId());
            // fixme 需增加重试次数，不然会无限重试下去
            push(message);
        }
    }

    private void withTask(Messages.RunTask message, Consumer3<DagStepDTO, TaskExecution, Task> consumer) {
        withTask(message, (stage, taskExecution) -> {
            try {
                Task task = taskResolver.getTask(taskExecution.getImplementingClass());
                consumer.accept(stage, taskExecution, task);
            } catch (NoSuchTaskException e) {
                push(new Messages.InvalidTaskType(message, message.getTaskType().getName()));
            }
        });
    }

    private void withLoggingContext(DagStepDTO dagStepDTO, TaskExecution task, Consumer<DagStepDTO> action) {
        try {
            MDC.put("stageType", dagStepDTO.getDagConfigStep().getStepName());
            MDC.put("taskType", task.getImplementingClass());
            if (Objects.nonNull(task.getStartTime())) {
                MDC.put("taskStartTime", task.getStartTime().toString());
            }
            action.accept(dagStepDTO);
        } finally {
            MDC.remove("stageType");
            MDC.remove("taskType");
            MDC.remove("taskStartTime");
        }
    }

    private void handleCanceledExecution(Messages.RunTask message,
                                         DagStepDTO dagStepDTO,
                                         TaskExecution taskModel,
                                         Task task) {
        TaskResult result = task.onCancelWithResult(dagStepDTO);
        if (result != null) {
            processTaskOutput(dagStepDTO, result);
        }
        push(new Messages.CompleteTask(message, ExecutionStatus.CANCELED));
    }

    private void processTaskOutput(DagStepDTO dagStepDTO, TaskResult result) {
        Map<String, Object> filteredOutputs = Maps.newHashMap();
        for (Map.Entry entry : result.getOutputs().entrySet()) {
            if (Objects.equals(entry.getKey(), "stepTimeoutMs")) {
                filteredOutputs.put((String) entry.getKey(), entry.getValue());
            }
        }
        if (MapUtils.isNotEmpty(result.getContext()) || MapUtils.isNotEmpty(filteredOutputs)) {
            ObjectNode outputs = JacksonUtil.createObjectNode();
            if (MapUtils.isNotEmpty(result.getContext())) {
                result.getContext().forEach((key, value) -> {
                    outputs.putPOJO(key, value);
                });
            }

            if (MapUtils.isNotEmpty(filteredOutputs)) {
                filteredOutputs.forEach((key, value) -> {
                    outputs.putPOJO(key, value);
                });
            }

            // todo store task output
//            stage.getContext().putAll(result.getContext());
//            stage.getOutputs().putAll(filteredOutputs);
//            getRepository().storeStage(stage);
        }
    }


    private TaskResult executeTask(Messages.RunTask message,
                                   DagStepDTO dagStepDTO,
                                   TaskExecution taskModel,
                                   Task task) throws Exception {
        try {
            checkForTimeout(task, dagStepDTO, taskModel, message);
        } catch (StepTimeoutException e) {
//            registry.counter(getTimeoutCounterId(stage, taskModel)).increment();
            TaskResult timeoutResult = task.onTimeout(dagStepDTO);
            if (timeoutResult == null) {
                throw e;
            }
            EnumSet<ExecutionStatus> set = EnumSet.of(ExecutionStatus.TERMINAL, ExecutionStatus.FAILED_CONTINUE);
            if (set.contains(timeoutResult.getStatus())) {
                getLog().error("Dag Task (namespace: {}, type: {}, dagId: {}, stepId: {}, task: {}) return invalid status ({}) for onTimeout",
                        message.getNamespace(), message.getType(), message.getDagId(), message.getStepId(),
                        task.getClass().getName(), timeoutResult.getStatus());
                throw e;
            }
            return timeoutResult;
        }

//        TaskResult result = task.execute(withMergedContext(stage));
        TaskResult result = task.execute(dagStepDTO, taskModel);
        result = DagExecutionUtil.afterTask(taskExecutionInterceptors, dagStepDTO, task, result);
        return result;
    }


    private void checkForTimeout(Task task, DagStepDTO dagStepDTO, TaskExecution taskModel, Messages.RunTask message) {
//        if (Objects.equals(stage.getType(), RestrictExecutionDuringTimeWindow.TYPE)) {
//            return;
//        }

        checkForStageTimeout(dagStepDTO);
        checkForTaskTimeout(task, taskModel, dagStepDTO, message);
    }

    private void checkForTaskTimeout(Task task, TaskExecution taskModel, DagStepDTO dagStepDTO, Messages.RunTask message) {
        if (!(task instanceof RetryableTask)) {
            return;
        }

        RetryableTask retryableTask = (RetryableTask) task;

        Instant startTime = taskModel.getStartTime() != null ? taskModel.getStartTime() : null;

//        if (startTime != null) {
//            Duration pausedDuration = pausedDurationRelativeTo(stage.getPipelineExecution(), startTime);
//            Duration elapsedTime = Duration.between(startTime, clock.instant());
//
//            Duration timeout = (task instanceof OverridableTimeoutRetryableTask &&
//                    stage.getParentWithTimeout().isPresent()) ?
//                    Duration.ofMillis(stage.getParentWithTimeout().get().getTimeout().get()) :
//                    retryableTask.getDynamicTimeout(stage);
//
//            if (elapsedTime.minus(pausedDuration).compareTo(timeout) > 0) {
//                String durationString = formatTimeout(elapsedTime.toMillis());
//                StringBuilder messageStr = new StringBuilder()
//                        .append(task.getClass().getSimpleName())
//                        .append(" of stage ")
//                        .append(stage.getName())
//                        .append(" timed out after ")
//                        .append(durationString)
//                        .append(". ");
//                messageStr.append("pausedDuration: ")
//                        .append(formatTimeout(pausedDuration.toMillis()))
//                        .append(", ");
//                messageStr.append("elapsedTime: ")
//                        .append(formatTimeout(elapsedTime.toMillis()))
//                        .append(", ");
//                messageStr.append("timeoutValue: ")
//                        .append(formatTimeout(timeout.toMillis()));
//
//                log.info(messageStr.toString());
//                throw new TimeoutException(messageStr.toString());
//            }
//        }
    }

    private void checkForStageTimeout(DagStepDTO dagStepDTO) {
//        stage.getParentWithTimeout().ifPresent(parentStage -> {
//            Instant startTime = parentStage.getStartTime() != null ? parentStage.getStartTime() : null;
//
//            if (startTime != null) {
//                Duration elapsedTime = Duration.between(startTime, clock.instant());
//                Duration pausedDuration = pausedDurationRelativeTo(stage.getPipelineExecution(), startTime);
//                Duration executionWindowDuration = getExecutionWindowDuration(stage);
//                Duration timeout = Duration.ofMillis(parentStage.getTimeout().get());
//
//                if (elapsedTime.minus(pausedDuration).minus(executionWindowDuration).compareTo(timeout) > 0) {
//                    throw new TimeoutException(String.format(
//                            "Stage %s timed out after %s",
//                            stage.getName(),
//                            formatTimeout(elapsedTime.toMillis())
//                    ));
//                }
//            }
//        });
    }


    private void handleTaskResult(Messages.RunTask message,
                                  DagStepDTO dagStepDTO,
                                  TaskExecution taskModel,
                                  Task task,
                                  TaskResult result,
                                  long startTimeMs) {

        switch (result.getStatus()) {
            case RUNNING:
                processTaskOutput(dagStepDTO, result);
                push(message, getBackoffPeriod(task, taskModel, dagStepDTO));
                break;
            case SUCCEEDED:
            case REDIRECT:
            case SKIPPED:
            case FAILED_CONTINUE:
            case STOPPED:
                processTaskOutput(dagStepDTO, result);
                push(new Messages.CompleteTask(message, result.getStatus()));
                break;
            case CANCELED:
                processTaskOutput(dagStepDTO, mergeOutputs(result, task.onCancelWithResult(dagStepDTO)));
                ExecutionStatus finalStatus = DagExecutionUtil.failureStatus(dagStepDTO, result.getStatus());
                push(new Messages.CompleteTask(message, finalStatus, result.getStatus()));
                break;
            case TERMINAL:
                processTaskOutput(dagStepDTO, result);
                ExecutionStatus status = DagExecutionUtil.failureStatus(dagStepDTO, result.getStatus());
                push(new Messages.CompleteTask(message, status, result.getStatus()));
                break;
            default:
                processTaskOutput(dagStepDTO, result);
                throw new IllegalStateException("Unhandled task status " + result.getStatus());
        }

//        trackResult(stage, startTimeMs, taskModel, result.getStatus());
    }


    private Duration getBackoffPeriod(Task task, TaskExecution taskModel, DagStepDTO dagStepDTO) {
        if (task instanceof RetryableTask retryableTask) {
            return Duration.ofMillis(
                    Math.min(
                            retryableBackOffPeriod(retryableTask, taskModel, dagStepDTO),
                            getMaxTaskBackoff()
                    )
            );
        }
        return Duration.ofMillis(1000);
    }

    private Long retryableBackOffPeriod(RetryableTask task, TaskExecution taskModel, DagStepDTO dagStepDTO) {
        return task.getDynamicBackoffPeriod(
                dagStepDTO,
                Duration.ofMillis(System.currentTimeMillis() - (taskModel.getStartTime() != null ? taskModel.getStartTime().toEpochMilli() : 0L))
        ).toMillis();
    }


    private long getMaxTaskBackoff() {
        return taskExecutionInterceptors.stream()
                .mapToLong(TaskExecutionInterceptor::maxTaskBackoff)
                .min()
                .orElse(Long.MAX_VALUE);
    }


    private TaskResult mergeOutputs(TaskResult result, TaskResult newResult) {
        if (newResult == null) {
            return result;
        }

        Map outputs = new HashMap(result.getOutputs());
        outputs.putAll(newResult.getOutputs());

        Map context = new HashMap(result.getContext());
        context.putAll(newResult.getContext());

        return TaskResult.builder(result.getStatus())
                .outputs(outputs)
                .context(context)
                .build();
    }


    private void handleTaskException(Messages.RunTask message,
                                     DagStepDTO dagStepDTO,
                                     TaskExecution taskModel,
                                     Task task,
                                     Exception e,
                                     long startTimeMs) {
        ExceptionVO exceptionVO = handleException(taskModel.getName(), e);
        if (exceptionVO != null && exceptionVO.isRetryable()) {
            getLog().warn("Dag Task (namespace: {}, type: {}, dagId: {}, stepId: {}, task: {}) run error, retry",
                    message.getNamespace(), message.getType(), message.getDagId(), message.getStepId(), message.getTaskType().getSimpleName());
            push(message, getBackoffPeriod(task, taskModel, dagStepDTO));
//            trackResult(stage, startTimeMs, taskModel, ExecutionStatus.RUNNING);
        } else if (e instanceof StepTimeoutException) {
//                && Boolean.TRUE.equals(dagStepDTO.getContext().get("markSuccessfulOnTimeout"))) {
//            trackResult(stage, startTimeMs, taskModel, ExecutionStatus.SUCCEEDED);
            push(new Messages.CompleteTask(message, ExecutionStatus.SUCCEEDED));
        } else {
            if (!(e instanceof StepTimeoutException)) {
//                if (e instanceof UserException) {
//                    log.warn("{} for {}[{}] failed, likely due to user error",
//                            message.getTaskType().getSimpleName(),
//                            message.getExecutionType(),
//                            message.getExecutionId(),
//                            e);
//                } else {
                getLog().error("Dag Task (namespace: {}, type: {}, dagId: {}, stepId: {}, task: {}) run error, terminal",
                        message.getNamespace(), message.getType(), message.getDagId(), message.getStepId(), message.getTaskType().getSimpleName());
            }

            ExecutionStatus status = DagExecutionUtil.failureStatus(dagStepDTO, ExecutionStatus.TERMINAL);
//            dagStepDTO.getContext().put("exception", exceptionDetails);
//            taskModel.getTaskExceptionDetails().put("exception", exceptionDetails);
//            getRepository().storeStage(stage);
            push(new Messages.CompleteTask(message, status, ExecutionStatus.TERMINAL));
//            trackResult(stage, startTimeMs, taskModel, status);
        }
    }


}
