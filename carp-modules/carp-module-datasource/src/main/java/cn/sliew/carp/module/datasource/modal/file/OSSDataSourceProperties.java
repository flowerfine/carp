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
package cn.sliew.carp.module.datasource.modal.file;

import cn.sliew.carp.framework.common.dict.datasource.CarpDataSourceType;
import cn.sliew.carp.module.datasource.modal.AbstractDataSourceProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OSSDataSourceProperties extends AbstractDataSourceProperties {

    @NotBlank
    @Schema(description = "endpoint")
    private String endpoint;

    @NotBlank
    @Schema(description = "bucket")
    private String bucket;

    @NotBlank
    @Schema(description = "access key")
    private String accessKey;

    @NotBlank
    @Schema(description = "access secret")
    private String accessSecret;

    @Override
    public String getType() {
        return CarpDataSourceType.OSS.getValue();
    }
}
