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

package cn.sliew.carp.module.http.sync.remote.jst.response.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDO {

    /**
     * 供应商ID
     */
    @JsonProperty("supplier_id")
    private Long supplierId;

    /**
     * 供应商代码
     */
    @JsonProperty("supplier_code")
    private String supplierCode;

    /**
     * 名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 修改时间
     */
    @JsonProperty("modified")
    private String modified;

    /**
     * 备注1
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 备注2
     */
    @JsonProperty("remark2")
    private String remark2;

    /**
     * 备注3
     */
    @JsonProperty("remark3")
    private String remark3;

    /**
     * 供应商分类
     */
    @JsonProperty("group")
    private String group;

    /**
     * 是否启用，true：启用，false：未启用
     */
    @JsonProperty("enabled")
    private String enabled;

    /**
     * 开户银行
     */
    @JsonProperty("depositbank")
    private String depositbank;

    /**
     * 账户名称
     */
    @JsonProperty("bankacount")
    private String bankacount;

    /**
     * 银行账号
     */
    @JsonProperty("acountnumber")
    private String acountnumber;
}
