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
import org.pf4j.Extension;
import org.pf4j.PluginWrapper;

public class HelloPlugin extends DemoPlugin {

    private PluginSdks pluginSdks;
    private HelloPluginProperties properties;

    public HelloPlugin(PluginWrapper wrapper, PluginSdks pluginSdks, HelloPluginProperties properties) {
        super(wrapper);
        this.pluginSdks = pluginSdks;
        this.properties = properties;
    }

    @Override
    public void start() {
        log.info("HelloPlugin.start(), name: {}", properties.getName());
    }

    @Override
    public void stop() {
        log.info("HelloPlugin.stop(), name: {}", properties.getName());
    }

    @Extension(ordinal = 1)
    @RequiredArgsConstructor
    public static class HelloGreeting implements Greeting {

        private final HelloExtensionProperties properties;

        @Override
        public String getGreeting() {
            return "Hello, " + properties.getName();
        }
    }
}
