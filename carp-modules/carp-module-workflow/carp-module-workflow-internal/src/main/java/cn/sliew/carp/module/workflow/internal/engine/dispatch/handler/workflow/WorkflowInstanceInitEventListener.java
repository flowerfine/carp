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
package cn.sliew.carp.module.workflow.internal.engine.dispatch.handler.workflow;

import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.module.workflow.domain.enums.CarpWorkflowInstanceEvent;
import cn.sliew.carp.module.workflow.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowInstanceEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class WorkflowInstanceInitEventListener extends AbstractWorkflowInstanceEventListener<WorkflowInstanceEventDTO> {

    @Autowired
    private DagInstanceService dagInstanceService;

    @Override
    public CarpWorkflowInstanceEvent getType() {
        return CarpWorkflowInstanceEvent.COMMAND_INIT;
    }

    @Override
    protected CompletableFuture<?> handleEventAsync(WorkflowInstanceEventDTO event) {
        return CompletableFuture.runAsync(() -> init(event));
    }

    private void init(WorkflowInstanceEventDTO event) {

        WorkflowInstance workflowInstance = workflowInstanceService.get(event.getWorkflowInstanceId());
        stateMachine.deploy(workflowInstance);
    }

}
