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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class V1DetailApplicationResponse {
    @JsonProperty("alias")
    private String alias = null;

    @JsonProperty("annotations")
    private Map<String, String> annotations = null;

    @JsonProperty("createTime")
    private Date createTime = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("envBindings")
    private List<String> envBindings = new ArrayList<String>();

    @JsonProperty("icon")
    private String icon = null;

    @JsonProperty("labels")
    private Map<String, String> labels = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("policies")
    private List<String> policies = new ArrayList<String>();

    @JsonProperty("project")
    private V1ProjectBase project = null;

    @JsonProperty("readOnly")
    private Boolean readOnly = null;

    @JsonProperty("resourceInfo")
    private V1ApplicationResourceInfo resourceInfo = null;

    @JsonProperty("updateTime")
    private Date updateTime = null;

}
