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
public class AftersaleReceivedQuery extends ModifiedTimeQuery {

    /**
     * 外部订单号列表
     */
    @JsonProperty("so_ids")
    private List<String> soIds;
    /**
     * ERP 内部订单号，唯一
     */
    @JsonProperty("o_ids")
    private List<String> oIds;

    /**
     * 退货店铺
     */
    @JsonProperty("shop_id")
    private String shopId;

    /**
     * 日期类型，0:修改时间，1:创建日期，2:订单日期，4:实际出/入库时间
     */
    @JsonProperty("date_type")
    private Integer dateType = 0;
}
