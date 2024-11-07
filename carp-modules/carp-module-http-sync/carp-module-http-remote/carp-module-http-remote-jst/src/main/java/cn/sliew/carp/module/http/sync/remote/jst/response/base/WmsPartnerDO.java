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

package cn.sliew.carp.module.http.sync.remote.jst.response.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WmsPartnerDO {

    /**
     * 分仓名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 主仓公司编号
     */
    @JsonProperty("co_id")
    private Integer coId;

    /**
     * 分仓编号
     */
    @JsonProperty("wms_co_id")
    private Integer wmsCoId;

    /**
     * 是否为主仓，true=主仓
     */
    @JsonProperty("is_main")
    private String isMain;

    /**
     * 仓储列表里的状态，不是仓库账号的状态
     */
    @JsonProperty("status")
    private String status;

    /**
     * 我方备注
     */
    @JsonProperty("remark1")
    private String remark1;

    /**
     * 对方备注
     */
    @JsonProperty("remark2")
    private String remark2;
}
