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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

/**
 * dag orca pipeline
 */
@Data
@Schema(name = "CarpDagOrcaPipeline", description = "dag orca pipeline")
public class CarpDagOrcaPipelineDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "uuid")
    private String uuid;

    @Schema(description = "输入")
    private JsonNode intputs;

    @Schema(description = "输出")
    private JsonNode outputs;

    @Schema(description = "status")
    private String status;

    @Schema(description = "start time")
    private Instant startTime;

    @Schema(description = "end time")
    private Instant endTime;

    @Schema(description = "body")
    private CarpDagOrcaPipelineBody body;

    @Schema(description = "stages")
    private DAG<CarpDagOrcaPipelineStageDTO> stages;

    @Data
    public static class CarpDagOrcaPipelineBody {

        private JsonNode trigger;
        private Instant buildTime;
        private Instant startTimeExpiry;
        private JsonNode paused;
        private boolean canceled;
        private String canceledBy;
        private String cancellationReason;
    }
}
