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

import org.pf4j.*;

public class CustomPluginManager extends DefaultPluginManager {

    @Override
    protected PluginRepository createPluginRepository() {
        return new CompoundPluginRepository()
                .add(new DevelopmentPluginRepository(getPluginsRoots()), this::isDevelopment)
                .add(new JarPluginRepository(getPluginsRoots()), this::isNotDevelopment)
                .add(new DefaultPluginRepository(getPluginsRoots()), this::isNotDevelopment)
                .add(new CustomPluginRepository());
    }

//    @Override
//    protected ExtensionFinder createExtensionFinder() {
//        DefaultExtensionFinder extensionFinder = new DefaultExtensionFinder(this);
//        extensionFinder.addServiceProviderExtensionFinder();
//        addPluginStateListener(extensionFinder);
//
//        return extensionFinder;
//    }

    @Override
    protected PluginStatusProvider createPluginStatusProvider() {
        return new CustomPluginStatusProvider();
    }
}
