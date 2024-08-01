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

package cn.sliew.carp.module.application.vela.api.v1;

import cn.sliew.carp.module.application.vela.api.v1.model.V1ApplicationRequest;
import cn.sliew.carp.module.application.vela.api.v1.model.V1ApplicationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface OamApplicationApi {

    @GetMapping(value = "/v1/namespaces/{namespace}/applications/{appname}")
    ResponseEntity<V1ApplicationResponse> getApplication(@PathVariable("namespace") String namespace, @PathVariable("appname") String appname);

    @PostMapping(value = "/v1/namespaces/{namespace}/applications/{appname}")
    ResponseEntity<Void> createOrUpdateApplication(@PathVariable("namespace") String namespace, @PathVariable("appname") String appname, @RequestBody V1ApplicationRequest body, @RequestParam("dryRun") String dryRun, @RequestParam("publishVersion") String publishVersion);

    @DeleteMapping(value = "/v1/namespaces/{namespace}/applications/{appname}")
    ResponseEntity<Void> deleteOAMApplication(@PathVariable("namespace") String namespace, @PathVariable("appname") String appname);
}
