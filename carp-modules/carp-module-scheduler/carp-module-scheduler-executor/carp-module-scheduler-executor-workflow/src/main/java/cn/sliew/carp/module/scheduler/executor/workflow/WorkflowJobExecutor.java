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
package cn.sliew.carp.module.scheduler.executor.workflow;

import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleEngineType;
import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleJobType;
import cn.sliew.carp.module.scheduler.executor.api.dict.CarpScheduleExecuteType;
import cn.sliew.carp.module.scheduler.executor.api.executor.JobExecutor;
import cn.sliew.carp.module.scheduler.executor.api.executor.entity.ScheduleResponse;
import cn.sliew.carp.module.scheduler.executor.api.executor.entity.trigger.TriggerParam;
import cn.sliew.carp.module.workflow.engine.internal.api.service.ServerlessWorkflowInstanceService;
import cn.sliew.carp.module.workflow.engine.internal.api.service.param.WorkflowRunParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class WorkflowJobExecutor implements JobExecutor {

    private final ServerlessWorkflowInstanceService workflowInstanceService;

    @Override
    public List<CarpScheduleEngineType> getEngines() {
        return Collections.singletonList(CarpScheduleEngineType.INTERNAL);
    }

    @Override
    public CarpScheduleJobType getType() {
        return CarpScheduleJobType.WORKFLOW;
    }

    @Override
    public List<CarpScheduleExecuteType> getSupportExecuteTypes() {
        return Collections.singletonList(CarpScheduleExecuteType.NATIVE);
    }

    @Override
    public ScheduleResponse execute(TriggerParam param) {
        if (MapUtils.isEmpty(param.getProps()) ||
                !param.getProps().containsKey("workflowDefinitionId")) {
            return ScheduleResponse.FAILED;
        }
        Object value = param.getProps().get("workflowDefinitionId");
        if (Objects.isNull(value)) {
            return ScheduleResponse.FAILED;
        }
        try {
            Long workflowDefinitionId = (Long) value;
            WorkflowRunParam runParam = new WorkflowRunParam();
            runParam.setId(workflowDefinitionId);
            workflowInstanceService.run(runParam);
            return ScheduleResponse.SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ScheduleResponse.FAILED;
        }
    }
}
