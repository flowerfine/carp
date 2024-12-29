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
package cn.sliew.carp.module.dataservice.repository.entity;

import cn.sliew.carp.framework.common.dict.common.CarpYesOrNo;
import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("carp_data_service_config")
public class CarpDataServiceConfig extends BaseAuditDO {

    private static final long serialVersionUID = 1L;

    @TableField("group_id")
    private Long groupId;

    @TableField("`type`")
    private String type;

    @TableField("`name`")
    private String name;

    @TableField("http_path")
    private String httpPath;

    @TableField("http_method")
    private String httpMethod;

    @TableField("http_content_type")
    private String httpContentType;

    @TableField("ds_id")
    private Long dsId;

    @TableField("query_type")
    private String queryType;

    @TableField("query_script")
    private String queryScript;

    @TableField("query_page_enabled")
    private CarpYesOrNo queryPageEnabled;

    @TableField("query_result_type")
    private String queryResultType;

    @TableField("`status`")
    private CarpYesOrNo status;

    @TableField("remark")
    private String remark;
}
