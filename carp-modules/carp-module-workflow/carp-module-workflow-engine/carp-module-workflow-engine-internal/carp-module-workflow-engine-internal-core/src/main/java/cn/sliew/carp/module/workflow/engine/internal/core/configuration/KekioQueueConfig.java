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
package cn.sliew.carp.module.workflow.engine.internal.core.configuration;

import cn.sliew.carp.framework.queue.kekio.configuration.KekioQueueAutoConfiguration;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.*;

@Configuration
@AutoConfigureBefore(KekioQueueAutoConfiguration.class)
public class KekioQueueConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public JedisPool jedisPool() {
        GenericObjectPoolConfig<Jedis> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setJmxNameBase("jedisPool");
        poolConfig.setJmxNamePrefix("jedis");
        poolConfig.setJmxEnabled(false);
        poolConfig.setMinIdle(1);
        poolConfig.setMaxIdle(3);
        poolConfig.setMaxTotal(10);

        HostAndPort hostAndPort = new HostAndPort(redisProperties.getHost(), redisProperties.getPort());
        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .clientName("kekio")
                .database(redisProperties.getDatabase())
                .password(redisProperties.getPassword())
                .build();
        return new JedisPool(poolConfig, hostAndPort, config);
    }
}
