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
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OrdersOutSimpleDO {

    @JsonProperty("co_id")
    @Schema(description = "公司编号")
    private Long coId;

    @JsonProperty("shop_id")
    @Schema(description = "店铺编号")
    private Long shopId;

    @JsonProperty("io_id")
    @Schema(description = "出库单号")
    private Long ioId;

    @JsonProperty("o_id")
    @Schema(description = "内部单号")
    private Long oId;

    @JsonProperty("so_id")
    @Schema(description = "线上单号")
    private String soId;

    @JsonProperty("created")
    @Schema(description = "创建时间")
    private String created;

    @JsonProperty("modified")
    @Schema(description = "修改时间")
    private String modified;

    @JsonProperty("status")
    @Schema(description = "状态;Archive:归档,WaitConfirm:待出库,Confirmed:已出库," +
            "Cancelled:取消,Delete:作废,OuterConfirming:外部发货中")
    private String status;

    @JsonProperty("order_type")
    @Schema(description = "单据类型")
    private String orderType;

    @JsonProperty("invoice_title")
    @Schema(description = "发票抬头")
    private String invoiceTitle;

    @JsonProperty("shop_buyer_id")
    @Schema(description = "买家帐号")
    private String shopBuyerId;

    @JsonProperty("receiver_country")
    @Schema(description = "国家")
    private String receiverCountry;

    @JsonProperty("receiver_state")
    @Schema(description = "省")
    private String receiverState;

    @JsonProperty("receiver_city")
    @Schema(description = "市")
    private String receiverCity;

    @JsonProperty("receiver_district")
    @Schema(description = "区")
    private String receiverDistrict;

    @JsonProperty("receiver_town")
    @Schema(description = "街道")
    private String receiverTown;

    @JsonProperty("receiver_address")
    @Schema(description = "地址")
    private String receiverAddress;

    @JsonProperty("receiver_name")
    @Schema(description = "收件人姓名")
    private String receiverName;

    @JsonProperty("receiver_phone")
    @Schema(description = "收件人手机")
    private String receiverPhone;

    @JsonProperty("receiver_mobile")
    @Schema(description = "收件人电话")
    private String receiverMobile;

    @JsonProperty("buyer_message")
    @Schema(description = "买家留言")
    private String buyerMessage;

    @JsonProperty("remark")
    @Schema(description = "备注")
    private String remark;

    @JsonProperty("is_cod")
    @Schema(description = "是否货到付款")
    private String isCod;

    @JsonProperty("pay_amount")
    @Schema(description = "应付金额")
    private BigDecimal payAmount;

    @JsonProperty("l_id")
    @Schema(description = "物流单号")
    private String lId;

    @JsonProperty("io_date")
    @Schema(description = "出库时间")
    private String ioDate;

    @JsonProperty("lc_id")
    @Schema(description = "快递公司编码")
    private String lcId;

    @JsonProperty("stock_enabled")
    @Schema(description = "是否启用库存管理")
    private String stockEnabled;

    @JsonProperty("drp_co_id_from")
    @Schema(description = "分销商编号")
    private String drpCoIdFrom;

    @JsonProperty("labels")
    @Schema(description = "标记|多标签")
    private String labels;

    @JsonProperty("paid_amount")
    @Schema(description = "实付金额")
    private BigDecimal paidAmount;

    @JsonProperty("free_amount")
    @Schema(description = "优惠金额")
    private BigDecimal freeAmount;

    @JsonProperty("freight")
    @Schema(description = "运费")
    private BigDecimal freight;

    @JsonProperty("weight")
    @Schema(description = "预估重量")
    private BigDecimal weight;

    @JsonProperty("f_weight")
    @Schema(description = "实称重量")
    private BigDecimal fWeight;

    @JsonProperty("merge_so_id")
    @Schema(description = "合并订单号")
    private String mergeSoId;

    @JsonProperty("wms_co_id")
    @Schema(description = "分仓编号")
    private Long wmsCoId;

    @JsonProperty("business_staff")
    @Schema(description = "业务人员")
    private String businessStaff;

    @JsonProperty("currency")
    @Schema(description = "货币类型")
    private String currency;

    @Schema(description = "商品集合")
    private List<Map> items;

    @Schema(description = "批次集合，获取该节点系统中相关业务项需配置")
    private List<Map> batchs;

    @Schema(description = "唯一码集合，获取该节点系统中相关业务项需配置")
    private List<Map> sns;

    @JsonProperty("pay_date")
    @Schema(description = "付款日期")
    private String payDate;

    @JsonProperty("logistics_company")
    @Schema(description = "物流公司名称")
    private String logisticsCompany;

    @JsonProperty("shop_name")
    private String shopName;

    @JsonProperty("buyer_id")
    private String buyerId;

    @JsonProperty("qty")
    private Integer qty;

    @JsonProperty("f_freight")
    private String fFreight;

    @JsonProperty("node")
    private String node;

    @JsonProperty("seller_flag")
    private Integer sellerFlag;

    @JsonProperty("open_id")
    private String openId;

    @JsonProperty("wave_id")
    private Long waveId;

    @JsonProperty("order_staff_id")
    private Long orderStaffId;

    @JsonProperty("order_staff_name")
    private String orderStaffName;

    @JsonProperty("is_print_express")
    private String isPrintExpress;

    @JsonProperty("is_print")
    private String isPrint;

    @JsonProperty("ts")
    private Long ts;

    @JsonProperty("ClusterInfos")
    private List clusterInfos;

    @Getter
    @Setter
    public static class ItemDO {

        @JsonProperty("ioi_id")
        @Schema(description = "子单号")
        private Long ioiId;

        @JsonProperty("pic")
        @Schema(description = "图片")
        private String pic;

        @JsonProperty("sku_id")
        @Schema(description = "商品编码")
        private String skuId;

        @JsonProperty("qty")
        @Schema(description = "数量")
        private Integer qty;

        @JsonProperty("name")
        @Schema(description = "商品名称")
        private String name;

        @JsonProperty("properties_value")
        @Schema(description = "颜色规格")
        private String propertiesValue;

        @JsonProperty("sale_price")
        @Schema(description = "单价")
        private BigDecimal salePrice;

        @JsonProperty("sale_amount")
        @Schema(description = "金额")
        private BigDecimal saleAmount;

        @JsonProperty("i_id")
        @Schema(description = "款式编码")
        private String iId;

        @JsonProperty("sale_base_price")
        @Schema(description = "原价")
        private BigDecimal saleBasePrice;

        @JsonProperty("combine_sku_id")
        @Schema(description = "组合装商品编码")
        private String combineSkuId;

        @JsonProperty("is_gift")
        @Schema(description = "是否赠品")
        private Boolean isGift;

        @JsonProperty("raw_so_id")
        private String rawSoId;
    }

    @Getter
    @Setter
    public static class BatchDO {

        @JsonProperty("batch_no")
        @Schema(description = "批次号")
        private String batchNo;

        @JsonProperty("sku_id")
        @Schema(description = "商品编码")
        private String skuId;

        @JsonProperty("qty")
        @Schema(description = "数量")
        private Integer qty;

        @JsonProperty("product_date")
        @Schema(description = "批次日期")
        private String productDate;

        @JsonProperty("supplier_id")
        @Schema(description = "供应商编号")
        private Long supplierId;

        @JsonProperty("supplier_name")
        @Schema(description = "供应商名称")
        private String supplierName;

        @JsonProperty("expiration_date")
        @Schema(description = "有效期至")
        private String expirationDate;
    }

    @Getter
    @Setter
    public static class SnDO {

        @JsonProperty("sku_id")
        @Schema(description = "商品编码")
        private String skuId;

        @JsonProperty("sn")
        @Schema(description = "唯一码")
        private String sn;
    }
}
