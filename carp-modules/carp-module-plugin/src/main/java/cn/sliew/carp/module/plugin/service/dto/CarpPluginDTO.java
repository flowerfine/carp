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

package cn.sliew.carp.module.plugin.service.dto;

import cn.sliew.carp.framework.common.dict.common.YesOrNo;
import cn.sliew.carp.framework.common.dict.plugin.PluginType;
import cn.sliew.carp.framework.common.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Schema(name = "CarpPlugin", description = "plugin")
public class CarpPluginDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "type")
    private PluginType type;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "链接")
    private String url;

    @Schema(description = "状态")
    private YesOrNo status;

    @Schema(description = "pf4j pluginId")
    private String pluginId;

    @Schema(description = "备注")
    private String remark;
}
