package cn.sliew.carp.module.orca.spinnaker.orca.queue;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.Task;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

public class Messages {

    /**
     * Messages used internally by the queueing system.
     */
    interface NamespaceAware {

        String getNamespace();
    }

    public interface ExecutionLevel extends NamespaceAware {

        ExecutionType getExecutionType();

        Long getExecutionId();
    }

    public interface StageLevel extends ExecutionLevel {

        Long getStageId();
    }

    public interface TaskLevel extends StageLevel {

        Long getTaskId();
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("startExecution")
    public static class StartExecution extends Message implements ExecutionLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;

        public StartExecution(PipelineExecution source) {
            this(source.getType(), source.getId(), source.getNamespace());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("rescheduleExecution")
    public static class RescheduleExecution extends Message implements ExecutionLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;

        public RescheduleExecution(PipelineExecution source) {
            this(source.getType(), source.getId(), source.getNamespace());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("completeExecution")
    public static class CompleteExecution extends Message implements ExecutionLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;

        public CompleteExecution(ExecutionLevel source) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace());
        }

        public CompleteExecution(PipelineExecution source) {
            this(source.getType(), source.getId(), source.getNamespace());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("resumeExecution")
    public static class ResumeExecution extends Message implements ExecutionLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;

        public ResumeExecution(PipelineExecution source) {
            this(source.getType(), source.getId(), source.getNamespace());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("cancelExecution")
    public static class CancelExecution extends Message implements ExecutionLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private String user;
        private String reason;

        public CancelExecution(ExecutionLevel source) {
            this(source, null, null);
        }

        public CancelExecution(ExecutionLevel source, String user, String reason) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), user, reason);
        }

        public CancelExecution(PipelineExecution source) {
            this(source, null, null);
        }

        public CancelExecution(PipelineExecution source, String user, String reason) {
            this(source.getType(), source.getId(), source.getNamespace(), user, reason);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("startWaitingExecutions")
    public static class StartWaitingExecutions extends Message {
        private String pipelineConfigId;
        private Boolean purgeQueue;
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("startStage")
    public static class StartStage extends Message implements StageLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;

        public StartStage(StageLevel source) {
            this(source, source.getStageId());
        }

        public StartStage(ExecutionLevel source, Long stageId) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), stageId);
        }

        public StartStage(StageExecution source) {
            this(source.getPipelineExecution().getType(), source.getPipelineExecution().getId(), source.getPipelineExecution().getNamespace(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("continueParentStage")
    public static class ContinueParentStage extends Message implements StageLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;
        private SyntheticStageOwner phase = SyntheticStageOwner.STAGE_BEFORE;

        public ContinueParentStage(StageLevel source, SyntheticStageOwner phase) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), source.getStageId(), phase);
        }

        public ContinueParentStage(StageExecution source, SyntheticStageOwner phase) {
            this(source.getPipelineExecution().getType(), source.getPipelineExecution().getId(), source.getPipelineExecution().getNamespace(), source.getId(), phase);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("completeStage")
    public static class CompleteStage extends Message implements StageLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;

        public CompleteStage(StageLevel source) {
            this(source, source.getStageId());
        }

        public CompleteStage(ExecutionLevel source, Long stageId) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), stageId);
        }

        public CompleteStage(StageExecution source) {
            this(source.getPipelineExecution().getType(), source.getPipelineExecution().getId(), source.getPipelineExecution().getNamespace(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("skipStage")
    public static class SkipStage extends Message implements StageLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;

        public SkipStage(StageLevel source) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), source.getStageId());
        }

        public SkipStage(StageExecution source) {
            this(source.getPipelineExecution().getType(), source.getPipelineExecution().getId(), source.getPipelineExecution().getNamespace(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("abortStage")
    public static class AbortStage extends Message implements StageLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;

        public AbortStage(StageLevel source) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), source.getStageId());
        }

        public AbortStage(StageExecution source) {
            this(source.getPipelineExecution().getType(), source.getPipelineExecution().getId(), source.getPipelineExecution().getNamespace(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("pauseStage")
    public static class PauseStage extends Message implements StageLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;

        public PauseStage(StageLevel source) {
            this(source, source.getStageId());
        }

        public PauseStage(ExecutionLevel source, Long stageId) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), stageId);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("restartStage")
    public static class RestartStage extends Message implements StageLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;
        private String user;

        public RestartStage(PipelineExecution source, Long stageId, String user) {
            this(source.getType(), source.getId(), source.getNamespace(), stageId, user);
        }

        public RestartStage(StageExecution source, String user) {
            this(source.getPipelineExecution().getType(), source.getPipelineExecution().getId(), source.getPipelineExecution().getNamespace(), source.getId(), user);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("resumeStage")
    public static class ResumeStage extends Message implements StageLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;

        public ResumeStage(ExecutionLevel source, Long stageId) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), stageId);
        }

        public ResumeStage(StageExecution source) {
            this(source.getPipelineExecution().getType(), source.getPipelineExecution().getId(), source.getPipelineExecution().getNamespace(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("cancelStage")
    public static class CancelStage extends Message implements StageLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;

        public CancelStage(StageLevel source) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), source.getStageId());
        }

        public CancelStage(StageExecution source) {
            this(source.getPipelineExecution().getType(), source.getPipelineExecution().getId(), source.getPipelineExecution().getNamespace(), source.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("startTask")
    public static class StartTask extends Message implements TaskLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;
        private final Long taskId;

        public StartTask(ExecutionLevel source, Long stageId, Long taskId) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(),
                    stageId, taskId);
        }

        public StartTask(StageLevel source, Long taskId) {
            this(source, source.getStageId(), taskId);
        }

        public StartTask(StageExecution source, Long taskId) {
            this(source.getPipelineExecution().getType(), source.getPipelineExecution().getId(),
                    source.getPipelineExecution().getNamespace(), source.getId(), taskId);
        }

        public StartTask(StageExecution source, TaskExecution task) {
            this(source.getPipelineExecution().getType(), source.getPipelineExecution().getId(),
                    source.getPipelineExecution().getNamespace(), source.getId(), task.getId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("runTask")
    public static class RunTask extends Message implements TaskLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;
        private final Long taskId;
        private Class<? extends Task> taskType;

        public RunTask(TaskLevel source, Class<? extends Task> taskType) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(),
                    source.getStageId(), source.getTaskId(), taskType);
        }

        public RunTask(StageLevel source, Long taskId, Class<? extends Task> taskType) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(),
                    source.getStageId(), taskId, taskType);
        }

        public RunTask(ExecutionLevel source, Long stageId, Long taskId, Class<? extends Task> taskType) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(),
                    stageId, taskId, taskType);
        }

        @Override
        public Long getAckTimeoutMs() {
            return Duration.ofMinutes(10).toMillis();
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("completeTask")
    public static class CompleteTask extends Message implements TaskLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;
        private final Long taskId;
        private ExecutionStatus status;
        private ExecutionStatus originalStatus;

        public CompleteTask(TaskLevel source, ExecutionStatus status) {
            this(source, status, null);
        }

        public CompleteTask(TaskLevel source, ExecutionStatus status, ExecutionStatus originalStatus) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(),
                    source.getStageId(), source.getTaskId(), status, originalStatus);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("pauseTask")
    public static class PauseTask extends Message implements TaskLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;
        private final Long taskId;

        public PauseTask(TaskLevel source) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(),
                    source.getStageId(), source.getTaskId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("resumeTask")
    public static class ResumeTask extends Message implements TaskLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;
        private final Long taskId;

        public ResumeTask(StageLevel source, Long taskId) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(),
                    source.getStageId(), taskId);
        }
    }

    public static abstract class ConfigurationError extends Message implements ExecutionLevel {

    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("invalidExecutionId")
    public static class InvalidExecutionId extends ConfigurationError {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;

        public InvalidExecutionId(ExecutionLevel source) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("invalidStageId")
    public static class InvalidStageId extends ConfigurationError implements StageLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;

        public InvalidStageId(StageLevel source) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), source.getStageId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("invalidTaskId")
    public static class InvalidTaskId extends ConfigurationError implements TaskLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;
        private final Long taskId;

        public InvalidTaskId(TaskLevel source) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), source.getStageId(), source.getTaskId());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("invalidTaskType")
    public static class InvalidTaskType extends ConfigurationError implements StageLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;
        private String className;

        public InvalidTaskType(StageLevel source, String className) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), source.getStageId(), className);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonTypeName("noDownstreamTasks")
    public static class NoDownstreamTasks extends ConfigurationError implements TaskLevel {
        private final ExecutionType executionType;
        private final Long executionId;
        private final String namespace;
        private final Long stageId;
        private final Long taskId;

        public NoDownstreamTasks(TaskLevel source) {
            this(source.getExecutionType(), source.getExecutionId(), source.getNamespace(), source.getStageId(), source.getTaskId());
        }
    }

}
