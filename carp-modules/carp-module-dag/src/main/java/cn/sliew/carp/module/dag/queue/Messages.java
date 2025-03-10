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
package cn.sliew.carp.module.dag.queue;

import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import cn.sliew.carp.module.workflow.stage.model.domain.definition.WorkflowDefinition;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.TaskExecution;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowInstance;
import cn.sliew.carp.module.workflow.stage.model.domain.instance.WorkflowStepInstance;
import cn.sliew.carp.module.workflow.stage.model.task.Task;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Map;

public class Messages {

    /**
     * Messages used internally by the queueing system.
     */
    interface NamespaceAware {

        String getNamespace();
    }

    public interface WorkflowLevel extends NamespaceAware {

        String getType();

        Long getDagId();
    }

    public interface StepLevel extends WorkflowLevel {

        Long getStepId();
    }

    public interface TaskLevel extends StepLevel {

        Long getTaskId();
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("initWorkflow")
    public static class InitWorkflow implements NamespaceAware, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagConfigId;
        private Map<String, Object> inputs;
        private Map<String, Map<String, Object>> stepInputs;

        public InitWorkflow(WorkflowDefinition source) {
            this(source.getNamespace(), source.getType(), source.getId(), null, null);
        }

        public InitWorkflow(WorkflowDefinition source, Map<String, Object> inputs, Map<String, Map<String, Object>> stepInputs) {
            this(source.getNamespace(), source.getType(), source.getId(), inputs, stepInputs);
        }

    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("startWorkflow")
    public static class StartWorkflow implements WorkflowLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;

        public StartWorkflow(WorkflowInstance source) {
            this(source.getNamespace(), source.getDefinition().getType(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("rescheduleWorkflow")
    public static class RescheduleWorkflow implements WorkflowLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;

        public RescheduleWorkflow(WorkflowInstance source) {
            this(source.getNamespace(), source.getDefinition().getType(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("completeWorkflow")
    public static class CompleteWorkflow implements WorkflowLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;

        public CompleteWorkflow(WorkflowLevel source) {
            this(source.getNamespace(), source.getType(), source.getDagId());
        }

        public CompleteWorkflow(WorkflowInstance source) {
            this(source.getNamespace(), source.getDefinition().getType(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("resumeWorkflow")
    public static class ResumeWorkflow implements WorkflowLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;

        public ResumeWorkflow(WorkflowInstance source) {
            this(source.getNamespace(), source.getDefinition().getType(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("cancelWorkflow")
    public static class CancelWorkflow implements WorkflowLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private String user;
        private String reason;

        public CancelWorkflow(WorkflowLevel source) {
            this(source, null, null);
        }

        public CancelWorkflow(WorkflowLevel source, String user, String reason) {
            this(source.getNamespace(), source.getType(), source.getDagId(), user, reason);
        }

        public CancelWorkflow(WorkflowInstance source) {
            this(source, null, null);
        }

        public CancelWorkflow(WorkflowInstance source, String user, String reason) {
            this(source.getNamespace(), source.getDefinition().getType(), source.getId(), user, reason);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("startWaitingExecutions")
    public static class StartWaitingExecutions implements Serializable {
        private static final long serialVersionUID = 1L;
        private Long pipelineConfigId;
        private Boolean purgeQueue;
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("startStep")
    public static class StartStep implements StepLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;

        public StartStep(StepLevel source) {
            this(source, source.getStepId());
        }

        public StartStep(WorkflowLevel source, Long stepId) {
            this(source.getNamespace(), source.getType(), source.getDagId(), stepId);
        }

        public StartStep(WorkflowStepInstance source) {
            this(source.getWorkflowInstance().getNamespace(), source.getWorkflowInstance().getDefinition().getType(), source.getWorkflowInstance().getId(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("continueParentStep")
    public static class ContinueParentStep implements StepLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;

        public ContinueParentStep(StepLevel source) {
            this(source.getNamespace(), source.getType(), source.getDagId(), source.getStepId());
        }

        public ContinueParentStep(WorkflowStepInstance source) {
            this(source.getWorkflowInstance().getNamespace(), source.getWorkflowInstance().getDefinition().getType(), source.getWorkflowInstance().getId(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("completeStep")
    public static class CompleteStep implements StepLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;

        public CompleteStep(StepLevel source) {
            this(source, source.getStepId());
        }

        public CompleteStep(WorkflowLevel source, Long stepId) {
            this(source.getNamespace(), source.getType(), source.getDagId(), stepId);
        }

        public CompleteStep(WorkflowStepInstance source) {
            this(source.getWorkflowInstance().getNamespace(), source.getWorkflowInstance().getDefinition().getType(), source.getWorkflowInstance().getId(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("skipStep")
    public static class SkipStep implements StepLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;

        public SkipStep(StepLevel source) {
            this(source.getNamespace(), source.getType(), source.getDagId(), source.getStepId());
        }

        public SkipStep(WorkflowStepInstance source) {
            this(source.getWorkflowInstance().getNamespace(), source.getWorkflowInstance().getDefinition().getType(), source.getWorkflowInstance().getId(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("abortStep")
    public static class AbortStep implements StepLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;

        public AbortStep(StepLevel source) {
            this(source.getNamespace(), source.getType(), source.getDagId(), source.getStepId());
        }

        public AbortStep(WorkflowStepInstance source) {
            this(source.getWorkflowInstance().getNamespace(), source.getWorkflowInstance().getDefinition().getType(), source.getWorkflowInstance().getId(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("pauseStep")
    public static class PauseStep implements StepLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;

        public PauseStep(StepLevel source) {
            this(source, source.getStepId());
        }

        public PauseStep(WorkflowLevel source, Long stepId) {
            this(source.getNamespace(), source.getType(), source.getDagId(), stepId);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("restartStep")
    public static class RestartStep implements StepLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;
        private String user;

        public RestartStep(WorkflowInstance source, Long stepId, String user) {
            this(source.getNamespace(), source.getDefinition().getType(), source.getId(), stepId, user);
        }

        public RestartStep(WorkflowStepInstance source, String user) {
            this(source.getWorkflowInstance().getNamespace(), source.getWorkflowInstance().getDefinition().getType(), source.getWorkflowInstance().getId(), source.getId(), user);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("resumeStep")
    public static class ResumeStep implements StepLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;

        public ResumeStep(WorkflowLevel source, Long stepId) {
            this(source.getNamespace(), source.getType(), source.getDagId(), stepId);
        }

        public ResumeStep(WorkflowStepInstance source) {
            this(source.getNamespace(), source.getWorkflowInstance().getDefinition().getType(), source.getWorkflowInstance().getId(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("cancelStep")
    public static class CancelStep implements StepLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;

        public CancelStep(StepLevel source) {
            this(source.getNamespace(), source.getType(), source.getDagId(), source.getStepId());
        }

        public CancelStep(WorkflowLevel source, Long stepId) {
            this(source.getNamespace(), source.getType(), source.getDagId(), stepId);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("startTask")
    public static class StartTask implements TaskLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;
        private final Long taskId;

        public StartTask(WorkflowLevel source, Long stepId, Long taskId) {
            this(source.getNamespace(), source.getType(), source.getDagId(),
                    stepId, taskId);
        }

        public StartTask(StepLevel source, Long taskId) {
            this(source, source.getStepId(), taskId);
        }

        public StartTask(WorkflowStepInstance source, Long taskId) {
            this(source.getWorkflowInstance().getNamespace(), source.getWorkflowInstance().getDefinition().getType(),
                    source.getWorkflowInstance().getId(), source.getId(), taskId);
        }

        public StartTask(WorkflowStepInstance source, TaskExecution task) {
            this(source.getWorkflowInstance().getNamespace(), source.getWorkflowInstance().getDefinition().getType(),
                    source.getWorkflowInstance().getId(), source.getId(), task.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("runTask")
    public static class RunTask implements TaskLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;
        private final Long taskId;
        private Class<? extends Task> taskType;

        public RunTask(TaskLevel source, Class<? extends Task> taskType) {
            this(source.getNamespace(), source.getType(), source.getDagId(),
                    source.getStepId(), source.getTaskId(), taskType);
        }

        public RunTask(StepLevel source, Long taskId, Class<? extends Task> taskType) {
            this(source.getNamespace(), source.getType(), source.getDagId(),
                    source.getStepId(), taskId, taskType);
        }

        public RunTask(WorkflowLevel source, Long stepId, Long taskId, Class<? extends Task> taskType) {
            this(source.getNamespace(), source.getType(), source.getDagId(),
                    stepId, taskId, taskType);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("completeTask")
    public static class CompleteTask implements TaskLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;
        private final Long taskId;
        private ExecutionStatus status;
        private ExecutionStatus originalStatus;

        public CompleteTask(TaskLevel source, ExecutionStatus status) {
            this(source, status, null);
        }

        public CompleteTask(TaskLevel source, ExecutionStatus status, ExecutionStatus originalStatus) {
            this(source.getNamespace(), source.getType(), source.getDagId(),
                    source.getStepId(), source.getTaskId(), status, originalStatus);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("pauseTask")
    public static class PauseTask implements TaskLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;
        private final Long taskId;

        public PauseTask(TaskLevel source) {
            this(source.getNamespace(), source.getType(), source.getDagId(),
                    source.getStepId(), source.getTaskId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("resumeTask")
    public static class ResumeTask implements TaskLevel, Serializable {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;
        private final Long taskId;

        public ResumeTask(StepLevel source, Long taskId) {
            this(source.getNamespace(), source.getType(), source.getDagId(),
                    source.getStepId(), taskId);
        }
    }

    public static abstract class ConfigurationError implements WorkflowLevel, Serializable {

    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("invalidWorkflowId")
    public static class InvalidWorkflowId extends ConfigurationError {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;

        public InvalidWorkflowId(WorkflowLevel source) {
            this(source.getNamespace(), source.getType(), source.getDagId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("invalidStepId")
    public static class InvalidStepId extends ConfigurationError implements StepLevel {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;

        public InvalidStepId(StepLevel source) {
            this(source.getNamespace(), source.getType(), source.getDagId(), source.getStepId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("invalidTaskId")
    public static class InvalidTaskId extends ConfigurationError implements TaskLevel {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;
        private final Long taskId;

        public InvalidTaskId(TaskLevel source) {
            this(source.getNamespace(), source.getType(), source.getDagId(), source.getStepId(), source.getTaskId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("invalidTaskType")
    public static class InvalidTaskType extends ConfigurationError implements StepLevel {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;
        private String className;

        public InvalidTaskType(StepLevel source, String className) {
            this(source.getNamespace(), source.getType(), source.getDagId(), source.getStepId(), className);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("noDownstreamTasks")
    public static class NoDownstreamTasks extends ConfigurationError implements TaskLevel {
        private static final long serialVersionUID = 1L;
        private final String namespace;
        private final String type;
        private final Long dagId;
        private final Long stepId;
        private final Long taskId;

        public NoDownstreamTasks(TaskLevel source) {
            this(source.getNamespace(), source.getType(), source.getDagId(), source.getStepId(), source.getTaskId());
        }
    }

}
