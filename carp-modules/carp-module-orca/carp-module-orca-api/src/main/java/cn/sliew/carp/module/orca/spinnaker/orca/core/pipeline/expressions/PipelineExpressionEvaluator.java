/*
 * Copyright 2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sliew.carp.module.orca.spinnaker.orca.core.pipeline.expressions;

import cn.sliew.carp.module.orca.spinnaker.api.model.ExecutionStatus;
import cn.sliew.carp.module.orca.spinnaker.api.model.pipeline.PipelineExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.stage.StageExecution;
import cn.sliew.carp.module.orca.spinnaker.api.model.trigger.Trigger;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.ExpressionEvaluationSummary;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.ExpressionFunctionProvider;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.ExpressionTransform;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.ExpressionsSupport;
import cn.sliew.carp.module.orca.spinnaker.kork.expressions.config.ExpressionProperties;
import com.google.common.base.Strings;
import lombok.Getter;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.*;
import java.util.function.Function;

public class PipelineExpressionEvaluator {
    public static final String SUMMARY = "expressionEvaluationSummary";
    public static final String ERROR = "Failed Expression Evaluation";

    @Getter
    public enum SpelEvaluatorVersion {
        V4(
                "v4",
                "Evaluates expressions at stage start; supports sequential evaluation of variables in Evaluate Variables stage",
                true),
        V3("v3", "Evaluates expressions as soon as possible, not recommended", true, false),
        V2("v2", "DO NOT USE", true, true);

        SpelEvaluatorVersion(String key, String description, boolean supported) {
            this(key, description, supported, false);
        }

        SpelEvaluatorVersion(String key, String description, boolean supported, boolean deprecated) {
            this.key = key;
            this.description = description;
            this.supported = supported;
            this.deprecated = deprecated;
        }

        String key;
        String description;
        boolean supported;
        boolean deprecated;

        public static SpelEvaluatorVersion fromStringKey(String key) {
            if (Strings.isNullOrEmpty(key)) {
                return Default();
            }

            for (SpelEvaluatorVersion spelVersion : values()) {
                if (key.equalsIgnoreCase(spelVersion.key)) {
                    return spelVersion;
                }
            }

            return Default();
        }

        public static boolean isSupported(String version) {
            for (SpelEvaluatorVersion spelEvaluatorVersion : values()) {
                if (version.equals(spelEvaluatorVersion.key)) {
                    return spelEvaluatorVersion.isSupported();
                }
            }

            return false;
        }

        public static SpelEvaluatorVersion Default() {
            return V3;
        }
    }

    // No new items should go in to this list. We should use functions instead of vars going forward.
    private static final List<String> EXECUTION_AWARE_ALIASES =
            Collections.singletonList("deployedServerGroups");

    private static Class[] extraAllowedReturnTypes =
            new Class[]{
                    PipelineExecution.class,
                    PipelineExecution.PausedDetails.class,
                    StageExecution.class,
                    ExecutionStatus.class,
                    Trigger.class,
            };
    private final ExpressionParser parser;
    private final ParserContext parserContext = new TemplateParserContext("${", "}");
    private final ExpressionsSupport support;

    @Getter
    private final Set<String> executionAwareFunctions = new HashSet<String>();

    public PipelineExpressionEvaluator(
            List<ExpressionFunctionProvider> expressionFunctionProviders,
            ExpressionProperties expressionProperties) {
        this.support =
                new ExpressionsSupport(
                        extraAllowedReturnTypes,
                        expressionFunctionProviders,
                        expressionProperties);
        initExecutionAwareFunctions(expressionFunctionProviders);
        parser =
                new SpelExpressionParser(
                        expressionProperties.getMaxExpressionLength() > 0
                                ? new SpelParserConfiguration(
                                null, null, false, false, 0, expressionProperties.getMaxExpressionLength())
                                : new SpelParserConfiguration());
    }

    public Map<String, Object> evaluate(
            Map<String, Object> source,
            Object rootObject,
            ExpressionEvaluationSummary summary,
            boolean allowUnknownKeys) {
        StandardEvaluationContext evaluationContext =
                support.buildEvaluationContext(rootObject, allowUnknownKeys);
        return new ExpressionTransform(
                parserContext, parser, includeExecutionParameter, ExecutionStatus.class)
                .transformMap(source, evaluationContext, summary);
    }

    private final Function<String, String> includeExecutionParameter =
            e -> {
                String expression = e;
                for (String fn : this.executionAwareFunctions) {
                    if (expression.contains("#" + fn)
                            && !expression.contains("#" + fn + "( #root.execution, ")) {
                        expression = expression.replaceAll("#" + fn + "\\(", "#" + fn + "( #root.execution, ");
                    }
                }

                // 'deployServerGroups' is a variable instead of a function and this block handles that.
                // Migrate the pipelines to use function instead, before removing this block of code.
                for (String a : EXECUTION_AWARE_ALIASES) {
                    if (expression.contains(a) && !expression.contains("#" + a + "( #root.execution, ")) {
                        expression = expression.replaceAll(a, "#" + a + "( #root.execution)");
                    }
                }

                return expression;
            };

    private void initExecutionAwareFunctions(
            List<ExpressionFunctionProvider> expressionFunctionProviders) {

        expressionFunctionProviders.forEach(
                p -> {
                    p.getFunctions()
                            .getFunctionsDefinitions()
                            .forEach(
                                    f -> {
                                        if (!f.getParameters().isEmpty()
                                                && f.getParameters().get(0).getType() == PipelineExecution.class) {
                                            this.executionAwareFunctions.add(f.getName());
                                        }
                                    });
                });
    }
}
