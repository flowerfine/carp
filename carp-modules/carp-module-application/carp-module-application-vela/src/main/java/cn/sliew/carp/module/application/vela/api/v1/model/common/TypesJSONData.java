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

package cn.sliew.carp.module.application.vela.api.v1.model.common;

import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1PolicyRule;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TypesJSONData {
    @JsonProperty("authSecret")
    private TypesKubernetesSecret authSecret = null;

    @JsonProperty("authType")
    private String authType = null;

    @JsonProperty("backend")
    private Boolean backend = null;

    @JsonProperty("backendService")
    private TypesKubernetesService backendService = null;

    @JsonProperty("backendType")
    private String backendType = null;

    @JsonProperty("category")
    private String category = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("includes")
    private List<TypesIncludes> includes = new ArrayList<TypesIncludes>();

    @JsonProperty("info")
    private TypesInfo info = null;

    @JsonProperty("kubePermissions")
    private List<V1PolicyRule> kubePermissions = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("proxy")
    private Boolean proxy = null;

    @JsonProperty("requirement")
    private TypesRequirement requirement = null;

    @JsonProperty("routes")
    private List<TypesRoute> routes = null;

    @JsonProperty("subType")
    private String subType = null;

    @JsonProperty("type")
    private String type = null;

}

