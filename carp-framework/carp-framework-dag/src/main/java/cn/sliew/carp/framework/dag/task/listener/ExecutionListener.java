package cn.sliew.carp.framework.dag.task.listener;

import cn.sliew.carp.framework.dag.task.model.ExecutionStatus;
import cn.sliew.carp.framework.dag.task.model.PipelineExecution;
import jakarta.annotation.Nonnull;
import org.springframework.core.Ordered;

public interface ExecutionListener extends Ordered, Comparable<ExecutionListener> {
  default void beforeInitialPersist(@Nonnull PipelineExecution execution) {
    // do nothing
  }

  default void beforeExecution(@Nonnull Persister persister, @Nonnull PipelineExecution execution) {
    // do nothing
  }

  default void afterExecution(
      @Nonnull Persister persister,
      @Nonnull PipelineExecution execution,
      @Nonnull ExecutionStatus executionStatus,
      boolean wasSuccessful) {
    // do nothing
  }

  default int getOrder() {
    return 0;
  }

  @Override
  default int compareTo(@Nonnull ExecutionListener o) {
    return o.getOrder() - getOrder();
  }
}