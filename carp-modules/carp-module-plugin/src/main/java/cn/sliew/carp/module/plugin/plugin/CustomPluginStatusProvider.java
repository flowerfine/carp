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
package cn.sliew.carp.module.plugin.plugin;

import cn.sliew.carp.module.plugin.service.CarpPluginStatusService;
import cn.sliew.carp.module.plugin.service.dto.CarpPluginStatusDTO;
import lombok.AllArgsConstructor;
import org.pf4j.PluginState;
import org.pf4j.PluginStatusProvider;

import java.util.Optional;

@AllArgsConstructor
public class CustomPluginStatusProvider implements PluginStatusProvider {

    private CarpPluginStatusService carpPluginStatusService;

    @Override
    public boolean isPluginDisabled(String pluginId) {
        Optional<CarpPluginStatusDTO> optional = carpPluginStatusService.get(pluginId);
        return optional.filter(status -> status.getStatus() == PluginState.DISABLED).isPresent();
    }

    @Override
    public void disablePlugin(String pluginId) {
        carpPluginStatusService.upsert(pluginId, PluginState.DISABLED);
    }

    @Override
    public void enablePlugin(String pluginId) {
        carpPluginStatusService.upsert(pluginId, PluginState.STARTED);
    }
}
