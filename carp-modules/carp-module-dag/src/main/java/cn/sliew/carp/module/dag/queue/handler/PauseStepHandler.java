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

@Component
public class PauseStepHandler extends AbstractDagMessageHandler<Messages.PauseStep> {

    @Override
    public Class<Messages.PauseStep> getMessageType() {
        return Messages.PauseStep.class;
    }

    @Override
    public void handle(Messages.PauseStep message) {
        withStep(message, workflowStepInstance -> {
            workflowStepInstance.setStatus(ExecutionStatus.PAUSED.name());
            getWorkflowRepository().updateStepInstance(workflowStepInstance);

            // 先 pause task 在触发 pause step

            // orca 没有真正意义上的 sub dag 概念，新的实现没有使用 before、after、onfailure
//            Long parentStageId = stage.getParentStageId();
//            if (parentStageId != null) {
//                queue.push(new Messages.PauseStage(message, parentStageId));
//            }
        });
    }
}
