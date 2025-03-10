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
package cn.sliew.carp.module.orca.core.persistence.sql.service.dto;

import cn.sliew.carp.framework.common.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;


/**
 * dag orca pipeline stage config
 */
@Data
@Schema(name = "CarpDagOrcaPipelineStageConfigDTO", description = "dag orca pipeline stage config")
public class CarpDagOrcaPipelineStageConfigDTO extends BaseDTO {

    @Schema(description = "uuid")
    private String uuid;

    @Schema(description = "name")
    private String name;

    @Schema(description = "meta")
    private CarpDagOrcaPipelineStageConfigMeta meta;

    @Schema(description = "attrs")
    private CarpDagOrcaPipelineStageConfigAttrs attrs;

    @Data
    public static class CarpDagOrcaPipelineStageConfigMeta {

        @Schema(description = "type")
        private String type;

        @Schema(description = "syntheticStageOwner")
        private String syntheticStageOwner;
    }

    @Data
    public static class CarpDagOrcaPipelineStageConfigAttrs {

        @Schema(description = "context")
        private Map<String, Object> context;

        @Schema(description = "additional metric tags")
        private Map<String, Object> additionalMetricTags;
    }
}
