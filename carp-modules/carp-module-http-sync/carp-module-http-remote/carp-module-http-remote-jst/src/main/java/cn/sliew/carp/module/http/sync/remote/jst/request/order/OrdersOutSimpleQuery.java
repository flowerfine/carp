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

package cn.sliew.carp.module.http.sync.remote.jst.request.order;

import cn.sliew.carp.module.http.sync.remote.jst.request.ModifiedTimeQuery;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrdersOutSimpleQuery extends ModifiedTimeQuery {

    /**
     * 店铺编号
     */
    @JsonProperty("shop_id")
    private Integer shopId;

    /**
     * shop_id为0且is_offline_shop为true查询线下店铺单据
     */
    @JsonProperty("is_offline_shop")
    private Boolean isOfflineShop;

    /**
     * 指定线上订单号，和时间段不能同时为空
     */
    @JsonProperty("so_ids")
    private List<String> soIds;

    /**
     * 单据状态: WaitConfirm=待出库; Confirmed=已出库; Cancelled=作废
     */
    @JsonProperty("status")
    private String status;

    /**
     * 出库仓编号
     */
    @JsonProperty("wms_co_id")
    private Integer wmsCoId;

    /**
     * 内部单号最大50条；与时间条件不能同时为空
     */
    @JsonProperty("o_ids")
    private List<String> oIds;

    /**
     * 物流单号；不超过20条
     */
    @JsonProperty("l_ids")
    private List<String> lIds;

    /**
     * 拣货批次号；不超过50条 与时间条件不能同时为空
     */
    @JsonProperty("wave_ids")
    private List<Long> waveIds;

    /**
     * 时间戳，sql server中的行版本号，该字段查询防止分页过程中漏单
     */
    @JsonProperty("start_ts")
    private Long startTs;

    /**
     * 是否查询总条数默认true，如果使用start_ts查询，该值传false否则影响查询效率
     */
    @JsonProperty("is_get_total")
    private Boolean isGetTotal;

    /**
     * 出库单号列表，最大50
     */
    @JsonProperty("io_ids")
    private List<String> ioIds;

    /**
     * 货主编码
     */
    @JsonProperty("owner_co_id")
    private Long ownerCoId;

    /**
     * 是否获取跨境财务信息
     */
    @JsonProperty("is_get_cbfinance")
    private Boolean isGetCbfinance;

    /**
     * 时间类型 0:修改时间modified，1:创建时间created，2:出库时间io_date;默认0
     */
    @JsonProperty("date_type")
    private Integer dateType = 0;
}
