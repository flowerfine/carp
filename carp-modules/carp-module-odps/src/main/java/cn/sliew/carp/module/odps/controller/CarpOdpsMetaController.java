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
package cn.sliew.carp.module.odps.controller;

import cn.sliew.carp.framework.common.nio.FileUtil;
import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.log.web.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import com.aliyun.odps.*;
import com.aliyun.odps.account.Account;
import com.aliyun.odps.account.AliyunAccount;
import com.aliyun.odps.type.TypeInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

@WebLog
@AnonymousAccess
@RestController
@AllArgsConstructor
@ApiResponseWrapper
@RequestMapping("/api/carp/odps/meta")
@Tag(name = "ODPS管理-Meta管理")
public class CarpOdpsMetaController {

    private static final String accessId = "my_access_id";
    private static final String accessKey = "my_access_key";
    private static final String endPoint = "http://service.odps.aliyun.com/api";

    public static Odps getConnection(String projectName) {
        Account account = new AliyunAccount(accessId, accessKey, "cn-hangzhou");

        Odps odps = new Odps(account);
        odps.setEndpoint(endPoint);
        if (StringUtils.isNotBlank(projectName)) {
            odps.setDefaultProject(projectName);
        }
        return odps;
    }

    @GetMapping("projects")
    @Operation(summary = "查询-项目列表", description = "查询-项目列表")
    public void listProjects() throws Exception {
        Odps odps = getConnection("my_project");

        Projects projects = odps.projects();
        Project project = projects.get();
//        String regionId = project.getRegionId();
//        ProjectFilter projectFilter = new ProjectFilter();
//        projectFilter.setRegionId(regionId);
//        Iterator<Project> projectIterator = projects.iteratorByFilter(projectFilter);
//        while (projectIterator.hasNext()) {
//            Project next = projectIterator.next();
//            handleProject(odps, next);
//        }
        handleProject(odps, project);
    }

    private static void handleProject(Odps odps, Project project) {

        try {
            System.out.println("开始处理: " + project.getName());
            Path path = FileUtil.createFile(Paths.get("/Users/mac/sql/temp"), project.getName() + ".sql");
            File file = path.toFile();

            Tables tables = odps.tables();
            Iterator<Table> iterator = tables.iterator(project.getName());
            while (iterator.hasNext()) {
                Table table = iterator.next();
                cn.hutool.core.io.FileUtil.appendUtf8String(buildTable(table), file);
            }
            System.out.println("处理完成: " + project.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String buildTable(Table table) {
        StringBuilder ddl = new StringBuilder();
        ddl.append("create table if not exists ");
        String project = table.getProject();
        if (StringUtils.isNotBlank(table.getSchemaName())) {
            String tableName = String.format("%s.%s.%s", project, table.getSchemaName(), table.getName());
            ddl.append(tableName);
        } else {
            String tableName = String.format("%s.%s", project, table.getName());
            ddl.append(tableName);
        }
        ddl.append(" (");

        String tableId = table.getTableID();
        String comment = table.getComment();

        String jsonSchema = table.getJsonSchema();
        String metadataJson = table.getMetadataJson();
        List<String> primaryKey = table.getPrimaryKey();

        TableSchema schema = table.getSchema();

        for (Column column : schema.getColumns()) {
            ddl.append("\n    ");
            ddl.append(buildColumn(column));
            ddl.append(",");
        }
        ddl.deleteCharAt(ddl.length() - 1);

        ddl.append("\n)\n");
        if (StringUtils.isNotBlank(comment)) {
            ddl.append("comment '" + comment + "'\n");
        }
        List<Column> partitionColumns = schema.getPartitionColumns();
        if (CollectionUtils.isNotEmpty(partitionColumns)) {
            ddl.append("partitioned by (");
            for (Column column : partitionColumns) {
                ddl.append("\n    ");
                ddl.append(buildColumn(column));
                ddl.append(",");
            }
            ddl.deleteCharAt(ddl.length() - 1);
            ddl.append("\n)\n");
        }

        ddl.append(";\n");

        return ddl.toString();
    }

    private static String buildColumn(Column column) {
        String name = column.getName();
        String comment = column.getComment();
        TypeInfo typeInfo = column.getTypeInfo();
        OdpsType odpsType = typeInfo.getOdpsType();
        if (StringUtils.isNotBlank(comment)) {
            return String.format("%s %s comment '%s'", name, typeInfo.getTypeName(), comment);
        } else {
            return String.format("%s %s", name, typeInfo.getTypeName());
        }
    }
}
