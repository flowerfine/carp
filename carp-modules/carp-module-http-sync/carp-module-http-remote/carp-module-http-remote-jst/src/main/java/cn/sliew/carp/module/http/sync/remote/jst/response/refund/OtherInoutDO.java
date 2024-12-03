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
public class OtherInoutDO {

    /**
     * 出仓单号
     */
    @JsonProperty("io_id")
    private Long ioId;

    /**
     * 单据日期
     */
    @JsonProperty("io_date")
    private String ioDate;

    /**
     * 单据状态，Confirmed生效，WaitConfirm待审核，OuterConfirming外部确认中，Cancelled取消（单据生效后的作废），Delete作废（单据生效前的作废）
     */
    @JsonProperty("status")
    private String status;

    /**
     * 线上单号
     */
    @JsonProperty("so_id")
    private String soId;

    /**
     * 单据类型
     */
    @JsonProperty("type")
    private String type;

    /**
     * 财务状态
     */
    @JsonProperty("f_status")
    private String fStatus;

    /**
     * 仓库名称
     */
    @JsonProperty("warehouse")
    private String warehouse;

    /**
     * 收货人
     */
    @JsonProperty("receiver_name")
    private String receiverName;

    /**
     * 收货人手机
     */
    @JsonProperty("receiver_mobile")
    private String receiverMobile;

    /**
     * 收货人省
     */
    @JsonProperty("receiver_state")
    private String receiverState;

    /**
     * 收货人市
     */
    @JsonProperty("receiver_city")
    private String receiverCity;

    /**
     * 收货人区
     */
    @JsonProperty("receiver_district")
    private String receiverDistrict;

    /**
     * 收货人地址
     */
    @JsonProperty("receiver_address")
    private String receiverAddress;

    /**
     * 仓库编号；主仓=1，销退仓=2，进货仓=3，次品仓=4，自定义1仓=6，自定义2仓=7，自定义3仓=8
     */
    @JsonProperty("wh_id")
    private Long whId;

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
     * 创建时间
     */
    @JsonProperty("created")
    private String created;

    /**
     * 多标签
     */
    @JsonProperty("labels")
    private String labels;

    /**
     * 分仓编号
     */
    @JsonProperty("wms_co_id")
    private Long wmsCoId;

    /**
     * 创建人
     */
    @JsonProperty("creator_name")
    private String creatorName;

    /**
     * 出库类型
     */
    @JsonProperty("drp_co_name")
    private String drpCoName;

    /**
     * 其它出入库人员
     */
    @JsonProperty("inout_user")
    private String inoutUser;

    /**
     * 快递单号
     */
    @JsonProperty("l_id")
    private String lId;

    /**
     * 物流公司编码
     */
    @JsonProperty("lc_id")
    private String lcId;

    /**
     * 物流公司
     */
    @JsonProperty("logistics_company")
    private String logisticsCompany;

    /**
     * 虚拟仓编码
     */
    @JsonProperty("lock_wh_id")
    private Long lockWhId;

    /**
     * 虚拟仓名称
     */
    @JsonProperty("lock_wh_name")
    private String lockWhName;

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
     * 唯一码集合，获取该节点系统中相关业务项需配置（对应erp基础设置商品唯一码开关）
     */
    @JsonProperty("sns")
    private List sns;
}
