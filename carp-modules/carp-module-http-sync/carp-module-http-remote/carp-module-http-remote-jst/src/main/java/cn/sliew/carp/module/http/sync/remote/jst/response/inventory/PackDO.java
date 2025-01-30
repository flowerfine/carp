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
package cn.sliew.carp.module.http.sync.remote.jst.response.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackDO {

    /**
     * 仓库id
     */
    @JsonProperty("wms_co_id")
    private Integer wmsCoId;

    /**
     * 箱号
     */
    @JsonProperty("pack_id")
    private String packId;

    /**
     * 仓库编号;1=主仓，2=销退仓， 3=进货仓，4=次品仓
     */
    @JsonProperty("wh_id")
    private Integer whId;

    /**
     * 类型
     */
    @JsonProperty("pack_type")
    private String packType;

    /**
     * 仓位
     */
    @JsonProperty("bin")
    private String bin;

    /**
     * 明细仓位
     */
    @JsonProperty("item_bin")
    private String itemBin;

    /**
     * 商品编码
     */
    @JsonProperty("sku_id")
    private String skuId;

    /**
     * 数量
     */
    @JsonProperty("qty")
    private Integer qty;

    /**
     * 有效期
     */
    @JsonProperty("expiration_date")
    private String expirationDate;

    /**
     * 生产日期
     */
    @JsonProperty("product_date")
    private String productDate;

    /**
     * 生产批次
     */
    @JsonProperty("batch_no")
    private String batchNo;

    /**
     * 修改时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * 供应商ID
     */
    @JsonProperty("supplier_id")
    private String supplierId;
}
