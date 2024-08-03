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

import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1alpha1InputItem;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1alpha1OutputItem;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1alpha1WorkflowStepMeta;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class V1WorkflowStepBase {
    @JsonProperty("alias")
    private String alias = null;

    @JsonProperty("dependsOn")
    private List<String> dependsOn = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("if")
    private String _if = null;

    @JsonProperty("inputs")
    private List<V1alpha1InputItem> inputs = null;

    @JsonProperty("meta")
    private V1alpha1WorkflowStepMeta meta = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("outputs")
    private List<V1alpha1OutputItem> outputs = null;

    @JsonProperty("properties")
    private Object properties = null;

    @JsonProperty("timeout")
    private String timeout = null;

    @JsonProperty("type")
    private String type = null;

}

