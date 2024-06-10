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

import cn.sliew.carp.framework.common.dict.common.*;
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

    GENDER("gender", "性别", Gender.class),
    ID_CARD_TYPE("id_card_type", "证件类型", IdCardType.class),
    NATION("nation", "国家", Nation.class),
    MESSAGE_TYPE("message_type", "消息类型", MessageType.class),
    REGISTER_CHANNEL("register_channel", "注册渠道", RegisterChannel.class),
    LOGIN_TYPE("login_type", "登录类型", LoginType.class),
    USER_TYPE("user_type", "用户类型", UserType.class),
    USER_STATUS("user_status", "用户状态", UserStatus.class),
    ROLE_TYPE("role_type", "角色类型", RoleType.class),
    ROLE_STATUS("role_status", "角色状态", RoleStatus.class),
    DEPT_STATUS("dept_status", "部门状态", DeptStatus.class),
    RESOURCE_TYPE("resource_type", "权限资源类型", ResourceType.class),
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