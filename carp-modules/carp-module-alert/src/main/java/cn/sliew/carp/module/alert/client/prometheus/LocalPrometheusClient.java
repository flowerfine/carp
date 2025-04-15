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
import cn.sliew.carp.module.alert.service.dto.CarpAlertRuleDTO;
import io.fabric8.kubernetes.client.utils.Serialization;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Component
public class LocalPrometheusClient implements PrometheusClient {

    @Autowired
    private OkHttpClient client;

    @Override
    public PrometheusDeployType getDeployType() {
        return PrometheusDeployType.LOCAL;
    }

    @Override
    public PrometheusConfig.ScrapeConfig convertToScrapeConfig(CarpAlertPrometheus prometheus, CarpAlertRuleDTO alertRuleDTO) {
        return null;
    }

    @Override
    public <T> T convertToRule(CarpAlertPrometheus prometheus, CarpAlertRuleDTO alertRuleDTO) {
        return null;
    }

    @Override
    public boolean updatePrometheusConfig(CarpAlertPrometheus prometheus) {
        String prometheusConfig = Serialization.asYaml(null);
        try (FileWriter fileWriter = new FileWriter(prometheus.getConfigFilePath());
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            writer.write(prometheusConfig);
            writer.flush();
            reload(prometheus);
        } catch (Exception e) {
            log.error("Write prometheus config file error", e);
            return false;
        }
        return true;
    }

    private void reload(CarpAlertPrometheus prometheus) throws Exception {
        String reloadUrl = getPrometheusReloadUrl(prometheus);
        Request.Builder builder = new Request.Builder()
                .url(reloadUrl)
                .post(RequestBody.create(new byte[0],
                        MediaType.parse("application/x-www-form-urlencoded")))
                .header("Content-type", "application/x-www-form-urlencoded");
        if (prometheus.getIsAuthEnabled() == CarpYesOrNo.YES) {
            String credentials = prometheus.getUsername() + ":" + prometheus.getPassword();
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            builder.header("Authorization", "Basic " + encodedCredentials);
        }
        try (Response response = client.newCall(builder.build()).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException(response.message());
            }
        } catch (IOException e) {
            throw e;
        }
    }

    private String getPrometheusReloadUrl(CarpAlertPrometheus prometheus) {
        return String.format("%s/-/reload", prometheus.getUrl());
    }
}
