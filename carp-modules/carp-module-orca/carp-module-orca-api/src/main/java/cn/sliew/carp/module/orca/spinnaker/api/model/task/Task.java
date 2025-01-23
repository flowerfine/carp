package cn.sliew.carp.module.orca.spinnaker.api.model.task;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.kork.plugins.api.internal.SpinnakerExtensionPoint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public interface Task extends SpinnakerExtensionPoint {

    /**
     * Execute the business logic of the task, using the provided stage execution state.
     *
     * @param stage The running stage execution stage
     * @return The result of this Task's execution
     */
    TaskResult execute(StageExecution stage);

    /**
     * Behavior to be called on Task timeout.
     *
     * <p>This method should be used if you need to perform any cleanup operations in response to the
     * task being aborted after taking too long to complete.
     *
     * @param stage The running state execution state
     */
    default TaskResult onTimeout(StageExecution stage) {
        return null;
    }


    /**
     * Behavior to be called on Task cancellation.
     *
     * <p>This method should be used if you need to perform cleanup in response to the task being
     * cancelled before it was able to complete.
     *
     * <p>When returning a {@link TaskResult}, the {@link ExecutionStatus} will be ignored, as the
     * resulting status will always be {@link ExecutionStatus#CANCELED}.
     *
     * @param stage The running state execution state
     */
    default TaskResult onCancelWithResult(StageExecution stage) {
        return null;
    }

    /**
     * A collection of known aliases.
     */
    default Collection<String> aliases() {
        if (getClass().isAnnotationPresent(Aliases.class)) {
            return Arrays.asList(getClass().getAnnotation(Aliases.class).value());
        }

        return Collections.emptyList();
    }

    /**
     * method to filter certain keys from a stage's "context.outputs" key. This takes in the
     * context.outputs map as an input, as well as a collection of keys to be filtered from it. It
     * then returns the outputs map sans the keys to filter.
     *
     * @param outputs      Map of a stage context's "outputs"
     * @param keysToFilter Collection of keys that need to be filtered from outputs
     * @return filtered map of stage context's "outputs"
     */
    default Map<String, Object> filterContextOutputs(
            Map<String, Object> outputs, Collection<String> keysToFilter) {
        return outputs.entrySet().stream()
                .filter(map -> !keysToFilter.contains(map.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Allows backwards compatibility of a task's "type", even through class renames / refactors.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Aliases {
        String[] value() default {};
    }
}
