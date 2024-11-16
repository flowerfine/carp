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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PurchaseinDO {

    /**
     * 入库单号
     */
    @JsonProperty("io_id")
    private Long ioId;

    /**
     * 数据库行版本号：https://docs.microsoft.com/zh-cn/sql/t-sql/data-types/rowversion-transact-sql?view=sql-server-ver16
     */
    @JsonProperty("ts")
    private Long ts;

    /**
     * 仓库名称
     */
    @JsonProperty("warehouse")
    private String warehouse;

    /**
     * 采购单号
     */
    @JsonProperty("po_id")
    private Long poId;

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

    /**
     * 修改时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * 线上单号
     */
    @JsonProperty("so_id")
    private String soId;

    /**
     * 外部单号
     */
    @JsonProperty("out_io_id")
    private String outIoId;

    /**
     * 状态
     */
    @JsonProperty("status")
    private String status;

    /**
     * 单据日期
     */
    @JsonProperty("io_date")
    private String ioDate;

    /**
     * 仓库编号;主仓=1，销退仓=2， 进货仓=3，次品仓 = 4
     */
    @JsonProperty("wh_id")
    private String whId;

    /**
     * 分仓编号
     */
    @JsonProperty("wms_co_id")
    private Long wmsCoId;

    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 税率
     */
    @JsonProperty("tax_rate")
    private BigDecimal taxRate;

    /**
     * 多标签
     */
    @JsonProperty("labels")
    private String labels;

    /**
     * 财务审核日期
     */
    @JsonProperty("archived")
    private String archived;

    /**
     * 预约单号
     */
    @JsonProperty("merge_so_id")
    private String mergeSoId;

    /**
     * 进仓类型
     */
    @JsonProperty("type")
    private String type;

    /**
     * 制单人
     */
    @JsonProperty("creator_name")
    private String creatorName;

    /**
     * 商品集合
     */
    private List<Map> items;

    /**
     * 批次集合，获取该节点系统中相关业务项需配置
     */
    private List<Map> batchs;

    /**
     * 唯一码集合，获取该节点系统中相关业务项需配置
     */
    private List<Map> sns;

    /**
     * 财审状态，WaitConfirm=待审核;Confirmed=已审核
     */
    @JsonProperty("f_status")
    private String fStatus;

    /**
     * 物流单号
     */
    @JsonProperty("l_id")
    private String lId;

    @Getter
    @Setter
    public static class ItemDO {

        /**
         * 入库子单号
         */
        @JsonProperty("ioi_id")
        private Long ioiId;

        /**
         * 商品编码
         */
        @JsonProperty("sku_id")
        private String skuId;

        /**
         * 商品名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 入库数量
         */
        @JsonProperty("qty")
        private Integer qty;

        /**
         * 入库单号
         */
        @JsonProperty("io_id")
        private Long ioId;

        /**
         * 单价
         */
        @JsonProperty("cost_price")
        private BigDecimal costPrice;

        /**
         * 金额
         */
        @JsonProperty("cost_amount")
        private BigDecimal costAmount;

        /**
         * 商品明细备注
         */
        @JsonProperty("remark")
        private String remark;
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
         * 数量
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

        /**
         * 有效期至
         */
        @JsonProperty("expiration_date")
        private String expirationDate;
    }

    @Getter
    @Setter
    public static class SnDO {

        /**
         * 商品编码
         */
        @JsonProperty("sku_id")
        private String skuId;
        /**
         * 唯一码
         */
        @JsonProperty("sn")
        private String sn;
    }
}
