package cn.sliew.carp.module.orca.spinnaker.orca.core.events;

import cn.sliew.carp.module.orca.spinnaker.orca.core.listeners.StageListener;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;

/**
 * This listener translates events coming from the nu-orca queueing system to the old Spring Batch
 * style listeners. Once we're running full-time on the queue we should simplify things.
 */
@AllArgsConstructor
public final class StageListenerAdapter implements ApplicationListener<ExecutionEvent> {

    private final StageListener delegate;

    @Override
    public void onApplicationEvent(@NotNull ExecutionEvent event) {
        if (event instanceof StageStarted) {
            onStageStarted((StageStarted) event);
        } else if (event instanceof StageComplete) {
            onStageComplete((StageComplete) event);
        } else if (event instanceof TaskStarted) {
            onTaskStarted((TaskStarted) event);
        } else if (event instanceof TaskComplete) {
            onTaskComplete((TaskComplete) event);
        }
    }

    private void onStageStarted(StageStarted event) {
        delegate.beforeStage(event.getStage());
    }

    private void onStageComplete(StageComplete event) {
        delegate.afterStage(event.getStage());
    }

    private void onTaskStarted(TaskStarted event) {
        delegate.beforeTask(event.getStage(), event.getTask());
    }

    private void onTaskComplete(TaskComplete event) {
        delegate.afterTask(event.getStage(), event.getTask());
    }
}
