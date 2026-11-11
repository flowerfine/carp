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
package cn.sliew.carp.module.odps.controller.dataworks;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.log.web.annotation.WebLog;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import com.aliyun.dataworks_public20200518.Client;
import com.aliyun.dataworks_public20200518.models.*;
import com.aliyun.teaopenapi.models.Config;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@WebLog
@AnonymousAccess
@RestController
@AllArgsConstructor
@ApiResponseWrapper
@RequestMapping("/api/carp/odps/dataworks-public/20200518")
@Tag(name = "ODPS管理-DataWorks-Public-20200518管理")
public class CarpOdpsDataWorksPublic20200518Controller {

    private static final String accessId = "my_access_key";;
    private static final String accessKey = "my_access_key";
    private static final String regionId = "cn-hangzhou";

    public static Client getClient() throws Exception {
        Config config = new Config();
        config.setAccessKeyId(accessId);
        config.setAccessKeySecret(accessKey);
        config.setRegionId(regionId);
        return new Client(config);
    }

    @GetMapping("projects/{projectId}/nodes")
    @Operation(summary = "查询-节点列表", description = "查询-节点列表")
    public ListNodesResponse listNodes(@PathVariable("projectId") Long projectId,
                                       @RequestParam(value = "bizName", required = false) String bizName) throws Exception {
        Client client = getClient();
        ListNodesRequest request = new ListNodesRequest();
        request.setProjectId(projectId);
        request.setProjectEnv("PROD");
        request.setBizName(bizName);
        request.setPageNumber(1);
        request.setPageSize(100);
        return client.listNodes(request);
    }

    @GetMapping("projects/nodes/{nodeId}/code")
    @Operation(summary = "查询-节点代码详情", description = "查询-节点代码详情")
    public GetNodeCodeResponse getNodeCode(@PathVariable("nodeId") Long nodeId) throws Exception {
        Client client = getClient();
        GetNodeCodeRequest request = new GetNodeCodeRequest();
        request.setNodeId(nodeId);
        request.setProjectEnv("PROD");
        return client.getNodeCode(request);
    }



}
