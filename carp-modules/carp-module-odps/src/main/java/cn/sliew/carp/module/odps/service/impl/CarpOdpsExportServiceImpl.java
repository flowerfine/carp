package cn.sliew.carp.module.odps.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.sliew.carp.framework.common.nio.FileUtil;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.odps.config.MybatisUtil;
import cn.sliew.carp.module.odps.config.OdpsDataSourceConfig;
import cn.sliew.carp.module.odps.service.CarpOdpsExportService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CarpOdpsExportServiceImpl implements CarpOdpsExportService {

    @Autowired
    @Qualifier(OdpsDataSourceConfig.SQL_SESSION_FACTORY)
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void export() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> doExport());
        future.whenComplete(((unused, throwable) -> {
            if (throwable != null) {
                log.error(throwable.getMessage(), throwable);
            }
        }));
    }

    private void doExport() {
        try {
            String sql = loadSql();
            if (StringUtils.isBlank(sql)) {
                throw new RuntimeException("sql must not be null");
            }
            log.info("导出 sql: {}", sql);
            HashMap<String, Object> params = new HashMap<>();
            params.put("sql", sql);
            CompletableFuture<Void> callback = new CompletableFuture<>();
            Cursor<Map> cursor = MybatisUtil.getCursor(sqlSessionFactory,
                "cn.sliew.carp.module.odps.repository.mapper.CarpOdpsExportMapper.export", params, callback);

            CsvMapper csvMapper = new CsvMapper();
            // Prevent Jackson's writeValue() method calls from closing the stream.
            csvMapper.getFactory().disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

            Path file = FileUtil.createFile(Paths.get("/Users/mac/Downloads/"), UUIDUtil.randomUUId() + ".csv");
            log.info("导出文件: {}", file.toUri());
            OutputStream outputStream = Files.newOutputStream(file, StandardOpenOption.APPEND);
            ObjectWriter writer = null;

            Long count = 0L;
            boolean initilized = false;
            for (Map data : cursor) {
                count++;
                if (!initilized) {
                    CsvSchema schema = buildCsvSchema(data);
                    writer = csvMapper.writer(schema);
                    writeCharset(outputStream);
                    writeHeader(writer, outputStream, schema);
                    initilized = true;
                }
                write(writer, outputStream, data);
            }
            log.info("导出完成, 总数: {}", count);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CsvSchema buildCsvSchema(Map<String, Object> data) {
        CsvSchema.Builder builder = CsvSchema.builder();
        //        for (String key : data.keySet()) {
        //            builder.addColumn(key);
        //        }

        String[] headers =
            new String[] {"日期", "游戏名称", "数据源", "dau", "搜索请求发起事件uv", "筛选请求发起事件uv", "搜筛事件uv",
                "商列搜索页面uv", "商列搜索页面搜筛事件uv", "商列搜索页面商品点击uv",
                "商详点击uv", "商详点击后24h内下单uv", "商详点击后24h内支付uv", "商详点击后48h内完结uv",
                "商详点击后48h内完结单量", "商详咨询点击uv", "商详咨询点击后24h内下单uv", "商详咨询点击后24h内支付uv",
                "商详咨询点击后48h内完结uv", "商详咨询点击后48h内完结单量", "商详还价点击uv",
                "商详还价点击后24h内下单uv", "商详还价点击后24h内支付uv", "商详还价点击后48h内完结uv",
                "商详还价点击后48h内完结单量", "商详收藏点击uv", "商详收藏点击后24h内下单uv",
                "商详收藏点击后24h内支付uv", "商详收藏点击后48h内完结uv", "商详收藏点击后48h内完结单量"};
        for (String header : headers) {
            builder.addColumn(header);
        }

        return builder.build();
    }

    private void write(ObjectWriter writer, OutputStream outputStream, Map data) throws IOException {
        Map newData = new HashMap();
        data.forEach((key, value) -> {
            if (Objects.nonNull(value) && value instanceof Date) {
                String newValue = DateUtil.format((Date)value, DatePattern.NORM_DATETIME_PATTERN);
                newData.put(key, newValue);
            } else if (Objects.nonNull(value) && value instanceof java.sql.Date) {
                String newValue = DateUtil.format((java.sql.Date)value, DatePattern.NORM_DATETIME_PATTERN);
                newData.put(key, newValue);
            } else {
                newData.put(key, value);
            }
        });
        writer.writeValue(outputStream, newData);
    }

    private void writeCharset(OutputStream outputStream) throws IOException {
        outputStream.write(new byte[] {(byte)0xEF, (byte)0xBB, (byte)0xBF});
    }

    private void writeHeader(ObjectWriter writer, OutputStream outputStream, CsvSchema csvSchema) throws IOException {
        List<String> headers = new LinkedList<>();
        for (CsvSchema.Column column : csvSchema) {
            headers.add(column.getName());
        }
        writer.writeValue(outputStream, headers);
    }

    private String loadSql() {
        ClassPathResource classPathResource = new ClassPathResource("sql/odps.sql");
        if (classPathResource.exists() == false) {
            return null;
        }

        try (InputStream inputStream = classPathResource.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            StreamUtils.copy(inputStream, outputStream);
            return new String(outputStream.toByteArray());
        } catch (IOException e) {
            log.error("导出异常", e);
            throw new RuntimeException(e);
        }
    }
}
