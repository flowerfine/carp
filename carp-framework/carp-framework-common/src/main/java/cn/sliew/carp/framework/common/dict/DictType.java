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

package cn.sliew.carp.framework.common.dict;

import cn.sliew.carp.framework.common.dict.common.IsDeleted;
import cn.sliew.carp.framework.common.dict.common.YesOrNo;
import cn.sliew.carp.framework.common.dict.security.*;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DictType implements DictDefinition {

    YES_OR_NO("yes_or_no", "是否", YesOrNo.class),
    IS_DELETED("is_delete", "是否删除", IsDeleted.class),

    SEC_APPLICATION_TYPE("sec_application_type", "应用类型", SecApplicationType.class),
    SEC_APPLICATION_STATUS("sec_application_status", "应用状态", SecApplicationStatus.class),
    USER_TYPE("user_type", "用户类型", UserType.class),
    USER_STATUS("user_status", "用户状态", UserStatus.class),
    ROLE_TYPE("role_type", "角色类型", RoleType.class),
    ROLE_STATUS("role_status", "角色状态", RoleStatus.class),
    RESOURCE_WEB_TYPE("resource_web_type", "资源-web-类型", ResourceWebType.class),
    RESOURCE_DATA_TYPE("resource_data_type", "资源-数据-类型", ResourceDataType.class),
    RESOURCE_STATUS("resource_status", "资源状态", ResourceStatus.class),
    ;

    @JsonCreator
    public static DictType of(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getCode().equals(code))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(DictType.class, code));
    }

    @EnumValue
    private String code;
    private String name;
    private Class instanceClass;

    DictType(String code, String name, Class instanceClass) {
        this.code = code;
        this.name = name;
        this.instanceClass = instanceClass;
    }

    @Override
    public String getType() {
        return "Enum";
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
