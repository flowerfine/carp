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
import lombok.Data;

@Data
public class WorkflowDefinitionGraphNode extends BaseDTO {

    private String namespace;

    private Long workflowDefinitionId;

    /**
     * todo stepId -> nodeId
     */
    private String stepId;

    /**
     * todo stepName -> nodeName
     */
    private String stepName;

    private Integer positionX;

    private Integer positionY;

    private String shape;

    private String stype;

    private WorkflowDefinitionGraphNodeMeta meta;

    private WorkflowDefinitionGraphNodeAttrs attrs;
}
