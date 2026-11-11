///*
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package cn.sliew.carp.module.odps.controller;
//
//import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
//import cn.sliew.carp.framework.log.web.annotation.WebLog;
//import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
//import com.aliyun.odps.Client;
//import com.aliyun.odps.models.Config;
//import com.aliyun.odps.models.OpenApiRequest;
//import com.aliyun.odps.models.Params;
//import com.aliyun.teautil.models.RuntimeOptions;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@WebLog
//@AnonymousAccess
//@RestController
//@AllArgsConstructor
//@ApiResponseWrapper
//@RequestMapping("/api/carp/odps/openapi")
//@Tag(name = "ODPS管理-OpenAPI管理")
//public class CarpOdpsOpenAPIController {
//
//    private static final String accessId = "my_access_key";
//    private static final String accessKey = "my_access_key";
//    private static final String endPoint = "service.cn-hangzhou.maxcompute.aliyun.com";
//
//    public static Client getClient() throws Exception {
//        Config config = new Config();
//        config.setAccessKeyId(accessId);
//        config.setAccessKeySecret(accessKey);
//        config.setRegionId("cn-hangzhou");
//        return new Client(config);
//    }
//
//    @GetMapping("projects")
//    @Operation(summary = "查询-项目列表", description = "查询-项目列表")
//    public Map<String, ?> listProjects() throws Exception {
//        Client client = getClient();
//        Params params = new Params();
//        params.setVersion("2020-05-18");
//        params.setAction("ListProjectIds");
//        params.setMethod("POST");
//        params.setProtocol("HTTPS");
//        params.setPathname("/");
//        OpenApiRequest request = new OpenApiRequest();
//        Map<String, String> headers = new HashMap<>();
//        headers.put("host", endPoint);
//        request.setHeaders(headers);
//        Map<String, String> query = new HashMap<>();
////        query.put("NodeId", PROJECT_ID);
////        query.put("ProjectId", PROJECT_ID);
////        query.put("ProjectEnv", PROJECT_ENV);
//        request.setQuery(query);
//        RuntimeOptions options = new RuntimeOptions();
//        return client.doRequest(params, request, options);
//    }
//
//}
