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
package cn.sliew.carp.module.workflow.manager.service.dto;

import cn.sliew.carp.framework.common.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CarpWorkflowInstance", description = "workflow instance")
public class CarpWorkflowInstanceDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "命名空间")
    private String namespace;

    @Schema(description = "workflow definition id")
    private Long workflowDefinitionId;

    @Schema(description = "uuid")
    private String uuid;

    @Schema(description = "params")
    private String params;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "调度实例 ID")
    private Long schedulerInstanceId;

    @Schema(description = "remark")
    private String remark;
}
