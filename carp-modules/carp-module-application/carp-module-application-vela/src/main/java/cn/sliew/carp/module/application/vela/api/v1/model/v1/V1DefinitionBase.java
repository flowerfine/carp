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

package cn.sliew.carp.module.application.vela.api.v1.model.v1;

import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1beta1ComponentDefinitionSpec;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1beta1PolicyDefinitionSpec;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1beta1TraitDefinitionSpec;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1beta1WorkflowStepDefinitionSpec;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class V1DefinitionBase {
    @JsonProperty("alias")
    private String alias = null;

    @JsonProperty("category")
    private String category = null;

    @JsonProperty("component")
    private V1beta1ComponentDefinitionSpec component = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("icon")
    private String icon = null;

    @JsonProperty("labels")
    private Map<String, String> labels = new HashMap<String, String>();

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("ownerAddon")
    private String ownerAddon = null;

    @JsonProperty("policy")
    private V1beta1PolicyDefinitionSpec policy = null;

    @JsonProperty("status")
    private String status = null;

    @JsonProperty("trait")
    private V1beta1TraitDefinitionSpec trait = null;

    @JsonProperty("workflowStep")
    private V1beta1WorkflowStepDefinitionSpec workflowStep = null;

    @JsonProperty("workloadType")
    private String workloadType = null;

}

