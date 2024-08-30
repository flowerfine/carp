package cn.sliew.carp.framework.dag.task.pipeline.model;

import cn.sliew.carp.framework.dag.task.model.*;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
public class StateExecutionImpl implements StageExecution, Serializable {

    @Nonnull
    @Override
    public String getId() {
        return "";
    }

    @Nullable
    @Override
    public String getRefId() {
        return "";
    }

    @Nonnull
    @Override
    public String getType() {
        return "";
    }

    @Nonnull
    @Override
    public String getName() {
        return "";
    }

    @Override
    public PipelineExecution getExecution() {
        return null;
    }

    @Nullable
    @Override
    public Long getStartTime() {
        return 0;
    }

    @Nullable
    @Override
    public Long getEndTime() {
        return 0;
    }

    @Nullable
    @Override
    public Long getStartTimeExpiry() {
        return 0;
    }

    @Nonnull
    @Override
    public ExecutionStatus getStatus() {
        return null;
    }

    @Nonnull
    @Override
    public Map<String, Object> getContext() {
        return Map.of();
    }

    @Nonnull
    @Override
    public Map<String, Object> getOutputs() {
        return Map.of();
    }

    @Nonnull
    @Override
    public List<TaskExecution> getTasks() {
        return List.of();
    }

    @Nullable
    @Override
    public SyntheticStageOwner getSyntheticStageOwner() {
        return null;
    }

    @Nullable
    @Override
    public String getParentStageId() {
        return "";
    }

    @Nonnull
    @Override
    public Collection<String> getRequisiteStageRefIds() {
        return List.of();
    }

    @Nullable
    @Override
    public Long getScheduledTime() {
        return 0;
    }

    @Override
    public TaskExecution taskById(@Nonnull String taskId) {
        return null;
    }

    @Nonnull
    @Override
    public List<StageExecution> ancestors() {
        return List.of();
    }

    @Nonnull
    @Override
    public List<StageExecution> directAncestors() {
        return List.of();
    }

    @Nullable
    @Override
    public StageExecution findAncestor(@Nonnull Predicate<StageExecution> predicate) {
        return null;
    }

    @Nonnull
    @Override
    public List<StageExecution> allDownstreamStages() {
        return List.of();
    }

    @Nonnull
    @Override
    public List<StageExecution> directChildren() {
        return List.of();
    }

    @Nonnull
    @Override
    public <O> O mapTo(@Nonnull Class<O> type) {
        return null;
    }

    @Nonnull
    @Override
    public <O> O mapTo(@Nullable String pointer, @Nonnull Class<O> type) {
        return null;
    }

    @Nonnull
    @Override
    public <O> O decodeBase64(@Nullable String pointer, @Nonnull Class<O> type) {
        return null;
    }

    @Override
    public void resolveStrategyParams() {

    }

    @Nullable
    @Override
    public StageExecution getParent() {
        return null;
    }

    @Nonnull
    @Override
    public StageExecution getTopLevelStage() {
        return null;
    }

    @Nonnull
    @Override
    public Optional<StageExecution> getParentWithTimeout() {
        return Optional.empty();
    }

    @Nonnull
    @Override
    public Optional<Long> getTimeout() {
        return Optional.empty();
    }

    @Override
    public boolean getAllowSiblingStagesToContinueOnFailure() {
        return false;
    }

    @Override
    public void setAllowSiblingStagesToContinueOnFailure(boolean allowSiblingStagesToContinueOnFailure) {

    }

    @Override
    public boolean getContinuePipelineOnFailure() {
        return false;
    }

    @Override
    public void setContinuePipelineOnFailure(boolean continuePipelineOnFailure) {

    }

    @Override
    public boolean isJoin() {
        return false;
    }

    @Override
    public boolean isManualJudgmentType() {
        return false;
    }

    @Override
    public boolean withPropagateAuthentication() {
        return false;
    }

    @Override
    public void appendErrorMessage(String errorMessage) {

    }

    @Nonnull
    @Override
    public List<StageExecution> downstreamStages() {
        return List.of();
    }
}
