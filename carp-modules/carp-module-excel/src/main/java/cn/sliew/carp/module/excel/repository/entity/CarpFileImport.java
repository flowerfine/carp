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

package cn.sliew.carp.module.excel.repository.entity;

import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("carp_file_import")
public class CarpFileImport extends BaseAuditDO {

    private static final long serialVersionUID = 1L;

    @TableField("namespace")
    private String namespace;

    @TableField("app")
    private String app;

    @TableField("env")
    private String env;

    @TableField("tenant")
    private String tenant;

    @TableField("biz_code")
    private String bizCode;

    @TableField("sub_biz_code")
    private String subBizCode;

    @TableField("`type`")
    private String type;

    @TableField("execute_type")
    private String executeType;

    @TableField("priority")
    private Integer priority;

    @TableField("file_url")
    private String fileUrl;

    @TableField("file_name")
    private String fileName;

    @TableField("`status`")
    private String status;

    @TableField("message")
    private String message;

    @TableField("retry_times")
    private Integer retryTimes;

    @TableField("remark")
    private String remark;
}
