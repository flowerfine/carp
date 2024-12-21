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
package cn.sliew.carp.module.http.sync.remote.jst.response.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PurchaseDO {

    @Schema(description = "采购日期")
    @JsonProperty("po_date")
    private String poDate;

    @Schema(description = "采购单号")
    @JsonProperty("po_id")
    private Long poId;

    @Schema(description = "外部订单号")
    @JsonProperty("so_id")
    private String soId;

    @Schema(description = "备注")
    @JsonProperty("remark")
    private String remark;

    @Schema(description = "状态:Creating:草拟,WaitConfirm:待审核,Confirmed:已确认,WaitDeliver:待发货,WaitReceive:待收货,"
            + "Finished:完成,Archive:归档,Cancelled:作废")
    @JsonProperty("status")
    private String status;

    @Schema(description = "供应商id")
    @JsonProperty("supplier_id")
    private Long supplierId;

    @Schema(description = "供应商名称")
    @JsonProperty("seller")
    private String seller;

    @Schema(description = "税率")
    @JsonProperty("tax_rate")
    private BigDecimal taxRate;

    @Schema(description = "采购员")
    @JsonProperty("purchaser_name")
    private String purchaserName;

    @Schema(description = "送货地址")
    @JsonProperty("send_address")
    private String sendAddress;

    @Schema(description = "合同条款")
    @JsonProperty("term")
    private String term;

    @Schema(description = "商品类型")
    @JsonProperty("item_type")
    private String itemType;

    @Schema(description = "商品集合")
    private List<Map> items;

    @Schema(description = "多表现")
    @JsonProperty("labels")
    private String labels;

    @Schema(description = "审核生效日期")
    @JsonProperty("confirm_date")
    private String confirmDate;

    @Schema(description = "完成日期")
    @JsonProperty("finish_time")
    private String finishTime;

    @Schema(description = "仓库编号")
    @JsonProperty("wms_co_id")
    private Long wmsCoId;

    @Schema(description = "收货状态（Timeout：预计收货超时；Received：全部入库；Part_Received：部分入库；Not_Received：未入库）")
    @JsonProperty("receive_status")
    private String receiveStatus;

    @Schema(description = "溢装比")
    @JsonProperty("more_rate")
    private BigDecimal moreRate;

    @Schema(description = "物流公司名称")
    @JsonProperty("logistics_company")
    private String logisticsCompany;

    @Schema(description = "物流单号")
    @JsonProperty("l_id")
    private String lId;

    @Schema(description = "运费")
    @JsonProperty("freight")
    private BigDecimal freight;

    @Schema(description = "修改时间")
    @JsonProperty("modified")
    private String modified;

    @JsonProperty("receiver_name")
    private String receiverName;

    @JsonProperty("wms_co_name")
    private String wmsCoName;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("receiver_phone")
    private String receiverPhone;

    @Data
    public static class ItemDO {

        @Schema(description = "商家sku")
        @JsonProperty("sku_id")
        private String skuId;

        @Schema(description = "商品名称")
        @JsonProperty("name")
        private String name;

        @Schema(description = "数量")
        @JsonProperty("qty")
        private Integer qty;

        @Schema(description = "单价")
        @JsonProperty("price")
        private BigDecimal price;

        @Schema(description = "款号")
        @JsonProperty("i_id")
        private String iId;

        @Schema(description = "采购单编号")
        @JsonProperty("po_id")
        private Long poId;

        @Schema(description = "采购单明细编号")
        @JsonProperty("poi_id")
        private Long poiId;

        @Schema(description = "交货日期")
        @JsonProperty("delivery_date")
        private String deliveryDate;

        @Schema(description = "备注")
        @JsonProperty("remark")
        private String remark;
    }
}
