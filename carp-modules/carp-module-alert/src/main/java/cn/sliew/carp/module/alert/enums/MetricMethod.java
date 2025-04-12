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
public enum MetricMethod implements DictInstance {
    COUNT("count", "count"),
    COUNT_BY("count by", "count by"),
    SUM("sum", "sum"),
    SUM_BY("sum by", "sum by"),
    AVG("avg", "avg"),
    AVG_BY("avg by", "avg by"),
    MAX("max", "max"),
    MAX_BY("max by", "max by"),
    DELTA("delta", "delta"),
    ABS("abs", "abs"),
    RATE("rate", "rate"),
    ;

    @JsonCreator
    public static MetricMethod of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(MetricMethod.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    MetricMethod(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
