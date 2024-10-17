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

import cn.sliew.carp.processor.core.TestHelper;
import cn.sliew.carp.processor.core.exporter.StandaloneExportProcessor;
import cn.sliew.carp.processor.core.model.UserQuery;
import cn.sliew.milky.common.util.JacksonUtil;
import com.alibaba.ageiport.common.utils.JsonUtil;
import com.alibaba.ageiport.processor.core.AgeiPort;
import com.alibaba.ageiport.processor.core.api.http.HttpApiServer;
import com.alibaba.ageiport.processor.core.api.http.HttpApiServerOptions;
import com.alibaba.ageiport.processor.core.spi.api.model.ExecuteMainTaskRequest;
import com.alibaba.ageiport.processor.core.spi.api.model.ExecuteMainTaskResponse;
import com.alibaba.ageiport.processor.core.spi.api.model.GetMainTaskProgressRequest;
import com.alibaba.ageiport.processor.core.spi.api.model.GetMainTaskProgressResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.RequestOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/carp/example/ageiport/http")
@Tag(name = "测试模块-Http功能")
public class HttpController {

    @Autowired
    private AgeiPort ageiPort;

    @GetMapping
    @Operation(summary = "测试", description = "测试")
    public void reqeust() throws Exception {
        UserQuery query = new UserQuery();
        query.setTotalCount(100);
        ExecuteMainTaskRequest executeMainTaskRequest = new ExecuteMainTaskRequest();
        executeMainTaskRequest.setTaskSpecificationCode(StandaloneExportProcessor.class.getSimpleName());
        executeMainTaskRequest.setBizUserId("userId");
        executeMainTaskRequest.setBizQuery(JsonUtil.toJsonString(query));

        RequestOptions requestOptions = buildRequestOptions();
        CompletableFuture<ExecuteMainTaskResponse> executeFuture = new CompletableFuture<>();
        Vertx.vertx().createHttpClient().request(requestOptions, event -> {
            HttpClientRequest clientRequest = event.result();
            String json = JsonUtil.toJsonString(executeMainTaskRequest);
            clientRequest.send(json, event1 -> {
                HttpClientResponse result = event1.result();
                result.body(event2 -> {
                    String jsonString = event2.result().toString();
                    ExecuteMainTaskResponse response = JsonUtil.toObject(jsonString, ExecuteMainTaskResponse.class);
                    executeFuture.complete(response);
                });
            });
        });
        ExecuteMainTaskResponse executeMainTaskResponse = executeFuture.get(3, TimeUnit.SECONDS);
        log.info("future, executeMainTaskRequest: {}, executeMainTaskResponse:{}", JacksonUtil.toJsonString(executeMainTaskRequest), JacksonUtil.toJsonString(executeMainTaskResponse));


        RequestOptions requestOptions2 = buildRequestOptions();
        GetMainTaskProgressRequest getMainTaskProgressRequest = new GetMainTaskProgressRequest();
        getMainTaskProgressRequest.setMainTaskId(executeMainTaskResponse.getMainTaskId());
        CompletableFuture<GetMainTaskProgressResponse> progressFuture = new CompletableFuture<>();
        Vertx.vertx().createHttpClient().request(requestOptions2, event -> {
            HttpClientRequest clientRequest = event.result();
            String json = JsonUtil.toJsonString(getMainTaskProgressRequest);
            clientRequest.send(json, event1 -> {
                HttpClientResponse result = event1.result();
                result.body(event22 -> {
                    final String jsonString = event22.result().toString();
                    GetMainTaskProgressResponse response = JsonUtil.toObject(jsonString, GetMainTaskProgressResponse.class);
                    progressFuture.complete(response);
                });
            });
        });
        GetMainTaskProgressResponse getMainTaskInstanceResponse = progressFuture.get(3, TimeUnit.SECONDS);
        log.info("future, getMainTaskProgressRequest: {}, getMainTaskInstanceResponse: {}", JacksonUtil.toJsonString(getMainTaskProgressRequest), JacksonUtil.toJsonString(getMainTaskInstanceResponse));

        TestHelper testHelper = new TestHelper(ageiPort);
        testHelper.assertWithFile(executeMainTaskResponse.getMainTaskId(), query.getTotalCount());
    }

    private RequestOptions buildRequestOptions() {
        HttpApiServerOptions serverOptions = (HttpApiServerOptions) ageiPort.getOptions().getApiServerOptions();
        return new RequestOptions()
                .setHost("localhost")
                .setURI(HttpApiServer.TASK_EXECUTE_URL)
                .setPort(serverOptions.getPort())
                .setMethod(HttpMethod.POST);
    }
}
