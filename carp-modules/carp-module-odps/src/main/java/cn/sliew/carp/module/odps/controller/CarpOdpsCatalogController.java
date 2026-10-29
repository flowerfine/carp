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

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.log.web.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.milky.common.util.JacksonUtil;
import com.aliyun.odps.catalog.Client;
import com.aliyun.odps.catalog.models.*;
import com.aliyun.odps.models.Config;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@WebLog
@AnonymousAccess
@RestController
@AllArgsConstructor
@ApiResponseWrapper
@RequestMapping("/api/carp/odps/catalog")
@Tag(name = "ODPS管理-Catalog管理")
public class CarpOdpsCatalogController {

    private static final String accessId = "my_access_id";
    private static final String accessKey = "my_access_key";
    private static final String endPoint = "service.cn-hangzhou.maxcompute.aliyun.com";
//    private static final String endPoint = "http://service.odps.aliyun.com/api";

    public static Client getClient() throws Exception {
        Config config = new Config();
        config.setAccessKeyId(accessId);
        config.setAccessKeySecret(accessKey);
        config.setOdpsEndpoint(endPoint);
        return new Client(config);
    }

    @GetMapping("projects")
    @Operation(summary = "查询-项目列表", description = "查询-项目列表")
    public List<Project> listProjects() throws Exception {
        Client client = getClient();

        List<Project> projects = new ArrayList<>();

        Integer pageSize = 10;
        String pageToken = null;
        while (true) {
            ListProjectsResponse response = client.listProjects(pageSize, pageToken);
            System.out.println(JacksonUtil.toJsonString(response));
            pageToken = response.getNextPageToken();
            projects.addAll(response.getProjects());
            if (CollectionUtils.isEmpty(response.getProjects())) {
                break;
            }
        }
        return projects;
    }

    @GetMapping("schemas")
    @Operation(summary = "查询-Schema列表", description = "查询-Schema列表")
    public List<Schema> listSchemas(@RequestParam("projectId") String projectId) throws Exception {
        Client client = getClient();

        List<Schema> schemas = new ArrayList<>();

        Integer pageSize = 10;
        String pageToken = null;
        while (true) {
            ListSchemasResponse response = client.listSchemas(projectId, pageSize, pageToken);
            System.out.println(JacksonUtil.toJsonString(response));
            pageToken = response.getNextPageToken();
            schemas.addAll(response.getSchemas());
            if (CollectionUtils.isEmpty(response.getSchemas())) {
                break;
            }
        }
        return schemas;
    }

    @GetMapping("tables")
    @Operation(summary = "查询-Table列表", description = "查询-Table列表")
    public List<Table> listTables(@RequestParam("projectId") String projectId, @RequestParam("schemaName") String schemaName) throws Exception {
        Client client = getClient();

        List<Table> tables = new ArrayList<>();

        Integer pageSize = 10;
        String pageToken = null;
        String view = null;
        String query = null;
        while (true) {
            ListTablesResponse response = client.listTables(projectId, schemaName, pageSize, pageToken, view, query);
            System.out.println(JacksonUtil.toJsonString(response));
            pageToken = response.getNextPageToken();
            tables.addAll(response.getTables());
            if (CollectionUtils.isEmpty(response.getTables())) {
                break;
            }
        }
        return tables;
    }
}
