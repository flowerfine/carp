package cn.sliew.carp.framework.dag.task.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/** The runtime execution state of a stage. */
public interface StageExecution {

  @Nonnull
  String getId();

  @Nullable
  String getRefId();

  @Nonnull
  String getType();

  @Nonnull
  String getName();

  PipelineExecution getExecution();

  @Nullable
  Long getStartTime();

  @Nullable
  Long getEndTime();

  @Nullable
  Long getStartTimeExpiry();

  @Nonnull
  ExecutionStatus getStatus();

  @Nonnull
  Map<String, Object> getContext();

  @Nonnull
  Map<String, Object> getOutputs();

  @Nonnull
  List<TaskExecution> getTasks();

  @Nullable
  SyntheticStageOwner getSyntheticStageOwner();

  @Nullable
  String getParentStageId();

  @Nonnull
  Collection<String> getRequisiteStageRefIds();

  @Nullable
  Long getScheduledTime();

  // ------------- InternalStageExecution?
  // A lot of these methods are used in a single place somewhere in Orca. I don't know why we
  // decided to put a bunch
  // of these methods on the class...?

  TaskExecution taskById(@Nonnull String taskId);

  @Nonnull
  List<StageExecution> ancestors();

  @Nonnull
  List<StageExecution> directAncestors();

  @Nullable
  StageExecution findAncestor(@Nonnull Predicate<StageExecution> predicate);

  @Nonnull
  List<StageExecution> allDownstreamStages();

  @Nonnull
  List<StageExecution> directChildren();

  @Nonnull
  <O> O mapTo(@Nonnull Class<O> type);

  @Nonnull
  <O> O mapTo(@Nullable String pointer, @Nonnull Class<O> type);

  @Nonnull
  <O> O decodeBase64(@Nullable String pointer, @Nonnull Class<O> type);

  void resolveStrategyParams();

  @Nullable
  StageExecution getParent();

  @Nonnull
  StageExecution getTopLevelStage();

  @Nonnull
  Optional<StageExecution> getParentWithTimeout();

  @Nonnull
  Optional<Long> getTimeout();

  boolean getAllowSiblingStagesToContinueOnFailure();

  void setAllowSiblingStagesToContinueOnFailure(boolean allowSiblingStagesToContinueOnFailure);

  boolean getContinuePipelineOnFailure();

  void setContinuePipelineOnFailure(boolean continuePipelineOnFailure);

  boolean isJoin();

  boolean isManualJudgmentType();

  boolean withPropagateAuthentication();

  void appendErrorMessage(String errorMessage);

  @Nonnull
  List<StageExecution> downstreamStages();

}
