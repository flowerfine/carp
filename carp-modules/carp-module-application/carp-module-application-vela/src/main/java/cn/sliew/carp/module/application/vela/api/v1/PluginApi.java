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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PluginApi {

    @GetMapping(value = "/api/v1/manage/plugins")
    ResponseEntity<V1ListPluginResponse> listInstalledPlugins();

    @GetMapping(value = "/api/v1/manage/plugins/{pluginId}")
    ResponseEntity<V1ManagedPluginDTO> detailPlugin(@PathVariable("pluginId") String pluginId);

    @PostMapping(value = "/api/v1/manage/plugins/{pluginId}/disable")
    ResponseEntity<V1ManagedPluginDTO> disablePlugin(@PathVariable("pluginId") String pluginId);

    @PostMapping(value = "/api/v1/manage/plugins/{pluginId}/enable")
    ResponseEntity<V1ManagedPluginDTO> enablePlugin(@RequestBody V1PluginEnableRequest body, @PathVariable("pluginId") String pluginId);

    @PostMapping(value = "/api/v1/manage/plugins/{pluginId}/install")
    ResponseEntity<V1ManagedPluginDTO> installPlugin(@RequestBody V1InstallPluginRequest body, @PathVariable("pluginId") String pluginId);

    @PostMapping(value = "/api/v1/manage/plugins/{pluginId}/uninstall")
    ResponseEntity<V1EmptyResponse> uninstallPlugin(@PathVariable("pluginId") String pluginId);

    @PostMapping(value = "/api/v1/manage/plugins/{pluginId}/setting")
    ResponseEntity<V1ManagedPluginDTO> pluginSetting(@PathVariable("pluginId") String pluginId);


    @GetMapping(value = "/api/v1/plugins")
    ResponseEntity<V1ListPluginResponse> listEnabledPlugins();

    @GetMapping(value = "/api/v1/plugins/{pluginId}")
    ResponseEntity<V1PluginDTO> detailPlugin_0(@PathVariable("pluginId") String pluginId);

}
