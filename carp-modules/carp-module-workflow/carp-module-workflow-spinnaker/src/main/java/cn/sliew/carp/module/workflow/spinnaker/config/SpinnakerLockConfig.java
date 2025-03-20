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
package cn.sliew.carp.module.workflow.spinnaker.config;

import cn.sliew.carp.framework.common.lock.RetriableLockAndRunExecutor;
import cn.sliew.carp.framework.lock.redis.RedisLockAndRunAutoConfiguration;
import cn.sliew.carp.framework.lock.redis.RedisLockAndRunExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RedisLockAndRunAutoConfiguration.class)
public class SpinnakerLockConfig {

    public static final String RETRIABLE_LOCK_AND_RUN_EXECUTOR = "carpRetriableLockAndRunExecutor";

    @Bean(RETRIABLE_LOCK_AND_RUN_EXECUTOR)
    public RetriableLockAndRunExecutor carpRetriableLockAndRunExecutor(RedisLockAndRunExecutor lockAndRunExecutor) {
        return new RetriableLockAndRunExecutor(lockAndRunExecutor);
    }
}
