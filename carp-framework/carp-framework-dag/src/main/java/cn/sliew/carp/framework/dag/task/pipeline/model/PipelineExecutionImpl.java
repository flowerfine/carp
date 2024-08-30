package cn.sliew.carp.framework.dag.task.pipeline.model;

import cn.sliew.carp.framework.dag.task.model.ExecutionStatus;
import cn.sliew.carp.framework.dag.task.model.ExecutionType;
import cn.sliew.carp.framework.dag.task.model.PipelineExecution;
import cn.sliew.carp.framework.dag.task.model.StageExecution;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Slf4j
public class PipelineExecutionImpl implements PipelineExecution, Serializable {

    @Nonnull
    @Override
    public ExecutionType getType() {
        return null;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getApplication() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Long getBuildTime() {
        return 0;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public String getCanceledBy() {
        return "";
    }

    @Override
    public String getCancellationReason() {
        return "";
    }

    @Override
    public boolean isLimitConcurrent() {
        return false;
    }

    @Override
    public int getMaxConcurrentExecutions() {
        return 0;
    }

    @Override
    public boolean isKeepWaitingPipelines() {
        return false;
    }

    @Override
    public Map<String, Object> getContext() {
        return Map.of();
    }

    @Override
    public List<StageExecution> getStages() {
        return List.of();
    }

    @Override
    public Long getStartTime() {
        return 0;
    }

    @Override
    public Long getEndTime() {
        return 0;
    }

    @Override
    public Long getStartTimeExpiry() {
        return 0;
    }

    @Override
    public ExecutionStatus getStatus() {
        return null;
    }

    @Override
    public PausedDetails getPaused() {
        return null;
    }

    @Override
    public String getOrigin() {
        return "";
    }

    @Override
    public Trigger getTrigger() {
        return null;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getPipelineConfigId() {
        return "";
    }

    @Override
    public PipelineSource getSource() {
        return null;
    }

    @Override
    public String getSpelEvaluator() {
        return "";
    }

    @Override
    public void setSpelEvaluator(String spelEvaluator) {

    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        return Map.of();
    }

    @Override
    public String getPartition() {
        return "";
    }

    @Override
    public StageExecution namedStage(String type) {
        return null;
    }

    @Override
    public StageExecution stageById(String stageId) {
        return null;
    }

    @Override
    public StageExecution stageByRef(String refId) {
        return null;
    }

    @Override
    public void updateStatus(ExecutionStatus status) {

    }
}
