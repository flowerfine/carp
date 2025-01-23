package cn.sliew.carp.module.orca.spinnaker.api.model.util;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.SyntheticStageOwner;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.task.TaskExecution;
import jakarta.annotation.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum StageExecutionUtil {
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

    public static List<StageExecution> firstBeforeStages(StageExecution stage) {
        return beforeStages(stage).stream()
                .filter(StageExecutionUtil::isInitial)
                .collect(Collectors.toList());
    }

    public static List<StageExecution> firstAfterStages(StageExecution stage) {
        return afterStages(stage).stream()
                .filter(StageExecutionUtil::isInitial)
                .collect(Collectors.toList());
    }

    public static boolean isInitial(StageExecution stage) {
        return stage.getRequisiteStageRefIds().isEmpty();
    }

    @Nullable
    public static TaskExecution firstTask(StageExecution stage) {
        List<TaskExecution> tasks = stage.getTasks();
        return tasks.isEmpty() ? null : tasks.get(0);
    }

    public static StageExecution parent(StageExecution stage) {
        return stage.getPipelineExecution().getStages().stream()
                .filter(s -> s.getId().equals(stage.getParentStageId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not a synthetic stage"));
    }

    /**
     * 获取在指定任务之后的任务,如果任务是阶段的最后一个任务则返回null
     */
    @Nullable
    public static TaskExecution nextTask(StageExecution stage, TaskExecution task) {
        if (task.isStageEnd()) {
            return null;
        }
        List<TaskExecution> tasks = stage.getTasks();
        int index = tasks.indexOf(task);
        return tasks.get(index + 1);
    }

    /**
     * 获取此阶段的所有直接上游阶段
     */
    public static List<StageExecution> upstreamStages(StageExecution stage) {
        return stage.getPipelineExecution().getStages().stream()
                .filter(s -> stage.getRequisiteStageRefIds().contains(s.getRefId()))
                .collect(Collectors.toList());
    }

    /**
     * 判断此阶段的所有上游阶段是否都已成功完成
     */
    public static boolean allUpstreamStagesComplete(StageExecution stage) {
        return upstreamStages(stage).stream()
                .allMatch(s -> STAGE_COMPLETE_STATUS.contains(s.getStatus()));
    }

    /**
     * 判断是否有任何上游阶段失败
     */
    public static boolean anyUpstreamStagesFailed(StageExecution stage) {
        return upstreamStages(stage).stream()
                .anyMatch(s -> STAGE_FAILED_STATUS.contains(s.getStatus()) ||
                        (s.getStatus() == ExecutionStatus.NOT_STARTED && anyUpstreamStagesFailed(s)));
    }

    /**
     * 获取合成阶段
     */
    public static List<StageExecution> syntheticStages(StageExecution stage) {
        return stage.getPipelineExecution().getStages().stream()
                .filter(s -> stage.getId().equals(s.getParentStageId()))
                .collect(Collectors.toList());
    }

    /**
     * 递归获取所有合成阶段
     */
    public static List<StageExecution> recursiveSyntheticStages(StageExecution stage) {
        List<StageExecution> syntheticStages = syntheticStages(stage);
        List<StageExecution> recursiveStages = syntheticStages.stream()
                .flatMap(s -> recursiveSyntheticStages(s).stream())
                .collect(Collectors.toList());
        syntheticStages.addAll(recursiveStages);
        return syntheticStages;
    }

    /**
     * 获取前置阶段
     */
    public static List<StageExecution> beforeStages(StageExecution stage) {
        return syntheticStages(stage).stream()
                .filter(s -> s.getSyntheticStageOwner() == SyntheticStageOwner.STAGE_BEFORE)
                .collect(Collectors.toList());
    }

    /**
     * 获取后置阶段
     */
    public static List<StageExecution> afterStages(StageExecution stage) {
        return syntheticStages(stage).stream()
                .filter(s -> s.getSyntheticStageOwner() == SyntheticStageOwner.STAGE_AFTER)
                .collect(Collectors.toList());
    }

    /**
     * 判断所有前置阶段是否成功
     */
    public static boolean allBeforeStagesSuccessful(StageExecution stage) {
        return beforeStages(stage).stream()
                .allMatch(s -> STAGE_COMPLETE_STATUS.contains(s.getStatus()));
    }

    /**
     * 判断所有后置阶段是否成功
     */
    public static boolean allAfterStagesSuccessful(StageExecution stage) {
        return afterStages(stage).stream()
                .allMatch(s -> STAGE_COMPLETE_STATUS.contains(s.getStatus()));
    }

    /**
     * 判断是否有任何前置阶段失败
     */
    public static boolean anyBeforeStagesFailed(StageExecution stage) {
        return beforeStages(stage).stream()
                .anyMatch(s -> STAGE_FAILED_STATUS.contains(s.getStatus()));
    }

    /**
     * 判断是否有任何后置阶段失败
     */
    public static boolean anyAfterStagesFailed(StageExecution stage) {
        return afterStages(stage).stream()
                .anyMatch(s -> STAGE_FAILED_STATUS.contains(s.getStatus()));
    }

    /**
     * 判断所有后置阶段是否完成
     */
    public static boolean allAfterStagesComplete(StageExecution stage) {
        return afterStages(stage).stream()
                .allMatch(s -> s.getStatus().isComplete());
    }

    /**
     * 判断是否有任务
     */
    public static boolean hasTasks(StageExecution stage) {
        return !stage.getTasks().isEmpty();
    }

    /**
     * 判断是否有后置阶段
     */
    public static boolean hasAfterStages(StageExecution stage) {
        return !firstAfterStages(stage).isEmpty();
    }

    /**
     * 将阶段上下文映射到指定类型
     */
    public static <O> O mapTo(StageExecution stage, String pointer, Class<O> type) {
        return stage.mapTo(pointer, type);
    }

    /**
     * 将阶段上下文映射到指定类型
     */
    public static <O> O mapTo(StageExecution stage, Class<O> type) {
        return stage.mapTo(type);
    }

    /**
     * 判断是否应该使管道失败
     */
    public static boolean shouldFailPipeline(StageExecution stage) {
        Object failPipeline = stage.getContext().get("failPipeline");
        return failPipeline == null || Boolean.TRUE.equals(failPipeline);
    }

    /**
     * 获取失败状态
     */
    public static ExecutionStatus failureStatus(StageExecution stage, ExecutionStatus defaultStatus) {
        if (Objects.isNull(defaultStatus)) {
            defaultStatus = ExecutionStatus.TERMINAL;
        }
        if (stage.getContinuePipelineOnFailure()) {
            return ExecutionStatus.FAILED_CONTINUE;
        }
        return shouldFailPipeline(stage) ? defaultStatus : ExecutionStatus.STOPPED;
    }

    /**
     * 判断是否手动跳过
     */
    public static boolean isManuallySkipped(StageExecution stage) {
        Object manualSkip = stage.getContext().get("manualSkip");
        return Boolean.TRUE.equals(manualSkip) ||
                (stage.getParentStageId() != null && isManuallySkipped(parent(stage)));
    }
}
