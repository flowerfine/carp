/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.util;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionType;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageContext;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.trigger.Trigger;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.ExpressionEvaluationSummary;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.ExpressionFunctionProvider;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.config.ExpressionProperties;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.expressions.PipelineExpressionEvaluator;
import cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.expressions.functions.StageExpressionFunctionProvider;
import cn.sliew.milky.common.util.JacksonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Common methods for dealing with passing context parameters used by both Script and Jenkins stages
 */
@Slf4j
public class ContextParameterProcessor {

    private static final ObjectMapper mapper = JacksonUtil.getMapper();

    private PipelineExpressionEvaluator expressionEvaluator;

    @VisibleForTesting
    public ContextParameterProcessor() {
        this(
                Arrays.asList(
                        new StageExpressionFunctionProvider()),
                new ExpressionProperties());
    }

    public ContextParameterProcessor(
            List<ExpressionFunctionProvider> expressionFunctionProviders,
            ExpressionProperties expressionProperties) {
        this.expressionEvaluator =
                new PipelineExpressionEvaluator(
                        expressionFunctionProviders, expressionProperties);
    }

    public Map<String, Object> process(
            Map<String, Object> source, Map<String, Object> context, boolean allowUnknownKeys) {
        ExpressionEvaluationSummary summary = new ExpressionEvaluationSummary();

        return process(source, context, allowUnknownKeys, summary);
    }

    /**
     * Process pipeline to evaluate spel expressions. Note that 'stages' key is not processed if we
     * are using spel v4
     */
    public Map<String, Object> processPipeline(
            Map<String, Object> pipeline, Map<String, Object> context, boolean allowUnknownKeys) {

        final String spelEvaluatorKey = "spelEvaluator";

        ExpressionEvaluationSummary summary = new ExpressionEvaluationSummary();
        PipelineExpressionEvaluator.SpelEvaluatorVersion spelEvaluatorVersion =
                getEffectiveSpelVersionToUse((String) pipeline.get(spelEvaluatorKey));

        Object stages = null;
        if (PipelineExpressionEvaluator.SpelEvaluatorVersion.V4.equals(spelEvaluatorVersion)) {
            stages = pipeline.remove("stages");
        }

        Map<String, Object> processedPipeline = process(pipeline, context, allowUnknownKeys, summary);

        if (PipelineExpressionEvaluator.SpelEvaluatorVersion.V4.equals(spelEvaluatorVersion)) {
            processedPipeline.put("stages", stages);
        }

        return processedPipeline;
    }

    public Map<String, Object> process(
            Map<String, Object> source,
            Map<String, Object> context,
            boolean allowUnknownKeys,
            ExpressionEvaluationSummary summary) {

        if (source == null) {
            return null;
        }

        if (source.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, Object> result =
                expressionEvaluator.evaluate(source, precomputeValues(context), summary, allowUnknownKeys);

        if (summary.getTotalEvaluated() > 0 && context.containsKey("execution")) {
            log.info("Evaluated {}", summary);
        }

        if (summary.getFailureCount() > 0) {
            result.put(
                    PipelineExpressionEvaluator.SUMMARY,
                    mapper.convertValue(summary.getExpressionResult(), Map.class));
        }

        return result;
    }

    /**
     * Builds a context for the SpEL evaluator to use while processing a stage This involves merging
     * the following into a map - the stage context - execution object (if PIPELINE) - trigger object
     * (if PIPELINE)
     *
     * @param stage Stage to build context for
     * @return StageContext (really a map) for the merged context
     */
    public StageContext buildExecutionContext(StageExecution stage) {
        Map<String, Object> augmentedContext = new HashMap<>(stage.getContext());
        PipelineExecution execution = stage.getPipelineExecution();

        if (execution.getType() == ExecutionType.PIPELINE) {
            augmentedContext.putAll(buildExecutionContext(execution));

            // MPTv2 uses templateVariables which used to be expanded at pipeline creation time.
            // With SpEL V4 we don't preprocess the whole pipeline anymore, hence we append
            // "templateVariables" to the SpEL
            // evaluation context here so that those vars can be processed and expanded when the stage
            // runs
            PipelineExpressionEvaluator.SpelEvaluatorVersion spelEvaluatorVersion =
                    getEffectiveSpelVersionToUse(execution.getSpelEvaluator());

            if (PipelineExpressionEvaluator.SpelEvaluatorVersion.V4.equals(spelEvaluatorVersion)) {
                Map templatedVariables = execution.getTemplateVariables();
                if (templatedVariables != null && !templatedVariables.isEmpty()) {
                    augmentedContext.put("templateVariables", templatedVariables);
                }
            }
        }

        return new StageContext(stage, augmentedContext);
    }

    /**
     * Builds a context for the SpEL evaluator to use while processing an execution This involves
     * merging the following into a map - execution object (if PIPELINE) - trigger object (if
     * PIPELINE)
     *
     * @param execution Execution to build context for
     * @return Map of the merged context
     */
    public Map<String, Object> buildExecutionContext(PipelineExecution execution) {
        Map<String, Object> executionContext = new HashMap<>();

        executionContext.put("execution", execution);
        executionContext.put(
                "trigger",
                mapper.convertValue(execution.getTrigger(), new TypeReference<Map<String, Object>>() {
                }));

        return executionContext;
    }

    public static boolean containsExpression(String value) {
        return isNotEmpty(value) && value.contains("${");
    }

    public PipelineExpressionEvaluator.SpelEvaluatorVersion getEffectiveSpelVersionToUse(String executionSpelVersion) {
        return PipelineExpressionEvaluator.SpelEvaluatorVersion.fromStringKey(executionSpelVersion);
    }

    private Map<String, Object> precomputeValues(Map<String, Object> context) {
        // Copy the data over so we don't mutate the original context!
        if (context instanceof StageContext) {
            context = new StageContext((StageContext) context);
        } else {
            context = new HashMap<>(context);
        }

        Object rawTrigger = context.get("trigger");
        Trigger trigger;
        if (rawTrigger != null && !(rawTrigger instanceof Trigger)) {
            trigger = mapper.convertValue(rawTrigger, Trigger.class);
        } else {
            trigger = (Trigger) rawTrigger;
        }

        if (trigger != null && !trigger.getParameters().isEmpty()) {
            context.put("parameters", trigger.getParameters());
        } else {
            if (!context.containsKey("parameters")) {
                context.put("parameters", Collections.emptyMap());
            }
        }

        return context;
    }
}
