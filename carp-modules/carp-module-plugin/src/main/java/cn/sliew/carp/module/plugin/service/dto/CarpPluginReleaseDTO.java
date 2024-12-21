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

import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CarpPluginRelease", description = "plugin release")
public class CarpPluginReleaseDTO extends BaseAuditDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "插件ID")
    private Long pluginId;

    @Schema(description = "uuid")
    private String uuid;

    @Schema(description = "版本")
    private String version;

    @Schema(description = "链接")
    private String url;
}
