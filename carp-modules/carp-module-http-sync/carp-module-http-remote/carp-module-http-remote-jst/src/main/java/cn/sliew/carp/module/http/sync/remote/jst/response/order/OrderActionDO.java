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
package cn.sliew.carp.module.http.sync.remote.jst.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderActionDO {

    /**
     * 日志id
     */
    @JsonProperty("oa_id")
    private Long oaId;

    /**
     * 订单id
     */
    @JsonProperty("o_id")
    private Long oId;

    /**
     * 操作名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 操作时间
     */
    @JsonProperty("created")
    private String created;

    /**
     * 创建人
     */
    @JsonProperty("creator_name")
    private String creatorName;
}
