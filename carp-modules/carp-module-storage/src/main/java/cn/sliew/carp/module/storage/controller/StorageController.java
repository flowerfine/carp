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
package cn.sliew.carp.module.storage.controller;

import cn.hutool.core.io.FileUtil;
import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.storage.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

@AnonymousAccess
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/storage")
@Tag(name = "存储模块-文件管理")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @GetMapping("files")
    @Operation(summary = "查询-文件列表", description = "查询-文件列表")
    public List<String> list(@RequestParam("path") String path) throws IOException {
        return storageService.list(path);
    }

    @GetMapping("file/uri")
    @Operation(summary = "查询-文件链接", description = "查询-文件链接")
    public URI getUri(@RequestParam("path") String path) throws IOException {
        return storageService.getUri(path);
    }

    @PostMapping("upload")
    @Operation(summary = "上传-文件", description = "上传部署配置")
    public URI upload(@RequestPart("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            return storageService.upload(inputStream, fileName);
        }
    }

    @GetMapping("download/{id}")
    @Operation(summary = "下载-文件", description = "下载-文件")
    public void download(@RequestParam("path") String path, HttpServletResponse response) throws IOException {
        String filename = FileUtil.getName(path);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            storageService.download(path, outputStream);
            response.setCharacterEncoding("utf-8");// 设置字符编码
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8")); // 设置响应头
        }
    }

}
