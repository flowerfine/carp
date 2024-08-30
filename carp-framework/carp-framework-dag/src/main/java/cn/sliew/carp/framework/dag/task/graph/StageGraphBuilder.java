package cn.sliew.carp.framework.dag.task.graph;

import cn.sliew.carp.framework.dag.task.model.StageExecution;
import jakarta.annotation.Nonnull;

import java.util.function.Consumer;

/**
 * Provides a low-level API for manipulating a stage DAG.
 */
public interface StageGraphBuilder {

    /**
     * Adds a new stage to the graph. By default the new stage is not dependent on any others. Use
     * {@link #connect(StageExecution, StageExecution)} to make it depend on other stages or have
     * other stages depend on it.
     *
     * @param init builder for setting up the stage. You do not need to configure {@link
     *             StageExecution#getExecution()}, {@link StageExecution#getParentStageId()}, {@link
     *             StageExecution#getSyntheticStageOwner()} or {@link StageExecution#getRefId()} as this
     *             method will do that automatically.
     * @return the newly created stage.
     */
    @Nonnull
    StageExecution add(@Nonnull Consumer<StageExecution> init);

    /**
     * Adds a new stage to the graph. By default the new stage is not dependent on any others. Use
     * {@link #connect(StageExecution, StageExecution)} to make it depend on other stages or have
     * other stages depend on it.
     */
    void add(@Nonnull StageExecution stage);

    /**
     * Adds a new stage to the graph and makes it depend on {@code previous} via its {@link
     * StageExecution#getRequisiteStageRefIds()}.
     *
     * @param previous The stage the new stage will depend on. If {@code previous} does not already
     *                 exist in the graph, this method will add it.
     * @param init     See {@link #add(Consumer)}
     * @return the newly created stage.
     */
    @Nonnull
    StageExecution connect(@Nonnull StageExecution previous, @Nonnull Consumer<StageExecution> init);

    /**
     * Makes {@code next} depend on {@code previous} via its {@link
     * StageExecution#getRequisiteStageRefIds()}. If either {@code next} or {@code previous} are not
     * yet present in the graph this method will add them.
     */
    void connect(@Nonnull StageExecution previous, @Nonnull StageExecution next);

    /**
     * Adds a new stage to the graph and makes it depend on the last stage that was added if any.
     *
     * <p>This is convenient for straightforward stage graphs to avoid having to pass around
     * references to stages in order to use {@link #connect(StageExecution, Consumer)}.
     *
     * <p>If no stages have been added so far, this is synonymous with calling {@link #add(Consumer)}.
     *
     * @param init See {@link #add(Consumer)}
     * @return the newly created stage.
     */
    @Nonnull
    StageExecution append(@Nonnull Consumer<StageExecution> init);

    void append(@Nonnull StageExecution stage);

    /**
     * Builds and returns the stages represented in the graph. This method is not typically useful to
     * implementors of {@link StageDefinitionBuilder}, it's used internally and by tests.
     */
    @Nonnull
    Iterable<StageExecution> build();
}
