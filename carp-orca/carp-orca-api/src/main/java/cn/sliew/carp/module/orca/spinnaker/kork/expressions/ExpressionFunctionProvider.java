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
package cn.sliew.carp.module.orca.spinnaker.kork.expressions;

import cn.sliew.carp.module.orca.spinnaker.kork.plugins.api.internal.SpinnakerExtensionPoint;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Provides a contract for adding new function definitions for SpEL evaluation.
 *
 * <p>The SpEL expression evaluator expects the function implementations are included in the
 * same concrete class as the {@link ExpressionFunctionProvider}, with method names matching those
 * defined in the {@code getFunctions()} definitions.
 *
 * <pre>{@code
 * class HelloFunctionProvider : ExpressionFunctionProvider {
 *   override fun getNamespace(): String? = "netflix"
 *   override fun getFunctions(): Functions =
 *     Functions(
 *       "hello",
 *       FunctionParameter(
 *         Execution::class.java,
 *         "execution",
 *         "The pipeline execution object that this function is being invoked on"
 *       )
 *     )
 *
 *   @JvmStatic
 *   fun hello(execution: Execution): String =
 *     "Hello, ${execution.id}"
 * }
 * }</pre>
 * <p>
 * The above function provider could then be called in a SpEL expression:
 *
 * <p>{@code ${netflix.hello()}}
 */
public interface ExpressionFunctionProvider extends SpinnakerExtensionPoint {

    /**
     * Optional. Typically, a namespace should be provided if you are providing a non-core function.
     * The namespace value typically would be the name of your organization (e.g. {@code netflix} or
     * {@code myteamname}.
     */
    @Nullable
    String getNamespace();

    /**
     * A collection of {@link FunctionDefinition}s.
     */
    Functions getFunctions();

    /**
     * A wrapper for a collection of {@link FunctionDefinition} objects.
     */
    @Data
    @AllArgsConstructor
    class Functions {

        private final Collection<FunctionDefinition> functionsDefinitions;

        public Functions(FunctionDefinition... functionDefinitions) {
            this(Arrays.asList(functionDefinitions));
        }
    }

    /**
     * A single function definition. This defines the name and input parameter contract for
     * interacting with the function.
     */
    @Data
    @AllArgsConstructor
    class FunctionDefinition {

        /**
         * The name of the function, without a namespace value.
         */
        private final String name;

        /**
         * Developer-friendly description of the function.
         */
        private final String description;

        /**
         * A list of {@link FunctionParameter}s.
         */
        private final List<FunctionParameter> parameters;

        /**
         * End-user friendly documentation of the function, will be surfaced to the UI via Deck.
         */
        @Nullable
        private final FunctionDocumentation documentation;

        public FunctionDefinition(String name, String description, FunctionParameter... parameters) {
            this(name, description, Arrays.asList(parameters));
        }

        public FunctionDefinition(String name, String description, List<FunctionParameter> parameters) {
            this(name, description, parameters, null);
        }
    }

    /**
     * The definition of a single function parameter.
     */
    @Data
    @AllArgsConstructor
    class FunctionParameter {

        /**
         * The parameter value class type.
         */
        private final Class<?> type;

        /**
         * The human-friendly, yet machine-readable, name of the parameter.
         */
        private final String name;

        /**
         * A user-friendly description of the parameter.
         */
        private final String description;
    }

    /**
     * Documentation for a function.
     *
     * <p>This documentation is used by Deck to display in-line docs for a SpEL function.
     */
    @Data
    @AllArgsConstructor
    class FunctionDocumentation {

        /**
         * Documentation text. Can contain Markdown.
         */
        private final String documentation;

        /**
         * List of example usages of the function.
         */
        @Nullable
        private final List<FunctionUsageExample> examples;

        public FunctionDocumentation(String documentation) {
            this.documentation = documentation;
            this.examples = null;
        }

        public FunctionDocumentation(String documentation, FunctionUsageExample... examples) {
            this.documentation = documentation;
            this.examples = Arrays.asList(examples);
        }
    }

    /**
     * Function usage example.
     *
     * <p>This is used by Deck to display in-line docs for a SpEL function.
     */
    @Data
    @AllArgsConstructor
    class FunctionUsageExample {

        /**
         * Example usage, e.g. "#stage('bake in us-east-1').hasSucceeded"
         */
        private final String usage;

        /**
         * Explanation of the usage sample. Markdown is supported.
         *
         * <p>e.g. "checks if the bake stage has completed successfully"
         */
        private final String description;
    }
}
