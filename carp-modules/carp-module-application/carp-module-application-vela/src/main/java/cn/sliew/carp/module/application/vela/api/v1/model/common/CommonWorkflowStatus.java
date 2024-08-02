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

import cn.sliew.carp.module.application.vela.api.v1.model.V1ObjectReference;
import cn.sliew.carp.module.application.vela.api.v1.model.V1alpha1WorkflowStepStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommonWorkflowStatus {
    @JsonProperty("appRevision")
    private String appRevision = null;

    @JsonProperty("contextBackend")
    private V1ObjectReference contextBackend = null;

    @JsonProperty("endTime")
    private String endTime = null;

    @JsonProperty("finished")
    private Boolean finished = null;

    @JsonProperty("message")
    private String message = null;

    @JsonProperty("mode")
    private String mode = null;

    @JsonProperty("startTime")
    private String startTime = null;

    @JsonProperty("status")
    private String status = null;

    @JsonProperty("steps")
    private List<V1alpha1WorkflowStepStatus> steps = null;

    @JsonProperty("suspend")
    private Boolean suspend = null;

    @JsonProperty("suspendState")
    private String suspendState = null;

    @JsonProperty("terminated")
    private Boolean terminated = null;

}

