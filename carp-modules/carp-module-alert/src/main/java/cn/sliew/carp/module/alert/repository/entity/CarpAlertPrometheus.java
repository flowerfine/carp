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
import cn.sliew.carp.module.alert.enums.PrometheusDeployType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("carp_alert_prometheus")
public class CarpAlertPrometheus extends BaseAuditDO {

    private static final long serialVersionUID = 1L;

    @TableField("namespace")
    private String namespace;

    @TableField("`name`")
    private String name;

    @TableField("uuid")
    private String uuid;

    @TableField("`type`")
    private PrometheusDeployType type;

    @TableField("url")
    private String url;

    @TableField("config_file_path")
    private String configFilePath;

    @TableField("alert_rule_path")
    private String alertRulePath;

    @TableField("is_auth_enabled")
    private CarpYesOrNo isAuthEnabled;

    @TableField("username")
    private String username;

    @TableField("`password`")
    private String password;

    @TableField("remark")
    private String remark;
}
