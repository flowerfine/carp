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

package cn.sliew.carp.module.excel.service.dto;

import cn.sliew.carp.framework.common.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "CarpFileImport", description = "文件上传")
public class CarpFileImportDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "命名空间")
    private String namespace;

    @Schema(description = "应用")
    private String app;

    @Schema(description = "环境")
    private String env;

    @Schema(description = "租户")
    private String tenant;

    @Schema(description = "业务编码")
    private String bizCode;

    @Schema(description = "子业务编码")
    private String subBizCode;

    @Schema(description = "导入类型。excel, csv, json 等")
    private String type;

    @Schema(description = "执行类型")
    private String executeType;

    @Schema(description = "优先级")
    private Integer priority;

    @Schema(description = "文件url")
    private String fileUrl;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "信息")
    private String message;

    @Schema(description = "重试次数")
    private Integer retryTimes;

    @Schema(description = "备注")
    private String remark;
}
