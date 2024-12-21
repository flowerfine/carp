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
package cn.sliew.carp.module.plugin.plugin.update;

import org.apache.commons.collections4.CollectionUtils;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.pf4j.VersionManager;

import java.util.*;

public class UpdateManager {

    private List<RemotePluginRepository> repositories;
    private PluginManager pluginManager;

    public UpdateManager(List<RemotePluginRepository> repositories, PluginManager pluginManager) {
        this.repositories = repositories;
        this.pluginManager = pluginManager;
    }

    public boolean hasUpdates(String repositoryId) {
        List<RemotePluginInfo> updates = getUpdates(repositoryId);
        return CollectionUtils.isEmpty(updates);
    }

    public List<RemotePluginInfo> getUpdates(String repositoryId) {
        List<RemotePluginInfo> updates = new ArrayList<>();
        for (PluginWrapper installed : pluginManager.getPlugins()) {
            String pluginId = installed.getPluginId();
            if (hasPluginUpdate(repositoryId, pluginId)) {

            }
        }
        return updates;
    }

    public boolean hasPluginUpdate(String repositoryId, String pluginId) {
        Optional<RemotePluginInfo> optional = getPlugins(repositoryId).stream()
                .filter(remotePluginInfo -> Objects.equals(remotePluginInfo.getId(), pluginId))
                .findFirst();
        if (optional.isEmpty()) {
            return false;
        }

        String installedVersion = pluginManager.getPlugin(pluginId).getDescriptor().getVersion();
        return false;
    }

    public PluginRelease getLastPluginRelease(RemotePluginInfo pluginInfo) {

        VersionManager versionManager = pluginManager.getVersionManager();
        String systemVersion = pluginManager.getSystemVersion();
        return null;
    }


    public boolean hasAvailablePlugins(String repositoryId) {
        List<RemotePluginInfo> availablePlugins = getAvailablePlugins(repositoryId);
        return CollectionUtils.isNotEmpty(availablePlugins);
    }

    public List<RemotePluginInfo> getAvailablePlugins(String repositoryId) {
        List<RemotePluginInfo> plugins = getPlugins(repositoryId);
        for (RemotePluginInfo pluginInfo : getPlugins(repositoryId)) {
            if (pluginManager.getPlugin(pluginInfo.getId()) == null) {
                plugins.add(pluginInfo);
            }
        }
        return plugins;
    }

    private List<RemotePluginInfo> getPlugins(String repositoryId) {
        return repositories.stream()
                .filter(r -> r.getId().equals(repositoryId))
                .findFirst()
                .map(RemotePluginRepository::getAll)
                .orElse(Collections.emptyList());
    }
}
