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

import cn.sliew.carp.module.plugin.service.PluginService;
import cn.sliew.carp.plugin.test.api.Greeting;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PluginServiceImpl implements PluginService {

    @Autowired
    private PluginManager pluginManager;

    @Override
    public List<PluginDescriptor> listAll() {
        return pluginManager.getPlugins().stream().map(PluginWrapper::getDescriptor).collect(Collectors.toList());
    }

    @Override
    public PluginDescriptor get(String pluginId) {
        return pluginManager.getPlugin(pluginId).getDescriptor();
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
