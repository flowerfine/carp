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

package cn.sliew.carp.module.dataservice.domain.controller.spring;

import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.dataservice.service.CarpDataServiceExecutorService;
import cn.sliew.carp.module.dataservice.service.param.ExecuteParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SpringDynamicRouteController.PATH_REFIX)
@ApiResponseWrapper
public class SpringDynamicRouteController {

    public static final String PATH_REFIX = "/api/carp/data-service/route/";

    @Autowired
    private CarpDataServiceExecutorService carpDataServiceExecutorService;

    @ApiResponseWrapper
    public Object execute(HttpServletRequest request, @Valid @RequestBody ExecuteParam param) {
        return carpDataServiceExecutorService.execute(param);
    }
}