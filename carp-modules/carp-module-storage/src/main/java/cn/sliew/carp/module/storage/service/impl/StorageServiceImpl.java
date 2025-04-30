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
package cn.sliew.carp.module.storage.service.impl;

import cn.sliew.carp.framework.storage.FileInfo;
import cn.sliew.carp.framework.storage.FileStorage;
import cn.sliew.carp.framework.storage.FileStorageFactory;
import cn.sliew.carp.module.storage.service.StorageService;
import cn.sliew.milky.common.util.JacksonUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final FileStorageFactory fileStorageFactory;

    @Override
    public List<String> list(String path) throws IOException {
        FileStorage fileStorage = fileStorageFactory.getFileStorage();
        System.out.println(JacksonUtil.toJsonString(fileStorage.list(path)));
        return fileStorage.list(path).stream()
                .filter(fileInfo -> !fileInfo.isDir())
                .map(FileInfo::getName)
                .toList();
    }

    @Override
    public URI getUri(String path) throws IOException {
        FileStorage fileStorage = fileStorageFactory.getFileStorage();
        return fileStorage.getUri(path);
    }

    @Override
    public InputStream get(String path) throws IOException {
        FileStorage fileStorage = fileStorageFactory.getFileStorage();
        return fileStorage.getStream(path).orElseThrow();
    }

    @Override
    public URI upload(InputStream inputStream, String path) throws IOException {
        FileStorage fileStorage = fileStorageFactory.getFileStorage();
        FileInfo fileInfo = fileStorage.putInputStream(path, inputStream);
        return fileInfo.getUri();
    }

    @Override
    public void download(String path, OutputStream outputStream) throws IOException {
        FileStorage fileStorage = fileStorageFactory.getFileStorage();
        Optional<InputStream> optional = fileStorage.getStream(path);
        if (!optional.isPresent()) {
            throw new FileNotFoundException(path);
        }
        try (InputStream inputStream = optional.get()) {
            IOUtils.copy(inputStream, outputStream, 1024);
        }
    }

    @Override
    public boolean delete(String path) throws IOException {
        FileStorage fileStorage = fileStorageFactory.getFileStorage();
        return fileStorage.delete(path);
    }

    @Override
    public boolean delete(URI uri) throws IOException {
        FileStorage fileStorage = fileStorageFactory.getFileStorage();
        return fileStorage.delete(uri);
    }
}
