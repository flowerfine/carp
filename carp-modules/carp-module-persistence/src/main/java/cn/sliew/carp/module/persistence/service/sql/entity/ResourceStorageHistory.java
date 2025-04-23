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
package cn.sliew.carp.module.persistence.service.sql.entity;

import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class ResourceStorageHistory extends BaseAuditDO {

    @TableField("resource_id")
    private Long resourceId;

    @TableField("namespace")
    private String namespace;

    @TableField("metadata")
    private String metadata;

    @TableField("spec")
    private String spec;

    @TableField("status")
    private String status;

    @TableField("remark")
    private String remark;

    @TableField("version")
    private Integer version;
}
