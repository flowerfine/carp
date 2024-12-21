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
import cn.sliew.carp.module.http.sync.remote.jst.response.refund.AllocateDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.refund.InoutWaterDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.refund.OtherInoutDO;
import cn.sliew.carp.module.http.sync.remote.jst.response.refund.TrackinfoDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

@FeignClient(value = "jstWmsClient", url = "EMPTY")
public interface JstWmsClient {

    @PostMapping(value = "/open/allocate/query", headers = {"Content-Type", "application/x-www-form-urlencoded;charset=utf-8", "Accept", "application/json"})
    JstNewResult<JstResult<AllocateDO>> getAllocate(URI uri, @RequestBody String paramStr);

    @PostMapping(value = "/open/jushuitan/trackinfo/query", headers = {"Content-Type", "application/x-www-form-urlencoded;charset=utf-8", "Accept", "application/json"})
    JstNewResult<JstResult<TrackinfoDO>> getTrackinfo(URI uri, @RequestBody String paramStr);

    @PostMapping(value = "/open/jushuitan/inout/water/query", headers = {"Content-Type", "application/x-www-form-urlencoded;charset=utf-8", "Accept", "application/json"})
    JstNewResult<JstResult<InoutWaterDO>> getInoutWater(URI uri, @RequestBody String paramStr);

    @PostMapping(value = "/open/other/inout/query", headers = {"Content-Type", "application/x-www-form-urlencoded;charset=utf-8", "Accept", "application/json"})
    JstNewResult<JstResult<OtherInoutDO>> getOtherInout(URI uri, @RequestBody String paramStr);
}
