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
package cn.sliew.carp.module.http.sync.remote.jst.request.wms;

import cn.sliew.carp.module.http.sync.remote.jst.request.ModifiedTimeQuery;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AllocateQuery extends ModifiedTimeQuery {

    /**
     * 指定线上订单号，和时间段不能同时为空
     */
    @JsonProperty("so_ids")
    private List<String> soIds;

    /**
     * 调拨单号
     */
    @JsonProperty("io_ids")
    private List<String> ioIds;

    /**
     * 调拨类型（调拨出，调拨入）
     */
    @JsonProperty("type")
    private String type;

    /**
     * 0:修改时间，modified。 2:出入库时间 io_date，未传入时默认为0
     */
    @JsonProperty("date_type")
    private Integer dateType;
}
