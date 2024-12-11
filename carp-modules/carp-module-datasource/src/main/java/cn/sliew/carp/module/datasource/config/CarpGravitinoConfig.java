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

package cn.sliew.carp.module.datasource.config;

import cn.sliew.carp.framework.common.util.NetUtil;
import org.apache.gravitino.client.GravitinoAdminClient;
import org.apache.gravitino.client.GravitinoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CarpGravitinoProperties.class)
public class CarpGravitinoConfig {

    @Autowired
    private CarpGravitinoProperties properties;

    @Bean
    public GravitinoAdminClient gravitinoAdminClient() {
        return GravitinoAdminClient.builder(NetUtil.replaceLocalhost(properties.getUrl()))
                .withSimpleAuth()
                .build();
    }

    /**
     * fixme 必须添加 metalakeName
     */
    public GravitinoClient gravitinoClient() {
        return GravitinoClient.builder(NetUtil.replaceLocalhost(properties.getUrl()))
                .withSimpleAuth()
                .build();
    }
}
