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
package cn.sliew.carp.plugin.storage.provider.s3;

import cn.sliew.carp.plugin.stroage.api.FileInfo;
import cn.sliew.carp.plugin.stroage.api.FileStorage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class S3FileStorage implements FileStorage {

    private final URI baseUri;

    @Override
    public boolean support(URI uri) {
        return StringUtils.startsWithIgnoreCase(S3Util.formatS3Uri(uri).toString(), baseUri.toString());
    }

    @Override
    public URI getUri(String path) {
        return null;
    }

    @Override
    public List<FileInfo> list(String path) {
        return List.of();
    }

    @Override
    public Optional<FileInfo> get(String path) {
        return Optional.empty();
    }

    @Override
    public Optional<byte[]> getData(String path) {
        return Optional.empty();
    }

    @Override
    public Optional<byte[]> getData(URI uri) {
        return Optional.empty();
    }

    @Override
    public FileInfo putData(String path, byte[] data) {
        return null;
    }

    @Override
    public boolean delete(String path) {
        return false;
    }

    @Override
    public boolean delete(URI uri) {
        return false;
    }
}
