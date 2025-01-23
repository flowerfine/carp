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
package cn.sliew.carp.module.orca.spinnaker.kork.plugins.api.internal;

import java.lang.reflect.InvocationHandler;

/**
 * When proxying an extension class, implement this interface to provide a mechanism to obtain the
 * underlying proxied class.
 */
public interface ExtensionInvocationHandler extends InvocationHandler {

    /**
     * Get the proxy target class.
     */
    Class<? extends SpinnakerExtensionPoint> getTargetClass();

    /**
     * Get the plugin ID of the proxied extension point.
     */
    String getPluginId();
}
