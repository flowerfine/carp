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

import cn.hutool.core.lang.func.Consumer3;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.RestrictExecutionDuringTimeWindow;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageNavigator;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.*;
import cn.sliew.carp.module.orca.spinnaker.api.model.util.StageExecutionUtil;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageDefinitionBuilderFactory;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.TaskResolver;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.core.exceptions.ExceptionHandler;
import cn.sliew.carp.module.orca.spinnaker.orca.core.exceptions.TimeoutException;
import cn.sliew.carp.module.orca.spinnaker.orca.core.lock.RetriableLock;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.util.ContextParameterProcessor;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.function.Consumer;

@Slf4j
@Component
public class RunTaskHandler extends AbstractOrcaMessageHandler<Messages.RunTask> implements ExpressionAware {

    private Duration warningInvocationTimeMs = Duration.ofMillis(30000L);

    private StageNavigator stageNavigator;
    private StageDefinitionBuilderFactory stageDefinitionBuilderFactory;
    private ContextParameterProcessor contextParameterProcessor;
    private TaskResolver taskResolver;
    private List<ExceptionHandler> exceptionHandlers;
    private List<TaskExecutionInterceptor> taskExecutionInterceptors;
    private RetriableLock retriableLock;

    public RunTaskHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock,
            StageNavigator stageNavigator,
            StageDefinitionBuilderFactory stageDefinitionBuilderFactory,
            ContextParameterProcessor contextParameterProcessor,
            TaskResolver taskResolver,
            List<ExceptionHandler> exceptionHandlers,
            List<TaskExecutionInterceptor> taskExecutionInterceptors,
            RetriableLock retriableLock) {
        super(queue, repository, publisher, clock);
        this.stageNavigator = stageNavigator;
        this.stageDefinitionBuilderFactory = stageDefinitionBuilderFactory;
        this.contextParameterProcessor = contextParameterProcessor;
        this.taskResolver = taskResolver;
        this.exceptionHandlers = exceptionHandlers;
        this.taskExecutionInterceptors = taskExecutionInterceptors;
        this.retriableLock = retriableLock;
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public Class<Messages.RunTask> getMessageType() {
        return Messages.RunTask.class;
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
    public void handle(Messages.RunTask message) {
        withLocking(message, () -> {
            withTask(message, (origStage, taskModel, task) -> {
                withLoggingContext(origStage, taskModel, (stage) -> {
                    long thisInvocationStartTimeMs = clock.millis();
                    TaskResult taskResult = null;
                    Exception taskException = null;

                    try {
                        for (TaskExecutionInterceptor interceptor : taskExecutionInterceptors) {
                            stage = interceptor.beforeTaskExecution(task, stage);
                        }

                        if (stage.getPipelineExecution().isCanceled()) {
                            handleCanceledExecution(message, stage, taskModel, task);
                        } else if (stage.getPipelineExecution().getStatus().isComplete()) {
                            getQueue().push(new Messages.CompleteTask(message, ExecutionStatus.CANCELED));
                        } else if (stage.getPipelineExecution().getStatus() == ExecutionStatus.PAUSED) {
                            getQueue().push(new Messages.PauseTask(message));
                        } else if (StageExecutionUtil.isManuallySkipped(stage)) {
                            getQueue().push(new Messages.CompleteTask(message, ExecutionStatus.SKIPPED));
                        } else {
                            taskResult = executeTask(message, stage, taskModel, task);
                            handleTaskResult(message, stage, taskModel, task, taskResult, thisInvocationStartTimeMs);
                        }
                    } catch (Exception e) {
                        taskException = e;
                        handleTaskException(message, stage, taskModel, task, e, thisInvocationStartTimeMs);
                    } finally {
                        for (TaskExecutionInterceptor interceptor : taskExecutionInterceptors) {
                            interceptor.finallyAfterTaskExecution(task, stage, taskResult, taskException);
                        }
                    }
                });
            });
        });
    }


    private TaskResult executeTask(Messages.RunTask message, StageExecution stage,
                                   TaskExecution taskModel, Task task) throws Exception {
        try {
            checkForTimeout(task, stage, taskModel, message);
        } catch (TimeoutException e) {
//            registry.counter(getTimeoutCounterId(stage, taskModel)).increment();
            TaskResult timeoutResult = task.onTimeout(stage);
            if (timeoutResult == null) {
                throw e;
            }
            EnumSet<ExecutionStatus> set = EnumSet.of(ExecutionStatus.TERMINAL, ExecutionStatus.FAILED_CONTINUE);
            if (set.contains(timeoutResult.getStatus())) {
                log.error("Task {} returned invalid status ({}) for onTimeout", task.getClass().getName(), timeoutResult.getStatus());
                throw e;
            }
            return timeoutResult;
        }

        TaskResult result = task.execute(withMergedContext(stage));
        for (TaskExecutionInterceptor interceptor : taskExecutionInterceptors) {
            result = interceptor.afterTaskExecution(task, stage, result);
        }
        return result;
    }

    private void handleTaskResult(Messages.RunTask message, StageExecution stage, TaskExecution taskModel,
                                  Task task, TaskResult result, long startTimeMs) {

        switch (result.getStatus()) {
            case RUNNING:
                processTaskOutput(stage, result);
                getQueue().push(message, getBackoffPeriod(task, taskModel, stage));
                break;
            case SUCCEEDED:
            case REDIRECT:
            case SKIPPED:
            case FAILED_CONTINUE:
            case STOPPED:
                processTaskOutput(stage, result);
                getQueue().push(new Messages.CompleteTask(message, result.getStatus()));
                break;
            case CANCELED:
                processTaskOutput(stage, mergeOutputs(result, task.onCancelWithResult(stage)));
                ExecutionStatus finalStatus = StageExecutionUtil.failureStatus(stage, result.getStatus());
                getQueue().push(new Messages.CompleteTask(message, finalStatus, result.getStatus()));
                break;
            case TERMINAL:
                processTaskOutput(stage, result);
                ExecutionStatus status = StageExecutionUtil.failureStatus(stage, result.getStatus());
                getQueue().push(new Messages.CompleteTask(message, status, result.getStatus()));
                break;
            default:
                processTaskOutput(stage, result);
                throw new IllegalStateException("Unhandled task status " + result.getStatus());
        }

//        trackResult(stage, startTimeMs, taskModel, result.getStatus());
    }

    private void handleCanceledExecution(Messages.RunTask message, StageExecution stage,
                                         TaskExecution taskModel, Task task) {
        TaskResult result = task.onCancelWithResult(stage);
        if (result != null) {
            processTaskOutput(stage, result);
        }
        getQueue().push(new Messages.CompleteTask(message, ExecutionStatus.CANCELED));
    }

    private void handleTaskException(Messages.RunTask message, StageExecution stage, TaskExecution taskModel,
                                     Task task, Exception e, long startTimeMs) {
        ExceptionHandler.Response exceptionDetails = shouldRetry(exceptionHandlers, e, taskModel.getName());
        if (exceptionDetails != null && exceptionDetails.isShouldRetry()) {
            log.warn("Error running {} for {}[{}]",
                    message.getTaskType().getSimpleName(),
                    message.getExecutionType(),
                    message.getExecutionId());
            getQueue().push(message, getBackoffPeriod(task, taskModel, stage));
//            trackResult(stage, startTimeMs, taskModel, ExecutionStatus.RUNNING);
        } else if (e instanceof TimeoutException &&
                Boolean.TRUE.equals(stage.getContext().get("markSuccessfulOnTimeout"))) {
//            trackResult(stage, startTimeMs, taskModel, ExecutionStatus.SUCCEEDED);
            getQueue().push(new Messages.CompleteTask(message, ExecutionStatus.SUCCEEDED));
        } else {
            if (!(e instanceof TimeoutException)) {
//                if (e instanceof UserException) {
//                    log.warn("{} for {}[{}] failed, likely due to user error",
//                            message.getTaskType().getSimpleName(),
//                            message.getExecutionType(),
//                            message.getExecutionId(),
//                            e);
//                } else {
                log.error("Error running {} for {}[{}]",
                        message.getTaskType().getSimpleName(),
                        message.getExecutionType(),
                        message.getExecutionId(),
                        e);
//                }
            }

            ExecutionStatus status = StageExecutionUtil.failureStatus(stage, ExecutionStatus.TERMINAL);
            stage.getContext().put("exception", exceptionDetails);
            taskModel.getTaskExceptionDetails().put("exception", exceptionDetails);
            getRepository().storeStage(stage);
            getQueue().push(new Messages.CompleteTask(message, status, ExecutionStatus.TERMINAL));
//            trackResult(stage, startTimeMs, taskModel, status);
        }
    }


    private void withTask(Messages.RunTask message, Consumer3<StageExecution, TaskExecution, Task> consumer) {
        withTask(message, (stage, taskExecution) -> {
            try {
                Task task = taskResolver.getTask(taskExecution.getImplementingClass());
                consumer.accept(stage, taskExecution, task);
            } catch (TaskResolver.NoSuchTaskException e) {
                getQueue().push(new Messages.InvalidTaskType(message, message.getTaskType().getName()));
            }
        });
    }

    private void withLocking(Messages.RunTask message, Runnable action) {
        RetriableLock.RetriableLockOptions lockOptions = new RetriableLock.RetriableLockOptions(message.getStageId().toString());
        Boolean lockAcquired = retriableLock.lock(lockOptions, action);
        if (!lockAcquired) {
            log.warn("Failed to obtain lock for stage: {}. Pushing original message back to queue");
            getQueue().push(message);
        }
    }

    private void withLoggingContext(StageExecution stage, TaskExecution task, Consumer<StageExecution> action) {
        try {
            MDC.put("stageType", stage.getType());
            MDC.put("taskType", task.getImplementingClass());
            if (Objects.nonNull(task.getStartTime())) {
                MDC.put("taskStartTime", task.getStartTime().toString());
            }
            action.accept(stage);
        } finally {
            MDC.remove("stageType");
            MDC.remove("taskType");
            MDC.remove("taskStartTime");
        }
    }

    private void checkForTimeout(Task task, StageExecution stage, TaskExecution taskModel, Message message) {
        if (Objects.equals(stage.getType(), RestrictExecutionDuringTimeWindow.TYPE)) {
            return;
        }

        checkForStageTimeout(stage);
        checkForTaskTimeout(task, taskModel, stage, message);
    }

    private void checkForTaskTimeout(Task task, TaskExecution taskModel, StageExecution stage, Message message) {
        if (!(task instanceof RetryableTask)) {
            return;
        }

        RetryableTask retryableTask = (RetryableTask) task;

        Instant startTime = taskModel.getStartTime() != null ? taskModel.getStartTime() : null;

        if (startTime != null) {
            Duration pausedDuration = pausedDurationRelativeTo(stage.getPipelineExecution(), startTime);
            Duration elapsedTime = Duration.between(startTime, clock.instant());

            Duration timeout = (task instanceof OverridableTimeoutRetryableTask &&
                    stage.getParentWithTimeout().isPresent()) ?
                    Duration.ofMillis(stage.getParentWithTimeout().get().getTimeout().get()) :
                    retryableTask.getDynamicTimeout(stage);

            if (elapsedTime.minus(pausedDuration).compareTo(timeout) > 0) {
                String durationString = formatTimeout(elapsedTime.toMillis());
                StringBuilder messageStr = new StringBuilder()
                        .append(task.getClass().getSimpleName())
                        .append(" of stage ")
                        .append(stage.getName())
                        .append(" timed out after ")
                        .append(durationString)
                        .append(". ");
                messageStr.append("pausedDuration: ")
                        .append(formatTimeout(pausedDuration.toMillis()))
                        .append(", ");
                messageStr.append("elapsedTime: ")
                        .append(formatTimeout(elapsedTime.toMillis()))
                        .append(", ");
                messageStr.append("timeoutValue: ")
                        .append(formatTimeout(timeout.toMillis()));

                log.info(messageStr.toString());
                throw new TimeoutException(messageStr.toString());
            }
        }
    }

    private void checkForStageTimeout(StageExecution stage) {
        stage.getParentWithTimeout().ifPresent(parentStage -> {
            Instant startTime = parentStage.getStartTime() != null ? parentStage.getStartTime() : null;

            if (startTime != null) {
                Duration elapsedTime = Duration.between(startTime, clock.instant());
                Duration pausedDuration = pausedDurationRelativeTo(stage.getPipelineExecution(), startTime);
                Duration executionWindowDuration = getExecutionWindowDuration(stage);
                Duration timeout = Duration.ofMillis(parentStage.getTimeout().get());

                if (elapsedTime.minus(pausedDuration).minus(executionWindowDuration).compareTo(timeout) > 0) {
                    throw new TimeoutException(String.format(
                            "Stage %s timed out after %s",
                            stage.getName(),
                            formatTimeout(elapsedTime.toMillis())
                    ));
                }
            }
        });
    }

    private TemporalAmount getBackoffPeriod(Task task, TaskExecution taskModel, StageExecution stage) {
        if (task instanceof RetryableTask retryableTask) {
            return Duration.ofMillis(
                    Math.min(
                            retryableBackOffPeriod(retryableTask, taskModel, stage),
                            getMaxTaskBackoff()
                    )
            );
        }
        return Duration.ofMillis(1000);
    }

    private Long retryableBackOffPeriod(RetryableTask task, TaskExecution taskModel, StageExecution stage) {
        return task.getDynamicBackoffPeriod(
                stage,
                Duration.ofMillis(System.currentTimeMillis() - (taskModel.getStartTime() != null ? taskModel.getStartTime().toEpochMilli() : 0L))
        ).toMillis();
    }

    private long getMaxTaskBackoff() {
        return taskExecutionInterceptors.stream()
                .mapToLong(TaskExecutionInterceptor::maxTaskBackoff)
                .min()
                .orElse(Long.MAX_VALUE);
    }

    private Duration getExecutionWindowDuration(StageExecution stage) {
        return StageExecutionUtil.beforeStages(stage).stream()
                .filter(stageExecution -> Objects.equals(stageExecution.getType(), RestrictExecutionDuringTimeWindow.TYPE))
                .findFirst().map(stageExecution -> {
                    if (Objects.isNull(stageExecution.getStartTime()) || Objects.isNull(stageExecution.getEndTime())) {
                        throw new IllegalStateException("Only valid on completed stages");
                    }
                    return Duration.between(stageExecution.getStartTime(), stageExecution.getEndTime());
                }).orElse(Duration.ZERO);
    }

    private Duration pausedDurationRelativeTo(PipelineExecution execution, Instant instant) {
        PipelineExecution.PausedDetails pausedDetails = execution.getPaused();
        if (Objects.nonNull(pausedDetails)) {
            if (pausedDetails.getPauseTime().isAfter(instant)) {
                return Duration.ofMillis(pausedDetails.getPausedMs());
            } else {
                return Duration.ZERO;
            }
        }
        return Duration.ZERO;
    }

    private void processTaskOutput(StageExecution stage, TaskResult result) {
        Map<String, Object> filteredOutputs = Maps.newHashMap();
        for (Map.Entry entry : result.getOutputs().entrySet()) {
            if (Objects.equals(entry.getKey(), "stageTimeoutMs")) {
                filteredOutputs.put((String) entry.getKey(), entry.getValue());
            }
        }
        if (MapUtils.isNotEmpty(result.getContext()) || MapUtils.isNotEmpty(filteredOutputs)) {
            stage.getContext().putAll(result.getContext());
            stage.getOutputs().putAll(filteredOutputs);
            getRepository().storeStage(stage);
        }
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


    private String formatTimeout(long timeoutMs) {
        return DurationFormatUtils.formatDurationWords(timeoutMs, true, true);
    }


}
