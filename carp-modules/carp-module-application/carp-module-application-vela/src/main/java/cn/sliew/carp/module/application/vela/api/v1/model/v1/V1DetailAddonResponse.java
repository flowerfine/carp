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

import cn.sliew.carp.module.application.vela.api.v1.model.addon.AddonDependency;
import cn.sliew.carp.module.application.vela.api.v1.model.addon.AddonDeployTo;
import cn.sliew.carp.module.application.vela.api.v1.model.addon.AddonSystemRequirements;
import cn.sliew.carp.module.application.vela.api.v1.model.common.SchemaUIParameter;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1AddonDefinition;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class V1DetailAddonResponse {
    @JsonProperty("annotations")
    private Map<String, String> annotations = null;

    @JsonProperty("availableVersions")
    private List<String> availableVersions = new ArrayList<String>();

    @JsonProperty("definitions")
    private List<V1AddonDefinition> definitions = new ArrayList<V1AddonDefinition>();

    @JsonProperty("dependencies")
    private List<AddonDependency> dependencies = null;

    @JsonProperty("deployTo")
    private AddonDeployTo deployTo = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("detail")
    private String detail = null;

    @JsonProperty("icon")
    private String icon = null;

    @JsonProperty("invisible")
    private Boolean invisible = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("needNamespace")
    private List<String> needNamespace = null;

    @JsonProperty("registryName")
    private String registryName = null;

    @JsonProperty("schema")
    private String schema = null;

    @JsonProperty("system")
    private AddonSystemRequirements system = null;

    @JsonProperty("tags")
    private List<String> tags = null;

    @JsonProperty("uiSchema")
    private List<SchemaUIParameter> uiSchema = new ArrayList<SchemaUIParameter>();

    @JsonProperty("url")
    private String url = null;

    @JsonProperty("uxPlugins")
    private Map<String, String> uxPlugins = null;

    @JsonProperty("version")
    private String version = null;

}

