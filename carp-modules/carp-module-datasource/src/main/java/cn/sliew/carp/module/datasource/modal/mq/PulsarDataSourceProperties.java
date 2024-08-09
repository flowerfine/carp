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

package cn.sliew.carp.module.datasource.modal.mq;

import cn.sliew.carp.framework.common.dict.datasource.DataSourceType;
import cn.sliew.carp.module.datasource.modal.AbstractDataSourceProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PulsarDataSourceProperties extends AbstractDataSourceProperties {

    @NotBlank
    @Schema(description = "admin web service url")
    private String webServiceUrl;

    @NotBlank
    @Schema(description = "client service url")
    private String clientServiceUrl;

    @Schema(description = "authentication plugin")
    private String authPlugin;

    @Schema(description = "authentication plugin parameters")
    private String authParams;

    @Override
    public String getType() {
        return DataSourceType.PULSAR.getValue();
    }
}
