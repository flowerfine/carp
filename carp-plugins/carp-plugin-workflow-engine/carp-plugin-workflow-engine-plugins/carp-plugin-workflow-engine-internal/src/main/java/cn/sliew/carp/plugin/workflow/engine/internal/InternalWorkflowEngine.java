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
package cn.sliew.carp.plugin.workflow.engine.internal;

import cn.sliew.carp.plugin.workflow.engine.api.WorkflowEngine;
import cn.sliew.carp.plugin.workflow.engine.api.dict.CarpWorkflowEngineType;
import cn.sliew.carp.plugin.workflow.engine.api.param.WorkflowInfo;
import cn.sliew.carp.module.workflow.engine.internal.api.service.ServerlessWorkflowInstanceService;
import cn.sliew.carp.module.workflow.engine.internal.api.service.param.WorkflowRunParam;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class InternalWorkflowEngine implements WorkflowEngine {

    private final ServerlessWorkflowInstanceService workflowInstanceService;

    @Override
    public CarpWorkflowEngineType getEngineType() {
        return CarpWorkflowEngineType.INTERNAL;
    }

    @Override
    public void start(WorkflowInfo workflowInfo) {
        // todo 如果包含调度信息，则应该同步至调度，交由调度执行
        Long dagConfigId = workflowInfo.getBody().path("dagConfigId").asLong();
        Objects.requireNonNull(dagConfigId, "Not Found dagConfigId in body");
        JsonNode inputs = workflowInfo.getParams().path("inputs");
        JsonNode variables = workflowInfo.getParams().path("variables");
        WorkflowRunParam runParam = WorkflowRunParam.builder()
                .id(dagConfigId)
                .globalVariable(variables)
                .build();
        workflowInstanceService.run(runParam);
    }
}
