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

package cn.sliew.carp.module.security.spring.web;

import cn.sliew.carp.framework.redis.RedissonUtil;
import cn.sliew.carp.module.security.core.service.SecAuthenticationService;
import cn.sliew.carp.module.security.core.service.SecUserService;
import cn.sliew.carp.module.security.core.service.dto.OnlineUserVO;
import cn.sliew.carp.module.security.core.util.CarpSecurityContext;
import cn.sliew.carp.module.security.spring.authentication.CarpUserDetail;
import cn.sliew.carp.module.security.spring.constant.SecurityConstants;
import cn.sliew.carp.module.security.spring.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class CarpTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedissonUtil redisUtil;
    @Autowired
    private SecUserService secUserService;
    @Autowired
    private SecAuthenticationService secAuthenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = SecurityUtil.resolveToken(request);

        Long userId = (Long) redisUtil.get(SecurityConstants.REDIS_ONLINE_TOKEN_KEY + token);
        if (userId != null) {
            OnlineUserVO onlineUser = secAuthenticationService.getOnlineUser(userId);
            onlineUser.setToken(token);
            if (onlineUser != null) {
                // 打通 spring security 的 authen 机制
                Authentication authentication = getAuthentication(onlineUser);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 打通 carp 自己的
                CarpSecurityContext.set(onlineUser);
            }
        }
        chain.doFilter(request, response);
        CarpSecurityContext.clear();
    }

    private Authentication getAuthentication(OnlineUserVO onlineUser) {
        if (onlineUser == null) {
            return null;
        }
        CarpUserDetail principal = new CarpUserDetail();
        principal.setUser(secUserService.get(onlineUser.getUserId()));
        principal.setRoles(onlineUser.getRoles());
        principal.setResourceWebs(onlineUser.getResourceWebs());
        // todo 增加 authority 转换
        principal.setAuthorities(Collections.emptyList());
        return new UsernamePasswordAuthenticationToken(principal, onlineUser.getToken(),
                principal.getAuthorities());
    }
}
