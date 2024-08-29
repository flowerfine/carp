package cn.sliew.carp.framework.dag.task.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public enum ExecutionStatus {

    /** The task has yet to start. */
    NOT_STARTED(false, false),

    /** The task is still running and the {@code Task} may be re-executed in order to continue. */
    RUNNING(false, false),

    /** The task is still running and the {@code Task} may be resumed in order to continue. */
    PAUSED(false, false),

    /** The task is complete but the pipeline should now be stopped pending a trigger of some kind. */
    SUSPENDED(false, false),

    /** The task executed successfully and the pipeline may now proceed to the next task. */
    SUCCEEDED(true, false),

    /** The task execution failed, but the pipeline may proceed to the next task. */
    FAILED_CONTINUE(true, false),

    /** The task failed and the failure was terminal. The pipeline will not progress any further. */
    TERMINAL(true, true),

    /** The task was canceled. The pipeline will not progress any further. */
    CANCELED(true, true),

    /**
     * The step completed but is indicating that a decision path should be followed, not the default
     * path.
     */
    REDIRECT(false, false),

    /** The task was stopped. The pipeline will not progress any further. */
    STOPPED(true, true),

    /** The task was skipped and the pipeline will proceed to the next task. */
    SKIPPED(true, false),

    /** The task is not started and must be transitioned to NOT_STARTED. */
    BUFFERED(false, false),
    ;

    public static final Collection<ExecutionStatus> COMPLETED =
            Collections.unmodifiableList(Arrays.asList(CANCELED, SUCCEEDED, STOPPED, SKIPPED, TERMINAL, FAILED_CONTINUE));public enum ExecutionType {
        /** Executions will show under the "Pipelines" tab of Deck. */
        PIPELINE,

        /** Executions will show under the "Tasks" tab of Deck. */
        ORCHESTRATION;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    private static final Collection<ExecutionStatus> SUCCESSFUL =
            Collections.unmodifiableList(Arrays.asList(SUCCEEDED, STOPPED, SKIPPED));
    private static final Collection<ExecutionStatus> FAILURE =
            Collections.unmodifiableList(Arrays.asList(TERMINAL, STOPPED, FAILED_CONTINUE));

    private final boolean complete;
    private final boolean halt;

    ExecutionStatus(boolean complete, boolean halt) {
        this.complete = complete;
        this.halt = halt;
    }
}
