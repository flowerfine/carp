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

package cn.sliew.carp.module.datasource.modal;

import cn.sliew.carp.framework.common.collection.PropValuePair;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class DataSourceInfo {

    @NotNull
    @Schema(description = "data source type id")
    private Long dsTypeId;

    @Schema(description = "version")
    private String version;

    @NotBlank
    @Schema(description = "name")
    private String name;

    @Schema(description = "props")
    private AbstractDataSourceProperties props;

    @Schema(description = "additional props")
    private List<PropValuePair> additionalProps;

    @Schema(description = "remark")
    private String remark;
}
