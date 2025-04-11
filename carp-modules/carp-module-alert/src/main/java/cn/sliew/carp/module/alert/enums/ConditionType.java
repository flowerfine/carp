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
package cn.sliew.carp.module.alert.enums;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConditionType implements DictInstance {
    GREATER(">", " greater than ", " 大于 "),
    GREATER_EQUAL(">=", " greater than or equal ", " 大于等于 "),
    LESS("<", " less than ", " 小于 "),
    LESS_EQUAL("<=", " less than or equal ", " 小于等于 "),
    EQUAL("==", " equal ", " 等于 "),
    NOT_EQUAL("!=", " not equal ", " 不等于 "),
    AND("and", " and ", " 且 "),
    OR("or", " or ", " 或 ");

    @JsonCreator
    public static ConditionType of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(ConditionType.class, value));
    }

    @EnumValue
    private String value;
    private String label;
    private String remark;

    ConditionType(String value, String label, String remark) {
        this.value = value;
        this.label = label;
        this.remark = remark;
    }
}
