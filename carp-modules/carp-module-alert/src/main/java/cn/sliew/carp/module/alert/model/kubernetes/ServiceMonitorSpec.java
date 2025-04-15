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
package cn.sliew.carp.module.alert.model.kubernetes;

import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.Quantity;
import lombok.Data;

import java.util.List;

@Data
public class ServiceMonitorSpec {

    private List<String> podTargetLabels;

    private List<Endpoint> endpoints;

    private LabelSelector selector;

    private String jobLabel;

    private List<String> targetLabels;

    private Object selectorMechanism;

    private NamespaceSelector namespaceSelector;

    private Long sampleLimit;

    private Long targetLimit;

    private Long labelLimit;

    private List<Object> scrapeProtocols;

    private Long labelNameLengthLimit;

    private Long labelValueLengthLimit;

    private Boolean scrapeClassicHistograms;

    private Quantity nativeHistogramMinBucketFactor;

    private Boolean convertClassicHistogramsToNHCB;

    private Long keepDroppedTargets;

    private Object attachMetadata;

    private String scrapeClass;

    private String bodySizeLimit;
}
