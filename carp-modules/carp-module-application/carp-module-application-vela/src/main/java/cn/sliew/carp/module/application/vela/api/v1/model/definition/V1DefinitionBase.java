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

package cn.sliew.carp.module.application.vela.api.v1.model.definition;

import lombok.Data;

import java.util.Map;

@Data
public class V1DefinitionBase {

    private String name;
    private String alias;
    private String description;
    private String category;
    private String icon;
    private Map<String, String> labels;
    private String workloadType;
    private String ownerAddon;
    private String status;
    private V1beta1ComponentDefinitionSpec component;
    private V1beta1PolicyDefinitionSpec policy;
    private V1beta1TraitDefinitionSpec trait;
    private V1beta1WorkflowStepDefinitionSpec workflowStep;
}

