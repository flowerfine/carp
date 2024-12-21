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

@Getter
@Setter
public class SkuDO {

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
     * 商品简称
     */
    @JsonProperty("short_name")
    private String shortName;

    /**
     * 销售价
     */
    @JsonProperty("sale_price")
    private BigDecimal salePrice;

    /**
     * 成本价
     */
    @JsonProperty("cost_price")
    private BigDecimal costPrice;

    /**
     * 颜色规格
     */
    @JsonProperty("properties_value")
    private String propertiesValue;

    /**
     * 类目id
     */
    @JsonProperty("c_id")
    private Integer cId;

    /**
     * 分类
     */
    @JsonProperty("category")
    private String category;

    /**
     * 大图地址
     */
    @JsonProperty("pic_big")
    private String picBig;

    /**
     * 图片地址
     */
    @JsonProperty("pic")
    private String pic;

    /**
     * 是否启用，0：备用，1：启用，-1：禁用
     */
    @JsonProperty("enabled")
    private Integer enabled;

    /**
     * 重量
     */
    @JsonProperty("weight")
    private BigDecimal weight;

    /**
     * 市场价
     */
    @JsonProperty("market_price")
    private BigDecimal marketPrice;

    /**
     * 品牌
     */
    @JsonProperty("brand")
    private String brand;

    /**
     * 供应商编号
     */
    @JsonProperty("supplier_id")
    private String supplierId;

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
     * 国际码
     */
    @JsonProperty("sku_code")
    private String skuCode;

    /**
     * 颜色
     */
    @JsonProperty("color")
    private String color;

    /**
     * 供应商商品编码
     */
    @JsonProperty("supplier_sku_id")
    private String supplierSkuId;

    /**
     * 供应商商品款号
     */
    @JsonProperty("supplier_i_id")
    private String supplierIId;

    /**
     * 虚拟分类
     */
    @JsonProperty("vc_name")
    private String vcName;

    /**
     * 商品类型
     */
    @JsonProperty("sku_type")
    private String skuType;

    /**
     * 创建者
     */
    @JsonProperty("creator")
    private String creator;

    /**
     * 创建时间
     */
    @JsonProperty("created")
    private String created;

    /**
     * 未知
     */
    @JsonProperty("autoid")
    private Long autoid;

    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 商品属性，成品，半成品，原材料，包材
     */
    @JsonProperty("item_type")
    private String itemType;

    /**
     * 是否禁止同步，0=启用同步，1=禁用同步，2=部分禁用
     */
    @JsonProperty("stock_disabled")
    private String stockDisabled;

    /**
     * 单位
     */
    @JsonProperty("unit")
    private String unit;

    /**
     * 保质期
     */
    @JsonProperty("shelf_life")
    private Integer shelfLife;

    /**
     * 商品标签，多个标签时以逗号分隔
     */
    @JsonProperty("labels")
    private String labels;

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
     * 是否开启序列号
     */
    @JsonProperty("is_series_number")
    private Boolean isSeriesNumber;

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
     * 链接同步状态
     */
    @JsonProperty("stock_type")
    private String stockType;

    /**
     * 是否启用生产批次
     */
    @JsonProperty("batch_enabled")
    private Boolean batchEnabled;
}
