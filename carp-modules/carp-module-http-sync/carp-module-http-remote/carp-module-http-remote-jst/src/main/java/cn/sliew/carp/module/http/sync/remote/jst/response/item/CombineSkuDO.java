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
public class CombineSkuDO {

    /**
     * 组合款式编码
     */
    @JsonProperty("i_id")
    private String iId;

    /**
     * 组合商品名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 简称
     */
    @JsonProperty("short_name")
    private String shortName;

    /**
     * 虚拟分类
     */
    @JsonProperty("vc_name")
    private String vcName;

    /**
     * 图片地址
     */
    @JsonProperty("pic")
    private String pic;

    /**
     * 组合颜色及规格
     */
    @JsonProperty("properties_value")
    private String propertiesValue;

    /**
     * 组合售价
     */
    @JsonProperty("sale_price")
    private BigDecimal salePrice;

    /**
     * 组合重量
     */
    @JsonProperty("weight")
    private BigDecimal weight;

    /**
     * 组合装编码
     */
    @JsonProperty("sku_id")
    private String skuId;

    /**
     * 组合装商品成本价
     */
    @JsonProperty("cost_price")
    private Integer costPrice;

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
     * 商品列表
     */
    private List<Map> items;

    /**
     * 组合商品实体编码
     */
    @JsonProperty("enty_sku_id")
    private String entySkuId;

    /**
     * 标签
     */
    @JsonProperty("labels")
    private String labels;

    /**
     * 品牌
     */
    @JsonProperty("brand")
    private String brand;

    /**
     * 是否启用1表示启用；0表示备用；-1表示禁用
     */
    @JsonProperty("enabled")
    private Integer enabled;

    /**
     * sku_qty
     */
    @JsonProperty("sku_qty")
    private Integer skuQty;

    /**
     * 国际码
     */
    @JsonProperty("sku_code")
    private String skuCode;

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
     * 体积
     */
    @JsonProperty("volume")
    private BigDecimal volume;

    /**
     * 商品属性，成品，半成品，原材料，包材
     */
    @JsonProperty("item_type")
    private String itemType;

    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;

    @Getter
    @Setter
    public static class ItemDO {

        /**
         * 子商品编码
         */
        @JsonProperty("src_sku_id")
        private String srcSkuId;

        /**
         * 子商品数量
         */
        @JsonProperty("qty")
        private Integer qty;

        /**
         * 售价
         */
        @JsonProperty("sale_price")
        private BigDecimal salePrice;

        /**
         * 修改时间
         */
        @JsonProperty("modified")
        private String modified;
    }
}
