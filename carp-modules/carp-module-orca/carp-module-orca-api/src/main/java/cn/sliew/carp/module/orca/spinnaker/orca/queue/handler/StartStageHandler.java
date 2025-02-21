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
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageNavigator;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.util.StageExecutionUtil;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageDefinitionBuilderFactory;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageDefinitionBuilders;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.TaskImplementationResolver;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.core.events.StageStarted;
import cn.sliew.carp.module.orca.spinnaker.orca.core.exceptions.ExceptionHandler;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.OptionalStageSupport;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.expressions.PipelineExpressionEvaluator;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.util.ContextParameterProcessor;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class StartStageHandler extends AbstractOrcaMessageHandler<Messages.StartStage> implements StageBuilderAware, ExpressionAware {

    private final StageNavigator stageNavigator;
    private final StageDefinitionBuilderFactory stageDefinitionBuilderFactory;
    private final ContextParameterProcessor contextParameterProcessor;
    private final List<ExceptionHandler> exceptionHandlers;
    private final ObjectMapper objectMapper;
    private final Duration retryDelay;
    private final TaskImplementationResolver taskImplementationResolver;

    public StartStageHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock,
            StageNavigator stageNavigator,
            StageDefinitionBuilderFactory stageDefinitionBuilderFactory,
            ContextParameterProcessor contextParameterProcessor,
            List<ExceptionHandler> exceptionHandlers,
            ObjectMapper objectMapper,
            @Value("${queue.retry.delay.ms:15000}") Long retryDelay,
            TaskImplementationResolver taskImplementationResolver) {
        super(queue, repository, publisher, clock);
        this.stageNavigator = stageNavigator;
        this.stageDefinitionBuilderFactory = stageDefinitionBuilderFactory;
        this.contextParameterProcessor = contextParameterProcessor;
        this.exceptionHandlers = exceptionHandlers;
        this.objectMapper = objectMapper;
        this.retryDelay = Duration.ofMillis(retryDelay);
        this.taskImplementationResolver = taskImplementationResolver;
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public Class<Messages.StartStage> getMessageType() {
        return Messages.StartStage.class;
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
    public void handle(Messages.StartStage message) {
        withStage(message, stage -> {
            try {
                StageExecutionImpl stageImpl = (StageExecutionImpl) stage;
                if (StageExecutionUtil.anyUpstreamStagesFailed(stageImpl)) {
                    log.warn("Tried to start stage {} but something upstream had failed (executionId: {})",
                            stageImpl.getId(), message.getExecutionId());
                    getQueue().push(new Messages.CompleteExecution(message));
                } else if (StageExecutionUtil.allUpstreamStagesComplete(stageImpl)) {
                    if (stageImpl.getStatus() != ExecutionStatus.NOT_STARTED) {
                        log.warn("Ignoring {} as stage is already {}", message, stage.getStatus());
                    } else if (shouldSkip(stageImpl)) {
                        getQueue().push(new Messages.SkipStage(message));
                    } else if (isAfterStartTimeExpiry(stageImpl)) {
                        log.warn("Stage is being skipped because its start time is after TTL (stageId: {}, executionId: {})",
                                stageImpl.getId(), message.getExecutionId());
                        getQueue().push(new Messages.SkipStage(stageImpl));
                    } else {
                        try {
                            stageImpl.setStartTime(clock.instant());
                            plan(stage);
                            stageImpl.setStatus(ExecutionStatus.RUNNING);
                            getRepository().storeStage(stage);

                            start(stage);

                            publisher.publishEvent(new StageStarted(this, stage));
//                            trackResult(stage);
                        } catch (Exception e) {
                            handlePlanningException(message, stage, e);
                        }
                    }
                } else {
                    log.info("Re-queuing {} as upstream stages are not yet complete", message);
                    getQueue().push(message, retryDelay);
                }
            } catch (Exception e) {
                handleUnexpectedException(message, stage, e);
            }
        });
    }

    private boolean shouldSkip(StageExecution stage) {
        if (stage.getPipelineExecution().getType() != ExecutionType.PIPELINE) {
            return false;
        }

        Map<String, Object> clonedContext = new HashMap<>(stage.getContext());
        StageExecutionImpl clonedStage = new StageExecutionImpl(stage.getPipelineExecution(), stage.getType(), clonedContext);
        clonedStage.setRefId(stage.getRefId());
        clonedStage.setRequisiteStageRefIds(stage.getRequisiteStageRefIds());
        clonedStage.setSyntheticStageOwner(stage.getSyntheticStageOwner());
        clonedStage.setParentStageId(stage.getParentStageId());

        if (clonedStage.getContext().containsKey(PipelineExpressionEvaluator.SUMMARY)) {
            stage.getContext().put(PipelineExpressionEvaluator.SUMMARY, clonedStage.getContext().get(PipelineExpressionEvaluator.SUMMARY));
        }

        return OptionalStageSupport.isOptional(withMergedContext(clonedStage), contextParameterProcessor);
    }

    private boolean isAfterStartTimeExpiry(StageExecution stage) {
        return Objects.nonNull(stage.getStartTimeExpiry()) &&
                stage.getStartTimeExpiry().isBefore(clock.instant());
    }

    private void plan(StageExecution stage) {
        StageDefinitionBuilder builder = builder(stage);
        StageExecution mergedStage = stage.getParentStageId() == null ? withMergedContext(stage) : stage;
        StageDefinitionBuilders.addContextFlags(builder, mergedStage);
        StageDefinitionBuilders.buildTasks(builder, mergedStage, taskImplementationResolver);
        StageDefinitionBuilders.buildBeforeStages(builder, mergedStage, newStage ->
                getRepository().addStage(withMergedContext(newStage)));
    }

    private void start(StageExecution stage) {
        List<StageExecution> beforeStages = StageExecutionUtil.firstBeforeStages(stage);
        if (CollectionUtils.isEmpty(beforeStages)) {
            TaskExecution task = StageExecutionUtil.firstTask(stage);
            if (task == null) {
                List<StageExecution> afterStages = StageExecutionUtil.firstAfterStages(stage);
                if (afterStages.isEmpty()) {
                    getQueue().push(new Messages.CompleteStage(stage));
                } else {
                    afterStages.forEach(s -> getQueue().push(new Messages.StartStage(s)));
                }
            } else {
                // task 是有序的。。。
                getQueue().push(new Messages.StartTask(stage, task.getId()));
            }
        } else {
            beforeStages.forEach(s -> getQueue().push(new Messages.StartStage(s)));
        }
    }

    private void handlePlanningException(Messages.StartStage message, StageExecution stage, Exception e) {
        ExceptionHandler.Response exceptionDetails = shouldRetry(exceptionHandlers, e, stage.getName());
        if (Boolean.TRUE.equals(exceptionDetails.isShouldRetry())) {
//            int attempts = message.getAttribute(AttemptsAttribute.class) != null ?
//                    message.getAttribute(AttemptsAttribute.class).getAttempts() : 0;
//            log.warn("Error planning {} stage for {}[{}] (attempts: {})",
//                    stage.getType(), message.getExecutionType(), message.getExecutionId(), attempts);
//
//            message.setAttribute(new MaxAttemptsAttribute(40));
            getQueue().push(message, retryDelay);
        } else {
            log.error("Error running {}[{}] stage for {}[{}]",
                    stage.getType(), stage.getId(), message.getExecutionType(), message.getExecutionId(), e);
            stage.getContext().put("exception", exceptionDetails);
            stage.getContext().put("beforeStagePlanningFailed", true);
            getRepository().storeStage(stage);
            getQueue().push(new Messages.CompleteStage(message));
        }
    }

    private void handleUnexpectedException(Messages.StartStage message, StageExecution stage, Exception e) {
        log.error("Error running {}[{}] stage for {}[{}]",
                stage.getType(), stage.getId(),
                message.getExecutionType(), message.getExecutionId(), e);

        ExceptionHandler.Response exceptionDetails = shouldRetry(exceptionHandlers, e, stage.getName());
        stage.getContext().put("exception", exceptionDetails);
        stage.getContext().put("beforeStagePlanningFailed", true);

        getRepository().storeStage(stage);
        getQueue().push(new Messages.CompleteStage(message));
    }


}