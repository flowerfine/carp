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
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecutionImpl;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.util.StageExecutionUtil;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.core.events.ExecutionComplete;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CompleteExecutionHandler extends AbstractOrcaMessageHandler<Messages.CompleteExecution> {

    private final Duration retryDelay;

    public CompleteExecutionHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock,
            @Value("${queue.retry.delay.ms:30000}") long retryDelayMs) {
        super(queue, repository, publisher, clock);
        this.retryDelay = Duration.ofMillis(retryDelayMs);
    }

    @Override
    public Class<Messages.CompleteExecution> getMessageType() {
        return Messages.CompleteExecution.class;
    }

    @Override
    public void handle(Messages.CompleteExecution message) {
        withExecution(message, execution -> {
            if (execution.getStatus().isComplete()) {
                log.info("Execution {} already completed with {} status",
                        execution.getId(), execution.getStatus());
            } else {
                determineFinalStatus(message, execution, status -> {
                    ((PipelineExecutionImpl) execution).setStatus(status);
                    getRepository().updateStatus(execution);

                    publisher.publishEvent(new ExecutionComplete(this, execution));

                    if (status != ExecutionStatus.SUCCEEDED) {
                        execution.getStages().stream()
                                .filter(it -> it.getStatus() == ExecutionStatus.RUNNING)
                                .forEach(it -> getQueue().push(new Messages.CancelStage(it)));
                    }
                });
            }

            log.debug("Execution {} is with {} status and Disabled concurrent executions is {}",
                    execution.getId(), execution.getStatus(), execution.isLimitConcurrent());

            if (execution.getStatus() != ExecutionStatus.RUNNING) {
                Long configId = execution.getPipelineConfigId();
                if (configId != null) {
                    getQueue().push(new Messages.StartWaitingExecutions(
                            configId,
                            !execution.isKeepWaitingPipelines()
                    ));
                }
            } else {
                log.debug("Not starting waiting executions as execution {} is currently RUNNING.",
                        execution.getId());
            }
        });
    }


    private void determineFinalStatus(Messages.CompleteExecution message, PipelineExecution execution,
                                      java.util.function.Consumer<ExecutionStatus> block) {
        List<StageExecution> stages = execution.getStages().stream()
                .filter(it -> it.getParentStageId() == null)
                .collect(Collectors.toList());

        if (stages.stream()
                .map(StageExecution::getStatus)
                .allMatch(it -> Set.of(ExecutionStatus.SUCCEEDED, ExecutionStatus.SKIPPED, ExecutionStatus.FAILED_CONTINUE).contains(it))) {
            block.accept(ExecutionStatus.SUCCEEDED);
        } else if (stages.stream().anyMatch(it -> it.getStatus() == ExecutionStatus.TERMINAL)) {
            block.accept(ExecutionStatus.TERMINAL);
        } else if (stages.stream().anyMatch(it -> it.getStatus() == ExecutionStatus.CANCELED)) {
            block.accept(ExecutionStatus.CANCELED);
        } else if (stages.stream().anyMatch(it -> it.getStatus() == ExecutionStatus.STOPPED) &&
                !hasOtherBranchesIncomplete(stages)) {
            block.accept(shouldOverrideSuccess(execution) ? ExecutionStatus.TERMINAL : ExecutionStatus.SUCCEEDED);
        } else {
//            Integer attempts = message.getAttribute(AttemptsAttribute.class)
//                    .map(AttemptsAttribute::getAttempts)
//                    .orElse(0);
//            log.info("Re-queuing {} as the execution is not yet complete (attempts: {})",
//                    message, attempts);
            getQueue().push(message, retryDelay);
        }
    }

    private boolean hasOtherBranchesIncomplete(List<StageExecution> stages) {
        return stages.stream().anyMatch(it -> it.getStatus() == ExecutionStatus.RUNNING) ||
                stages.stream().anyMatch(it -> it.getStatus() == ExecutionStatus.NOT_STARTED &&
                        StageExecutionUtil.allUpstreamStagesComplete(it));
    }

    private boolean shouldOverrideSuccess(PipelineExecution execution) {
        return execution.getStages().stream()
                .filter(it -> it.getStatus() == ExecutionStatus.STOPPED)
                .anyMatch(it -> Boolean.TRUE.equals(it.getContext()
                        .get("completeOtherBranchesThenFail")));
    }


}
