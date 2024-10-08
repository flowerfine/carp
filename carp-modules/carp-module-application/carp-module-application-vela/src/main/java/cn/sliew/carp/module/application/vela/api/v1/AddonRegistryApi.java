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

import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1AddonRegistry;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1CreateAddonRegistryRequest;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1ListAddonRegistryResponse;
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1UpdateAddonRegistryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface AddonRegistryApi {

    @GetMapping(value = "/api/v1/addon_registries")
    ResponseEntity<V1ListAddonRegistryResponse> listAddonRegistry();

    @PostMapping(value = "/api/v1/addon_registries")
    ResponseEntity<V1AddonRegistry> createAddonRegistry(@RequestBody V1CreateAddonRegistryRequest body);

    @PutMapping(value = "/api/v1/addon_registries/{addonRegName}")
    ResponseEntity<V1AddonRegistry> updateAddonRegistry(@RequestBody V1UpdateAddonRegistryRequest body, @PathVariable("addonRegName") String addonRegName);

    @DeleteMapping(value = "/api/v1/addon_registries/{addonRegName}")
    ResponseEntity<V1AddonRegistry> deleteAddonRegistry(@PathVariable("addonRegName") String addonRegName);

}
