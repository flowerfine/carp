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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ResumeWorkflowHandler extends AbstractDagMessageHandler<Messages.ResumeWorkflow> {

    @Override
    public Class<Messages.ResumeWorkflow> getMessageType() {
        return Messages.ResumeWorkflow.class;
    }

    @Override
    public void handle(Messages.ResumeWorkflow message) {
        withWorkflow(message, workflowInstance -> {
            getWorkflowRepository().getStepInstances(workflowInstance.getId()).stream()
                    .filter(stepInstance -> StringUtils.equalsIgnoreCase(stepInstance.getStatus(), ExecutionStatus.PAUSED.name()))
                    .forEach(stepInstance -> push(new Messages.ResumeStep(message, stepInstance.getId())));
        });
    }
}
