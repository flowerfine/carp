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

package cn.sliew.carp.module.kubernetes.service.impl;

import cn.sliew.carp.module.kubernetes.service.K8sClusterService;
import cn.sliew.carp.module.kubernetes.service.KubernetesClientService;
import cn.sliew.carp.module.kubernetes.service.entity.Cluster;
import cn.sliew.carp.module.kubernetes.service.entity.spec.ClusterSpec;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.NamespacedKubernetesClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KubernetesClientServiceImpl implements KubernetesClientService {

    @Autowired
    private K8sClusterService k8sClusterService;

    @Override
    public KubernetesClient getClient(Long id) {
        return new KubernetesClientBuilder().withConfig(getConfig(id)).build();
    }

    @Override
    public NamespacedKubernetesClient getClient(Long id, String namespace) {
        Config config = getConfig(id);
        config.setNamespace(namespace);
        return new KubernetesClientBuilder()
                .withConfig(config)
                .build()
                .adapt(NamespacedKubernetesClient.class);
    }

    @Override
    public Config getConfig(Long id) {
        Cluster cluster = k8sClusterService.get(id);
        ClusterSpec spec = cluster.getSpec();
        Config config = null;
        switch (spec.getAuthMode()) {
            case "kubeconfig":
                config = buildConfigFromContent(spec.getContext(), spec.getConfigContent());
                break;
            default:
        }
        return config;
    }

    private Config buildConfigFromContent(String context, String configContent) {
        if (StringUtils.isNotBlank(configContent)) {
            return Config.fromKubeconfig(context, configContent, null);
        }
        return Config.fromKubeconfig(configContent);
    }
}
