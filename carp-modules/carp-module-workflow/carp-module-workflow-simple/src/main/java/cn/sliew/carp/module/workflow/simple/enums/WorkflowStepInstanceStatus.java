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
package cn.sliew.carp.module.workflow.simple.enums;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WorkflowStepInstanceStatus implements DictInstance {

    INIT("init", "Init"),
    RUNNING("running", "Running"),
    SUCCESS("success", "Success"),
    EXCEPTION("exception", "Exception"),

    WAIT_STOP("wait_stop", "Wait Stop"),
    STOPPING("stopping", "Stopping"),
    STOPPED("stopped", "Stopped"),

    MERGE("merge", "Merge"),
    SKIP("skip", "Skip"),
    SKIP_CAUSE_BY_STOPPED("skip_cause_by_stopped", "Skip By Stopped"),
    SKIP_CAUSE_BY_EXCEPTION("skip_cause_by_exception", "Skip By Exception"),
    ;

    @JsonCreator
    public static WorkflowStepInstanceStatus of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(WorkflowStepInstanceStatus.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    WorkflowStepInstanceStatus(String value, String label) {
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


    public boolean isRunningOrInit() {
        switch (this) {
            case INIT:
            case RUNNING:
                return true;
            default:
                return false;
        }
    }

    public boolean isNotSkip() {
        return this != WorkflowStepInstanceStatus.SKIP;
    }

    public boolean isSkip() {
        return this == WorkflowStepInstanceStatus.SKIP;
    }

    public boolean isSkipOrSuccess() {
        return this == WorkflowStepInstanceStatus.SKIP || this == WorkflowStepInstanceStatus.SUCCESS;
    }

    public boolean isNotSkipAndNotSuccess() {
        return !(isSkipOrSuccess());
    }

    public boolean isSuccess() {
        return this == WorkflowStepInstanceStatus.SUCCESS;
    }

    public boolean isException() {
        switch (this) {
            case EXCEPTION:
            case SKIP_CAUSE_BY_EXCEPTION:
                return true;
            default:
                return false;
        }
    }

    public boolean isStopped() {
        switch (this) {
            case STOPPED:
            case SKIP_CAUSE_BY_STOPPED:
                return true;
            default:
                return false;
        }
    }

    public boolean isEnd() {
        switch (this) {
            case INIT:
            case RUNNING:
            case STOPPING:
            case WAIT_STOP:
                return false;
            case EXCEPTION:
            case STOPPED:
            case SUCCESS:
            case SKIP_CAUSE_BY_EXCEPTION:
            case SKIP_CAUSE_BY_STOPPED:
            case SKIP:
            default:
                return true;
        }
    }

}
