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

package cn.sliew.carp.module.application.vela.api.v1.model.chart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChartMetadata {
    @JsonProperty("annotations")
    private Map<String, String> annotations = null;

    @JsonProperty("apiVersion")
    private String apiVersion = null;

    @JsonProperty("appVersion")
    private String appVersion = null;

    @JsonProperty("condition")
    private String condition = null;

    @JsonProperty("dependencies")
    private List<ChartDependency> dependencies = null;

    @JsonProperty("deprecated")
    private Boolean deprecated = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("home")
    private String home = null;

    @JsonProperty("icon")
    private String icon = null;

    @JsonProperty("keywords")
    private List<String> keywords = null;

    @JsonProperty("kubeVersion")
    private String kubeVersion = null;

    @JsonProperty("maintainers")
    private List<ChartMaintainer> maintainers = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("sources")
    private List<String> sources = null;

    @JsonProperty("tags")
    private String tags = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("version")
    private String version = null;

}

