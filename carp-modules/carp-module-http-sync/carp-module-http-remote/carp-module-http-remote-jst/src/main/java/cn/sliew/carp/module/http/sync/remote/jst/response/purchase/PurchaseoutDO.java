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
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseoutDO {

    /**
     * 入库单号
     */
    @JsonProperty("io_id")
    private Long ioId;

    /**
     * 退货日期
     */
    @JsonProperty("io_date")
    private String ioDate;

    /**
     * 状态(Confirmed:生效，WaitConfirm:待审核，Creating:草拟，Cancelled:作废,OuterConfirming:外部确认中，Delete:取消)
     */
    @JsonProperty("status")
    private String status;

    /**
     * 线上单号
     */
    @JsonProperty("so_id")
    private String soId;

    /**
     * 财审状态，WaitConfirm=待审核;Confirmed=已审核
     */
    @JsonProperty("f_status")
    private String fStatus;

    /**
     * 仓库名
     */
    @JsonProperty("warehouse")
    private String warehouse;

    /**
     * 收货人/供应商名称
     */
    @JsonProperty("receiver_name")
    private String receiverName;

    /**
     * 收货电话
     */
    @JsonProperty("receiver_mobile")
    private String receiverMobile;

    /**
     * 收件人省
     */
    @JsonProperty("receiver_state")
    private String receiverState;

    /**
     * 收件人市
     */
    @JsonProperty("receiver_city")
    private String receiverCity;

    /**
     * 收件人区
     */
    @JsonProperty("receiver_district")
    private String receiverDistrict;

    /**
     * 收件人省地址
     */
    @JsonProperty("receiver_address")
    private String receiverAddress;

    /**
     * 仓库编号;主仓=1，销退仓=2， 进货仓=3，次品仓 = 4
     */
    @JsonProperty("wh_id")
    private Integer whId;

    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 修改时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * 采购单号
     */
    @JsonProperty("po_id")
    private Long poId;

    /**
     * 分仓编号
     */
    @JsonProperty("wms_co_id")
    private Long wmsCoId;

    /**
     * 供应商ID
     */
    @JsonProperty("seller_id")
    private Long sellerId;

    /**
     * 标记|多标签
     */
    @JsonProperty("labels")
    private String labels;

    /**
     * 物流公司
     */
    @JsonProperty("logistics_company")
    private String logisticsCompany;

    /**
     * 物流公司编号
     */
    @JsonProperty("lc_id")
    private String lcId;

    /**
     * 物流单号
     */
    @JsonProperty("l_id")
    private String lId;

    /**
     * 财务审核日期
     */
    @JsonProperty("archived")
    private String archived;

    /**
     * 创建人
     */
    @JsonProperty("creator_name")
    private String creatorName;

    /**
     * 虚拟仓编号
     */
    @JsonProperty("lock_wh_id")
    private String lockWhId;

    /**
     * 虚拟仓名称
     */
    @JsonProperty("lock_wh_name")
    private String lockWhName;

    /**
     * 外部单号
     */
    @JsonProperty("out_io_id")
    private String outIoId;

    /**
     * 商品集合
     */
    @JsonProperty("items")
    private List items;

    /**
     * 批次集合，获取该节点系统中相关业务项需配置
     */
    @JsonProperty("batchs")
    private List batchs;

    /**
     * 唯一码集合，获取该节点系统中相关业务项需配置
     */
    @JsonProperty("sns")
    private List sns;
}
