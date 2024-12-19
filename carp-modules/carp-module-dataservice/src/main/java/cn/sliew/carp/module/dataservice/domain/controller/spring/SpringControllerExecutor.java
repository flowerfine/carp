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

import cn.hutool.extra.spring.SpringUtil;
import cn.sliew.carp.module.dataservice.domain.controller.ControllerExecutor;
import cn.sliew.carp.module.dataservice.service.param.ExecuteParam;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springdoc.core.service.OpenAPIService;
import org.springdoc.core.service.OperationService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class SpringControllerExecutor implements ControllerExecutor, InitializingBean {

    private ConcurrentMap<String, RequestMappingInfo> registry = new ConcurrentHashMap<>();

    @Autowired
    private OpenAPIService openAPIService;
    @Autowired
    private OperationService operationService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("OpenAPIService: " + openAPIService);
        System.out.println("OperationService: " + operationService);
    }

    @Override
    public void register(String id, String path) {
        RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtil.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);

        SpringDynamicRouteController dynamicRouteController = SpringUtil.getBean(SpringDynamicRouteController.class);
        RequestMappingInfo mappingInfo = RequestMappingInfo
                .paths(SpringDynamicRouteController.PATH_REFIX + path)
                .methods(RequestMethod.POST)
                .build();
        registry.put(id, mappingInfo);

        Method method = MethodUtils.getAccessibleMethod(SpringDynamicRouteController.class, "execute", HttpServletRequest.class, ExecuteParam.class);
        requestMappingHandlerMapping.registerMapping(mappingInfo, dynamicRouteController, method);


    }

    @Override
    public void unregister(String id) {
        RequestMappingHandlerMapping requestMappingHandlerMapping = SpringUtil.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        requestMappingHandlerMapping.unregisterMapping(registry.remove(id));
    }
}
