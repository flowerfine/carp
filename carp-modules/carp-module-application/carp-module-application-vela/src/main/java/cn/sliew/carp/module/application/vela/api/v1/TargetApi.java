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

import cn.sliew.carp.module.application.vela.api.v1.model.*;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1CreateTargetRequest;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1DetailTargetResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface TargetApi {

    @GetMapping(value = "/api/v1/targets")
    ResponseEntity<V1ListTargetResponse> listTargets(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("project") String project);

    @GetMapping(value = "/api/v1/targets/{targetName}")
    ResponseEntity<V1DetailTargetResponse> detailTarget(@PathVariable("targetName") String targetName);

    @PostMapping(value = "/api/v1/targets")
    ResponseEntity<V1DetailTargetResponse> createTarget(@RequestBody V1CreateTargetRequest body);

    @PutMapping(value = "/api/v1/targets/{targetName}")
    ResponseEntity<V1DetailTargetResponse> updateTarget(@PathVariable("targetName") String targetName, @RequestBody V1UpdateTargetRequest body);

    @DeleteMapping(value = "/api/v1/targets/{targetName}")
    ResponseEntity<V1SimpleResponse> deleteTarget(@PathVariable("targetName") String targetName);

}
