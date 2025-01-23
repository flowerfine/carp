package cn.sliew.carp.module.orca.spinnaker.api.executions;

import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;

public interface ExecutionRunner {

    void start(PipelineExecution execution);

    default void restart(PipelineExecution execution, Long stageId) {
        throw new UnsupportedOperationException();
    }

    default void reschedule(PipelineExecution execution) {
        throw new UnsupportedOperationException();
    }

    default void unpause(PipelineExecution execution) {
        throw new UnsupportedOperationException();
    }

    default void cancel(PipelineExecution execution, String reason) {
        throw new UnsupportedOperationException();
    }
}
