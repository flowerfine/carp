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
package cn.sliew.carp.module.dataservice.domain.mybatis.entity;

import cn.sliew.carp.framework.common.dict.DictInstance;
import cn.sliew.carp.framework.common.dict.alert.AlertStatus;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ParamType implements DictInstance {

    PLACEHOLDER_COMPILE("#{}", "#{}占位符"),
    PLACEHOLDER_REPLACE("${}", "${}占位符"),
    VARIABLE_TEST("test", "<if><when>临时变量"),
    VARIABLE_FOREACH("foreach", "<foreach>临时变量"),
    VARIABLE_BIND("bind", "<bind>标签变量");

    @EnumValue
    private String value;
    private String label;

    @JsonCreator
    public static ParamType of(String value) {
        return Arrays.stream(values()).filter((instance) -> {
            return instance.getValue().equals(value);
        }).findAny().orElseThrow(() -> {
            return new EnumConstantNotPresentException(AlertStatus.class, value);
        });
    }

    ParamType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }
}
