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

import cn.hutool.core.util.StrUtil;
import cn.sliew.carp.module.security.spring.vo.OnlineUserVO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * token 过滤器
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private OnlineUserService onlineUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = resolveToken(request);

        OnlineUserVO onlineUser = onlineUserService.getOnlineUser(token);
        if (onlineUser != null) {
            long time =
                    onlineUser.getRemember() ? properties.getLongTokenValidityInSeconds() / 1000 :
                            properties.getTokenValidityInSeconds() / 1000;
            Authentication authentication = getAuthentication(onlineUser);
            redisUtil.set(Constants.ONLINE_USER_KEY + onlineUser.getUserName(), onlineUser.getToken(), time);
            redisUtil.set(Constants.ONLINE_TOKEN_KEY + onlineUser.getToken(), onlineUser, time);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    /**
     * 获取请求header中的token数据
     */
    private String resolveToken(HttpServletRequest request) {
        String headerToken = request.getHeader(Constants.TOKEN_KEY);
        String paramToken = request.getParameter(Constants.TOKEN_KEY);
        return StrUtil.isEmpty(headerToken) ? paramToken : headerToken;
    }

    /**
     * 获取redis中登录用户的权限信息
     * 后台修改角色权限时会同步更新redis中用户的权限信息为null，这里如果权限为null时则到数据库中再次加载用户的最新权限数据
     *
     * @param onlineUser 在线用户
     * @return Authentication
     */
    private Authentication getAuthentication(OnlineUserVO onlineUser) {
        if (onlineUser == null) {
            return null;
        }
        if (onlineUser.getPrivileges() == null) {
            //从数据库中再次加载用户权限 放入缓存
            OnlineUserVO user = onlineUserService.getAllPrivilegeByToken(onlineUser.getToken());
            List<String> roleList = new ArrayList<>();
            List<String> privilegeList = new ArrayList<>();
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String p : user.getPrivileges()) {
                authorities.add(new SimpleGrantedAuthority(p));
                privilegeList.add(p.toLowerCase());
            }
            for (String r : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(r));
                roleList.add(r.toLowerCase());
            }
            onlineUser.setRoles(roleList);
            onlineUser.setPrivileges(privilegeList);
            // fixme User != UserDetailInfo
            User principal = new User(onlineUser.getUserName(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, onlineUser.getToken(),
                    authorities);
        } else {
            List<GrantedAuthority> authorities = new ArrayList<>();
            List<String> privileges = onlineUser.getPrivileges();
            List<String> roles = onlineUser.getRoles();
            for (String privilege : privileges) {
                authorities.add(new SimpleGrantedAuthority(privilege));
            }
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            User principal = new User(onlineUser.getUserName(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, onlineUser.getToken(),
                    authorities);
        }
    }
}
