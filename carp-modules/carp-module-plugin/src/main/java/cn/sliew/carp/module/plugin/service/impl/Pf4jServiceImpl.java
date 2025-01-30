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
package cn.sliew.carp.module.plugin.service.impl;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.module.plugin.plugin.update.PluginRepositoryInfo;
import cn.sliew.carp.module.plugin.plugin.update.RemotePluginInfo;
import cn.sliew.carp.module.plugin.plugin.update.RemotePluginRepository;
import cn.sliew.carp.module.plugin.service.Pf4jService;
import cn.sliew.carp.module.plugin.service.param.CarpRemotePluginInfoPageParam;
import cn.sliew.carp.plugin.test.api.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class Pf4jServiceImpl implements Pf4jService {

    @Autowired
    private PluginManager pluginManager;
    @Autowired
    private List<RemotePluginRepository> repositories;

    @Override
    public List<PluginRepositoryInfo> listRemoteRepository() {
        if (CollectionUtils.isEmpty(repositories)) {
            return Collections.emptyList();
        }
        return repositories.stream().map(RemotePluginRepository::getInfo).collect(Collectors.toList());
    }

    @Override
    public PageResult<RemotePluginInfo> pageRemotePluginInfo(CarpRemotePluginInfoPageParam param) {
        if (CollectionUtils.isEmpty(repositories)) {
            return new PageResult<>(param.getCurrent(), param.getPageSize(), 0L);
        }
        return repositories.stream().filter(repository -> Objects.equals(repository.getId(), param.getRepositoryId()))
                .map(repository -> repository.page(param))
                .findFirst().orElseThrow();
    }

    @Override
    public List<RemotePluginInfo> listRemotePluginInfo(CarpRemotePluginInfoPageParam param) {
        if (CollectionUtils.isEmpty(repositories)) {
            return Collections.emptyList();
        }
        return repositories.stream().filter(repository -> Objects.equals(repository.getId(), param.getRepositoryId()))
                .map(repository -> repository.getAll())
                .findFirst().orElseThrow();
    }

    @Override
    public PageResult<PluginDescriptor> page(PageParam param) {
        return PageResult.build(listAll(), param);
    }

    @Override
    public List<PluginDescriptor> listAll() {
        return pluginManager.getPlugins().stream()
                .map(PluginWrapper::getDescriptor)
                .sorted(Comparator.comparing(PluginDescriptor::getPluginId))
                .collect(Collectors.toList());
    }

    @Override
    public PluginDescriptor get(String pluginId) {
        return pluginManager.getPlugin(pluginId).getDescriptor();
    }

    @Override
    public String loadPlugin(Path path) {
        return pluginManager.loadPlugin(path);
    }

    @Override
    public boolean unloadPlugin(String pluginId) {
        return pluginManager.unloadPlugin(pluginId);
    }

    @Override
    public boolean enablePlugin(String pluginId) {
        pluginManager.enablePlugin(pluginId);
        pluginManager.startPlugin(pluginId);
        return true;
    }

    @Override
    public boolean disablePlugin(String pluginId) {
        pluginManager.disablePlugin(pluginId);
        return pluginManager.deletePlugin(pluginId);
    }

    @Override
    public <EP> List<EP> getExtensions(Class<EP> clazz) {
        return pluginManager.getExtensions(clazz);
    }

    @Override
    public <EP> List<EP> getExtensions(Class<EP> clazz, String pluginId) {
        return pluginManager.getExtensions(clazz, pluginId);
    }

    @Override
    public void testExtension() {
        List<Greeting> extensions = getExtensions(Greeting.class);
        for (Greeting greeting : extensions) {
            log.info("   {}", greeting.getGreeting());
        }
    }
}
