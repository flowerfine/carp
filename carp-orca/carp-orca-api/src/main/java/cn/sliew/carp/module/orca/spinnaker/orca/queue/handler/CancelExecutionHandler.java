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
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.orca.core.events.ExecutionComplete;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.Messages;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CancelExecutionHandler extends AbstractOrcaMessageHandler<Messages.CancelExecution> {

    public CancelExecutionHandler(
            Queue queue,
            ExecutionRepository repository,
            ApplicationEventPublisher publisher,
            Clock clock) {
        super(queue, repository, publisher, clock);
    }

    @Override
    public Class<Messages.CancelExecution> getMessageType() {
        return Messages.CancelExecution.class;
    }

    @Override
    public void handle(Messages.CancelExecution message) {
        withExecution(message, execution -> {
            // 取消执行
            getRepository().cancel(execution.getType(), execution.getId(), message.getUser(), message.getReason());

            // 恢复所有暂停的阶段，以便它们的 RunTaskHandler 可以执行
            // 并处理 "canceled" 标志
            List<StageExecution> pausedStages = execution.getStages().stream()
                    .filter(stage -> stage.getStatus() == ExecutionStatus.PAUSED)
                    .collect(Collectors.toList());

            pausedStages.forEach(stage ->
                    getQueue().push(new Messages.ResumeStage(stage))
            );

            // 确保这些 runTask 消息立即运行
            getQueue().push(new Messages.RescheduleExecution(execution));

            // 更新执行状态并发布事件
            ((PipelineExecutionImpl) execution).setStatus(ExecutionStatus.CANCELED);
            publisher.publishEvent(new ExecutionComplete(this, execution));
        });
    }


}
