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

import java.util.List;

@Data
public class AllocateDO {

    /**
     * 分仓编号，调拨出为发起方，调拨入为接收方
     */
    @JsonProperty("wms_co_id")
    private Long wmsCoId;

    /**
     * 公司编号
     */
    @JsonProperty("co_id")
    private Long coId;

    /**
     * 调拨单号
     */
    @JsonProperty("io_id")
    private Long ioId;

    /**
     * 单据日期
     */
    @JsonProperty("io_date")
    private String ioDate;

    /**
     * 修改时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * Creating：待确认，Confirmed：调拨中，完成（若remark包含”红冲单据“则ERP显示红冲状态），Picking：拣货中，OuterConfirming：外部确认中，Cancelled：取消,Confirming:确认
     */
    @JsonProperty("status")
    private String status;

    /**
     * 调拨出仓库名称（对应ERP仓库资料设定页面）
     */
    @JsonProperty("warehouse")
    private String warehouse;

    /**
     * 调拨入仓库名称（对应ERP仓库资料设定页面）
     */
    @JsonProperty("link_warehouse")
    private String linkWarehouse;

    /**
     * 财务状态Archive:归档,modifing:变更,WaitConfirm:待审核,Confirmed:已审核,Cancelled:取消,Delete:作废
     */
    @JsonProperty("f_status")
    private String fStatus;

    /**
     * 调拨类型
     */
    @JsonProperty("type")
    private String type;

    /**
     * 调拨出仓库编号；主仓=1，销退仓=2，进货仓=3，次品仓=4
     */
    @JsonProperty("wh_id")
    private Long whId;

    /**
     * 调拨入仓库编号；主仓=1，销退仓=2，进货仓=3，次品仓=4
     */
    @JsonProperty("link_wh_id")
    private Long linkWhId;

    /**
     * 调拨入分仓编号，调拨出为接收方，调拨入为发起方
     */
    @JsonProperty("link_wms_co_id")
    private Long linkWmsCoId;

    /**
     * 调拨单号
     */
    @JsonProperty("link_io_id")
    private Long linkIoId;

    /**
     * 调拨建议号，跨仓调拨入单据才有值，数据来源是跨仓调拨上传的so_id
     */
    @JsonProperty("so_id")
    private String soId;

    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 标记|多标签
     */
    @JsonProperty("labels")
    private List<String> labels;

    /**
     * 商品集合
     */
    @JsonProperty("items")
    private List items;

    /**
     * 批次集合，获取该节点系统中相关业务项需配置（对应erp基础设置开启生产批次管理 如果是分仓数据 分仓也需要开启）
     */
    @JsonProperty("batchs")
    private List batchs;

    /**
     * 唯一码集合，获取该节点系统中相关业务项需配置
     */
    @JsonProperty("sns")
    private List sns;

    /**
     * 收货人
     */
    @JsonProperty("receiver_name_en")
    private String receiverNameEn;

    /**
     * 移动电话
     */
    @JsonProperty("receiver_mobile_en")
    private String receiverMobileEn;

    /**
     * 省
     */
    @JsonProperty("receiver_state")
    private String receiverState;

    /**
     * 市
     */
    @JsonProperty("receiver_city")
    private String receiverCity;

    /**
     * 区
     */
    @JsonProperty("receiver_district")
    private String receiverDistrict;

    /**
     * 地址
     */
    @JsonProperty("receiver_address")
    private String receiverAddress;

    /**
     * 物流单号
     */
    @JsonProperty("l_id")
    private String lId;

    /**
     * 快递公司编码
     */
    @JsonProperty("lc_id")
    private String lcId;

    /**
     * 物流公司名称
     */
    @JsonProperty("logistics_company")
    private String logisticsCompany;

    /**
     * 单位
     */
    @JsonProperty("unit")
    private String unit;

    /**
     * 调出虚拟仓编码
     */
    @JsonProperty("lock_wh_id")
    private String lockWhId;

    /**
     * 调入虚拟仓编码
     */
    @JsonProperty("lock_link_wh_id")
    private String lockLinkWhId;

    /**
     * 外部单号
     */
    @JsonProperty("out_io_id")
    private String outIoId;

}
