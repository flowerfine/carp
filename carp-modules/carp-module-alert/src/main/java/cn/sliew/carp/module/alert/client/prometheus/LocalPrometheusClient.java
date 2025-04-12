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

import cn.sliew.carp.framework.common.dict.common.CarpYesOrNo;
import cn.sliew.carp.module.alert.enums.PrometheusDeployType;
import cn.sliew.carp.module.alert.model.config.prometheus.PrometheusConfig;
import cn.sliew.carp.module.alert.repository.entity.CarpAlertPrometheus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Component
public class LocalPrometheusClient implements PrometheusClient, InitializingBean {

    private Representer representer;

    @Override
    public void afterPropertiesSet() throws Exception {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        this.representer = new Representer(options) {
            protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
                return propertyValue == null ? null : super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
            }
        };
    }

    @Override
    public PrometheusDeployType getDeployType() {
        return PrometheusDeployType.LOCAL;
    }

    @Override
    public boolean updatePrometheusConfig(CarpAlertPrometheus prometheus, PrometheusConfig config) {
        Yaml yaml = new Yaml(representer);
        String prometheusConfig = yaml.dump(config);
        try (FileWriter fileWriter = new FileWriter(prometheus.getConfigFilePath());
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(prometheusConfig);
            writer.flush();
            reload(prometheus);
        } catch (IOException e) {
            log.error("Write prometheus config file error", e);
            return false;
        }
        return true;
    }

    private void reload(CarpAlertPrometheus prometheus) {
        String reloadUrl = getPrometheusReloadUrl(prometheus);
        if (prometheus.getIsAuthEnabled() == CarpYesOrNo.YES) {
            String credentials = prometheus.getUsername() + ":" + prometheus.getPassword();
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
//            request.setHeader("Authorization", "Basic " + encodedCredentials);
        } else {

        }
    }

    private String getPrometheusReloadUrl(CarpAlertPrometheus prometheus) {
        return String.format("%s/-/reload", prometheus.getUrl());
    }
}
