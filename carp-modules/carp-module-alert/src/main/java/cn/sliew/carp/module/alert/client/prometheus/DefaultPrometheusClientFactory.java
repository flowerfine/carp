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
package cn.sliew.carp.module.alert.client.prometheus;

import cn.sliew.carp.module.alert.repository.entity.CarpAlertPrometheus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DefaultPrometheusClientFactory implements PrometheusClientFactory {

    @Autowired(required = false)
    private List<PrometheusClient> clients;

    @Override
    public PrometheusClient getInstance(CarpAlertPrometheus prometheus) {
        return clients.stream()
                .filter(client -> Objects.equals(client.getDeployType(), prometheus.getType()))
                .findFirst()
                .orElseThrow();
    }
}
