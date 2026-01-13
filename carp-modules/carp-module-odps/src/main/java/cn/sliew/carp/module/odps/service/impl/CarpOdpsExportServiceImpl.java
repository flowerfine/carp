package cn.sliew.carp.module.odps.service.impl;

import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.module.odps.config.MybatisUtil;
import cn.sliew.carp.module.odps.config.OdpsDataSourceConfig;
import cn.sliew.carp.module.odps.service.CarpOdpsExportService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CarpOdpsExportServiceImpl implements CarpOdpsExportService {

    @Autowired
    @Qualifier(OdpsDataSourceConfig.SQL_SESSION_FACTORY)
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void export() {
        try {
            String sql = loadSql();
            HashMap<String, Object> params = new HashMap<>();
            params.put("sql", sql);
            CompletableFuture<Void> callback = new CompletableFuture<>();
            Cursor<Map> cursor = MybatisUtil.getCursor(sqlSessionFactory, "cn.sliew.carp.module.odps.repository.mapper.CarpOdpsExportMapper.export", params, callback);

            CsvMapper csvMapper = new CsvMapper();
            // Prevent Jackson's writeValue() method calls from closing the stream.
            csvMapper.getFactory().disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

            Path path = Paths.get("/Users/mac/Downloads/" + UUIDUtil.randomUUId() + ".csv");
            OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.APPEND);
            ObjectWriter writer = null;

            boolean initilized = false;
            for (Map data : cursor) {
                if (!initilized) {
                    CsvSchema schema = buildCsvSchema(data);
                    writer = csvMapper.writer(schema);
                    writeCharset(outputStream);
                    writeHeader(writer, outputStream, schema);
                    initilized = true;
                }
                write(writer, outputStream, data);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CsvSchema buildCsvSchema(Map<String, Object> data) {
        CsvSchema.Builder builder = CsvSchema.builder();
        for (String key : data.keySet()) {
            builder.addColumn(key);
        }
        return builder.build();
    }

    private void write(ObjectWriter writer, OutputStream outputStream, Map data) throws IOException {
        writer.writeValue(outputStream, data);
    }

    private void writeCharset(OutputStream outputStream) throws IOException {
        outputStream.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
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
