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

package cn.sliew.carp.module.excel.service.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UploadParam {

    @Schema(description = "命名空间", hidden = true)
    private String namespace;

    @Schema(description = "应用", hidden = true)
    private String app;

    @Schema(description = "环境", hidden = true)
    private String env;

    @Schema(description = "租户", hidden = true)
    private String tenant;

    @Schema(description = "业务编码")
    private String bizCode;

    @Schema(description = "子业务编码")
    private String subBizCode;

    @Schema(description = "导入类型。excel, csv, json 等")
    private String type;

    @Schema(description = "文件url", hidden = true)
    private String fileUrl;

    @Schema(description = "文件名", hidden = true)
    private String fileName;

    @Schema(description = "备注")
    private String remark;
}
