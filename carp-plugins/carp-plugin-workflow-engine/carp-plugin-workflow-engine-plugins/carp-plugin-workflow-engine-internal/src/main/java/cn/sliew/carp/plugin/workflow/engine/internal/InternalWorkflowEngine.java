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

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleEngineType;
import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleJobType;
import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleStatus;
import cn.sliew.carp.framework.common.dict.schedule.CarpScheduleType;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.scheduler.executor.api.dict.CarpScheduleExecuteType;
import cn.sliew.carp.module.scheduler.executor.api.scheduler.JobScheduler;
import cn.sliew.carp.module.scheduler.service.ScheduleJobConfigService;
import cn.sliew.carp.module.scheduler.service.ScheduleJobGroupService;
import cn.sliew.carp.module.scheduler.service.ScheduleJobInstanceService;
import cn.sliew.carp.module.scheduler.service.dto.ScheduleJobConfigDTO;
import cn.sliew.carp.module.scheduler.service.dto.ScheduleJobGroupDTO;
import cn.sliew.carp.module.scheduler.service.dto.ScheduleJobInstanceDTO;
import cn.sliew.carp.module.scheduler.service.param.*;
import cn.sliew.carp.plugin.workflow.engine.api.WorkflowEngine;
import cn.sliew.carp.plugin.workflow.engine.api.dict.CarpWorkflowEngineType;
import cn.sliew.carp.plugin.workflow.engine.api.param.WorkflowInfo;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static cn.sliew.milky.common.check.Ensures.checkState;

@RequiredArgsConstructor
public class InternalWorkflowEngine implements WorkflowEngine {

    private static final String SCHEDULE_JOB_GROUP_NAME = "internal-workflow-engine";

    private final ScheduleJobGroupService scheduleJobGroupService;
    private final ScheduleJobConfigService scheduleJobConfigService;
    private final ScheduleJobInstanceService scheduleJobInstanceService;
    private final JobScheduler jobScheduler;

    @Override
    public CarpWorkflowEngineType getEngineType() {
        return CarpWorkflowEngineType.INTERNAL;
    }

    /**
     * 同步至调度模块，定时执行
     */
    @Override
    public Map<String, Object> start(WorkflowInfo workflowInfo) {
        Long jobGroupId = findJobGroupId(workflowInfo.getNamespace());
        Long jobConfigId = findJobConfigId(jobGroupId, workflowInfo.getName());

        Long dagConfigId = workflowInfo.getBody().path("dagConfigId").asLong();
        ObjectNode props = JacksonUtil.createObjectNode();
        props.put("dagConfigId", dagConfigId);
        JsonNode params = workflowInfo.getParams();
        JsonNode trigger = Objects.requireNonNull(workflowInfo.getTrigger(), "trigger");

        String cron = Objects.requireNonNull(trigger.path("schedule").path("cron").asText());
        String timezone = Objects.requireNonNull(trigger.path("schedule").path("timezone").asText());
        ArrayNode arrayNode = (ArrayNode) Objects.requireNonNull(trigger.path("schedule").path("validTime"));
        Date startTime = DateUtil.parse(
                Objects.requireNonNull(arrayNode.get(0).asText()),
                DatePattern.NORM_DATETIME_PATTERN);
        Date endTime = DateUtil.parse(
                Objects.requireNonNull(arrayNode.get(1).asText()),
                DatePattern.NORM_DATETIME_PATTERN);

        Long jobInstanceId = findJobInstanceId(jobConfigId, workflowInfo.getUuid(), cron, timezone, startTime, endTime);
        ScheduleJobInstanceDTO jobInstanceDTO = scheduleJobInstanceService.get(jobInstanceId);
        checkState(Objects.equals(jobInstanceDTO.getStatus(), CarpScheduleStatus.STOP), () -> "Illegal schedule job instance status");
        ScheduleJobInstanceUpdateParam updateParam = ScheduleJobInstanceUpdateParam.builder()
                .id(jobInstanceId)
                .name(workflowInfo.getUuid())
                .cron(cron)
                .timezone(timezone)
                .startTime(startTime)
                .endTime(endTime)
                .props(props)
                .params(Objects.nonNull(params) ? params.toString() : null)
                .remark("InternalWorkflowEngine generated")
                .build();
        scheduleJobInstanceService.update(updateParam);
        jobScheduler.schedule(jobInstanceId);
        return Map.of("scheduleJobInstanceId", jobInstanceId);
    }

    @Override
    public void stop(WorkflowInfo workflowInfo) {
        JsonNode jsonNode = workflowInfo.getTrigger().path("status").path("scheduleJobInstanceId");
        if (Objects.isNull(jsonNode) || jsonNode.isNull()) {
            throw new IllegalStateException("Workflow instance trigger lack status");
        }
        Long scheduleJobInstanceId = jsonNode.asLong();
        jobScheduler.unschedule(scheduleJobInstanceId);
    }

    private Long findJobGroupId(String namespace) {
        ScheduleJobGroupPageParam pageParam = ScheduleJobGroupPageParam.builder()
                .namespace(namespace)
                .name(SCHEDULE_JOB_GROUP_NAME)
                .build();
        PageResult<ScheduleJobGroupDTO> pageResult = scheduleJobGroupService.list(pageParam);
        if (CollectionUtils.isEmpty(pageResult.getRecords())) {
            ScheduleJobGroupAddParam addParam = ScheduleJobGroupAddParam.builder()
                    .namespace(namespace)
                    .name(SCHEDULE_JOB_GROUP_NAME)
                    .remark("InternalWorkflowEngine generated")
                    .build();
            return scheduleJobGroupService.add(addParam);
        } else if (pageResult.getRecords().size() == 1) {
            return pageResult.getRecords().get(0).getId();
        } else {
            throw new IllegalStateException("More than one schedule job group for namespace: " + namespace + ", name: " + SCHEDULE_JOB_GROUP_NAME);
        }
    }

    private Long findJobConfigId(Long jobGroupId, String name) {
        ScheduleJobConfigPageParam pageParam = ScheduleJobConfigPageParam.builder()
                .jobGroupId(jobGroupId)
                .type(CarpScheduleType.USER)
                .jobType(CarpScheduleJobType.WORKFLOW)
                .name(name)
                .build();
        PageResult<ScheduleJobConfigDTO> pageResult = scheduleJobConfigService.list(pageParam);
        if (CollectionUtils.isEmpty(pageResult.getRecords())) {
            ScheduleJobConfigAddParam addParam = ScheduleJobConfigAddParam.builder()
                    .jobGroupId(jobGroupId)
                    .engineType(CarpScheduleEngineType.INTERNAL)
                    .jobType(CarpScheduleJobType.WORKFLOW)
                    .executeType(CarpScheduleExecuteType.NATIVE)
                    .handler("cn.sliew.carp.module.scheduler.quartz.service.QuartzJobHandler")
                    .name(name)
                    .remark("InternalWorkflowEngine generated")
                    .build();
            return scheduleJobConfigService.add(addParam);
        } else if (pageResult.getRecords().size() == 1) {
            return pageResult.getRecords().get(0).getId();
        } else {
            throw new IllegalStateException("More than one schedule job config for jobGroupId: " + jobGroupId + ", name: " + name);
        }
    }

    private Long findJobInstanceId(Long jobConfigId, String uuid, String cron, String timezone, Date startTime, Date endTime) {
        ScheduleJobInstancePageParam pageParam = ScheduleJobInstancePageParam.builder()
                .jobConfigId(jobConfigId)
                .name(uuid)
                .build();
        PageResult<ScheduleJobInstanceDTO> pageResult = scheduleJobInstanceService.list(pageParam);
        if (CollectionUtils.isEmpty(pageResult.getRecords())) {
            ScheduleJobInstanceAddParam addParam = ScheduleJobInstanceAddParam.builder()
                    .jobConfigId(jobConfigId)
                    .name(uuid)
                    .cron(cron)
                    .timezone(timezone)
                    .startTime(startTime)
                    .endTime(endTime)
                    .remark("InternalWorkflowEngine generated")
                    .build();
            return scheduleJobInstanceService.add(addParam);
        } else if (pageResult.getRecords().size() == 1) {
            return pageResult.getRecords().get(0).getId();
        } else {
            throw new IllegalStateException("More than one schedule job instance for jobConfigId: " + jobConfigId + ", name: " + uuid);
        }
    }

}
