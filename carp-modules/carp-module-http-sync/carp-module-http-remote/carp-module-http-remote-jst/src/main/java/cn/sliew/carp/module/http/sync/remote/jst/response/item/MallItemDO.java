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

package cn.sliew.carp.module.http.sync.remote.jst.response.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MallItemDO {

    /**
     * 款式编码
     */
    @JsonProperty("i_id")
    private String iId;

    /**
     * 公司编号
     */
    @JsonProperty("co_id")
    private Long coId;

    /**
     * 商品名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 商品分类编号
     */
    @JsonProperty("c_id")
    private Long cId;

    /**
     * 商品分类名称
     */
    @JsonProperty("c_name")
    private String cName;

    /**
     * 基本售价（款式）
     */
    @JsonProperty("s_price")
    private BigDecimal sPrice;

    /**
     * 成本价(采购价)
     */
    @JsonProperty("c_price")
    private BigDecimal cPrice;

    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 图片地址
     */
    @JsonProperty("pic")
    private String pic;

    /**
     * 修改时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * 品牌
     */
    @JsonProperty("brand")
    private String brand;

    /**
     * 重量
     */
    @JsonProperty("weight")
    private BigDecimal weight;

    /**
     * 市场|吊牌价
     */
    @JsonProperty("market_price")
    private BigDecimal marketPrice;

    /**
     * 虚拟分类
     */
    @JsonProperty("vc_name")
    private String vcName;

    /**
     * 商品属性：成品/半成品/原材料
     */
    @JsonProperty("item_type")
    private String itemType;

    /**
     * 长
     */
    @JsonProperty("l")
    private BigDecimal l;

    /**
     * 宽
     */
    @JsonProperty("w")
    private BigDecimal w;

    /**
     * 高
     */
    @JsonProperty("h")
    private BigDecimal h;

    /**
     * 保质期(天）
     */
    @JsonProperty("shelf_life")
    private Integer shelfLife;

    /**
     * sku列表
     */
    private List<Map> skus;

    /**
     * 商品属性集合
     */
    private List<Map> ups;

    /**
     * 生产批次信息
     */
    @JsonProperty("productionbatch_format")
    private String productionbatchFormat;

    /**
     * 生产许可证
     */
    @JsonProperty("production_licence")
    private String productionLicence;

    /**
     * 创建时间
     */
    @JsonProperty("created")
    private String created;

    @Getter
    @Setter
    public static class SkuDO {

        /**
         * 公司编号
         */
        @JsonProperty("co_id")
        private Long coId;

        /**
         * 商品编码
         */
        @JsonProperty("sku_id")
        private String skuId;

        /**
         * 款式编码
         */
        @JsonProperty("i_id")
        private String iId;

        /**
         * 商品名称
         */
        @JsonProperty("name")
        private String name;

        /**
         * 商品分类编号
         */
        @JsonProperty("c_id")
        private Integer cId;

        /**
         * 品牌
         */
        @JsonProperty("brand")
        private String brand;

        /**
         * 市场|吊牌价
         */
        @JsonProperty("market_price")
        private BigDecimal marketPrice;

        /**
         * 基本售价
         */
        @JsonProperty("sale_price")
        private BigDecimal salePrice;

        /**
         * 成本价
         */
        @JsonProperty("cost_price")
        private BigDecimal costPrice;

        /**
         * 是否启用，默认值1, 可选值: -1=禁用, 0=备用, 1=启用
         */
        @JsonProperty("enabled")
        private Integer enabled;

        /**
         * 分类
         */
        @JsonProperty("category")
        private String category;

        /**
         * 创建人编号
         */
        @JsonProperty("creator")
        private String creator;

        /**
         * 修改人编号
         */
        @JsonProperty("modifier")
        private String modifier;

        /**
         * 创建人名称
         */
        @JsonProperty("creator_name")
        private String creatorName;

        /**
         * 修改人名称
         */
        @JsonProperty("modifier_name")
        private String modifierName;

        /**
         * 颜色规格
         */
        @JsonProperty("properties_value")
        private String propertiesValue;

        /**
         * 国际码
         */
        @JsonProperty("sku_code")
        private String skuCode;

        /**
         * 图片地址
         */
        @JsonProperty("pic")
        private String pic;

        /**
         * 大图地址
         */
        @JsonProperty("pic_big")
        private String picBig;

        /**
         * 重量
         */
        @JsonProperty("weight")
        private BigDecimal weight;

        /**
         * 简称
         */
        @JsonProperty("short_name")
        private String shortName;

        /**
         * 商品属性，可选值["成品", "半成品", "原材料", "包材"]
         */
        @JsonProperty("item_type")
        private String itemType;

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
         * 供应商商品编码
         */
        @JsonProperty("supplier_sku_id")
        private String supplierSkuId;

        /**
         * 供应商款式编码
         */
        @JsonProperty("supplier_i_id")
        private String supplierIId;

        /**
         * 商品备注
         */
        @JsonProperty("remark")
        private String remark;

        /**
         * 虚拟分类
         */
        @JsonProperty("vc_name")
        private String vcName;

        /**
         * 长
         */
        @JsonProperty("l")
        private BigDecimal l;

        /**
         * 宽
         */
        @JsonProperty("w")
        private BigDecimal w;

        /**
         * 高
         */
        @JsonProperty("h")
        private BigDecimal h;

        /**
         * 其他价格1
         */
        @JsonProperty("other_price_1")
        private BigDecimal otherPrice1;

        /**
         * 其他价格2
         */
        @JsonProperty("other_price_2")
        private BigDecimal otherPrice2;

        /**
         * 其他价格3
         */
        @JsonProperty("other_price_3")
        private BigDecimal otherPrice3;

        /**
         * 其他价格4
         */
        @JsonProperty("other_price_4")
        private BigDecimal otherPrice4;

        /**
         * 其他价格5
         */
        @JsonProperty("other_price_5")
        private BigDecimal otherPrice5;

        /**
         * 商品标签，多个标签时以逗号分隔
         */
        @JsonProperty("labels")
        private String labels;

        /**
         * 其他属性1
         */
        @JsonProperty("other_1")
        private String other1;

        /**
         * 其他属性2
         */
        @JsonProperty("other_2")
        private String other2;

        /**
         * 其他属性3
         */
        @JsonProperty("other_3")
        private String other3;

        /**
         * 其他属性4
         */
        @JsonProperty("other_4")
        private String other4;

        /**
         * 其他属性5
         */
        @JsonProperty("other_5")
        private String other5;

        /**
         * 保质期
         */
        @JsonProperty("shelf_life")
        private Integer shelfLife;

        /**
         * 生产批次信息
         */
        @JsonProperty("productionbatch_format")
        private String productionbatchFormat;

        /**
         * 生产许可证
         */
        @JsonProperty("production_licence")
        private String productionLicence;
    }

    @Getter
    @Setter
    public static class UpDO {

        /**
         * 属性编号
         */
        @JsonProperty("p_id")
        private Long pId;

        /**
         * 属性列名
         */
        @JsonProperty("p_name")
        private String pName;

        /**
         * 属性值编号
         */
        @JsonProperty("pv_id")
        private Long pvId;

        /**
         * 属性值名称
         */
        @JsonProperty("pv_value")
        private String pvValue;
    }
}
