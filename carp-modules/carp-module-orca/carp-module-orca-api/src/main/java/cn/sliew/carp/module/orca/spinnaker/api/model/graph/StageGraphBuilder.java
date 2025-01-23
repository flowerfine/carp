package cn.sliew.carp.module.orca.spinnaker.api.model.graph;

import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;

import java.util.function.Consumer;

/**
 * Provides a low-level API for manipulating a stage DAG.
 */
public interface StageGraphBuilder {

    void add(StageExecution stage);

    StageExecution add(Consumer<StageExecution> init);

    void connect(StageExecution previous, StageExecution next);

    default StageExecution connect(StageExecution previous, Consumer<StageExecution> init) {
        StageExecution stage = add(init);
        connect(previous, stage);
        return stage;
    }

    void append(StageExecution stage);

    StageExecution append(Consumer<StageExecution> init);

    Iterable<StageExecution> build();
}
