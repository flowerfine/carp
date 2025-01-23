package cn.sliew.carp.module.orca.spinnaker.orca.core.listeners;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import org.springframework.core.Ordered;

import javax.annotation.Nonnull;

public interface ExecutionListener extends Ordered, Comparable<ExecutionListener> {

    default void beforeInitialPersist(@Nonnull PipelineExecution execution) {

    }

    default void beforeExecution(@Nonnull Persister persister, @Nonnull PipelineExecution execution) {

    }

    default void afterExecution(
            @Nonnull Persister persister,
            @Nonnull PipelineExecution execution,
            @Nonnull ExecutionStatus executionStatus,
            boolean wasSuccessful) {

    }

    default int getOrder() {
        return 0;
    }

    @Override
    default int compareTo(@Nonnull ExecutionListener o) {
        return o.getOrder() - getOrder();
    }
}
