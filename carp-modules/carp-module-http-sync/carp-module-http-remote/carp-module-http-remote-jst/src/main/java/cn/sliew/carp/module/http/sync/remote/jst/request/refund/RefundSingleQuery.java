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

package cn.sliew.carp.module.http.sync.remote.jst.request.refund;

import cn.sliew.carp.module.http.sync.remote.jst.request.ModifiedTimeQuery;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RefundSingleQuery extends ModifiedTimeQuery {

    /**
     * 指定线上订单号，和时间段不能同时为空
     */
    @JsonProperty("so_ids")
    private List<String> soIds;

    /**
     * 指定买家账号，最多50
     */
    @JsonProperty("shop_buyer_ids")
    private List<String> shopBuyerIds;

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
     * 指定内部单号，和时间段不能同时为空
     */
    @JsonProperty("o_ids")
    private List<Integer> oIds;

    /**
     * 指定售后单号，和时间段不能同时为空
     */
    @JsonProperty("as_ids")
    private List<Integer> asIds;

    /**
     * 售后单状态（WaitConfirm:待确认,Confirmed:已确认,Cancelled:作废,Merged:被合并）
     */
    @JsonProperty("status")
    private String status;

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
     * 货物状态（BUYER_NOT_RECEIVED:买家未收到货,BUYER_RECEIVED:买家已收到货,BUYER_RETURNED_GOODS:买家已退货,SELLER_RECEIVED:卖家已收到退货）
     */
    @JsonProperty("good_status")
    private String goodStatus;

    /**
     * 售后类型，普通退货，其它，拒收退货，仅退款，投诉，补发，换货，维修
     */
    @JsonProperty("type")
    private String type;

    /**
     * 货主编码
     */
    @JsonProperty("owner_co_id")
    private Long ownerCoId;
}
