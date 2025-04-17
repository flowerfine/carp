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
package cn.sliew.carp.plugin.workflow.engine.internal.domain.enums;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CarpWorkflowStepInstanceEvent implements DictInstance {

    COMMAND_DEPLOY("0", "COMMAND_DEPLOY"),
    COMMAND_SHUTDOWN("1", "COMMAND_SHUTDOWN"),
    COMMAND_SUSPEND("2", "COMMAND_SUSPEND"),
    COMMAND_RESUME("3", "COMMAND_RESUME"),
    COMMAND_SKIP("4", "COMMAND_SKIP"),

    PROCESS_TASK_CHANGE("5", "PROCESS_TASK_CHANGE"),
    PROCESS_SKIP_CAUSE_BY_SHUTDOWNED("6", "PROCESS_SKIP_CAUSE_BY_SHUTDOWNED"),
    PROCESS_SKIP_CAUSE_BY_FAILURE("7", "PROCESS_SKIP_CAUSE_BY_FAILURE"),
    PROCESS_SUCCESS("8", "PROCESS_SUCCESS"),
    PROCESS_FAILURE("9", "PROCESS_FAILURE"),
    ;

    @JsonCreator
    public static CarpWorkflowStepInstanceEvent of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(CarpWorkflowStepInstanceEvent.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    CarpWorkflowStepInstanceEvent(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
