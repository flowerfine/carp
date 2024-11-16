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

package cn.sliew.carp.module.http.sync.remote.jst.response.logistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LogisticDO {

    /**
     * ERP订单号;唯一
     */
    @JsonProperty("o_id")
    private Long oId;

    /**
     * 店铺编号
     */
    @JsonProperty("shop_id")
    private Long shopId;

    /**
     * 订单号，最长不超过 50;唯一
     */
    @JsonProperty("so_id")
    private String soId;

    /**
     * 发货时间
     */
    @JsonProperty("send_date")
    private String sendDate;

    /**
     * 运费
     */
    @JsonProperty("freight")
    private BigDecimal freight;

    /**
     * 重量
     */
    @JsonProperty("weight")
    private Long weight;

    /**
     * 快递公司代码
     */
    @JsonProperty("lc_id")
    private String lcId;

    /**
     * 快递单号
     */
    @JsonProperty("l_id")
    private String lId;

    /**
     * 快递公司
     */
    @JsonProperty("logistics_company")
    private String logisticsCompany;

    /**
     * 订单明细；商品信息
     */
    private List items;

    //    @Getter
//    @Setter
    @Data
    public static class ItemDO {

        /**
         * 商家SKU，对应库存管理的 SKU
         */
        @JsonProperty("sku_id")
        private String skuId;

        /**
         * 购买数量
         */
        @JsonProperty("qty")
        private Integer qty;

        /**
         * 子订单号
         */
        @JsonProperty("outer_oi_id")
        private String outerOiId;

        /**
         * 原始平台订单号，可以为空，最长不超过 50
         */
        @JsonProperty("raw_so_id")
        private String rawSoId;

        /**
         * 内部单号
         */
        @JsonProperty("o_id")
        private String oId;

        /**
         * 商品退款状态
         */
        @JsonProperty("refund_status")
        private String refundStatus;
    }
}
