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
package cn.sliew.carp.module.alert.model.config.prometheus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@Data
public class PrometheusConfig {

    private Global global = new Global();
    private Alerting alerting;
    @JsonProperty("rule_files")
    private List<String> ruleFiles;
    @JsonProperty("scrape_configs")
    private List<ScrapeConfig> scrapeConfigs;

    @Data
    public static class Global {
        @JsonProperty("scrape_interval")
        private String scrapeInterval = "15s";
        @JsonProperty("evaluation_interval")
        private String evaluationInterval = "15s";
    }

    @Data
    public static class Alerting {
        private List<Alertmanager> alertmanagers;
    }

    @Data
    public static class Alertmanager {
        @JsonProperty("basic_auth")
        private BasicAuth basicAuth;
        @JsonProperty("static_configs")
        private List<AlertStaticConfig> staticConfigs;
    }

    @Data
    public static class BasicAuth {
        private String username;
        private String password;
    }

    @Data
    public static class AlertStaticConfig {
        private List<String> targets;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScrapeConfig {
        @JsonProperty("job_name")
        private String jobName;
        @JsonProperty("metrics_path")
        private String metricsPath = "/metrics";
        private String scheme;
        @JsonProperty("static_configs")
        private List<ClusterStaticConfig> staticConfigs;
        @JsonProperty("relabel_configs")
        private List<RelabelConfig> relabelConfigs;
        @JsonProperty("basic_auth")
        private ClusterBasicAuth basicAuth;

        public ScrapeConfig(String jobName, String metricsPath) {
            this.jobName = jobName;
            if (StringUtils.isNotEmpty(metricsPath)) {
                this.metricsPath = metricsPath;
            }
        }
    }

    @Data
    public static class ClusterStaticConfig {
        private List<String> targets;
        private Map<String, String> labels;
    }

    @Data
    public static class ClusterBasicAuth {
        private String username;
        private String password;
    }

    @Data
    public static class RelabelConfig {
        @JsonProperty("source_labels")
        private List<String> sourceLabels;
        @JsonProperty("target_label")
        private String targetLabel;
        private String regex;
        private String replacement;
    }
}
