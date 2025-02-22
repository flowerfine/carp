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

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.algorithm.DagUtil;
import cn.sliew.carp.framework.dag.repository.entity.DagInstance;
import cn.sliew.carp.framework.dag.service.DagInstanceComplexService;
import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceComplexDTO;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.framework.dag.service.dto.DagStepDTO;
import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.module.workflow.stage.model.ExecutionStatus;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Component
public class StartDagHandler extends AbstractDagMessageHandler<Messages.StartDag> {

    @Autowired
    private DagInstanceComplexService dagInstanceComplexService;
    @Autowired
    private DagInstanceService dagInstanceService;

    @Override
    public Class<Messages.StartDag> getMessageType() {
        return Messages.StartDag.class;
    }

    @Override
    public void handle(Messages.StartDag message) {
        withDag(message, dagInstanceDTO -> {
            if (StringUtils.equalsIgnoreCase(dagInstanceDTO.getStatus(), ExecutionStatus.NOT_STARTED.name())
                    || isCanceled(dagInstanceDTO) == false) {
                if (shouldLimit(dagInstanceDTO)) {
                    getLog().info("Dag Instance (namespace: {}, type {}, id {}) execution limit and queue",
                            message.getNamespace(), message.getType(), message.getDagId());
                    // todo pending execution
//                    pendingExecutionService.enqueue(execution.getPipelineConfigId(), message);
                } else {
                    start(dagInstanceDTO);
                }
            } else {
                terminate(dagInstanceDTO);
            }
        });
    }

    private boolean shouldLimit(DagInstanceDTO dagInstanceDTO) {
        // 并发和速率限流
        return false;
    }

    private void start(DagInstanceDTO dagInstanceDTO) {
        if (isAfterStartTimeExpiry(dagInstanceDTO)) {
            getLog().warn("Dag Instance (namespace: {}, type {}, id {}) start was canceled because start time would be after defined start time expiry (now: {}, expiry: {})",
                    dagInstanceDTO.getNamespace(), dagInstanceDTO.getDagConfig().getType(), dagInstanceDTO.getId(), Instant.now(),
//                    dagInstanceDTO.getStartTimeExpiry()
                    null
            );
            push(new Messages.CancelExecution(dagInstanceDTO, "system", "Could not begin execution before start time expiry"));
        } else {
            DagInstanceComplexDTO dagInstanceComplexDTO = dagInstanceComplexService.selectOne(dagInstanceDTO.getId());
            DAG<DagStepDTO> dag = DagUtil.buildDag(dagInstanceComplexDTO);
            Set<DagStepDTO> initialSteps = dag.getSources();
            if (CollectionUtils.isEmpty(initialSteps)) {
                getLog().warn("Dag Instance (namespace: {}, type {}, id {}) found no initial steps",
                        dagInstanceDTO.getNamespace(), dagInstanceDTO.getDagConfig().getType(), dagInstanceDTO.getId());
                dagInstanceService.updateStatus(dagInstanceDTO.getId(), dagInstanceDTO.getStatus(), ExecutionStatus.TERMINAL.name());
            } else {
                LambdaUpdateWrapper<DagInstance> updateWrapper = Wrappers.lambdaUpdate(DagInstance.class)
                        .eq(DagInstance::getId, dagInstanceDTO.getId())
                        .eq(DagInstance::getStatus, dagInstanceDTO.getStatus())
                        .set(DagInstance::getStatus, ExecutionStatus.RUNNING.name())
                        .set(DagInstance::getStartTime, new Date());
                dagInstanceService.update(updateWrapper);
                initialSteps.forEach(stage -> push(new Messages.StartStep(stage)));
            }
        }
    }

    private void terminate(DagInstanceDTO dagInstanceDTO) {
        if (StringUtils.equalsIgnoreCase(dagInstanceDTO.getStatus(), ExecutionStatus.CANCELED.name())
                || isCanceled(dagInstanceDTO)) {
//            push(new Messages.StartWaitingExecutions(dagInstanceDTO.getDagConfig().getId(), !dagInstanceDTO.isKeepWaitingPipelines()));
        } else {
            getLog().warn("Dag Instance (namespace: {}, type: {}, id: {}, status: {}) cannot be started unless state is NOT_STARTED. Ignoring StartDag message.",
                    dagInstanceDTO.getNamespace(), dagInstanceDTO.getDagConfig().getType(), dagInstanceDTO.getId(), dagInstanceDTO.getStatus(), dagInstanceDTO.getNamespace());
        }
    }

    private boolean isAfterStartTimeExpiry(DagInstanceDTO dagInstanceDTO) {
//        return Objects.nonNull(dagInstanceDTO.getStartTimeExpiry()) && dagInstanceDTO.getStartTimeExpiry().isBefore(clock.instant());
        return false;
    }
}
