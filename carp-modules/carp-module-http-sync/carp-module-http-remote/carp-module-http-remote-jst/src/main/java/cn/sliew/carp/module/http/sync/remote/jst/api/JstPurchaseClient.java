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

package cn.sliew.carp.module.http.sync.remote.jst.api;

import cn.sliew.carp.module.http.sync.remote.jst.response.JstNewResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.JstResult;
import cn.sliew.carp.module.http.sync.remote.jst.response.purchase.PurchaseDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.purchase.PurchaseinDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.purchase.PurchaseoutDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.purchase.SupplierDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@FeignClient(value = "jstPurchaseClient", url = "EMPTY")
public interface JstPurchaseClient {

    @PostMapping(value = "/open/purchase/query", headers = {"Content-Type", "application/x-www-form-urlencoded;charset=utf-8", "Accept", "application/json"})
    JstNewResult<JstResult<PurchaseDO>> getPurchase(URI uri, @RequestBody String paramStr);

    @PostMapping(value = "/open/purchasein/query", headers = {"Content-Type", "application/x-www-form-urlencoded;charset=utf-8", "Accept", "application/json"})
    JstNewResult<JstResult<PurchaseinDO>> getPurchasein(URI uri, @RequestBody String paramStr);

    @PostMapping(value = "/open/purchaseout/query", headers = {"Content-Type", "application/x-www-form-urlencoded;charset=utf-8", "Accept", "application/json"})
    JstNewResult<JstResult<PurchaseoutDO>> getPurchaseout(URI uri, @RequestBody String paramStr);

    @PostMapping(value = "/open/supplier/query", headers = {"Content-Type", "application/x-www-form-urlencoded;charset=utf-8", "Accept", "application/json"})
    JstNewResult<JstResult<SupplierDO>> getSupplier(URI uri, @RequestBody String paramStr);
}
