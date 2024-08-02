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

import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonDefinitionReference;
import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonSchematic;
import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class V1beta1TraitDefinitionSpec {
    @JsonProperty("appliesToWorkloads")
    private List<String> appliesToWorkloads = null;

    @JsonProperty("conflictsWith")
    private List<String> conflictsWith = null;

    @JsonProperty("controlPlaneOnly")
    private Boolean controlPlaneOnly = null;

    @JsonProperty("definitionRef")
    private CommonDefinitionReference definitionRef = null;

    @JsonProperty("extension")
    private String extension = null;

    @JsonProperty("manageWorkload")
    private Boolean manageWorkload = null;

    @JsonProperty("podDisruptive")
    private Boolean podDisruptive = null;

    @JsonProperty("revisionEnabled")
    private Boolean revisionEnabled = null;

    @JsonProperty("schematic")
    private CommonSchematic schematic = null;

    @JsonProperty("stage")
    private String stage = null;

    @JsonProperty("status")
    private CommonStatus status = null;

    @JsonProperty("workloadRefPath")
    private String workloadRefPath = null;

}

