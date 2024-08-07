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

import cn.sliew.carp.module.application.vela.api.v1.model.common.SchemaUIParameter;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1DetailDefinitionResponse;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1ListDefinitionResponse;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1UpdateDefinitionStatusRequest;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1UpdateUISchemaRequest;
import cn.sliew.carp.module.application.vela.config.VelaFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@FeignClient(value = "VelaDefinitionApi", url = "EMPTY", configuration = VelaFeignConfig.class)
public interface DefinitionApi {

    @GetMapping(value = "/api/v1/definitions")
    ResponseEntity<V1ListDefinitionResponse> listDefinitions(URI uri, @RequestParam("type") String type, @RequestParam("queryAll") Boolean queryAll, @RequestParam("appliedWorkload") String appliedWorkload, @RequestParam("ownerAddon") String ownerAddon, @RequestParam("scope") String scope);

    @GetMapping(value = "/api/v1/definitions/{definitionName}")
    ResponseEntity<V1DetailDefinitionResponse> detailDefinition(URI uri, @PathVariable("definitionName") String definitionName, @RequestParam("type") String type);

    @PutMapping(value = "/api/v1/definitions/{definitionName}/status")
    ResponseEntity<List<SchemaUIParameter>> updateDefinitionStatus(URI uri, @PathVariable("definitionName") String definitionName, @RequestBody V1UpdateDefinitionStatusRequest body);

    @PutMapping(value = "/api/v1/definitions/{definitionName}/uischema")
    ResponseEntity<List<SchemaUIParameter>> updateUISchema(URI uri, @PathVariable("definitionName") String definitionName, @RequestBody V1UpdateUISchemaRequest body);

}
