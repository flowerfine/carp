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
package cn.sliew.carp.module.http.sync.remote.jst.request.inventory;

import cn.sliew.carp.module.http.sync.remote.jst.request.ModifiedTimeQuery;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryQuery extends ModifiedTimeQuery {

    /**
     * 分仓公司编号，值不传或为0查询所有仓的总库存，其它为指定仓的库存
     */
    @JsonProperty("wms_co_id")
    private Integer wmsCoId;

    /**
     * 商品编码,多个用逗号分隔，最多20，与修改时间不能同时为空
     */
    @JsonProperty("sku_ids")
    private String skuIds;

    /**
     * 时间戳，防漏单，如果用ts查询不需要传时间查询条件
     */
    @JsonProperty("ts")
    private Long ts;
}
