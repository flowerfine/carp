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

package cn.sliew.carp.module.application.vela.api.v1.model.definition;

import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonDefinitionReference;
import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonSchematic;
import cn.sliew.carp.module.application.vela.api.v1.model.common.CommonStatus;
import lombok.Data;

import java.util.List;

@Data
public class V1beta1TraitDefinitionSpec {

    private List<String> appliesToWorkloads;
    private List<String> conflictsWith;
    private Boolean controlPlaneOnly;
    private String extension;
    private Boolean manageWorkload;
    private Boolean podDisruptive;
    private Boolean revisionEnabled;
    private String stage;
    private CommonStatus status;
    private String workloadRefPath;
    private CommonDefinitionReference definitionRef;
    private CommonSchematic schematic;
}

