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

package cn.sliew.carp.example.ageiport.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.sliew.carp.example.ageiport.util.DataFakerUtil;
import cn.sliew.carp.processor.core.model.UserData;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api/carp/example/ageiport/easyexcel")
@Tag(name = "测试模块-EasyExcel功能")
public class EasyExcelController {

    @GetMapping
    @Operation(summary = "测试流式导出", description = "测试流式导出")
    public void testStreamExport() throws Exception {
        File file = FileUtil.file(FileUtil.getUserHomePath() + "/Downloads/export/" + UUID.fastUUID().toString(true) + ".xlsx");
        if (FileUtil.exist(file) == false) {
            file.createNewFile();
        }

//        EasyExcel.write(file).head(UserData.class).sheet(1).doWrite(DataFakerUtil.generateList(5));
        // 这种指定 sheet 的方式必须执行 finish() 方法。上面的 doWrite() 方法内部会自己执行 finish() 方法
        try (ExcelWriter excelWriter = EasyExcel.write(file).head(UserData.class).inMemory(false).autoCloseStream(true).build()) {
            // excel 单个 sheet 最多可写入 1048576 条数据。超出后需重新写
            for (int i = 1 ; i <= 2; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i, "测试" + i).head(UserData.class).build();
                doWriteSheet(excelWriter, writeSheet);
            }
        }
    }

    private void doWriteSheet(ExcelWriter excelWriter, WriteSheet writeSheet) {
        for (int i = 0; i < 50; i++) {
            excelWriter.write(DataFakerUtil.generateList(10000), writeSheet);
        }
    }

}
