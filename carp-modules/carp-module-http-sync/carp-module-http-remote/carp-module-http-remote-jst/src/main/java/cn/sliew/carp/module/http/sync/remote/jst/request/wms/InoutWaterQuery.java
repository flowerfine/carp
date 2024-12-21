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

import cn.sliew.carp.module.http.sync.remote.jst.request.PagedQuery;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InoutWaterQuery extends PagedQuery {

    @Schema(description = "开始时间")
    @JsonProperty("start_time")
    private String startTime;

    @Schema(description = "结束时间")
    @JsonProperty("end_time")
    private String endEime;

    @Schema(description = "默认0,0=修改时间;1=制单日期;2=出入库时间")
    @JsonProperty("date_type")
    private Integer dateType;

    @Schema(description = "单据类型，采购进仓，采购退货，调拨出，调拨入，加工出仓，加工进仓，盘点，销售出仓，销售退货，其它出库，其它进仓，其它退货")
    @JsonProperty("type")
    private String type;

    @Schema(description = "分仓编号")
    @JsonProperty("wms_co_id")
    private Integer wmsCoId;
}
