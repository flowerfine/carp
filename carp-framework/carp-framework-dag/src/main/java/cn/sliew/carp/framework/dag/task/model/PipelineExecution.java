package cn.sliew.carp.framework.dag.task.model;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Map;

/**
 * The runtime execution state of a Pipeline.
 */
public interface PipelineExecution {

    @Nonnull
    ExecutionType getType();

    String getId();

    String getApplication();

    String getName();

    Long getBuildTime();

    boolean isCanceled();

    String getCanceledBy();

    String getCancellationReason();

    boolean isLimitConcurrent();

    int getMaxConcurrentExecutions();

    boolean isKeepWaitingPipelines();

    Map<String, Object> getContext();

    List<StageExecution> getStages();

    Long getStartTime();

    Long getEndTime();

    Long getStartTimeExpiry();

    ExecutionStatus getStatus();

    PausedDetails getPaused();

    String getOrigin();

    Trigger getTrigger();

    String getDescription();

    String getPipelineConfigId();

    PipelineSource getSource();

    String getSpelEvaluator();

    void setSpelEvaluator(String spelEvaluator);

    Map<String, Object> getTemplateVariables();

    String getPartition();

    // -------

    StageExecution namedStage(String type);

    StageExecution stageById(String stageId);

    StageExecution stageByRef(String refId);

    /**
     * Based on the value of `status`, will also update synthetic fields like `canceled` and `endTime`
     */
    void updateStatus(ExecutionStatus status);


    class PausedDetails {
        String pausedBy;

        String resumedBy;

        Long pauseTime;

        Long resumeTime;
    }

    class PipelineSource {
        private String id;

        private String type;

        private String version;
    }
}
