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
package cn.sliew.carp.module.alert.repository.entity;

import cn.sliew.carp.framework.common.dict.common.CarpYesOrNo;
import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import cn.sliew.carp.module.alert.enums.AlertLevel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("carp_alert_rule")
public class CarpAlertRule extends BaseAuditDO {

    private static final long serialVersionUID = 1L;

    @TableField("namespace")
    private String namespace;

    @TableField("`name`")
    private String name;

    @TableField("uuid")
    private String uuid;

    @TableField("is_enabled")
    private CarpYesOrNo isEnabled;

    @TableField("`level`")
    private AlertLevel level;

    @TableField("promql")
    private String promql;

    @TableField("wait_for")
    private String waitFor;

    @TableField("summary")
    private String summary;

    @TableField("`description`")
    private String description;

    @TableField("remark")
    private String remark;
}
