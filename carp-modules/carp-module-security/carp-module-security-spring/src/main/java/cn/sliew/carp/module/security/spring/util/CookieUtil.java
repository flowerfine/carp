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

import cn.sliew.carp.module.security.spring.constant.SecurityConstants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public enum CookieUtil {
    ;

    public static Cookie findCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(cookieName) && StringUtils.isNotBlank(cookie.getValue())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static void addCookie(HttpServletResponse response, String token) {
        addCookie(response, SecurityConstants.COOKIE_PATH, SecurityConstants.TOKEN_KEY, token, SecurityConstants.COOKIE_MAX_AGE, true, true);
    }

    public static void refreshCookieMaxAge(HttpServletRequest request, HttpServletResponse response) {
        refreshCookieMaxAge(request, response, SecurityConstants.TOKEN_KEY, SecurityConstants.COOKIE_MAX_AGE, SecurityConstants.COOKIE_PATH);
    }

    public static void clearCookieByName(HttpServletRequest request, HttpServletResponse response) {
        clearCookieByName(request, response, SecurityConstants.TOKEN_KEY);
    }

    private static void refreshCookieMaxAge(HttpServletRequest request,
                                            HttpServletResponse response,
                                           String cookieName,
                                           int expireTime,
                                           String path) {
        Cookie cookie = findCookieByName(request, cookieName);
        if (cookie != null) {
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            cookie.setMaxAge(expireTime);
            cookie.setSecure(true);
            addHttpOnlyCookie(response, cookie);
        }
    }

    private static boolean clearCookieByName(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie cookie = findCookieByName(request, cookieName);
        if (cookie != null) {
            cookie.setValue("");
            cookie.setMaxAge(SecurityConstants.COOKIE_MAX_AGE_CLEAR_IMMEDIATELY_REMOVE);
            response.addCookie(cookie);
            return true;
        }
        return false;
    }

    private static void addCookie(HttpServletResponse response,
                                 String path,
                                 String name,
                                 String value,
                                 int maxAge,
                                 boolean httpOnly,
                                 boolean secured) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);

        /** Cookie 只在Https协议下传输设置 */
        if (secured) {
            cookie.setSecure(secured);
        }

        /** Cookie 只读设置 */
        if (httpOnly) {
            addHttpOnlyCookie(response, cookie);
        } else {
            /**
             * servlet 3.0 support
             * cookie.setHttpOnly(httpOnly)
             */
            response.addCookie(cookie);
        }
    }

    /**
     * 解决 servlet 3.0 以下版本不支持 HttpOnly
     */
    private static void addHttpOnlyCookie(HttpServletResponse response, Cookie cookie) {
        if (cookie == null) {
            return;
        }
        /**
         * 依次取得cookie中的名称、值、 最大生存时间、路径、域和是否为安全协议信息
         */
        String cookieName = cookie.getName();
        String cookieValue = cookie.getValue();
        int maxAge = cookie.getMaxAge();
        String path = cookie.getPath();
        String domain = cookie.getDomain();
        boolean isSecure = cookie.getSecure();
        StringBuffer sf = new StringBuffer();
        sf.append(cookieName + "=" + cookieValue + ";");
        if (maxAge >= 0) {
            sf.append("Max-Age=" + cookie.getMaxAge() + ";");
        }
        if (domain != null) {
            sf.append("domain=" + domain + ";");
        }
        if (path != null) {
            sf.append("path=" + path + ";");
        }
        if (isSecure) {
            sf.append("secure;HTTPOnly;");
        } else {
            sf.append("HTTPOnly;");
        }
        sf.append("SameSite=None");
        response.addHeader("Set-Cookie", sf.toString());
    }
}
