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

package cn.sliew.carp.module.security.core.service.dto;

import cn.sliew.carp.framework.common.dict.security.ResourceStatus;
import cn.sliew.carp.framework.common.dict.security.ResourceWebType;
import cn.sliew.carp.framework.common.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "SecResourceWeb对象", description = "资源-web")
public class SecResourceWebDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "资源类型。导航，菜单，页面，按钮")
    private ResourceWebType type;

    @Schema(description = "上级资源id")
    private Long pid;

    @Schema(description = "资源值")
    private String value;

    @Schema(description = "资源名称")
    private String label;

    @Schema(description = "路由路径")
    private String path;

    @Schema(description = "order")
    private Integer order;

    @Schema(description = "资源状态")
    private ResourceStatus status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "下级资源")
    private List<SecResourceWebDTO> children;
}