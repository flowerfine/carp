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

@Getter
@Setter
public class SkumapDO {

    /**
     * 店铺 id
     */
    @JsonProperty("shop_id")
    private Long shopId;

    /**
     * 平台
     */
    @JsonProperty("channel")
    private String channel;

    /**
     * 款号 id
     */
    @JsonProperty("i_id")
    private String iId;

    /**
     * 商品 id
     */
    @JsonProperty("sku_id")
    private String skuId;

    /**
     * 店铺款号 id
     */
    @JsonProperty("shop_i_id")
    private String shopIId;

    /**
     * 店铺商品 id
     */
    @JsonProperty("shop_sku_id")
    private String shopSkuId;

    /**
     * 修改时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * 商品对应关系修改时间
     */
    @JsonProperty("link_modified")
    private String linkModified;

    /**
     * 是否在售
     */
    @JsonProperty("enabled")
    private String enabled;

    /**
     * 公司编号
     */
    @JsonProperty("co_id")
    private Long coId;

    /**
     * 类目编码
     */
    @JsonProperty("c_id")
    private Integer cId;

    /**
     * 原始商品编码
     */
    @JsonProperty("raw_sku_id")
    private String rawSkuId;

    /**
     * 平台价格，系统中需开启相关配置
     */
    @JsonProperty("shop_price")
    private String shopPrice;

    /**
     * 平台商品名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 图片地址
     */
    @JsonProperty("pic")
    private String pic;

    /**
     * 创建时间
     */
    @JsonProperty("created")
    private String created;

    /**
     * 下架时间
     */
    @JsonProperty("pull_off_time")
    private String pullOffTime;

    /**
     * 线上国标码
     */
    @JsonProperty("outer_sku_code")
    private String outerSkuCode;

    /**
     * 线上颜色规格
     */
    @JsonProperty("properties_value")
    private String propertiesValue;

    /**
     * 链接同步设置，0是开启同步，2是禁止同步
     */
    @JsonProperty("type")
    private Integer type;

    /**
     * 店铺库存
     */
    @JsonProperty("shop_qty")
    private Integer shopQty;

    /**
     * 对应商品编码（商品对应关系页面）
     */
    @JsonProperty("link_sku_id")
    private String linkSkuId;

    /**
     * 售价下限
     */
    @JsonProperty("sale_price_min")
    private Integer salePriceMin;

    /**
     * 售价上限
     */
    @JsonProperty("sale_price_max")
    private Integer salePriceMax;
}
