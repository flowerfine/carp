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
package cn.sliew.carp.module.alert.metrics;

import cn.hutool.core.util.ArrayUtil;
import cn.sliew.carp.module.alert.enums.MetricMethod;

public enum PromQLGenerator {
    ;

    /**
     * flink_taskmanager_job_task_operator_currentEmitEventTimeLag ->
     * flink_taskmanager_job_task_operator_currentEmitEventTimeLag{deloymentId='xxxx'}
     */
    public static String concatMetricAndLabels(String metric, String... labels) {
        String labelString = ArrayUtil.join(labels, ",");
        return String.format("%s{%s}", metric, labelString);
    }

    /**
     * flink_taskmanager_job_task_operator_currentEmitEventTimeLag ->
     * flink_taskmanager_job_task_operator_currentEmitEventTimeLag{deloymentId='xxxx'}[1m]
     */
    public static String concatInterval(String promQL, Integer inverval, String unit) {
        return String.format("%s[%d%s]", promQL, inverval, unit);
    }

    /**
     * flink_taskmanager_job_task_operator_currentEmitEventTimeLag{deloymentId='xxxx'}[1m] ->
     * delta(flink_taskmanager_job_task_operator_currentEmitEventTimeLag{deloymentId='xxxx'}[1m])
     */
    public static String concatMethod(MetricMethod method, String promQL) {
        return String.format("%s(%s)", method.getValue(), promQL);
    }

    /**
     * flink_taskmanager_job_task_operator_currentEmitEventTimeLag{deloymentId='xxxx'}[1m] ->
     * max by(ali_metric_deploymentName, deploymentId) (flink_taskmanager_job_task_operator_currentEmitEventTimeLag{deloymentId='xxxx'}[1m])
     */
    public static String concatByMethod(MetricMethod method, String promQL, String... labels) {
        String labelString = ArrayUtil.join(labels, ",");
        return String.format("%s(%s) ()", method.getValue(), labelString, promQL);
    }
}
