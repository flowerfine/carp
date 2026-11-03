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
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    private static final String accessId = "my_access_key";
    private static final String accessKey = "my_access_key";
    private static final String endPoint = "service.cn-hangzhou.maxcompute.aliyun.com";

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
            if (StringUtils.isBlank(response.getNextPageToken())) {
                break;
            }
        }
        return projects;
    }

    @GetMapping("projects/{projectId}")
    @Operation(summary = "查询-项目详情", description = "查询-项目详情")
    public Project getProject(@PathVariable("projectId") String projectId) throws Exception {
        Client client = getClient();
        return client.getProject(projectId, null);
    }

    @GetMapping("schemas")
    @Operation(summary = "查询-Schema列表", description = "查询-Schema列表，只有开启三级项目的才可以")
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
            if (StringUtils.isBlank(response.getNextPageToken())) {
                break;
            }
        }
        return schemas;
    }

    @GetMapping("tables")
    @Operation(summary = "查询-Table列表", description = "查询-Table列表")
    public List<Table> listTables(@RequestParam(value = "projectId") String projectId,
                                  @RequestParam(value = "schemaName", defaultValue = "default", required = false) String schemaName,
                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                  @RequestParam(value = "pageToken", required = false) String pageToken) throws Exception {
        Client client = getClient();
        String view = null;
        String query = null;
        ListTablesResponse response = client.listTables(projectId, schemaName, pageSize, pageToken, view, query);
        System.out.println(JacksonUtil.toJsonString(response));
        return response.getTables();
    }
}
