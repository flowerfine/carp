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
package cn.sliew.carp.module.workflow.stage.model.domain.definition;

import cn.sliew.carp.framework.common.model.BaseDTO;
import cn.sliew.carp.module.workflow.stage.model.domain.param.StepInputParam;
import lombok.Data;

import java.util.List;

@Data
public class WorkflowDefinition extends BaseDTO {

    private String namespace;

    private String type;

    private String name;

    private String uuid;

    private WorkflowDefinitionGraph graph;

    private WorkflowDefinitionMeta meta;

    private WorkflowDefinitionAttrs attrs;

    private List<StepInputParam> inputOptions;

    private List<StepInputParam> outputOptions;

    private String remark;
}
