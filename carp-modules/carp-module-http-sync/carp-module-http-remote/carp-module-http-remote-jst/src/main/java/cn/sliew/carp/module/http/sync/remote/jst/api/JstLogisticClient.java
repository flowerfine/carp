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
import cn.sliew.carp.module.http.sync.remote.jst.response.logistics.JstLogisticResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@FeignClient(value = "jstLogisticClient", url = "EMPTY")
public interface JstLogisticClient {

    @PostMapping(value = "/open/logistic/query", headers = {"Content-Type", "application/x-www-form-urlencoded;charset=utf-8", "Accept", "application/json"})
    JstNewResult<JstLogisticResult> getLogisticInfo(URI uri, @RequestBody String paramStr);
}
