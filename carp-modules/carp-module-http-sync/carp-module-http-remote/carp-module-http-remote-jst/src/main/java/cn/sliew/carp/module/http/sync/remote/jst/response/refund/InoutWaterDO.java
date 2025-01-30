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

import java.math.BigDecimal;
import java.util.List;

@Data
public class InoutWaterDO {

    /**
     * 公司编号，调拨业务时代表发起方
     */
    @JsonProperty("co_id")
    private Long coId;

    /**
     * 分仓编号，调拨业务时代表接收方
     */
    @JsonProperty("wms_co_id")
    private Long wmsCoId;

    /**
     * 仓库编号，主仓=1，销退仓=2，进货仓=3，次品仓=4
     */
    @JsonProperty("wh_id")
    private Long whId;

    /**
     * 调入/出仓的仓库编号，主仓=1，销退仓=2，进货仓=3，次品仓=4调拨业务
     */
    @JsonProperty("link_wh_id")
    private Long linkWhId;

    /**
     * 调入/出仓单号，调拨业务
     */
    @JsonProperty("link_io_id")
    private Long linkIoId;

    /**
     * 店铺编号
     */
    @JsonProperty("shop_id")
    private Long shopId;

    /**
     * 进出仓单号
     */
    @JsonProperty("io_id")
    private Long ioId;

    /**
     * 内部单号
     */
    @JsonProperty("o_id")
    private Long oId;

    /**
     * 线上单号，订单业务
     */
    @JsonProperty("so_id")
    private String soId;

    /**
     * 创建时间
     */
    @JsonProperty("created")
    private String created;

    /**
     * 修改时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * 进出仓日期
     */
    @JsonProperty("io_date")
    private String ioDate;

    /**
     * 供应商ID,采购相关业务
     */
    @JsonProperty("supplier_id")
    private Long supplierId;

    /**
     * 状态
     */
    @JsonProperty("status")
    private String status;

    /**
     * 是否使用库存
     */
    @JsonProperty("stock_enabled")
    private String stockEnabled;

    /**
     * 预估重量
     */
    @JsonProperty("weight")
    private BigDecimal weight;

    /**
     * 实际重量
     */
    @JsonProperty("f_weight")
    private BigDecimal fWeight;

    /**
     * 出库类型，其它出入库业务
     */
    @JsonProperty("drp_co_name")
    private String drpCoName;

    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 商品集合
     */
    @JsonProperty("items")
    private List items;

    /**
     * 单据类型
     */
    @JsonProperty("type")
    private String type;
}
