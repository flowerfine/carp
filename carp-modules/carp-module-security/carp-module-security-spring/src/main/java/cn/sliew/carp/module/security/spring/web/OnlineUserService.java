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

import cn.sliew.carp.module.security.core.service.SecRoleService;
import cn.sliew.carp.module.security.core.service.SecUserService;
import cn.sliew.carp.module.security.spring.authentication.CarpUserDetail;
import cn.sliew.carp.module.security.spring.vo.OnlineUserVO;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 用户在线信息service
 */
@Service
public class OnlineUserService {

    @Autowired
    private SecurityProperties properties;
    @Autowired
    private RedissonClient redisUtil;
    @Autowired
    private SecUserService secUserService;
    @Autowired
    private SecRoleService secRoleService;

    /**
     * 存储登录用户信息到redis中
     */
    public void insert(CarpUserDetail userInfo, String token) {

    }

    public OnlineUserVO getOnlineUser(String token) {
        return null;
    }

    /**
     * 登出用户
     */

    public void logoutByUserName(String userName) {

    }

    /**
     * 踢出用户
     */

    public void logoutByToken(String token) {

    }


    public OnlineUserVO getAllPrivilegeByToken(String token) {
        return null;
    }


    @Async
    public void disableOnlineCacheRole(Long roleId) {

    }


    @Async
    public void disableOnlineCacheUser(String userName) {

    }
}
