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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ConfigDistribution {
    @JsonProperty("application")
    private TypesNamespacedName application = null;

    @JsonProperty("configs")
    private List<ConfigNamespacedName> configs = new ArrayList<ConfigNamespacedName>();

    @JsonProperty("createdTime")
    private Date createdTime = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("namespace")
    private String namespace = null;

    @JsonProperty("status")
    private CommonAppStatus status = null;

    @JsonProperty("targets")
    private List<ConfigClusterTarget> targets = new ArrayList<ConfigClusterTarget>();

}

