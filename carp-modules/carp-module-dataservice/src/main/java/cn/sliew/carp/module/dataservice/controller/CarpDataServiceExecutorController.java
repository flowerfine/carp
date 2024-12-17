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

package cn.sliew.carp.module.dataservice.controller;

import cn.sliew.carp.framework.common.model.IdParam;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.dataservice.service.CarpDataServiceExecutorService;
import cn.sliew.carp.module.dataservice.service.param.ExecuteParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/data-service/executor")
@Tag(name = "数据服务模块-服务管理")
public class CarpDataServiceExecutorController {

    @Autowired
    private CarpDataServiceExecutorService carpDataServiceExecutorService;

    @PostMapping("deploy")
    @Operation(summary = "部署", description = "部署")
    public void deploy(@Valid @RequestBody IdParam param) {
        carpDataServiceExecutorService.deploy(param.getId());
    }

    @PostMapping("stop")
    @Operation(summary = "下线", description = "下线")
    public void stop(@Valid @RequestBody IdParam param) {
        carpDataServiceExecutorService.stop(param.getId());
    }

    @PostMapping("execute")
    @Operation(summary = "执行", description = "执行")
    public Object execute(@Valid @RequestBody ExecuteParam param) {
        return carpDataServiceExecutorService.execute(param);
    }
}
