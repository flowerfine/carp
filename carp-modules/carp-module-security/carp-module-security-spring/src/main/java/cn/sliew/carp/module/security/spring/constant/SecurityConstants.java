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

package cn.sliew.carp.module.security.spring.constant;

public enum SecurityConstants {
    ;

    public static final String REDIS_ONLINE_TOKEN_KEY = "online-token:";

    public static final String TOKEN_KEY = "u_token";

    public static final String ROLE_AUTHORITY_PREFIX = "ROLE_";
    public static final String ROLE_SYS_SUPER_ADMIN = "ROLE_SYS_SUPER_ADMIN";

    public static final Integer COOKIE_MAX_AGE = 24 * 60 * 60 * 3;
    // 立即删除
    public final static Integer COOKIE_MAX_AGE_CLEAR_IMMEDIATELY_REMOVE = 0;
    public static final String COOKIE_PATH = "/";

    /**
     * 浏览器关闭时自动删除
     */
    public final static int COOKILE_CLEAR_BROWSER_IS_CLOSED = -1;


}
