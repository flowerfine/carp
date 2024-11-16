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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class RefundSingleDO {

    @Schema(description = "ERP售后单号")
    @JsonProperty("as_id")
    private Long asId;

    @Schema(description = "申请时间")
    @JsonProperty("as_date")
    private String asDate;

    @Schema(description = "平台退货退款单号")
    @JsonProperty("outer_as_id")
    private String outerAsId;

    @Schema(description = "内部单号")
    @JsonProperty("o_id")
    private Long oId;

    @Schema(description = "线上单号")
    @JsonProperty("so_id")
    private String soId;

    @Schema(description = "售后类型，普通退货，其它，拒收退货，仅退款，投诉，补发，换货")
    @JsonProperty("type")
    private String type;

    @Schema(description = "登记时间")
    @JsonProperty("created")
    private String created;

    @Schema(description = "修改时间")
    @JsonProperty("modified")
    private String modified;

    @Schema(description = "状态（WaitConfirm:待确认,Confirmed:已确认,Cancelled:作废,Merged:被合并）")
    @JsonProperty("status")
    private String status;

    @Schema(description = "WAIT_SELLER_AGREE:买家已经申请退款，等待卖家同意,WAIT_BUYER_RETURN_GOODS:卖家已经同意退款，等待买家退货," +
            "WAIT_SELLER_CONFIRM_GOODS:买家已经退货，等待卖家确认收货,SELLER_REFUSE_BUYER:卖家拒绝退款,CLOSED:退款关闭,SUCCESS:退款成功")
    @JsonProperty("shop_status")
    private String shopStatus;

    @Schema(description = "备注")
    @JsonProperty("remark")
    private String remark;

    @Schema(description = "问题类型")
    @JsonProperty("question_type")
    private String questionType;

    @Schema(description = "仓库")
    @JsonProperty("warehouse")
    private String warehouse;

    @Schema(description = "退款金额")
    @JsonProperty("refund")
    private BigDecimal refund;

    @Schema(description = "补偿金额")
    @JsonProperty("payment")
    private BigDecimal payment;

    @Schema(description = "货物状态（BUYER_NOT_RECEIVED:买家未收到货,BUYER_RECEIVED:买家已收到货,BUYER_RETURNED_GOODS:买家已退货," +
            "SELLER_RECEIVED:卖家已收到退货）")
    @JsonProperty("good_status")
    private String goodStatus;

    @Schema(description = "售后线下备注")
    @JsonProperty("node")
    private String node;

    @Schema(description = "原订单状态")
    @JsonProperty("order_status")
    private String orderStatus;

    @Schema(description = "店铺编号")
    @JsonProperty("shop_id")
    private Long shopId;

    @Schema(description = "物流公司")
    @JsonProperty("logistics_company")
    private String logisticsCompany;

    @Schema(description = "物流单号")
    @JsonProperty("l_id")
    private String lId;

    @Schema(description = "仓库编号")
    @JsonProperty("wh_id")
    private Long whId;

    @Schema(description = "分仓id")
    @JsonProperty("wms_co_id")
    private Long wmsCoId;

    @Schema(description = "最后确认日期")
    @JsonProperty("confirm_date")
    private String confirmDate;

    @Schema(description = "卖家应退运费")
    @JsonProperty("freight")
    private BigDecimal freight;

    @Schema(description = "分销商编号")
    @JsonProperty("drp_co_id_from")
    private Long drpCoIdFrom;

    @Schema(description = "收件人电话")
    @JsonProperty("receiver_mobile")
    private String receiverMobile;

    @Schema(description = "收件人名称")
    @JsonProperty("receiver_name")
    private String receiverName;

    @Schema(description = "买家账号")
    @JsonProperty("shop_buyer_id")
    private String shopBuyerId;

    @Schema(description = "商品集合")
    private List items;

    @Schema(description = "批次集合")
    private List batchs;

    @Schema(description = "sn码集合")
    private List sns;

    @Schema(description = "供销商编号")
    @JsonProperty("drp_co_id_to")
    private Long drpCoIdTo;

    @Schema(description = "退款版本号")
    @JsonProperty("refund_version")
    private Long refundVersion;

    @Schema(description = "多标签")
    private String labels;

    @Schema(description = "店铺名称")
    @JsonProperty("shop_name")
    private String shopName;

    @JsonProperty("creator_name")
    private String creatorName;

    @JsonProperty("modifier_name")
    private String modifierName;

    /**
     * 出库单号
     */
    @JsonProperty("io_id")
    private Long ioId;

    /**
     * order_type
     */
    @JsonProperty("order_type")
    private String orderType;

    /**
     * buyer_apply_refund
     */
    @JsonProperty("buyer_apply_refund")
    private String buyerApplyRefund;

    /**
     * ts
     */
    @JsonProperty("ts")
    private Long ts;

    /**
     * refund_phase
     */
    @JsonProperty("refund_phase")
    private String refundPhase;

    /**
     * 币种
     */
    @JsonProperty("currency")
    private String currency;

    /**
     * is_split
     */
    @JsonProperty("is_split")
    private String isSplit;

    /**
     * is_merge
     */
    @JsonProperty("is_merge")
    private String isMerge;

    /**
     * shop_freight
     */
    @JsonProperty("shop_freight")
    private String shopFreight;

    /**
     * 店铺站点信息
     */
    @JsonProperty("shop_site")
    private String shopSite;

    /**
     * shop_type
     */
    @JsonProperty("shop_type")
    private String shopType;

    /**
     * result
     */
    @JsonProperty("result")
    private String result;

    /**
     * order_labels
     */
    @JsonProperty("order_labels")
    private List orderLabels;

    /**
     * 优惠/差额
     */
    @JsonProperty("free_amount")
    private String freeAmount;

    @Getter
    @Setter
    public static class ItemDO {

        @Schema(description = "售后子单号")
        @JsonProperty("asi_id")
        private Long asiId;

        @Schema(description = "ERP售后单号")
        @JsonProperty("as_id")
        private Long asId;

        @Schema(description = "商品编码")
        @JsonProperty("sku_id")
        private String skuId;

        @Schema(description = "数量")
        @JsonProperty("qty")
        private Integer qty;

        @Schema(description = "单价")
        @JsonProperty("price")
        private BigDecimal price;

        @Schema(description = "金额")
        @JsonProperty("amount")
        private BigDecimal amount;

        @Schema(description = "商品名称")
        @JsonProperty("name")
        private String name;

        @Schema(description = "商品图片地址")
        @JsonProperty("pic")
        private String pic;

        @Schema(description = "类型（退货，换货,补发）")
        @JsonProperty("type")
        private String type;

        @Schema(description = "颜色规格")
        @JsonProperty("properties_value")
        private String propertiesValue;

        @Schema(description = "子订单号")
        @JsonProperty("outer_oi_id")
        private String outerOiId;

        @Schema(description = "商品类型")
        @JsonProperty("sku_type")
        private String skuType;

        @Schema(description = "实际退货数量")
        @JsonProperty("r_qty")
        private Integer rQty;

        @Schema(description = "入仓时间")
        @JsonProperty("receive_date")
        private String receiveDate;

        @Schema(description = "组合商品编码")
        @JsonProperty("combine_sku_id")
        private String combineSkuId;

        @Schema(description = "店铺商品编码")
        @JsonProperty("shop_sku_id")
        private String shopSkuId;

        @Schema(description = "款式编码")
        @JsonProperty("i_id")
        private String iId;

        @Schema(description = "次品数量")
        @JsonProperty("defective_qty")
        private Long defectiveQty;
    }

    @Getter
    @Setter
    public static class BatchDO {

        @Schema(description = "批次号")
        @JsonProperty("batch_no")
        private String batchNo;

        @Schema(description = "商品编码")
        @JsonProperty("sku_id")
        private String skuId;

        @Schema(description = "商品数量")
        @JsonProperty("qty")
        private Integer qty;

        @Schema(description = "批次日期")
        @JsonProperty("product_date")
        private String productDate;

        @Schema(description = "供应商编号")
        @JsonProperty("supplier_id")
        private Long supplierId;

        @Schema(description = "供应商名称")
        @JsonProperty("supplier_name")
        private String supplierName;

        @Schema(description = "有效期至")
        @JsonProperty("expiration_date")
        private String expirationDate;
    }

    @Getter
    @Setter
    public static class SnDO {

        @Schema(description = "商品编码")
        @JsonProperty("sku_id")
        private String skuId;

        @Schema(description = "SN号码")
        @JsonProperty("sn")
        private String sn;
    }
}
