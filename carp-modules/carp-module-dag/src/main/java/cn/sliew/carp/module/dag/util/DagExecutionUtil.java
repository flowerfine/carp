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
package cn.sliew.carp.module.dag.util;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecution;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecutionImpl;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.stage.model.task.Task;
import cn.sliew.carp.module.workflow.stage.model.task.TaskExecutionInterceptor;
import cn.sliew.carp.module.workflow.stage.model.task.TaskResult;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nullable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public enum DagExecutionUtil {
    ;

    private static final EnumSet<ExecutionStatus> STAGE_FAILED_STATUS = EnumSet.of(
            ExecutionStatus.TERMINAL,
            ExecutionStatus.STOPPED,
            ExecutionStatus.CANCELED
    );

    private static final EnumSet<ExecutionStatus> STAGE_COMPLETE_STATUS = EnumSet.of(
            ExecutionStatus.SUCCEEDED,
            ExecutionStatus.FAILED_CONTINUE,
            ExecutionStatus.SKIPPED
    );

    public static boolean anyUpstreamStepsFailed(DAG<WorkflowStepInstance> dag, WorkflowStepInstance stepInstance) {
        return dag.inDegreeOf(stepInstance).stream()
                .anyMatch(s -> STAGE_FAILED_STATUS.contains(s.getStatus())
                        || (StringUtils.equalsIgnoreCase(s.getStatus(), ExecutionStatus.NOT_STARTED.name()) && anyUpstreamStepsFailed(dag, s)));
    }

    public static boolean allUpstreamStepsComplete(DAG<WorkflowStepInstance> dag, WorkflowStepInstance stepInstance) {
        return dag.inDegreeOf(stepInstance).stream()
                .allMatch(s -> STAGE_COMPLETE_STATUS.contains(s.getStatus())
                        && allUpstreamStepsComplete(dag, s));
    }

    private static WorkflowStepInstance findNode(DAG<WorkflowStepInstance> dag, WorkflowStepInstance stepInstance) {
        return dag.nodes().stream().filter(s -> Objects.equals(s.getId(), stepInstance.getId())).findFirst().orElseThrow();
    }

    public static ExecutionStatus failureStatus(WorkflowStepInstance stepInstance, ExecutionStatus defaultStatus) {
        if (Objects.isNull(defaultStatus)) {
            defaultStatus = ExecutionStatus.TERMINAL;
        }
//        if (stage.getContinuePipelineOnFailure()) {
//            return ExecutionStatus.FAILED_CONTINUE;
//        }
        return shouldFailPipeline(stepInstance) ? defaultStatus : ExecutionStatus.STOPPED;
    }

    public static boolean shouldFailPipeline(WorkflowStepInstance stepInstance) {
//        Object failPipeline = stage.getContext().get("failPipeline");
//        return failPipeline == null || Boolean.TRUE.equals(failPipeline);
        return false;
    }

    public static List<TaskExecutionImpl> getTasks(WorkflowStepInstance stepInstance) {
        if (Objects.isNull(stepInstance.getBody())
                || stepInstance.getBody().isNull()
                || stepInstance.getBody().isEmpty()) {
            return Collections.emptyList();
        }
        JsonNode tasks = stepInstance.getBody().path("tasks");
        if (tasks.isNull() || tasks.isEmpty() || tasks.isArray() == false) {
            return Collections.emptyList();
        }

        return JacksonUtil.toObject(stepInstance.getBody().path("tasks"), new TypeReference<List<TaskExecutionImpl>>() {
        });
    }

    public static TaskExecution getTasks(WorkflowStepInstance stepInstance, Long taskId) {
        return getTasks(stepInstance).stream().filter(it -> Objects.equals(it.getId(), taskId)).findFirst().orElse(null);
    }

    public static TaskExecutionImpl firstTask(WorkflowStepInstance stepInstance) {
        List<TaskExecutionImpl> tasks = getTasks(stepInstance);
        return tasks.isEmpty() ? null : tasks.get(0);
    }

    @Nullable
    public static TaskExecution nextTask(WorkflowStepInstance stepInstance, TaskExecution task) {
        if (task.isStageEnd()) {
            return null;
        }
        List<TaskExecutionImpl> tasks = getTasks(stepInstance);
        int index = tasks.indexOf(task);

        if (index == tasks.size() - 1) {
            return null;
        }
        return CollectionUtils.get(tasks, index + 1);
    }

    public static WorkflowStepInstance beforeTask(List<TaskExecutionInterceptor> interceptors, WorkflowStepInstance stepInstance, Task task) {
        if (CollectionUtils.isEmpty(interceptors)) {
            return stepInstance;
        }
        for (TaskExecutionInterceptor interceptor : interceptors) {
            stepInstance = interceptor.beforeTaskExecution(task, stepInstance);
        }
        return stepInstance;
    }

    public static TaskResult afterTask(List<TaskExecutionInterceptor> interceptors,
                                       WorkflowStepInstance stepInstance, Task task, TaskResult taskResult) {
        if (CollectionUtils.isEmpty(interceptors)) {
            return taskResult;
        }
        for (TaskExecutionInterceptor interceptor : interceptors) {
            taskResult = interceptor.afterTaskExecution(task, stepInstance, taskResult);
        }
        return taskResult;
    }

    public static void finallyTask(List<TaskExecutionInterceptor> interceptors,
                                   WorkflowStepInstance stepInstance, Task task, TaskResult taskResult, Exception taskException) {
        if (CollectionUtils.isEmpty(interceptors)) {
            return;
        }
        for (TaskExecutionInterceptor interceptor : interceptors) {
            interceptor.finallyAfterTaskExecution(task, stepInstance, taskResult, taskException);
        }
    }

}
