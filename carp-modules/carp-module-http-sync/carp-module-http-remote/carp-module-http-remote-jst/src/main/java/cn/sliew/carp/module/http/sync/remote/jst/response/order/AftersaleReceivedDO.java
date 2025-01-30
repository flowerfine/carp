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
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AftersaleReceivedDO {

    /**
     * 内部单号
     */
    @JsonProperty("o_id")
    private Long oId;

    /**
     * 出库单号
     */
    @JsonProperty("io_id")
    private Long ioId;

    /**
     * 售后订单号
     */
    @JsonProperty("as_id")
    private Long asId;

    /**
     * 线上单号
     */
    @JsonProperty("so_id")
    private String soId;

    /**
     * 出库日期
     */
    @JsonProperty("io_date")
    private String ioDate;

    /**
     * 仓库名称
     */
    @JsonProperty("warehouse")
    private String warehouse;

    /**
     * 修改时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * 物流公司编码
     */
    @JsonProperty("lc_id")
    private String lcId;

    /**
     * 店铺编号
     */
    @JsonProperty("shop_id")
    private Long shopId;

    /**
     * 仓库代码（1 主仓，2 销退仓，3 进货仓，4 次品仓）
     */
    @JsonProperty("wh_id")
    private Long whId;

    /**
     * 分仓编号
     */
    @JsonProperty("wms_co_id")
    private Long wmsCoId;

    /**
     * 分销商名称
     */
    @JsonProperty("drp_co_name")
    private String drpCoName;

    /**
     * 商品集合
     */
    private List items;

    /**
     * 批次信息集合
     */
    private List batchs;

    /**
     * 分销商编号
     */
    @JsonProperty("drp_co_id")
    private Integer drpCoId;

    @Getter
    @Setter
    public static class ItemDO {

        /**
         * 出库单号
         */
        @JsonProperty("io_id")
        private Long ioId;

        /**
         * 商品编码
         */
        @JsonProperty("sku_id")
        private String skuId;

        /**
         * 商品数量
         */
        @JsonProperty("qty")
        private Integer qty;

        /**
         * 商品名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 属性值
         */
        @JsonProperty("properties_value")
        private String propertiesValue;

        /**
         * 销售价格
         */
        @JsonProperty("sale_price")
        private BigDecimal salePrice;

        /**
         * 销售数量
         */
        @JsonProperty("sale_amount")
        private BigDecimal saleAmount;
    }

    @Getter
    @Setter
    public static class BatchDO {

        /**
         * 批次号
         */
        @JsonProperty("batch_no")
        private String batchNo;

        /**
         * 商品编码
         */
        @JsonProperty("sku_id")
        private String skuId;

        /**
         * 商品数量
         */
        @JsonProperty("qty")
        private Integer qty;

        /**
         * 批次日期
         */
        @JsonProperty("product_date")
        private String productDate;

        /**
         * 供应商编号
         */
        @JsonProperty("supplier_id")
        private Long supplierId;

        /**
         * 供应商名称
         */
        @JsonProperty("supplier_name")
        private String supplierName;
    }
}
