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

package cn.sliew.carp.module.application.vela.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.net.URI;

public class VelaFeignConfig {

    public static final URI VELA_URI = URI.create("http://129.204.156.150:8000");

    @Bean
    public VelaOpenAPIAuthInterceptor velaOpenAPIInterceptor() {
        return new VelaOpenAPIAuthInterceptor();
    }

    @Slf4j
    public static class VelaOpenAPIAuthInterceptor implements RequestInterceptor {

        private static String token;

        public static void setToken(String accessToken) {
            VelaOpenAPIAuthInterceptor.token = "Bearer " + accessToken;
        }

        @Override
        public void apply(RequestTemplate requestTemplate) {
            requestTemplate.header("Authorization", token);
        }
    }
}
