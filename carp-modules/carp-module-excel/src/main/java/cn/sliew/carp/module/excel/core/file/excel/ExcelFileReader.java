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

package cn.sliew.carp.module.excel.core.file.excel;

import cn.sliew.carp.module.excel.core.file.FileReader;
import cn.sliew.carp.module.excel.core.model.ExcelCell;
import cn.sliew.carp.module.excel.core.model.ExcelSheet;
import cn.sliew.milky.common.util.JacksonUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ExcelFileReader implements FileReader {

    private List<ExcelReadListener> readListeners = Lists.newArrayList();

    @Override
    public void read(InputStream inputStream) {
        ExcelReader excelReader = EasyExcel.read(inputStream).excelType(ExcelTypeEnum.XLSX).build();
        List<ReadSheet> readSheets = excelReader.excelExecutor().sheetList();

        List<ReadSheet> sheetsNeedReads = Lists.newArrayList();

        int sheetIndex = 0;
        for (ReadSheet readSheet : readSheets) {
            String sheetName = readSheet.getSheetName();

            ExcelReadListener readListener = new ExcelReadListener(ageiPort, fileContext, columnHeaders);
            readListeners.add(readListener);
            readSheet.setCustomReadListenerList(Lists.newArrayList(readListener));
            Integer headerRowCount = columnHeaders.getHeaderRowCount(sheetIndex);
            readSheet.setHeadRowNumber(headerRowCount);
            sheetsNeedReads.add(readSheet);
            sheetIndex++;
        }

        excelReader.read(sheetsNeedReads);
    }


    @Getter
    @Setter
    public static class ExcelReadListener extends AnalysisEventListener<Map<Integer, Object>> {

        private CountDownLatch countDownLatch;
        private ExcelSheet sheet;

        @Override
        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
            if (sheet == null) {
                sheet = new ExcelSheet();
            }
            sheet.setSheetNo(context.readSheetHolder().getSheetNo());
            sheet.setSheetName(context.readSheetHolder().getSheetName());
            Map<Integer, List<String>> columnHeaderNames = Maps.newHashMap();
            for (Map.Entry<Integer, String> entry : headMap.entrySet()) {
                columnHeaderNames.computeIfAbsent(entry.getKey(), key -> Lists.newArrayList()).add(entry.getValue());
            }
            sheet.setColumnHeadNames(columnHeaderNames);
        }

        @Override
        public void invoke(Map<Integer, Object> data, AnalysisContext context) {
            Integer rowIndex = context.readRowHolder().getRowIndex();

            Map<String, Object> line = new HashMap<>(data.size() * 2);
            for (Map.Entry<Integer, Object> entry : data.entrySet()) {
                Integer column = entry.getKey();
                List<String> headers = sheet.getColumnHeadNames().get(column);
                ColumnHeader columnHeader = columnHeaders.getColumnHeaderByHeaderName(headers);
                if (columnHeader != null) {
                    String fieldName = columnHeader.getFieldName();
                    if (columnHeader.getDynamicColumn()) {
                        Object o = line.get(fieldName);
                        if (o == null) {
                            line.put(fieldName, new HashMap<>());
                        } else if (o instanceof Map) {
                            Map map = (Map) o;
                            map.put(columnHeader.getDynamicColumnKey(), entry.getValue());
                        }
                    } else {
                        line.put(fieldName, entry.getValue());
                    }
                }

                ExcelCell cell = ExcelCell.builder()
                        .rowIndex(rowIndex)
                        .columnIndex(entry.getKey())
                        .headerName("")
                        .fieldName("")
                        .value(entry.getValue())
                        .build();
                sheet.addCell(cell);
            }


            DataGroup.Item item = new DataGroup.Item();
            String sheetName = context.readSheetHolder().getSheetName();
            String sheetNo = context.readSheetHolder().getSheetNo().toString();
            String groupName = sheetName + "@" + sheetNo;
            String code = groupName + "@" + rowIndex;
            item.setCode(code);
            item.setValues(line);

            this.uploadData.getItems().add(item);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            Map<String, String> meta = this.uploadData.getMeta();
            meta.put(ExcelConstants.sheetUploadHeaders, JacksonUtil.toJsonString(columnHeaderNames));
            countDownLatch.countDown();
        }

        public DataGroup.Data getData() {
            try {
                this.countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return uploadData;
        }
    }
}
