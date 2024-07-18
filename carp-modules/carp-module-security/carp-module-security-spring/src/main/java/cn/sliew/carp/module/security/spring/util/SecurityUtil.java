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

    public static String resolveToken(HttpServletRequest request) {
        // header
        String headerToken = request.getHeader(SecurityConstants.TOKEN_KEY);
        if (StringUtils.hasText(headerToken)) {
            return headerToken;
        }
        // query
        String paramToken = request.getParameter(SecurityConstants.TOKEN_KEY);
        if (StringUtils.hasText(paramToken)) {
            return paramToken;
        }
        // cookie
        Cookie cookie = CookieUtil.findCookieByName(request, SecurityConstants.TOKEN_KEY);
        if (cookie != null && StringUtils.hasText(cookie.getValue())) {
            return cookie.getValue();
        }
        return null;
    }
}
