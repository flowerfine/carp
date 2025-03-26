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
package cn.sliew.carp.module.workflow.domain.instance;

import cn.sliew.carp.framework.common.model.BaseBuilderDTO;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * A "task" is a component piece of a stage
 */
@Data
@Jacksonized
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowTaskInstance extends BaseBuilderDTO {

    private String namespace;
    private Long workflowInstanceId;
    private Long stepId;
    private Long taskId;
    private String uuid = UUIDUtil.randomUUId();
    private String name;
    private String implementingClass;
    private Instant startTime;
    private Instant endTime;
    private String status;
    private boolean stageStart;
    private boolean stageEnd;
    private boolean loopStart;
    private boolean loopEnd;
    private Map<String, Object> taskExceptionDetails = new HashMap<>();
}
