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

import cn.sliew.carp.module.application.vela.api.v1.model.common.ModelJSONStruct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class V1DetailPolicyResponse {
    @JsonProperty("alias")
    private String alias = null;

    @JsonProperty("createTime")
    private Date createTime = null;

    @JsonProperty("creator")
    private String creator = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("envName")
    private String envName = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("properties")
    private ModelJSONStruct properties = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("updateTime")
    private Date updateTime = null;

    @JsonProperty("workflowPolicyBind")
    private List<V1WorkflowPolicyBinding> workflowPolicyBind = null;

}

