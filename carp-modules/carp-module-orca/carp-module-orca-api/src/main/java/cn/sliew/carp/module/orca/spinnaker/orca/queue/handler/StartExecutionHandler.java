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
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.util.PipelineExecutionUtil;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.core.events.ExecutionComplete;
import cn.sliew.carp.module.orca.spinnaker.orca.core.events.ExecutionStarted;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.pending.PendingExecutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Clock;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class StartExecutionHandler extends AbstractOrcaMessageHandler<Messages.StartExecution> {

    private PendingExecutionService pendingExecutionService;

    public StartExecutionHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock,
            PendingExecutionService pendingExecutionService) {
        super(queue, repository, publisher, clock);
        this.pendingExecutionService = pendingExecutionService;
    }

    @Override
    public Class<Messages.StartExecution> getMessageType() {
        return Messages.StartExecution.class;
    }

    @Override
    public void handle(Messages.StartExecution message) {
        withExecution(message, execution -> {
            if (execution.getStatus() == ExecutionStatus.NOT_STARTED
                    || execution.isCanceled() == false) {
                if (shouldQueue(execution)) {
                    if (execution.getPipelineConfigId() != null) {
                        log.info("Queueing {} {} {}", execution.getNamespace(), execution.getName(), execution.getId());
                        pendingExecutionService.enqueue(execution.getPipelineConfigId(), message);
                    }
                } else {
                    start(execution);
                }
            } else {
                terminate(execution);
            }
        });
    }


    private void start(PipelineExecution execution) {
        if (isAfterStartTimeExpiry(execution)) {
            log.warn("Execution (type {}, id {}, namespace: {}) start was canceled because start time would be after defined start time expiry (now: {}, expiry: {})",
                    execution.getType(), execution.getId(), execution.getNamespace(), clock.instant(), execution.getStartTimeExpiry());
            getQueue().push(new Messages.CancelExecution(execution.getType(), execution.getId(), execution.getNamespace(), "spinnaker", "Could not begin execution before start time expiry"));
        } else {
            List<StageExecution> initialStages = PipelineExecutionUtil.initialStages(execution);
            if (CollectionUtils.isEmpty(initialStages)) {
                log.warn("No initial stages found (executionId: {})", execution.getId());
                execution.updateStatus(ExecutionStatus.TERMINAL);
                getRepository().updateStatus(execution);
                publisher.publishEvent(new ExecutionComplete(this, execution));
            } else {
                execution.updateStatus(ExecutionStatus.RUNNING);
                getRepository().updateStatus(execution);
                initialStages.forEach(stage -> getQueue().push(new Messages.StartStage(stage)));
                publisher.publishEvent(new ExecutionStarted(this, execution));
            }
        }
    }

    private void terminate(PipelineExecution execution) {
        if (execution.getStatus() == ExecutionStatus.CANCELED || execution.isCanceled()) {
            publisher.publishEvent(new ExecutionComplete(this, execution));
            Long pipelineConfigId = execution.getPipelineConfigId();
            if (pipelineConfigId != null) {
                getQueue().push(new Messages.StartWaitingExecutions(pipelineConfigId, !execution.isKeepWaitingPipelines()));
            }
        } else {
            log.warn("Execution (type: {}, id: {}, status: {}, namespace: {}) cannot be started unless state is NOT_STARTED. Ignoring StartExecution message.",
                    execution.getType(), execution.getId(), execution.getStatus(), execution.getNamespace());
        }
    }

    private boolean isAfterStartTimeExpiry(PipelineExecution execution) {
        return Objects.nonNull(execution.getStartTimeExpiry()) && execution.getStartTimeExpiry().isBefore(clock.instant());
    }


}