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
public class InventoryDO {

    /**
     * 商品编码
     */
    @JsonProperty("sku_id")
    private String skuId;

    /**
     * 款式编码
     */
    @JsonProperty("i_id")
    private String iId;

    /**
     * 主仓实际库存
     */
    @JsonProperty("qty")
    private Integer qty;

    /**
     * 虚拟库存
     */
    @JsonProperty("order_lock")
    private Integer orderLock;

    /**
     * 店铺编号
     */
    @JsonProperty("virtual_qty")
    private Integer virtualQty;

    /**
     * 采购在途数
     */
    @JsonProperty("purchase_qty")
    private Integer purchaseQty;

    /**
     * 销退仓库存
     */
    @JsonProperty("return_qty")
    private Integer returnQty;

    /**
     * 进货仓库存
     */
    @JsonProperty("in_qty")
    private Integer inQty;

    /**
     * 次品库存
     */
    @JsonProperty("defective_qty")
    private Integer defectiveQty;

    /**
     * 修改时间,用此时间作为下一次查询的起始时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * 安全库存下限
     */
    @JsonProperty("min_qty")
    private Integer minQty;

    /**
     * 安全库存上限
     */
    @JsonProperty("max_qty")
    private Integer maxQty;

    /**
     * 商品名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 仓库待发数
     */
    @JsonProperty("pick_lock")
    private Integer pickLock;

    /**
     * 调拨在途数
     */
    @JsonProperty("allocate_qty")
    private Integer allocateQty;

    /**
     * 时间戳
     */
    @JsonProperty("ts")
    private Long ts;

    /**
     * 自定义仓1
     */
    @JsonProperty("customize_qty_1")
    private Integer customizeQty1;

    /**
     * 自定义仓2
     */
    @JsonProperty("customize_qty_2")
    private Integer customizeQty2;

    /**
     * 自定义仓3
     */
    @JsonProperty("customize_qty_3")
    private Integer customizeQty3;
}
