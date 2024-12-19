package cn.sliew.carp.module.dataservice.controller;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.SpringDocCustomizers;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.service.OpenAPIService;
import org.springdoc.webmvc.api.OpenApiActuatorResource;
import org.springdoc.webmvc.api.OpenApiResource;
import org.springdoc.webmvc.api.OpenApiWebMvcResource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@AnonymousAccess
@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/data-service/open-api")
@Tag(name = "数据服务模块-OpenAPI管理")
public class CarpOpenApiController implements InitializingBean {

    private Map<String, OpenApiResource> groupedOpenApiResources;

    @Autowired
    private List<GroupedOpenApi> groupedOpenApis;
    @Autowired
    private SpringDocConfigProperties springDocConfigProperties;
    @Autowired
    private OpenAPIService openAPIService;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @GetMapping("getMappingsMap")
    @Operation(summary = "部署", description = "部署")
    public Map<String, Object> getMappingsMap() {
        OpenAPI cachedOpenAPI = openAPIService.getCachedOpenAPI(Locale.getDefault());
        if (Objects.isNull(cachedOpenAPI)) {
            cachedOpenAPI = openAPIService.build(Locale.getDefault());
        }
        Map<String, Object> mappingsMap = openAPIService.getMappingsMap();
        for (Map.Entry<String, Object> mappings : mappingsMap.entrySet()) {
            System.out.println(mappings.getKey() + ": " + mappings.getValue());
        }
        return null;
    }

}
