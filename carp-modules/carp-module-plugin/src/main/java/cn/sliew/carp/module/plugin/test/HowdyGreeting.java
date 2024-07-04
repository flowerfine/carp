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

package cn.sliew.carp.module.plugin.test;

import cn.sliew.carp.plugin.test.api.Greeting;
import com.google.auto.service.AutoService;

/**
 * 不起效的，因为 {@link org.pf4j.DefaultExtensionFinder} 没有启用 ServiceLoader 相关的功能
 */
@AutoService(Greeting.class)
public class HowdyGreeting implements Greeting {

    @Override
    public String getGreeting() {
        return "Howdy";
    }
}