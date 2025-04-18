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
package cn.sliew.carp.plugin.workflow.engine.temporal;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.framework.workflow.temporal.TemporalUtil;
import cn.sliew.carp.plugin.workflow.engine.api.WorkflowEngine;
import cn.sliew.carp.plugin.workflow.engine.api.dict.CarpWorkflowEngineType;
import cn.sliew.carp.plugin.workflow.engine.api.param.WorkflowInfo;
import com.fasterxml.jackson.databind.JsonNode;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.client.schedules.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class TemporalWorkflowEngine implements WorkflowEngine {

    private final TemporalProperties properties;

    @Override
    public CarpWorkflowEngineType getEngineType() {
        return CarpWorkflowEngineType.TEMPORAL;
    }

    @Override
    public void start(WorkflowInfo workflowInfo) {
        String workflowMethod = Objects.requireNonNull(workflowInfo.getBody().path("workflowMethod").asText(),
                "Workflow body lack workflowMethod");
        String queue = Objects.requireNonNull(workflowInfo.getParams().path("queue").asText(),
                "Workflow params lack queue");
        JsonNode inputs = workflowInfo.getParams().path("inputs");

//        JsonNode schedule = workflowInfo.getParams().path("schedule");
//        if (Objects.nonNull(schedule) && !schedule.isNull()) {
//            // todo parse schedule-spec
//            startSchedule(workflowInfo.getNamespace(), workflowInfo.getUuid(), workflowMethod, queue, inputs);
//            return;
//        }

        JsonNode cron = workflowInfo.getParams().path("cron");
        if (Objects.nonNull(cron) && !cron.isNull()) {
            startCronJob(workflowInfo.getNamespace(), workflowInfo.getUuid(), workflowMethod, queue, cron.asText(), inputs);
            return;
        }

        startWorkflow(workflowInfo.getNamespace(), workflowInfo.getUuid(), workflowMethod, queue, inputs);
    }

    private void startCronJob(String namespace,
                              String uuid,
                              String workflowMethod,
                              String queue,
                              String cron,
                              JsonNode inputs) {
        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setWorkflowId(uuid)
                .setWorkflowTaskTimeout(Duration.ofHours(1L))
                .setTaskQueue(queue)
                .setCronSchedule(cron)
                .build();

        WorkflowClient workflowClient = TemporalUtil.getWorkflowClient(properties.getHost(), namespace);
        WorkflowStub workflow = workflowClient.newUntypedWorkflowStub(workflowMethod, workflowOptions);
        WorkflowExecution execution = workflow.start(inputs);
        log.info("Temporal CronJob start, workflowId: {}, runId: {}",
                execution.getWorkflowId(), execution.getRunId());
    }

    private void startSchedule(String namespace,
                               String uuid,
                               String workflowMethod,
                               String queue,
                               JsonNode inputs) {
        ScheduleSpec scheduleSpec = ScheduleSpec.newBuilder()
                .setCronExpressions(Arrays.asList(""))
                .setIntervals(Arrays.asList(new ScheduleIntervalSpec(Duration.ofMinutes(1L))))
                .setTimeZoneName("GMT+8")
                .setStartAt(null)
                .setEndAt(null)
                .build();
        Schedule schedule =
                Schedule.newBuilder()
                        .setAction(
                                ScheduleActionStartWorkflow.newBuilder()
                                        .setWorkflowType(workflowMethod)
                                        .setArguments(inputs)
                                        .setOptions(
                                                WorkflowOptions.newBuilder()
                                                        .setWorkflowId(uuid)
                                                        .setTaskQueue(queue)
                                                        .build())
                                        .build())
                        .setSpec(scheduleSpec)
                        .build();
        String schedulerId = UUIDUtil.randomUUId();
        ScheduleClient scheduleClient = TemporalUtil.getScheduleClient(properties.getHost(), namespace);
        scheduleClient.createSchedule(schedulerId, schedule, ScheduleOptions.newBuilder().build());
        log.info("Temporal Schedule start, scheduleId: {}, workflowId: {}", schedulerId, uuid);
    }

    private void startWorkflow(String namespace,
                               String uuid,
                               String workflowMethod,
                               String queue,
                               JsonNode inputs) {
        WorkflowOptions workflowOptions = WorkflowOptions.newBuilder()
                .setWorkflowId(uuid)
                .setWorkflowTaskTimeout(Duration.ofHours(1L))
                .setTaskQueue(queue)
                .build();
        WorkflowClient workflowClient = TemporalUtil.getWorkflowClient(properties.getHost(), namespace);
        WorkflowStub workflow = workflowClient.newUntypedWorkflowStub(workflowMethod, workflowOptions);
        WorkflowExecution execution = workflow.start(inputs);
        log.info("Temporal Workflow start, workflowId: {}, runId: {}",
                execution.getWorkflowId(), execution.getRunId());
    }

}
