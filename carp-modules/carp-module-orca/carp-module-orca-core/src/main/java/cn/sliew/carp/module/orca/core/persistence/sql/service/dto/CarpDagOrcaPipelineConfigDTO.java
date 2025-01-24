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
import cn.sliew.carp.framework.dag.algorithm.DAG;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * dag orca pipeline config
 */
@Data
@Schema(name = "CarpDagOrcaPipelineConfigDTO", description = "dag orca pipeline config")
public class CarpDagOrcaPipelineConfigDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "type")
    private String type;

    @Schema(description = "name")
    private String name;

    @Schema(description = "uuid")
    private String uuid;

    @Schema(description = "meta")
    private CarpDagOrcaPipelineConfigMeta meta;

    @Schema(description = "attrs")
    private CarpDagOrcaPipelineConfigAttrs attrs;

    @Schema(description = "输入参数声明")
    private JsonNode intputOptions;

    @Schema(description = "输出参数声明")
    private JsonNode outputOptions;

    @Schema(description = "remark")
    private String remark;

    @Schema(description = "stages")
    private DAG<CarpDagOrcaPipelineStageConfigDTO> stages;

    @Data
    public static class CarpDagOrcaPipelineConfigMeta {

        private String namespace;
        private String type;
        private String origin;
    }

    @Data
    public static class CarpDagOrcaPipelineConfigAttrs {

        private boolean limitConcurrent = false;
        private int maxConcurrentExecutions = 0;
        private boolean keepWaitingPipelines = false;
        private List<Map<String, Object>> notifications = Lists.newArrayList();
        private String spelEvaluator;
        private Map<String, Object> templateVariables = Maps.newHashMap();
    }

}
