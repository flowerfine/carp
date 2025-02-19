package cn.sliew.module.workflow.stage.model.graph;

import cn.sliew.carp.framework.dag.repository.entity.DagStep;

import java.util.function.Consumer;

public interface StageGraphBuilder {

    void add(DagStep step);

    DagStep add(Consumer<DagStep> init);

    void connect(DagStep previous, DagStep next);

    default DagStep connect(DagStep previous, Consumer<DagStep> init) {
        DagStep stage = add(init);
        connect(previous, stage);
        return stage;
    }

    void append(DagStep stage);

    DagStep append(Consumer<DagStep> init);

    Iterable<DagStep> build();
}
