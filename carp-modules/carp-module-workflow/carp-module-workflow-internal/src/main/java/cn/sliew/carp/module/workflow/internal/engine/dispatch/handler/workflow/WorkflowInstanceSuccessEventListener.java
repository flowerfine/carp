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

import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowInstanceEvent;
import cn.sliew.carp.framework.common.dict.workflow.CarpWorkflowInstanceState;
import cn.sliew.carp.framework.dag.service.DagInstanceService;
import cn.sliew.carp.framework.dag.service.dto.DagInstanceDTO;
import cn.sliew.carp.module.workflow.internal.engine.dispatch.event.WorkflowInstanceEventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Component
public class WorkflowInstanceSuccessEventListener extends AbstractWorkflowInstanceEventListener {

    @Autowired
    private DagInstanceService dagInstanceService;

    @Override
    public CarpWorkflowInstanceEvent getType() {
        return CarpWorkflowInstanceEvent.PROCESS_SUCCESS;
    }

    @Override
    protected CompletableFuture handleEventAsync(WorkflowInstanceEventDTO event) {
        return CompletableFuture.runAsync(new SuccessRunner(event.getWorkflowInstanceId()));
    }

    private class SuccessRunner implements Runnable, Serializable {

        private Long workflowInstanceId;

        public SuccessRunner(Long workflowInstanceId) {
            this.workflowInstanceId = workflowInstanceId;
        }

        @Override
        public void run() {
            DagInstanceDTO dagInstanceUpdateParam = new DagInstanceDTO();
            dagInstanceUpdateParam.setId(workflowInstanceId);
            dagInstanceUpdateParam.setStatus(CarpWorkflowInstanceState.SUCCESS.getValue());
            dagInstanceUpdateParam.setEndTime(new Date());
            dagInstanceService.update(dagInstanceUpdateParam);
        }
    }
}
