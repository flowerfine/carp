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
package cn.sliew.carp.module.workflow.stage.model.task;

import cn.sliew.carp.module.workflow.stage.model.ExecutionStatus;
import com.github.f4b6a3.uuid.UuidCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * A "task" is a component piece of a stage
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskExecutionImpl implements TaskExecution {

    @EqualsAndHashCode.Include
    private Long id;
    private String uuid = UuidCreator.getShortPrefixComb().toString();
    private String name;
    private String implementingClass;
    private Instant startTime;
    private Instant endTime;
    private ExecutionStatus status = ExecutionStatus.NOT_STARTED;
    private boolean stageStart;
    private boolean stageEnd;
    private boolean loopStart;
    private boolean loopEnd;
    private Map<String, Object> taskExceptionDetails = new HashMap<>();
}
