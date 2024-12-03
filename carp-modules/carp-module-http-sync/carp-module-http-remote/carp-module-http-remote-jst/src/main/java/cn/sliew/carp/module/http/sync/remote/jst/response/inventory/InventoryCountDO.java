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

package cn.sliew.carp.module.http.sync.remote.jst.response.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InventoryCountDO {

    /**
     * 单据类型
     */
    @JsonProperty("type")
    private String type;

    /**
     * 盘点单号
     */
    @JsonProperty("io_id")
    private Long ioId;

    /**
     * 单据日期
     */
    @JsonProperty("io_date")
    private String ioDate;

    /**
     * 状态;WaitConfirm:待确认,Confirmed:生效,Archive:归档,Cancelled:取消,Delete:作废
     */
    @JsonProperty("status")
    private String status;

    /**
     * 仓库名称
     */
    @JsonProperty("warehouse")
    private String warehouse;

    /**
     * 创建人
     */
    @JsonProperty("creator_name")
    private String creatorName;

    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 仓库编号，主仓=1，销退仓=2， 进货仓=3，次品仓 = 4
     */
    @JsonProperty("wh_id")
    private Long whId;

    /**
     * 分仓编号
     */
    @JsonProperty("wms_co_id")
    private Long wmsCoId;

    /**
     * 修改时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * 商品集合
     */
    private List items;

    /**
     * 批次集合，获取该节点系统中相关业务项需配置
     */
    private List batchs;

    /**
     * labels
     */
    @JsonProperty("labels")
    private List labels;

    /**
     * ts
     */
    @JsonProperty("ts")
    private Long ts;

    /**
     * f_status
     */
    @JsonProperty("f_status")
    private String fStatus;

    /**
     * lock_wh_id
     */
    @JsonProperty("lock_wh_id")
    private Integer lockWhId;

    /**
     * 唯一码集合，获取该节点系统中相关业务项需配置
     */
    @JsonProperty("sns")
    private List sns;

    @Getter
    @Setter
    public static class ItemDO {

        /**
         * 盘点单号
         */
        @JsonProperty("io_id")
        private Long ioId;

        /**
         * 子单号
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
         * 颜色及规格
         */
        @JsonProperty("properties_value")
        private String propertiesValue;

        /**
         * 盘点后数量
         */
        @JsonProperty("r_qty")
        private Integer rQty;

        /**
         * 盈亏数量
         */
        @JsonProperty("qty")
        private Integer qty;
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
}
