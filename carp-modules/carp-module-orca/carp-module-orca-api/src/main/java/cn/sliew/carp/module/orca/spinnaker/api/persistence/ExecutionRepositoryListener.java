package cn.sliew.carp.module.orca.spinnaker.api.persistence;

import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;

/**
 * Listens for changes to {@link PipelineExecution}.
 *
 * <p>These listeners are unable to perform any changes that would affect the outcome of a
 * persistence operation. They are directly notified of persistence operations after the change has
 * been committed.
 */
public interface ExecutionRepositoryListener {

    /**
     * Listen for upsert operations.
     *
     * <p>This could be adding, updating or removing stages; canceling, pausing, resuming executions,
     * and so-on.
     */
    void onUpsert(PipelineExecution pipelineExecution);
}
