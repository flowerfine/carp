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

package cn.sliew.carp.module.excel.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.sliew.carp.framework.exception.SliewException;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.milky.common.util.JacksonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.sliew.carp.module.excel.service.param.UploadParam;

import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/excel/import")
@Tag(name = "Excel模块-导入管理")
public class ExcelImportController {

    @PutMapping("upload")
    @Operation(summary = "上传", description = "上传")
    public void upload(@RequestParam("files") MultipartFile[] files, @Valid UploadParam param) throws IOException {
        if (ArrayUtil.isEmpty(files)) {
            throw new IllegalStateException("上传文件为空");
        }

        for (MultipartFile file : files) {
            param.setFileName(file.getOriginalFilename());
            param.setNamespace("default");
            param.setApp("default");
            param.setEnv("default");
            param.setTenant("default");

        }
    }

    @GetMapping("download")
    @Operation(summary = "下载", description = "下载")
    public void download(@RequestParam("id") Long id, HttpServletResponse response) {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
//            param.setUserId("data-center-userId");
//            param.setFileStream(outputStream);
//            DubboResult<String> dubboResult = uploadService.download(param);
            response.setContentType("application/vnd.ms-excel");// 设置文本内省
            response.setCharacterEncoding("utf-8");// 设置字符编码
//            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(dubboResult.getData(), "UTF-8")); // 设置响应头
        } catch (Exception e) {
//            log.error("下载上传的文件失败！param: {}", JacksonUtil.toJsonString(param), e);
            throw new SliewException(e);
        }
    }
}
