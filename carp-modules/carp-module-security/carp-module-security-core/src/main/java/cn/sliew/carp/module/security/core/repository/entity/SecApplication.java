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

import cn.sliew.carp.framework.common.dict.security.CarpSecApplicationStatus;
import cn.sliew.carp.framework.common.dict.security.CarpSecApplicationType;
import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("carp_sec_application")
@Schema(name = "SecApplication", description = "security application")
public class SecApplication extends BaseAuditDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户类型。系统，用户自定义")
    @TableField("`type`")
    private CarpSecApplicationType type;

    @Schema(description = "应用标识")
    @TableField("`code`")
    private String code;

    @Schema(description = "应用名称")
    @TableField("`name`")
    private String name;

    @Schema(description = "应用logo")
    @TableField("logo")
    private String logo;

    @Schema(description = "应用 url")
    @TableField("url")
    private String url;

    @Schema(description = "排序")
    @TableField("`order`")
    private Integer order;

    @Schema(description = "用户状态。启用，禁用")
    @TableField("`status`")
    private CarpSecApplicationStatus status;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
