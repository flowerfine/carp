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
