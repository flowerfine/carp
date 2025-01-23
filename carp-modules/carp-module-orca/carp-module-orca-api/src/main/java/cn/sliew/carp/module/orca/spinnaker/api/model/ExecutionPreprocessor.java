package cn.sliew.carp.module.orca.spinnaker.api.model;

import java.util.Map;

/**
 * ExecutionPreprocessor is a hook point that can modify an Execution upon initial receipt of the
 * configuration.
 *
 * <p>A common use case is to inspect and update a pipeline with configured context, such an
 * application name.
 */
public interface ExecutionPreprocessor {

    /**
     * Returns whether or not the preprocess can handle the inbound execution.
     */
    boolean supports(Map<String, Object> execution, Type type);

    /**
     * Allows modification of an execution configuration.
     */
    Map<String, Object> process(Map<String, Object> execution);

    enum Type {
        PIPELINE,
        ORCHESTRATION
    }
}
