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
package cn.sliew.carp.module.http.sync.job.repository.entity.jst;

import cn.sliew.carp.module.http.sync.job.repository.entity.BaseSyncMeta;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("jst_order")
public class JstOrder extends BaseSyncMeta {

    private static final long serialVersionUID = 1L;

    @TableField("app_key")
    private String appKey;

    @TableField("company")
    private String company;

    @TableField("is_cod")
    private String isCod;

    @TableField("l_id")
    private String lId;

    @TableField("send_date")
    private Date sendDate;

    @TableField("pay_date")
    private Date payDate;

    @TableField("freight")
    private String freight;

    @TableField("receiver_address")
    private String receiverAddress;

    @TableField("receiver_district")
    private String receiverDistrict;

    @TableField("wms_co_id")
    private String wmsCoId;

    @TableField("logistics_company")
    private String logisticsCompany;

    @TableField("as_id")
    private String asId;

    @TableField("free_amount")
    private String freeAmount;

    @TableField("shop_name")
    private String shopName;

    @TableField("question_type")
    private String questionType;

    @TableField("outer_pay_id")
    private String outerPayId;

    @TableField("so_id")
    private String soId;

    @TableField("`type`")
    private String type;

    @TableField("order_from")
    private String orderFrom;

    @TableField("`status`")
    private String status;

    @TableField("pay_amount")
    private String payAmount;

    @TableField("shop_buyer_id")
    private String shopBuyerId;

    @TableField("open_id")
    private String openId;

    @TableField("shop_status")
    private String shopStatus;

    @TableField("receiver_mobile")
    private String receiverMobile;

    @TableField("receiver_phone")
    private String receiverPhone;

    @TableField("order_date")
    private Date orderDate;

    @TableField("question_desc")
    private String questionDesc;

    @TableField("receiver_city")
    private String receiverCity;

    @TableField("receiver_state")
    private String receiverState;

    @TableField("receiver_name")
    private String receiverName;

    @TableField("o_id")
    private Long oId;

    @TableField("shop_id")
    private Long shopId;

    @TableField("co_id")
    private Long coId;

    @TableField("remark")
    private String remark;

    @TableField("drp_co_id_from")
    private String drpCoIdFrom;

    @TableField("modified")
    private Date modified;

    @TableField("labels")
    private String labels;

    @TableField("paid_amount")
    private String paidAmount;

    @TableField("currency")
    private String currency;

    @TableField("buyer_message")
    private String buyerMessage;

    @TableField("lc_id")
    private String lcId;

    @TableField("invoice_title")
    private String invoiceTitle;

    @TableField("invoice_type")
    private String invoiceType;

    @TableField("buyer_tax_no")
    private String buyerTaxNo;

    @TableField("creator_name")
    private String creatorName;

    @TableField("plan_delivery_date")
    private Date planDeliveryDate;

    @TableField("node")
    private String node;

    @TableField("receiver_town")
    private String receiverTown;

    @TableField("drp_co_id_to")
    private String drpCoIdTo;

    @TableField("shop_site")
    private String shopSite;

    @TableField("un_lid")
    private String unLid;

    @TableField("end_time")
    private Date endTime;

    @TableField("receiver_country")
    private String receiverCountry;

    @TableField("receiver_zip")
    private String receiverZip;

    @TableField("seller_flag")
    private Integer sellerFlag;

    @TableField("receiver_email")
    private String receiverEmail;

    @TableField("referrer_id")
    private String referrerId;

    @TableField("referrer_name")
    private String referrerName;

    @TableField("created")
    private String created;

    @TableField("pays")
    private String pays;

    @TableField("items")
    private String items;

    @TableField("skus")
    private String skus;

    @TableField("f_weight")
    private String fWeight;

    @TableField("weight")
    private String weight;

    @TableField("ts")
    private Long ts;

    @TableField("buyer_id")
    private String buyerId;

    @TableField("buyer_paid_amount")
    private String buyerPaidAmount;

    @TableField("seller_income_amount")
    private String sellerIncomeAmount;

    @TableField("chosen_channel")
    private String chosenChannel;

    @TableField("link_o_id")
    private String linkOId;

    @TableField("merge_so_id")
    private String mergeSoId;

    @TableField("shipment")
    private String shipment;

    @TableField("sign_time")
    private String signTime;

    @TableField("cb_finances")
    private String cbFinances;

    @TableField("f_freight")
    private String fFreight;

    @TableField("batch_id")
    private String batchId;

    @TableField("produced_date")
    private String producedDate;

    @TableField("tem_ext_data")
    private String temExtData;

    @TableField("discount_rate")
    private String discountRate;

    @TableField("tag")
    private String tag;

    @TableField("amount")
    private String amount;

    @TableField("is_split")
    private String isSplit;

    @TableField("is_merge")
    private String isMerge;

    @TableField("glasses")
    private String glasses;

    @TableField("outer_as_id")
    private String outerAsId;

    @TableField("outer_so_id")
    private String outerSoId;

    @TableField("__raw_so_ids__")
    private String rawSoIds;

    @TableField("ext_datas")
    private String extDatas;

    @TableField("raw_so_id")
    private String rawSoId;

    @TableField("drp_from")
    private String drpFrom;

    @TableField("drp_to")
    private String drpTo;
}
