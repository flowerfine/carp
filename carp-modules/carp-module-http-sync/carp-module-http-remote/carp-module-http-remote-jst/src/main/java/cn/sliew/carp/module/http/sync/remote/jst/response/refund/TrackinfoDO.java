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

package cn.sliew.carp.module.http.sync.remote.jst.response.refund;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TrackinfoDO {

    /**
     * 第几条
     */
    @JsonProperty("auto_id")
    private Long autoId;

    /**
     * 公司编号
     */
    @JsonProperty("co_idc")
    private Long coIdc;

    /**
     * 物流单号
     */
    @JsonProperty("l_id")
    private String lId;

    /**
     * 商品编码
     */
    @JsonProperty("sku_id")
    private String skuId;

    /**
     * 唯一码
     */
    @JsonProperty("sku_sn")
    private String skuSn;

    /**
     * 状态；1=发货
     */
    @JsonProperty("status")
    private String status;

    /**
     * 创建时间
     */
    @JsonProperty("created")
    private String created;

    /**
     * 创建人
     */
    @JsonProperty("creator")
    private String creator;

    /**
     * 修改时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * 修改人
     */
    @JsonProperty("modifier")
    private String modifier;
}
