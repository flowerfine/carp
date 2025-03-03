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

import cn.sliew.carp.module.dag.queue.Messages;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class AbortStepHandler extends AbstractDagMessageHandler<Messages.AbortStep> {

    private static final Set<String> RUNNING_STATUSES =
            Set.of(ExecutionStatus.RUNNING.name(), ExecutionStatus.NOT_STARTED.name());

    @Override
    public Class<Messages.AbortStep> getMessageType() {
        return Messages.AbortStep.class;
    }

    @Override
    public void handle(Messages.AbortStep message) {
        withStep(message, stepInstance -> {
            if (RUNNING_STATUSES.contains(stepInstance.getStatus())) {
                stepInstance.setStatus(ExecutionStatus.TERMINAL.name());
                stepInstance.setEndTime(new Date());
                getWorkflowRepository().updateStepInstance(stepInstance);

                push(new Messages.CancelStep(message));

//                if (stepInstance.getParentStageId() == null) {
//                    getQueue().push(new Messages.CompleteExecution(message));
//                } else {
//                    getQueue().push(new Messages.CompleteStage(stepInstance.getParent()));
//                }

                push(new Messages.CompleteWorkflow(message));

//                publisher.publishEvent(new StageComplete(this, stepInstance));
            }
        });
    }
}
