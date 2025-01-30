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
package cn.sliew.carp.module.http.sync.framework.model;

import cn.sliew.carp.module.http.sync.framework.model.job.JobInfo;
import cn.sliew.carp.module.http.sync.framework.model.job.JobLogLevel;
import cn.sliew.carp.module.http.sync.framework.util.GradientUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSetting {

    private JobInfo jobInfo;
    @Builder.Default
    private JobLogLevel logLevel = JobLogLevel.COMPLEX;
    // pekko default-dispatcher，必须是全路径
    @Builder.Default
    private String dispatcher = "pekko.actor.default-dispatcher";

    @Builder.Default
    private Integer parallelism = 2;
    @Builder.Default
    private Integer batchSize = 1;

    @Builder.Default
    private Duration minGradient = GradientUtil.MIN_GRADIENT;
    @Builder.Default
    private Boolean forceMinGradient = true;
    @Builder.Default
    private List<Duration> gradients = GradientUtil.getDefaultGradients();

    private String initSyncOffset;
    private String finalSyncOffset;

    private Properties properties;
}
