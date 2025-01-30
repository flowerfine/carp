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
package cn.sliew.carp.module.orca.core.config;

import cn.sliew.carp.framework.dag.service.CarpDagOrcaPipelineService;
import cn.sliew.carp.module.orca.core.persistence.sql.SqlExecutionRepository;
import cn.sliew.carp.module.orca.spinnaker.api.persistence.ExecutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SpinnakerOrcaCoreConfig {

    public static final String SQL_EXECUTION_REPOSITORY = "sql-execution-repository";
    public static final String QUEUE_EVENT_PUBLISHER = "queueEventPublisher";
    public static final String CANCELLABLE_STAGE_EXECUTOR = "cancellable-stage-executor";

    @Bean(SQL_EXECUTION_REPOSITORY)
    public ExecutionRepository sqlExecutionRepository(
            CarpDagOrcaPipelineService carpDagOrcaPipelineService) {
        return new SqlExecutionRepository(carpDagOrcaPipelineService);
    }

}
