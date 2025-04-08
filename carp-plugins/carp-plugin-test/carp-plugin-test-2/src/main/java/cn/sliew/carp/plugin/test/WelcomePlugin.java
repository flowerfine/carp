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
package cn.sliew.carp.plugin.test;

import cn.sliew.carp.framework.pf4j.api.PluginSdks;
import cn.sliew.carp.plugin.test.api.DemoPlugin;
import cn.sliew.carp.plugin.test.api.Greeting;
import lombok.RequiredArgsConstructor;
import org.pf4j.PluginWrapper;

public class WelcomePlugin extends DemoPlugin {

    private PluginSdks pluginSdks;
    private WelcomePluginWithNamespaceProperties properties;

    public WelcomePlugin(PluginWrapper wrapper, PluginSdks pluginSdks, WelcomePluginWithNamespaceProperties properties) {
        super(wrapper);
        this.pluginSdks = pluginSdks;
        this.properties = properties;
    }

    @Override
    public void start() {
        log.info("WelcomePlugin.start(), value: {}", properties.getValue());
    }

    @Override
    public void stop() {
        log.info("WelcomePlugin.stop(), value: {}", properties.getValue());
    }

    /**
     * 如果添加 @Extension 注解，pf4j 的 ExtensionFactory 会生成这个实例。
     * 无论添不添加 @Extension，都会因为 CarpExtensionPoint
     */
    // 不可添加 @Extension 注解，避免 pf4j 的 ExtensionFactory 生成这个实例
//    @Extension
    @RequiredArgsConstructor
    public static class WelcomeGreeting implements Greeting {

        // 自动注入
        private final WelcomeService welcomeService;

        @Override
        public String getGreeting() {
            return welcomeService.getGreeting();
        }

    }
}
