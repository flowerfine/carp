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

package cn.sliew.carp.module.application.vela.controller.definition;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.application.vela.api.v1.DefinitionApi;
import cn.sliew.carp.module.application.vela.api.v1.model.security.V1GetLoginTypeResponse;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1ListDefinitionResponse;
import cn.sliew.carp.module.application.vela.config.VelaFeignConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/application/vela/definitions")
@Tag(name = "应用模块-Vela-XDefinition管理")
public class VelaDefinitionController {

    @Autowired
    private DefinitionApi definitionApi;

    @AnonymousAccess
    @GetMapping
    @Operation(summary = "分页查询", description = "分页查询")
    public ResponseEntity<V1ListDefinitionResponse> listDefinitions(@RequestParam("type") String type,
                                                                    @RequestParam(value = "queryAll", required = false) Boolean queryAll,
                                                                    @RequestParam(value = "appliedWorkload", required = false) String appliedWorkload,
                                                                    @RequestParam(value = "ownerAddon", required = false) String ownerAddon,
                                                                    @RequestParam(value = "scope", required = false) String scope) {
        return definitionApi.listDefinitions(VelaFeignConfig.VELA_URI, type, queryAll, appliedWorkload, ownerAddon, scope);
    }

}
