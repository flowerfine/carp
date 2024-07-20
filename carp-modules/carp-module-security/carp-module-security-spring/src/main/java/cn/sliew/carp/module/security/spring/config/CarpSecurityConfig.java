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

package cn.sliew.carp.module.security.spring.config;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.web.util.RequestParamUtil;
import cn.sliew.carp.framework.web.util.SpringContextUtil;
import cn.sliew.carp.module.security.spring.authentication.CarpAccessDeniedHandler;
import cn.sliew.carp.module.security.spring.authentication.CarpAuthenticationEntryPoint;
import cn.sliew.carp.module.security.spring.authentication.CarpPasswordEncoder;
import cn.sliew.carp.module.security.spring.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.ObjectUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class CarpSecurityConfig {

    @Autowired
    private CarpTokenConfigurer carpTokenConfigurer;
    @Autowired
    private CarpAuthenticationEntryPoint carpAuthenticationEntryPoint;
    @Autowired
    private CarpAccessDeniedHandler carpAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        // 禁用cors
        http.csrf(this::csrf);
        // 禁用iframe
        http.headers(this::headers);
        // fixme 表单登陆不能用于前后端分离模式下的登陆
        // fixme 如果要实现前后端分离，使用 json 获取登陆信息，需要自定义拦截器
        http.formLogin(this::formLogin);
        // u_token 认证
        http.apply(carpTokenConfigurer);
        // 请求权限配置
        http.authorizeHttpRequests(this::authorizeRequests);
        // session
        http.sessionManagement(this::sessionManagement);
        http.exceptionHandling(this::exceptionHandling);
        // @formatter:on
        return http.build();
    }

    private void csrf(CsrfConfigurer<HttpSecurity> csrf) {
        csrf.disable();
    }

    private void headers(HeadersConfigurer<HttpSecurity> headers) {
        headers.frameOptions(frameOptions -> frameOptions.disable());
    }

    private void formLogin(FormLoginConfigurer<HttpSecurity> formLogin) {
        formLogin.disable();
    }

    /**
     * spring-security 按照从上往下顺序来匹配，一旦匹配成功则不在匹配
     */
    private void authorizeRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizeHttpRequests) {
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        // 自定义匿名访问url。查找匿名标记的资源
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = applicationContext
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class)
                .getHandlerMethods();
        Set<String> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethodMap.entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (!ObjectUtils.isEmpty(anonymousAccess)) {
                anonymousUrls.addAll(requestMappingInfo.getPatternValues());
            }
        }
        authorizeHttpRequests.requestMatchers(anonymousUrls.toArray(new String[0])).permitAll();
        // 放行endpoint
        authorizeHttpRequests.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll();
        // 静态资源
        authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/**/*.css", "/**/*.js", "/**/*.png",
                "/**/*.woff", "/**/*.woff2", "/**/*.svg", "/**/*.json", "/**/*.ttf", "/**/*.ico",
                "/index.html").permitAll();
        // swagger and ui
        String[] ignorePaths = RequestParamUtil.getDefaultIgnorePaths().stream().toArray(length -> new String[length]);
        authorizeHttpRequests.requestMatchers(ignorePaths).permitAll();
        // 放行options请求
        authorizeHttpRequests.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        authorizeHttpRequests.anyRequest().authenticated();
    }

    private void sessionManagement(SessionManagementConfigurer<HttpSecurity> sessionManagement) {
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void exceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {
        exceptionHandling
                .authenticationEntryPoint(carpAuthenticationEntryPoint)
                .accessDeniedHandler(carpAccessDeniedHandler);
    }

    // 为了解耦密码存储和 spring security
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CarpPasswordEncoder();
    }

    /**
     * fix When allowCredentials is true, allowedOrigins cannot contain the special value "*" since that cannot be set on the "Access-Control-Allow-Origin" response header.
     * To allow credentials to a set of origins, list them explicitly or consider using "allowedOriginPatterns" instead
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "responseType", SecurityConstants.TOKEN_KEY));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
