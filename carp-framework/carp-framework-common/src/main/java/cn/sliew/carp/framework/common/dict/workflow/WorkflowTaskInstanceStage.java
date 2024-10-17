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

package cn.sliew.carp.framework.common.dict.workflow;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WorkflowTaskInstanceStage implements DictInstance {

    PENDING("0", "PENDING"),
    RUNNING("1", "RUNNING"),
    SUSPEND("3", "SUSPEND"),
    SUCCESS("4", "SUCCESS"),
    FAILURE("5", "FAILURE"),
    TERMINATED("6", "TERMINATED"),
    ;

    @JsonCreator
    public static WorkflowTaskInstanceStage of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(WorkflowTaskInstanceStage.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    WorkflowTaskInstanceStage(String value, String label) {
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

    public boolean isEnd() {
        switch (this) {
            case SUCCESS:
            case FAILURE:
            case TERMINATED:
                return true;
            default:
                return false;
        }
    }

    public boolean isSuccess() {
        switch (this) {
            case SUCCESS:
                return true;
            default:
                return false;
        }
    }

    public boolean isFailure() {
        return isSuccess() == false;
    }

}
