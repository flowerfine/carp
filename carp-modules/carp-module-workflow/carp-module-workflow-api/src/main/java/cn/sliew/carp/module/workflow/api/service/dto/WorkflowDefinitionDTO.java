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

package cn.sliew.carp.module.workflow.api.service.dto;

import cn.sliew.carp.framework.common.dict.workflow.WorkflowType;
import cn.sliew.carp.framework.common.model.BaseDTO;
import cn.sliew.carp.framework.dag.service.dto.DagConfigComplexDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
public class WorkflowDefinitionDTO extends BaseDTO {

    @Schema(description = "workflow type")
    private WorkflowType type;

    @Schema(description = "workflow name")
    private String name;

    @Schema(description = "workflow param")
    private Map<String, Object> param;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "schedule")
    private WorkflowScheduleDTO schedule;

    @Schema(description = "dag")
    private DagConfigComplexDTO dag;
}
