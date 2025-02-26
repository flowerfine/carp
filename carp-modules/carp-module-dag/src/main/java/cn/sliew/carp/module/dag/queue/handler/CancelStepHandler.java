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

import cn.hutool.core.thread.ThreadUtil;
import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.dag.util.DagExecutionUtil;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.graph.CancellableStage;
import cn.sliew.carp.module.workflow.stage.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.workflow.stage.model.graph.StageDefinitionBuilderFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Slf4j
@Component
public class CancelStepHandler extends AbstractDagMessageHandler<Messages.CancelStep> implements StepBuilderAware {

    private final Executor executor = ThreadUtil.newExecutor(4, 8);

    @Autowired
    private StageDefinitionBuilderFactory stageDefinitionBuilderFactory;

    @Override
    public Class<Messages.CancelStep> getMessageType() {
        return Messages.CancelStep.class;
    }

    @Override
    public StageDefinitionBuilderFactory getStageDefinitionBuilderFactory() {
        return stageDefinitionBuilderFactory;
    }

    @Override
    public void handle(Messages.CancelStep message) {
        withStep(message, stepInstance -> {
            // 当执行以非 SUCCEEDED 状态结束时，仍在运行的阶段
            // 保持在 RUNNING 状态，直到它们的运行任务被出队到 RunTaskHandler。
            // 对于使用 getDynamicBackoffPeriod() 的任务，阶段可能会错误地
            // 报告为 RUNNING 相当长的时间，除非我们短路它们的回退时间。
            if (StringUtils.equalsIgnoreCase(stepInstance.getStatus(), ExecutionStatus.RUNNING.name())) {
                DagExecutionUtil.getTasks(stepInstance).stream()
                        .filter(task -> StringUtils.equalsIgnoreCase(task.getStatus(), ExecutionStatus.RUNNING.name()))
                        .forEach(task -> {
//                            getQueue().reschedule(new Messages.RunTask(
//                                    dagStepDTO.getPipelineExecution().getType(),
//                                    dagStepDTO.getPipelineExecution().getId(),
//                                    dagStepDTO.getPipelineExecution().getNamespace(),
//                                    dagStepDTO.getId(),
//                                    task.getId(),
//                                    getTaskType(task)
//                            ));
                        });
            }

            if (ExecutionStatus.valueOf(stepInstance.getStatus()).isHalt()) {
                StageDefinitionBuilder builder = builder(stepInstance);
                if (builder instanceof CancellableStage) {
                    // 暂时将其作为离线线程执行，因为某些取消例程
                    // 可能运行足够长的时间导致消息确认超时
                    Runnable cancellationClosure = () -> {
                        ((CancellableStage) builder).cancel(stepInstance);

                        // PipelineStage 的特殊情况，确保及时取消
                        // 子管道和部署策略，而不考虑任务回退
//                        if (dagStepDTO.getType().equalsIgnoreCase("pipeline") &&
//                                dagStepDTO.getContext().containsKey("executionId")) {
//                            Long childId = (Long) dagStepDTO.getContext().get("executionId");
//                            if (childId != null) {
//                                PipelineExecution pipelineExecution = getRepository().retrieve(ExecutionType.PIPELINE, childId);
//                                push(new Messages.RescheduleExecution(pipelineExecution));
//                            }
//                        }
                    };
                    executor.execute(cancellationClosure);
                }
            }
        });
    }
}
