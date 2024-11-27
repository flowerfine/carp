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

import cn.sliew.carp.framework.spring.util.SystemUtil;
import cn.sliew.carp.module.file.config.properties.HDFSConfig;
import cn.sliew.carp.module.file.config.properties.LocalConfig;
import cn.sliew.carp.module.file.config.properties.OSSConfig;
import cn.sliew.carp.module.file.config.properties.S3Config;
import cn.sliew.carp.module.file.util.HadoopUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.aliyun.oss.AliyunOSSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Configuration
public class FileSystemConfiguration {

    @Autowired
    private SystemUtil systemUtil;

    @Bean
    @ConditionalOnProperty(value = "file-system.type", havingValue = "local")
    public FileSystem localFileSystem(LocalConfig localConfig) throws IOException {
        org.apache.hadoop.conf.Configuration conf = HadoopUtil.getHadoopConfiguration(localConfig.getHadoopConfPath());
        FileSystem fileSystem = FileSystem.getLocal(conf);
        setFsWorkingDirectory(fileSystem, systemUtil.getLocalStorageDir().resolve(localConfig.getBasePath()).toString());
        return fileSystem;
    }

    @Bean
    @ConditionalOnProperty(value = "file-system.type", havingValue = "s3")
    public FileSystem s3FileSystem(S3Config s3Config) throws URISyntaxException, IOException {
        org.apache.hadoop.conf.Configuration conf = HadoopUtil.getHadoopConfiguration(s3Config.getHadoopConfPath());
        conf.set("fs.s3a.endpoint", s3Config.getEndpoint());
        conf.set("fs.s3a.access.key", s3Config.getAccessKey());
        conf.set("fs.s3a.secret.key", s3Config.getSecretKey());
        conf.setBoolean("fs.s3a.path.style.access", s3Config.isPathStyleAccess());
        URI uri = new URI(FileSystemType.S3.getSchema() + s3Config.getBucket());
        return FileSystem.get(uri, conf);
    }

    @Bean
    @ConditionalOnProperty(value = "file-system.type", havingValue = "oss")
    public FileSystem ossFileSystem(OSSConfig ossConfig) throws IOException, URISyntaxException {
        org.apache.hadoop.conf.Configuration conf = HadoopUtil.getHadoopConfiguration(ossConfig.getHadoopConfPath());
        conf.set("fs.oss.endpoint", ossConfig.getEndpoint());
        conf.set("fs.oss.accessKeyId", ossConfig.getAccessKey());
        conf.set("fs.oss.accessKeySecret", ossConfig.getSecretKey());
        URI uri = new URI(FileSystemType.OSS.getSchema() + ossConfig.getBucket());
        AliyunOSSFileSystem aliyunOSSFileSystem = new AliyunOSSFileSystem();
        aliyunOSSFileSystem.initialize(uri, conf);
        return aliyunOSSFileSystem;
    }

    @Bean
    @ConditionalOnProperty(value = "file-system.type", havingValue = "hdfs")
    public FileSystem hdfsFileSystem(HDFSConfig hdfsConfig) throws IOException {
        org.apache.hadoop.conf.Configuration conf = HadoopUtil.getHadoopConfiguration(hdfsConfig.getHadoopConfPath());
        if (StringUtils.hasText(hdfsConfig.getDefaultFS())) {
            conf.set("fs.defaultFS", hdfsConfig.getDefaultFS());
        }
        return FileSystem.get(conf);
    }

    private void setFsWorkingDirectory(FileSystem fileSystem, String workingDirectory) {
        if (workingDirectory == null) {
            log.warn("Null working directory");
            return;
        }
        String path = null;
        try {
            URI uri = new URI(workingDirectory);
            path = uri.getRawPath();
        } catch (Exception e) {
            log.error("Error parsing working directory {}", workingDirectory);
        }
        if (path != null) {
            log.info("Set working directory to {}", path);
            fileSystem.setWorkingDirectory(new Path(path));
        }
    }

}
