package cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline;

import cn.sliew.carp.module.orca.spinnaker.api.model.graph.StageDefinitionBuilder;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageContext;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.ExpressionEvaluationSummary;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.util.ContextParameterProcessor;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Decorates a StageDefinitionBuilder with expression processing capabilities.
 *
 * <p>This is not available for the public Orca API.
 */
public abstract class ExpressionAwareStageDefinitionBuilder implements StageDefinitionBuilder {

    /**
     * Allows the stage to process SpEL expression in its own context in a custom way
     *
     * @return true to continue processing, false to stop generic processing of expressions
     */
    public abstract boolean processExpressions(
            @Nonnull StageExecution stage,
            @Nonnull ContextParameterProcessor contextParameterProcessor,
            @Nonnull ExpressionEvaluationSummary summary);

    /**
     * Processes all entries in the stage context that aren't handled in a special way by the stage
     * itself (e.g. things like comments, notifications, etc)
     *
     * <p>This function should be called by the implementation of processExpressions, ideally, in the
     * very beginning
     *
     * @param stage                     (should be same as passed into processExpressions)
     * @param contextParameterProcessor (should be same as passed into processExpressions)
     * @param summary                   (should be same as passed into processExpressions)
     * @param excludedKeys              keys to exclude from processing
     */
    public final void processDefaultEntries(
            @Nonnull StageExecution stage,
            @Nonnull ContextParameterProcessor contextParameterProcessor,
            @Nonnull ExpressionEvaluationSummary summary,
            Collection<String> excludedKeys) {

        StageContext augmentedContext = contextParameterProcessor.buildExecutionContext(stage);
        Map<String, Object> stageContext = stage.getContext();

        for (String key : stageContext.keySet()) {
            if (!excludedKeys.contains(key)) {
                Map<String, Object> temp = new HashMap<>();
                temp.put(key, stageContext.get(key));
                stageContext.put(
                        key, contextParameterProcessor.process(temp, augmentedContext, true, summary).get(key));
            }
        }
    }
}
