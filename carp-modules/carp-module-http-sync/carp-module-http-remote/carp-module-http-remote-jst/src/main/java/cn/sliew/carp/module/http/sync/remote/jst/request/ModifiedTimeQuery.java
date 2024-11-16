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

package cn.sliew.carp.module.http.sync.remote.jst.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ModifiedTimeQuery extends PagedQuery {

    @Schema(description = "修改起始时间,起始时间和 结束时间必须同时存在，时间间隔不能超过七天")
    @JsonProperty("modified_begin")
    private String modifiedBegin;

    @Schema(description = "修改起始时间,起始时间和 结束时间必须同时存在，时间间隔不能超过七天")
    @JsonProperty("modified_end")
    private String modifiedEnd;
}
