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
package cn.sliew.carp.module.cep.service.dto.runtime;

import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.status.StatusData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class TaskReportDTO {

    private String id;
    private Map<String, Object> inputs;
    private Map<String, Object> outputs;
    private WorkflowStatusData workflowStatus;
    private Map<String, WorkflowNodeReport> reports;
    private Map<String, List<WorkflowMessage>> messages;

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    public static class WorkflowStatusData {
        private String status;
        private Boolean terminated;
        private Long startTime;
        private Long endTime;
        private Long timeCost;

        public WorkflowStatusData(StatusData statusData) {
            setStatus(statusData.getStatus().getValue());
            setTerminated(statusData.getTerminated());
            setStartTime(statusData.getStartTime());
            setEndTime(statusData.getEndTime());
            setTimeCost(statusData.getTimeCost());
        }
    }

    @Data
    @Accessors(chain = true)
    public static class WorkflowNodeSnapshot {
        private String id;
        private String nodeID;
        private Map<String, Object> inputs;
        private Map<String, Object> outputs;
        private Map<String, Object> data;
        private String branch;
    }

    @Data
    @Accessors(chain = true)
    public static class WorkflowNodeReport extends WorkflowStatusData {
        private String id;
        private List<WorkflowNodeSnapshot> snapshots;
    }

    @Data
    @Accessors(chain = true)
    public static class WorkflowMessage {
        private String id;
        private String type;
        private String message;
        private String nodeID;
        private Long timestamp;
    }
}
