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

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum FileSystemType {

    LOCAL("local", "file://"),
    HDFS("hdfs", "hdfs://"),
    S3("s3", "s3a://"),
    OSS("oss", "oss://"),
    ;

    @JsonValue
    private String type;
    private String schema;

    FileSystemType(String type, String schema) {
        this.type = type;
        this.schema = schema;
    }

    public static FileSystemType of(String type) {
        for (FileSystemType fileSystemType : values()) {
            if (fileSystemType.type.equals(type)) {
                return fileSystemType;
            }
        }
        throw new IllegalStateException("unknown file-system type for " + type);
    }
}
