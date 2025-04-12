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
package cn.sliew.carp.module.alert.service.impl;

import cn.sliew.carp.module.alert.enums.ConditionType;
import cn.sliew.carp.module.alert.enums.EventType;
import cn.sliew.carp.module.alert.enums.MetricType;
import cn.sliew.carp.module.alert.service.InternalMetricService;
import cn.sliew.carp.module.alert.metrics.MetricItem;
import cn.sliew.carp.module.alert.util.MetricItemConstants;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class InternalMetricServiceImpl implements InternalMetricService {

    @Override
    public List<EventType> listEventTypes() {
        return Arrays.asList(EventType.values());
    }

    @Override
    public List<MetricType> listMetricTypes() {
        return Arrays.asList(MetricType.values());
    }

    @Override
    public List<ConditionType> listConditionTypes() {
        return Arrays.asList(ConditionType.values());
    }

    @Override
    public List<MetricItem> listMetrics(MetricType metricType) {
        return MetricItemConstants.METRICS.values().stream().filter(
                metricItem -> Objects.equals(metricType, metricItem.getMetricType())).toList();
    }
}
