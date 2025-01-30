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
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.resolver.StageDefinitionBuilderFactory;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.pending.PendingExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RestartStageHandler extends AbstractOrcaMessageHandler<Messages.RestartStage> implements StageBuilderAware {

    private final StageDefinitionBuilderFactory stageDefinitionBuilderFactory;
    private final PendingExecutionService pendingExecutionService;

    public RestartStageHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock,
            StageDefinitionBuilderFactory stageDefinitionBuilderFactory,
            PendingExecutionService pendingExecutionService) {
        super(queue, repository, publisher, clock);
        this.stageDefinitionBuilderFactory = stageDefinitionBuilderFactory;
        this.pendingExecutionService = pendingExecutionService;
    }

    @Override
    public Class<Messages.RestartStage> getMessageType() {
        return Messages.RestartStage.class;
    }

    @Override
    public StageDefinitionBuilderFactory getStageDefinitionBuilderFactory() {
        return stageDefinitionBuilderFactory;
    }

    @Override
    public void handle(Messages.RestartStage message) {
        withStage(message, stage -> {
            // If RestartStage is requested for a synthetic stage, operate on its parent
            StageExecution topStage = stage.getTopLevelStage();
            Messages.StartStage startMessage = new Messages.StartStage(
                    message.getExecutionType(),
                    message.getExecutionId(),
                    message.getNamespace(),
                    topStage.getId());

            if (topStage.getStatus().isComplete() || topStage.getStatus() == ExecutionStatus.NOT_STARTED) {
                addRestartDetails(topStage, message.getUser());
                reset(topStage);
                resetChildren(topStage);

                if (shouldQueue(stage.getPipelineExecution())) {
                    // this pipeline is already running and has limitConcurrent = true
                    if (topStage.getPipelineExecution().getStatus() == ExecutionStatus.NOT_STARTED) {
                        log.info("Skipping queueing restart of {} {} {}",
                                stage.getPipelineExecution().getNamespace(),
                                stage.getPipelineExecution().getName(),
                                stage.getPipelineExecution().getId());
                        return;
                    }
                    ((PipelineExecutionImpl) topStage.getPipelineExecution()).setStatus(ExecutionStatus.NOT_STARTED);
                    getRepository().updateStatus(topStage.getPipelineExecution());
                    Long pipelineConfigId = stage.getPipelineExecution().getPipelineConfigId();
                    if (pipelineConfigId != null) {
                        log.info("Queueing restart of {} {} {}",
                                stage.getPipelineExecution().getNamespace(),
                                stage.getPipelineExecution().getName(),
                                stage.getPipelineExecution().getId());
                        pendingExecutionService.enqueue(pipelineConfigId, message);
                    }
                } else {
                    restartParentPipelineIfNeeded(message, topStage);
                    ((PipelineExecutionImpl) topStage.getPipelineExecution()).setStatus(ExecutionStatus.RUNNING);
                    getRepository().updateStatus(topStage.getPipelineExecution());
                    getQueue().push(new Messages.StartStage(startMessage));
                }
            }
        });
    }

    private void restartParentPipelineIfNeeded(Messages.RestartStage message, StageExecution topStage) {
        // todo PipelineTrigger
//        if (!(topStage.getPipelineExecution().getTrigger() instanceof PipelineTrigger)) {
//            return;
//        }
//
//        PipelineTrigger trigger = (PipelineTrigger) topStage.getExecution().getTrigger();
//        if (trigger.getParentPipelineStageId() == null) {
//            // Must've been triggered by dependent pipeline, we don't restart those
//            return;
//        }
//
//        // We have a copy of the parent execution, not the live one. So we retrieve the live one.
//        var parentExecution = getRepository().retrieve(trigger.getParentExecution().getType(),
//                trigger.getParentExecution().getId());
//
//        if (!parentExecution.getStatus().isComplete()) {
//            // only attempt to restart the parent pipeline if it's not running
//            return;
//        }
//
//        StageExecution parentStage = parentExecution.stageById(trigger.getParentPipelineStageId());
//        addSkipRestart(parentStage);
//        getRepository().storeStage(parentStage);
//
//        getQueue().push(new Messages.RestartStage(trigger.getParentExecution(), parentStage.getId(), message.getUser()));
    }

    private void addSkipRestart(StageExecution stage) {
        stage.getContext().put("_skipPipelineRestart", true);
    }

    private void addRestartDetails(StageExecution stage, String user) {
        Map<String, Object> restartDetails = new HashMap<>();
        restartDetails.put("restartedBy", user != null ? user : "anonymous");
        restartDetails.put("restartTime", clock.millis());
        restartDetails.put("previousException", stage.getContext().remove("exception"));
        stage.getContext().put("restartDetails", restartDetails);
    }

    private void reset(StageExecution stage) {
        if (stage.getStatus().isComplete()) {
            StageExecutionImpl stageImpl = (StageExecutionImpl) stage;
            stageImpl.setStatus(ExecutionStatus.NOT_STARTED);
            stageImpl.setStartTime(null);
            stageImpl.setEndTime(null);
            stageImpl.setTasks(Collections.emptyList());
            builder(stage).prepareStageForRestart(stage);
            getRepository().storeStage(stage);
            removeSynthetics(stage);
        }
    }

    private void resetChildren(StageExecution stage) {
        stage.allDownstreamStages().forEach(this::reset);
    }

    private void removeSynthetics(StageExecution stage) {
        stage.getPipelineExecution().getStages().stream()
                .filter(s -> s.getParentStageId() != null && s.getParentStageId().equals(stage.getId()))
                .forEach(syntheticStage -> {
                    removeSynthetics(syntheticStage);
                    getRepository().removeStage(stage.getPipelineExecution(), syntheticStage.getId());
                });
    }

}
