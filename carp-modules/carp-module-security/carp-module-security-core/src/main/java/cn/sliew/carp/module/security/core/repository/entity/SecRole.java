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

package cn.sliew.carp.module.security.core.repository.entity;

import cn.sliew.carp.framework.common.dict.security.RoleStatus;
import cn.sliew.carp.framework.common.dict.security.RoleType;
import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "sec_role", resultMap = "SecRoleMap")
public class SecRole extends BaseAuditDO {

    private static final long serialVersionUID = 2621684597930016649L;

    @TableField("type")
    private RoleType type;

    @TableField("`code`")
    private String code;

    @TableField("`name`")
    private String name;

    @TableField("`order`")
    private Integer order;

    @TableField("`status`")
    private RoleStatus status;

    @TableField("remark")
    private String remark;
}