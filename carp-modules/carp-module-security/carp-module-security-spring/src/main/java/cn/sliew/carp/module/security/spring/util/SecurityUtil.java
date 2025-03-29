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
package cn.sliew.carp.module.security.spring.util;

import cn.sliew.carp.framework.common.util.KeyUtil;
import cn.sliew.carp.module.security.spring.authentication.CarpUserDetail;
import cn.sliew.carp.module.security.spring.constant.SecurityConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;

public enum SecurityUtil {
    ;

    public static CarpUserDetail getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!ObjectUtils.isEmpty(authentication) && authentication.getPrincipal() instanceof CarpUserDetail) {
            return (CarpUserDetail) authentication.getPrincipal();
        }
        return null;
    }

    public static String getCurrentUserName() {
        CarpUserDetail currentUser = getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUsername();
        }
        return null;
    }

    public static Optional<Long> getCurrentUserId() {
        CarpUserDetail userDetailInfo = SecurityUtil.getCurrentUser();
        if (userDetailInfo != null) {
            return Optional.of(userDetailInfo.getUser().getId());
        }
        return Optional.empty();
    }

    public static String buildRedisToken(String token) {
        return KeyUtil.buildCacheKey(SecurityConstants.REDIS_ONLINE_TOKEN_KEY, token);
    }

    public static String resolveToken(HttpServletRequest request) {
        return doResolveToken(request, SecurityConstants.TOKEN_KEY);
    }

    public static String doResolveToken(HttpServletRequest request, String tokenKey) {
        // cookie
        Cookie cookie = CookieUtil.findCookieByName(request, tokenKey);
        if (cookie != null && StringUtils.hasText(cookie.getValue())) {
            return cookie.getValue();
        }
        // header
        String headerToken = request.getHeader(tokenKey);
        if (StringUtils.hasText(headerToken)) {
            return headerToken;
        }
        // query
        String paramToken = request.getParameter(tokenKey);
        if (StringUtils.hasText(paramToken)) {
            return paramToken;
        }
        return null;
    }
}
