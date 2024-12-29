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
package cn.sliew.carp.module.scheduler.api.dict;

import cn.sliew.carp.framework.common.dict.CarpDictType;
import cn.sliew.carp.framework.common.dict.DictDefinition;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CarpScheduleDictType implements DictDefinition {

    SCHEDULE_JAVA_EXECUTOR_TYPE("carp_schedule_java_executor_type", "Schedule Java Executor Type", CarpScheduleExecuteType.class),
    ;

    @JsonCreator
    public static CarpScheduleDictType of(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(CarpScheduleDictType.class, code));
    }

    @EnumValue
    private String code;
    private String name;
    private Class instanceClass;

    CarpScheduleDictType(String code, String name, Class instanceClass) {
        this.code = code;
        this.name = name;
        this.instanceClass = instanceClass;
    }

    @Override
    public String getProvider() {
        return "Carp";
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @JsonIgnore
    public Class getInstanceClass() {
        return instanceClass;
    }
}
