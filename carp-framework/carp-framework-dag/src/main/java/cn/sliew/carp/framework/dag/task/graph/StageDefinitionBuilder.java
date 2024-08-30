package cn.sliew.carp.framework.dag.task.graph;

import cn.sliew.carp.framework.dag.task.model.StageExecution;
import jakarta.annotation.Nonnull;

import static cn.sliew.carp.framework.dag.task.graph.TaskNode.Builder;
import static cn.sliew.carp.framework.dag.task.graph.TaskNode.GraphType.FULL;

/**
 * Provides a low-level API for building stages.
 *
 * <p>A stage in its simplest form will consist of a series of Tasks that are executed serially.
 * However, a stage can also configure other stages to be run before it, or after it, and or on
 * failure only. This can enable you to build stages which compose and abstract the details of other
 * stages.
 */
public interface StageDefinitionBuilder {

    default @Nonnull TaskNode.TaskGraph buildTaskGraph(@Nonnull StageExecution stage) {
        Builder graphBuilder = Builder(FULL);
        taskGraph(stage, graphBuilder);
        return graphBuilder.build();
    }

    /**
     * Implement this method to define any tasks that should run as part of this stage's workflow.
     *
     * @param stage   The execution runtime of the stage
     * @param builder The task graph builder
     */
    default void taskGraph(@Nonnull StageExecution stage, @Nonnull Builder builder) {
    }

    /**
     * Implement this method to define any stages that should run before any tasks in this stage as
     * part of a composed workflow.
     *
     * @param parent The execution runtime of the stage (which is the parent of any stages created
     *               herein)
     * @param graph  The stage graph builder
     */
    default void beforeStages(@Nonnull StageExecution parent, @Nonnull StageGraphBuilder graph) {
    }

    /**
     * Implement this method to define any stages that should run after any tasks in this stage as
     * part of a composed workflow.
     *
     * @param parent The execution runtime of the stage (which is the parent of any stages created
     *               herein)
     * @param graph  The stage graph builder
     */
    default void afterStages(@Nonnull StageExecution parent, @Nonnull StageGraphBuilder graph) {
    }

    /**
     * Implement this method to define any stages that should run in response to a failure in tasks,
     * before or after stages.
     *
     * @param stage The execution runtime of the stage
     * @param graph The stage graph builder
     */
    default void onFailureStages(@Nonnull StageExecution stage, @Nonnull StageGraphBuilder graph) {
    }

    /**
     * Returns the stage type this builder handles.
     */
    @SuppressWarnings("unchecked")
    default @Nonnull String getType() {
        return getType((Class<StageDefinitionBuilder>) this.getExtensionClass());
    }

    /**
     * Implementations can override this if they need any special cleanup on restart.
     *
     * @param stage The execution runtime of the stage
     */
    default void prepareStageForRestart(@Nonnull StageExecution stage) {
    }

    /**
     * Get the pipeline configuration-friendly type name for this stage.
     *
     * <p>If a stage class is {@code MyFancyStage}, the resulting type would be {@code myFancy}.
     *
     * @param clazz The stage definition builder class
     */
    static String getType(Class<? extends StageDefinitionBuilder> clazz) {
        String className = clazz.getSimpleName();
        if (className.equals("")) {
            throw new IllegalStateException(
                    "StageDefinitionBuilder.getType() cannot be called on an anonymous type");
        }
        return className.substring(0, 1).toLowerCase()
                + className
                .substring(1)
                .replaceFirst("StageDefinitionBuilder$", "")
                .replaceFirst("Stage$", "");
    }

    /**
     * Return true if the stage can be manually skipped from the API.
     */
    default boolean canManuallySkip(StageExecution stage) {
        return false;
    }

}
