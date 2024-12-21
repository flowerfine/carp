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

package cn.sliew.carp.module.plugin.config;

import cn.sliew.carp.module.plugin.plugin.CustomPluginManager;
import cn.sliew.carp.module.plugin.plugin.CustomPluginRepository;
import cn.sliew.carp.module.plugin.plugin.CustomPluginStateListener;
import cn.sliew.carp.module.plugin.plugin.CustomPluginStatusProvider;
import cn.sliew.carp.module.plugin.service.CarpPluginReleaseService;
import cn.sliew.carp.module.plugin.service.CarpPluginStatusService;
import org.pf4j.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Pf4jConfig {

    @Autowired
    private CarpPluginReleaseService carpPluginReleaseService;
    @Autowired
    private CarpPluginStatusService carpPluginStatusService;

    @Bean
    public CustomPluginRepository customPluginRepository() {
        return new CustomPluginRepository(carpPluginReleaseService, carpPluginStatusService);
    }

    @Bean
    public CustomPluginStatusProvider customPluginStatusProvider() {
        return new CustomPluginStatusProvider(carpPluginStatusService);
    }

    @Bean
    public CustomPluginStateListener customPluginStateListener() {
        return new CustomPluginStateListener(carpPluginStatusService);
    }

    @Bean
    public PluginManager pluginManager() {
        PluginManager pluginManager = new CustomPluginManager(
                customPluginRepository(),
                customPluginStatusProvider());
        pluginManager.addPluginStateListener(customPluginStateListener());

        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        return pluginManager;
    }
}
