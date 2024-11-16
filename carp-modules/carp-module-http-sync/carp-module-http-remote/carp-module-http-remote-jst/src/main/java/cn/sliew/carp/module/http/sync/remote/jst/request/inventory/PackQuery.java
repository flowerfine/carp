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

import cn.sliew.carp.module.http.sync.remote.jst.request.PagedQuery;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PackQuery extends PagedQuery {

    /**
     * 商品编码列表，最多20个sku
     */
    @JsonProperty("sku_ids")
    private List<String> skuIds;

    /**
     * 仓库编号
     */
    @JsonProperty("wms_co_id")
    private Integer wmsCoId;

    /**
     * 类型，Bin=仓位库存，DefaultPack=暂存位 Pack=装箱库存
     */
    @JsonProperty("pack_type")
    private String packType;

    /**
     * 查询起始时间;时间条件与商品编码不能同时为空
     */
    @JsonProperty("start_time")
    private String modifiedBegin;

    /**
     * 查询截止时间，时间间 隔最大1天
     */
    @JsonProperty("end_time")
    private String modifiedEnd;
}
