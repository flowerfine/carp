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

import cn.sliew.carp.module.application.vela.api.v1.model.V1CloudShellPrepareResponse;
import cn.sliew.carp.module.application.vela.api.v1.model.V1SimpleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface CloudshellApi {

    @GetMapping(value = "/view/cloudshell")
    ResponseEntity<V1SimpleResponse> proxy();

    @GetMapping(value = "/view/cloudshell/{subpath}")
    ResponseEntity<V1SimpleResponse> proxyPath(@PathVariable("subpath") String subpath);

    @PostMapping(value = "/api/v1/cloudshell")
    ResponseEntity<V1CloudShellPrepareResponse> prepareCloudShell();

    @DeleteMapping(value = "/api/v1/cloudshell")
    ResponseEntity<V1SimpleResponse> destroyCloudShell();

}
