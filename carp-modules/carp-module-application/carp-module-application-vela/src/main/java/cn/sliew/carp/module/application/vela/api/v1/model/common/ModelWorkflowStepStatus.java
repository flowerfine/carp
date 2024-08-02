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

import java.util.Date;
import java.util.List;

@Data
public class ModelWorkflowStepStatus {
    @JsonProperty("alias")
    private String alias = null;

    @JsonProperty("firstExecuteTime")
    private Date firstExecuteTime = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("lastExecuteTime")
    private Date lastExecuteTime = null;

    @JsonProperty("message")
    private String message = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("phase")
    private String phase = null;

    @JsonProperty("reason")
    private String reason = null;

    @JsonProperty("subSteps")
    private List<ModelStepStatus> subSteps = null;

    @JsonProperty("type")
    private String type = null;

}

