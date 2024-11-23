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

package cn.sliew.carp.module.file.config;

import cn.sliew.carp.module.file.config.properties.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HadoopStorageConfig.class)
public class FileSystemConfig {

    @SuppressWarnings("all")
    @Bean
    @ConfigurationProperties(prefix = "file.storage")
    @ConditionalOnProperty(value = "type", havingValue = "local")
    public LocalConfig localFileSystemProperties() {
        return new LocalConfig();
    }

    @SuppressWarnings("all")
    @Bean
    @ConfigurationProperties(prefix = "file.storage")
    @ConditionalOnProperty(value = "type", havingValue = "s3")
    public S3Config s3FileSystemProperties() {
        return new S3Config();
    }

    @SuppressWarnings("all")
    @Bean
    @ConfigurationProperties(prefix = "file.storage")
    @ConditionalOnProperty(value = "type", havingValue = "oss")
    public OSSConfig ossFileSystemProperties() {
        return new OSSConfig();
    }

    @SuppressWarnings("all")
    @Bean
    @ConfigurationProperties(prefix = "file.storage")
    @ConditionalOnProperty(value = "type", havingValue = "hdfs")
    public HDFSConfig hdfsFileSystemProperties() {
        return new HDFSConfig();
    }

}
