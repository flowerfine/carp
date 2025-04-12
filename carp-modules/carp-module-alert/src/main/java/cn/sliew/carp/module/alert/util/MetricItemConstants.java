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
package cn.sliew.carp.module.alert.util;

import cn.sliew.carp.module.alert.enums.AlertIndexType;
import cn.sliew.carp.module.alert.enums.MetricMethod;
import cn.sliew.carp.module.alert.enums.MetricType;
import cn.sliew.carp.module.alert.metrics.MetricItem;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Map;

public enum MetricItemConstants {
    ;

    public static Map<String, MetricItem> METRICS = Maps.newHashMap();

    static {
        METRICS.put("restart_count",
                MetricItem.builder()
                        .indexType(AlertIndexType.METRIC)
                        .metricType(MetricType.JOB)
                        .name("Job Restart")
                        .metric("flink_jobmanager_job_numRestarts")
                        .unit("")
                        .method(MetricMethod.DELTA)
                        .label(Lists.newArrayList("deploymentId={deploymentId}"))
                        .build());

        METRICS.put("lag_time",
                MetricItem.builder()
                        .indexType(AlertIndexType.METRIC)
                        .metricType(MetricType.JOB)
                        .name("Job Lag")
                        .metric("flink_taskmanager_job_task_operator_currentEmitEventTimeLag")
                        .unit("s")
                        .method(MetricMethod.MAX)
                        .label(Lists.newArrayList("deploymentId={deploymentId}"))
                        .build());

        METRICS.put("checkpoint_count",
                MetricItem.builder()
                        .indexType(AlertIndexType.METRIC)
                        .metricType(MetricType.JOB)
                        .name("Checkpoints Count")
                        .metric("flink_jobmanager_job_numberOfCompletedCheckpoints")
                        .unit("")
                        .method(MetricMethod.DELTA)
                        .label(Lists.newArrayList("deploymentId={deploymentId}"))
                        .build());

        METRICS.put("in_rps",
                MetricItem.builder()
                        .indexType(AlertIndexType.METRIC)
                        .metricType(MetricType.TRAFFIC)
                        .name("In RPS")
                        .metric("flink_taskmanager_job_task_operator_source_numRecordsInPerSecond")
                        .unit("")
                        .method(MetricMethod.SUM_BY)
                        .label(Lists.newArrayList("deploymentId={deploymentId}"))
                        .build());

        METRICS.put("out_rps",
                MetricItem.builder()
                        .indexType(AlertIndexType.METRIC)
                        .metricType(MetricType.TRAFFIC)
                        .name("Out RPS")
                        .metric("flink_taskmanager_job_task_operator_sink_numRecordsOutPerSecond")
                        .unit("")
                        .method(MetricMethod.SUM_BY)
                        .label(Lists.newArrayList("deploymentId={deploymentId}"))
                        .build());

        METRICS.put("source_idle_time",
                MetricItem.builder()
                        .indexType(AlertIndexType.METRIC)
                        .metricType(MetricType.TRAFFIC)
                        .name("Source Idle Time")
                        .metric("flink_taskmanager_job_task_operator_sourceIdleTime")
                        .unit("")
                        .method(MetricMethod.MAX_BY)
                        .label(Lists.newArrayList("deploymentId={deploymentId}"))
                        .build());
    }

}
