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

import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("carp_alert_log")
public class CarpAlertLog extends BaseAuditDO {

    private static final long serialVersionUID = 1L;

    @TableField("version")
    private String version;

    @TableField("group_key")
    private String groupKey;

    @TableField("group_labels")
    private String groupLabels;

    @TableField("receiver")
    private String receiver;

    @TableField("common_labels")
    private String commonLabels;

    @TableField("common_annotations")
    private String commonAnnotations;

    @TableField("fingerprint")
    private String fingerprint;

    @TableField("`status`")
    private String status;

    @TableField("labels")
    private String labels;

    @TableField("annotations")
    private String annotations;

    @TableField("starts_at")
    private Long startsAt;

    @TableField("ends_at")
    private Long endsAt;
}
