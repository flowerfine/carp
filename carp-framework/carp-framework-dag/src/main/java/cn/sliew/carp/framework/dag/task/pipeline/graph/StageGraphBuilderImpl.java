package cn.sliew.carp.framework.dag.task.pipeline.graph;

import cn.sliew.carp.framework.dag.task.graph.StageGraphBuilder;
import cn.sliew.carp.framework.dag.task.model.StageExecution;
import cn.sliew.carp.framework.dag.task.model.SyntheticStageOwner;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import static cn.sliew.carp.framework.dag.task.model.SyntheticStageOwner.STAGE_AFTER;
import static cn.sliew.carp.framework.dag.task.model.SyntheticStageOwner.STAGE_BEFORE;

public class StageGraphBuilderImpl implements StageGraphBuilder {

  private final StageExecution parent;
  private final SyntheticStageOwner type;
  private final MutableGraph<StageExecution> graph =
      GraphBuilder.directed().build(); // TODO: is this actually useful?
  private final Optional<StageExecution> requiredPrefix;
  private @Nullable StageExecution lastAdded = null;

  private StageGraphBuilderImpl(
      StageExecution parent, SyntheticStageOwner type, Optional<StageExecution> requiredPrefix) {
    this.parent = parent;
    this.type = type;
    this.requiredPrefix = requiredPrefix;
    this.requiredPrefix.ifPresent(this::add);
  }

  /**
   * Create a new builder for the before stages of {@code parent}.
   *
   * @param parent
   */
  public static @Nonnull StageGraphBuilderImpl beforeStages(@Nonnull StageExecution parent) {
    return new StageGraphBuilderImpl(parent, STAGE_BEFORE, Optional.empty());
  }

  /**
   * Create a new builder for the before stages of {@code parent}.
   *
   * @param parent
   * @param requiredPrefix
   */
  public static @Nonnull StageGraphBuilderImpl beforeStages(
      @Nonnull StageExecution parent, @Nullable StageExecution requiredPrefix) {
    return new StageGraphBuilderImpl(parent, STAGE_BEFORE, Optional.ofNullable(requiredPrefix));
  }

  /**
   * Create a new builder for the after stages of {@code parent}.
   *
   * @param parent
   */
  public static @Nonnull StageGraphBuilderImpl afterStages(@Nonnull StageExecution parent) {
    return new StageGraphBuilderImpl(parent, STAGE_AFTER, Optional.empty());
  }

  @Override
  public @Nonnull StageExecution add(@Nonnull Consumer<StageExecution> init) {
    StageExecution stage = newStage(init);
    add(stage);
    return stage;
  }

  @Override
  public void add(@Nonnull StageExecution stage) {
    stage.setExecution(parent.getExecution());
    stage.setParentStageId(parent.getId());
    stage.setSyntheticStageOwner(type);
    if (graph.addNode(stage)) {
      stage.setRefId(generateRefId());
    }
    lastAdded = stage;
  }

  @Override
  public @Nonnull StageExecution connect(
      @Nonnull StageExecution previous, @Nonnull Consumer<StageExecution> init) {
    StageExecution stage = add(init);
    connect(previous, stage);
    return stage;
  }

  @Override
  public void connect(@Nonnull StageExecution previous, @Nonnull StageExecution next) {
    add(previous);
    add(next);
    Set<String> requisiteStageRefIds = new HashSet<>(next.getRequisiteStageRefIds());
    requisiteStageRefIds.add(previous.getRefId());
    next.setRequisiteStageRefIds(requisiteStageRefIds);
    graph.putEdge(previous, next);
  }

  @Override
  public @Nonnull StageExecution append(@Nonnull Consumer<StageExecution> init) {
    if (lastAdded == null) {
      return add(init);
    } else {
      return connect(lastAdded, init);
    }
  }

  @Override
  public void append(@Nonnull StageExecution stage) {
    if (lastAdded == null) {
      add(stage);
    } else {
      connect(lastAdded, stage);
    }
  }

  @Override
  public @Nonnull Iterable<StageExecution> build() {
    requiredPrefix.ifPresent(
        prefix ->
            graph
                .nodes()
                .forEach(
                    it -> {
                      if (it != prefix && it.getRequisiteStageRefIds().isEmpty()) {
                        connect(prefix, it);
                      }
                    }));
    return graph.nodes();
  }

  private String generateRefId() {
    long offset =
        parent.getExecution().getStages().stream()
            .filter(
                i ->
                    parent.getId().equals(i.getParentStageId())
                        && type == i.getSyntheticStageOwner())
            .count();

    return format(
        "%s%s%d",
        parent.getRefId(), type == STAGE_BEFORE ? "<" : ">", offset + graph.nodes().size());
  }

  private StageExecution newStage(Consumer<StageExecution> init) {
    StageExecution stage = new StageExecutionImpl();
    init.accept(stage);
    return stage;
  }
}
