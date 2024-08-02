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
import cn.sliew.carp.module.application.vela.api.v1.model.v1.V1AddonStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface AddonApi {

    @GetMapping(value = "/api/v1/addons")
    ResponseEntity<V1ListAddonResponse> listAddons(@RequestParam("registry") String registry, @RequestParam("query") String query);

    @GetMapping(value = "/api/v1/enabled_addon")
    ResponseEntity<V1ListEnabledAddonResponse> callList(@RequestParam("registry") String registry, @RequestParam("query") String query);

    @GetMapping(value = "/api/v1/addons/{addonName}")
    ResponseEntity<V1DetailAddonResponse> detailAddon(@PathVariable("addonName") String addonName, @RequestParam("version") String version, @RequestParam("registry") String registry);

    @GetMapping(value = "/api/v1/addons/{addonName}/status")
    ResponseEntity<V1AddonStatusResponse> statusAddon(@PathVariable("addonName") String addonName);

    @PostMapping(value = "/api/v1/addons/{addonName}/disable")
    ResponseEntity<V1AddonStatusResponse> disableAddon(@PathVariable("addonName") String addonName, @RequestParam("force") Boolean force);

    @PostMapping(value = "/api/v1/addons/{addonName}/enable")
    ResponseEntity<V1AddonStatusResponse> enableAddon(@RequestBody V1EnableAddonRequest body, @PathVariable("addonName") String addonName);

    @PutMapping(value = "/api/v1/addons/{addonName}/update")
    ResponseEntity<V1AddonStatusResponse> updateAddon(@RequestBody V1EnableAddonRequest body, @PathVariable("addonName") String addonName);

}
