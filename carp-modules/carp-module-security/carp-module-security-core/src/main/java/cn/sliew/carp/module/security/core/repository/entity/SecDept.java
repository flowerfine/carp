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

import cn.sliew.carp.framework.common.dict.security.CarpSecDeptStatus;
import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("carp_sec_dept")
public class SecDept extends BaseAuditDO {

    private static final long serialVersionUID = -6268620152048177679L;

    @TableField("code")
    private String code;

    @TableField("name")
    private String name;

    @TableField("pid")
    private Long pid;

    @TableField("status")
    private CarpSecDeptStatus status;

    @TableField("remark")
    private String remark;
}
