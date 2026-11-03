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
import com.aliyun.dataworks_public20240518.Client;
import com.aliyun.dataworks_public20240518.models.GetProjectRequest;
import com.aliyun.dataworks_public20240518.models.GetProjectResponse;
import com.aliyun.dataworks_public20240518.models.ListProjectsRequest;
import com.aliyun.dataworks_public20240518.models.ListProjectsResponse;
import com.aliyun.teaopenapi.models.Config;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebLog
@AnonymousAccess
@RestController
@AllArgsConstructor
@ApiResponseWrapper
@RequestMapping("/api/carp/odps/dataworks-public20240518")
@Tag(name = "ODPS管理-DataWorks-Public20240518管理")
public class CarpOdpsDataWorksPublic20240518Controller {

    private static final String accessId = "my_access_id";;
    private static final String accessKey = "my_access_key";
    private static final String regionId = "cn-hangzhou";

    public static Client getClient() throws Exception {
        Config config = new Config();
        config.setAccessKeyId(accessId);
        config.setAccessKeySecret(accessKey);
        config.setRegionId(regionId);
        return new Client(config);
    }

    @GetMapping("projects")
    @Operation(summary = "查询-项目列表", description = "查询-项目列表")
    public ListProjectsResponse listProjects() throws Exception {
        Client client = getClient();
        ListProjectsRequest request = new ListProjectsRequest();
        return client.listProjects(request);
    }

    @GetMapping("projects/{projectId}")
    @Operation(summary = "查询-项目详情", description = "查询-项目详情")
    public GetProjectResponse getProject(@PathVariable("projectId") Long projectId) throws Exception {
        Client client = getClient();
        GetProjectRequest request = new GetProjectRequest();
        request.setId(projectId);
        return client.getProject(request);
    }

}
