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
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageGraphBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageGraphBuilderImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.util.StageExecutionUtil;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageDefinitionBuilderFactory;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageDefinitionBuilders;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.core.events.StageComplete;
import cn.sliew.carp.module.orca.spinnaker.orca.core.exceptions.ExceptionHandler;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.util.ContextParameterProcessor;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class CompleteStageHandler extends AbstractOrcaMessageHandler<Messages.CompleteStage> implements StageBuilderAware, ExpressionAware {

    private final List<ExceptionHandler> exceptionHandlers;
    private final StageDefinitionBuilderFactory stageDefinitionBuilderFactory;
    private final ContextParameterProcessor contextParameterProcessor;

    public CompleteStageHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock,
            List<ExceptionHandler> exceptionHandlers,
            StageDefinitionBuilderFactory stageDefinitionBuilderFactory,
            ContextParameterProcessor contextParameterProcessor) {
        super(queue, repository, publisher, clock);
        this.exceptionHandlers = exceptionHandlers;
        this.stageDefinitionBuilderFactory = stageDefinitionBuilderFactory;
        this.contextParameterProcessor = contextParameterProcessor;
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public Class<Messages.CompleteStage> getMessageType() {
        return Messages.CompleteStage.class;
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
    public void handle(Messages.CompleteStage message) {
        withStage(message, stage -> {
            EnumSet<ExecutionStatus> statuses = EnumSet.of(ExecutionStatus.RUNNING, ExecutionStatus.NOT_STARTED);
            if (statuses.contains(stage.getStatus())) {
                ExecutionStatus status = determineStatus(stage);
                if (shouldFailOnFailedExpressionEvaluation(stage)) {
                    log.warn(
                            "Stage {} ({}) of {} is set to fail because of failed expressions.",
                            stage.getId(),
                            stage.getType(),
                            stage.getPipelineExecution().getId()
                    );
                    status = ExecutionStatus.TERMINAL;
                }

                try {
                    if (statuses.contains(stage.getStatus()) || (status.isComplete() && !status.isHalt())) {
                        // 检查此阶段是否有任何未计划的合成后置阶段
                        List<StageExecution> afterStages = StageExecutionUtil.firstAfterStages(stage);
                        if (CollectionUtils.isEmpty(afterStages)) {
                            planAfterStages(stage);
                            afterStages = StageExecutionUtil.firstAfterStages(stage);
                        }
                        if (!afterStages.isEmpty() && afterStages.stream().anyMatch(it -> it.getStatus() == ExecutionStatus.NOT_STARTED)) {
                            afterStages.stream()
                                    .filter(it -> it.getStatus() == ExecutionStatus.NOT_STARTED)
                                    .forEach(it -> getQueue().push(new Messages.StartStage(message, it.getId())));
                            return;
                        } else if (status == ExecutionStatus.NOT_STARTED) {
                            // 阶段没有合成阶段或任务，这很奇怪但无论如何
                            log.warn("Stage {} ({}) of {} had no tasks or synthetic stages!",
                                    stage.getId(), stage.getType(), stage.getPipelineExecution().getId());
                            status = ExecutionStatus.SKIPPED;
                        }
                    } else if (status.isFailure()) {
                        boolean hasOnFailureStages = planOnFailureStages(stage);

                        if (hasOnFailureStages) {
                            StageExecutionUtil.firstAfterStages(stage).forEach(it -> getQueue().push(new Messages.StartStage(it)));
                            return;
                        }
                    }

                    ((StageExecutionImpl) stage).setStatus(status);
                    if (status == ExecutionStatus.FAILED_CONTINUE) {
                        handleFailedContinue(stage);
                    }
                    ((StageExecutionImpl) stage).setEndTime(clock.instant());
                } catch (Exception e) {
                    log.error("Failed to construct after stages for {} {}", stage.getName(), stage.getId(), e);

                    ExceptionHandler.Response exceptionDetails = shouldRetry(exceptionHandlers, e, stage.getName() + ":ConstructAfterStages");
                    stage.getContext().put("exception", exceptionDetails);
                    ((StageExecutionImpl) stage).setStatus(ExecutionStatus.TERMINAL);
                    ((StageExecutionImpl) stage).setEndTime(clock.instant());
                }

                includeExpressionEvaluationSummary(stage);
                getRepository().storeStage(stage);

                EnumSet<ExecutionStatus> set = EnumSet.of(ExecutionStatus.SUCCEEDED, ExecutionStatus.FAILED_CONTINUE, ExecutionStatus.SKIPPED);
                // 当合成阶段以 FAILED_CONTINUE 结束时，将该状态传播到阶段的父级
                // 这样父级的其他合成子阶段就不会运行
                if (stage.getStatus() == ExecutionStatus.FAILED_CONTINUE &&
                        stage.getSyntheticStageOwner() != null &&
                        !stage.getAllowSiblingStagesToContinueOnFailure()) {
                    Messages.CompleteStage copyMessage = new Messages.CompleteStage(message, stage.getParentStageId());
                    getQueue().push(copyMessage);
                } else if (set.contains(stage.getStatus())) {
                    startNext(stage);
                } else {
                    getQueue().push(new Messages.CancelStage(message));
                    if (stage.getSyntheticStageOwner() == null) {
                        log.debug("Stage has no synthetic owner and status is {}, completing execution (original message: {})",
                                stage.getStatus(), message);
                        getQueue().push(new Messages.CompleteExecution(message));
                    } else {
                        Messages.CompleteStage copyMessage = new Messages.CompleteStage(message, stage.getParentStageId());
                        getQueue().push(copyMessage);
                    }
                }

                publisher.publishEvent(new StageComplete(this, stage));
            }
        });
    }


    private void handleFailedContinue(StageExecution stage) {
        @SuppressWarnings("unchecked")
        Map<String, Object> exception = (Map<String, Object>) stage.getContext().get("exception");
        if (exception != null) {
            @SuppressWarnings("unchecked")
            Map<String, Object> details = (Map<String, Object>) exception.get("details");
            if (details != null) {
                @SuppressWarnings("unchecked")
                List<String> errors = (List<String>) details.get("errors");
                List<String> updatedErrors = Lists.newArrayList();

                stage.getTasks().stream()
                        .filter(t -> !t.getTaskExceptionDetails().isEmpty())
                        .forEach(task -> {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> taskException =
                                    (Map<String, Object>) task.getTaskExceptionDetails().get("exception");
                            @SuppressWarnings("unchecked")
                            Map<String, Object> taskDetails =
                                    (Map<String, Object>) taskException.get("details");

                            updatedErrors.add(task.getName() + ":");
                            updatedErrors.add((String) taskDetails.get("error"));
                            @SuppressWarnings("unchecked")
                            List<String> taskErrors = (List<String>) taskDetails.get("errors");
                            updatedErrors.addAll(taskErrors);
                        });

                if (errors != null) {
                    errors.addAll(updatedErrors);
                } else {
                    details.put("errors", updatedErrors);
                }
            }
        }
    }

    private ExecutionStatus determineStatus(StageExecution stage) {
        List<ExecutionStatus> syntheticStatuses = StageExecutionUtil.syntheticStages(stage).stream()
                .map(StageExecution::getStatus)
                .collect(Collectors.toList());

        List<ExecutionStatus> taskStatuses = stage.getTasks().stream()
                .map(TaskExecution::getStatus)
                .collect(Collectors.toList());

        List<ExecutionStatus> planningStatus = hasPlanningFailure(stage) ?
                Collections.singletonList(StageExecutionUtil.failureStatus(stage, null)) :
                Collections.emptyList();

        List<ExecutionStatus> allStatuses = new ArrayList<>();
        allStatuses.addAll(syntheticStatuses);
        allStatuses.addAll(taskStatuses);
        allStatuses.addAll(planningStatus);

        List<ExecutionStatus> afterStageStatuses = StageExecutionUtil.afterStages(stage).stream()
                .map(StageExecution::getStatus)
                .collect(Collectors.toList());

        if (allStatuses.isEmpty()) {
            return ExecutionStatus.NOT_STARTED;
        } else if (allStatuses.contains(ExecutionStatus.TERMINAL)) {
            return StageExecutionUtil.failureStatus(stage, null); // 正确处理配置的"如果阶段失败"选项
        } else if (allStatuses.contains(ExecutionStatus.STOPPED)) {
            return ExecutionStatus.STOPPED;
        } else if (allStatuses.contains(ExecutionStatus.CANCELED)) {
            return ExecutionStatus.CANCELED;
        } else if (allStatuses.contains(ExecutionStatus.FAILED_CONTINUE)) {
            return ExecutionStatus.FAILED_CONTINUE;
        } else if (allStatuses.stream().allMatch(it -> it == ExecutionStatus.SUCCEEDED || it == ExecutionStatus.SKIPPED)) {
            return ExecutionStatus.SUCCEEDED;
        } else if (afterStageStatuses.contains(ExecutionStatus.NOT_STARTED)) {
            return ExecutionStatus.RUNNING; // 后置阶段已计划但尚未运行
        } else {
            log.error("Unhandled condition for stage {} of {}, marking as TERMINAL. " +
                            "syntheticStatuses={}, taskStatuses={}, planningStatus={}, afterStageStatuses={}",
                    stage.getId(),
                    stage.getPipelineExecution().getId(),
                    syntheticStatuses,
                    taskStatuses,
                    planningStatus,
                    afterStageStatuses);

            return ExecutionStatus.TERMINAL;
        }
    }

    private boolean hasPlanningFailure(StageExecution stage) {
        Object value = stage.getContext().get("beforeStagePlanningFailed");
        return Objects.nonNull(value) && (boolean) value;
    }

    private void planAfterStages(StageExecution stage) {
        StageDefinitionBuilders.buildAfterStages(builder(stage), stage, newStage -> {
            getRepository().addStage(newStage);
            ((StageExecutionImpl) stage).setPipelineExecution(getRepository().retrieve(stage.getPipelineExecution().getType(), stage.getPipelineExecution().getId()));
        });
    }

    private boolean planOnFailureStages(StageExecution stage) {
        // 避免计划失败阶段，如果任何同名的阶段已经完成

        List<String> previouslyPlannedAfterStageNames = StageExecutionUtil.afterStages(stage).stream()
                .filter(it -> it.getStatus().isComplete())
                .map(StageExecution::getName)
                .collect(Collectors.toList());

        StageGraphBuilder graph = StageGraphBuilderImpl.afterStages(stage);
        builder(stage).onFailureStages(stage, graph);

        List<StageExecution> onFailureStages = StreamSupport.stream(graph.build().spliterator(), false)
                .collect(Collectors.toList());
        // 所有失败处理阶段应该线性运行
        for (int i = 1; i < onFailureStages.size(); i++) {
            graph.connect(onFailureStages.get(i - 1), onFailureStages.get(i));
        }

        boolean alreadyPlanned = onFailureStages.stream()
                .anyMatch(it -> previouslyPlannedAfterStageNames.contains(it.getName()));

        if (alreadyPlanned || onFailureStages.isEmpty()) {
            return false;
        } else {
            // 应该移除所有合成阶段（没有任何阶段应该已经开始！）
            removeNotStartedSynthetics(stage);
            appendAfterStages(stage, onFailureStages, item -> getRepository().addStage(item));
            return true;
        }
    }

    /**
     * 移除未开始的合成阶段
     */
    private void removeNotStartedSynthetics(StageExecution stage) {

        StageExecutionUtil.syntheticStages(stage).stream()
                .filter(it -> it.getStatus() == ExecutionStatus.NOT_STARTED)
                .forEach(syntheticStage -> {
                    // 更新依赖此合成阶段的其他阶段的引用
                    stage.getPipelineExecution().getStages().stream()
                            .filter(it -> it.getRequisiteStageRefIds().contains(syntheticStage.getId()))
                            .forEach(dependentStage -> {
                                ((StageExecutionImpl) dependentStage).setRequisiteStageRefIds(
                                        dependentStage.getRequisiteStageRefIds().stream()
                                                .filter(id -> !id.equals(syntheticStage.getId()))
                                                .collect(Collectors.toSet())
                                );
                                getRepository().addStage(dependentStage);
                            });

                    // 递归移除此合成阶段的子合成阶段
                    removeNotStartedSynthetics(syntheticStage);

                    // 从执行中移除此合成阶段
                    getRepository().removeStage(stage.getPipelineExecution(), syntheticStage.getId());
                });
    }

    /**
     * 添加后置阶段
     */
    private void appendAfterStages(
            StageExecution stage,
            List<StageExecution> afterStages,
            Consumer<StageExecution> callback) {
        if (afterStages.isEmpty()) {
            return;
        }

        afterStages.forEach(callback);

        ((StageExecutionImpl) stage).setPipelineExecution(getRepository().retrieve(
                stage.getPipelineExecution().getType(),
                stage.getPipelineExecution().getId()
        ));
    }

}
