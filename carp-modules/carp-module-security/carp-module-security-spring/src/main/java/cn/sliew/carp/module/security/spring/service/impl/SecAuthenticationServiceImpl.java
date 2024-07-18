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

package cn.sliew.carp.module.security.spring.service.impl;

import cn.sliew.carp.framework.common.enums.ResponseCodeEnum;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.framework.exception.SliewException;
import cn.sliew.carp.framework.redis.RedisUtil;
import cn.sliew.carp.framework.web.util.I18nUtil;
import cn.sliew.carp.module.security.core.service.SecAuthenticationService;
import cn.sliew.carp.module.security.core.service.SecCaptchaService;
import cn.sliew.carp.module.security.core.service.SecUserService;
import cn.sliew.carp.module.security.core.service.dto.OnlineUserVO;
import cn.sliew.carp.module.security.core.service.dto.SecUserDTO;
import cn.sliew.carp.module.security.core.service.param.authenticate.LoginParam;
import cn.sliew.carp.module.security.spring.authentication.CarpPasswordEncoder;
import cn.sliew.carp.module.security.spring.authentication.CarpUserDetail;
import cn.sliew.carp.module.security.spring.constant.SecurityConstants;
import cn.sliew.carp.module.security.spring.util.CookieUtil;
import cn.sliew.carp.module.security.spring.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;

@Service
public class SecAuthenticationServiceImpl implements SecAuthenticationService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SecUserService secUserService;
    @Autowired
    private SecCaptchaService secCaptchaService;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public OnlineUserVO login(LoginParam param, HttpServletRequest request, HttpServletResponse response) {
        if (secCaptchaService.verityCaptcha(param.getUuid(), param.getAuthCode()) == false) {
            throw new SliewException(ResponseCodeEnum.ERROR_CUSTOM.getCode(), I18nUtil.get("response.error.authCode"));
        }
        try {
            authenticateForm(param);

            OnlineUserVO onlineUserVO = new OnlineUserVO();
            onlineUserVO.setToken(UUIDUtil.randomUUId());
            CarpUserDetail userDetail = SecurityUtil.getCurrentUser();
            SecUserDTO secUserDTO = userDetail.getUser();
            onlineUserVO.setUserId(secUserDTO.getId());
            onlineUserVO.setUserName(secUserDTO.getUserName());
            onlineUserVO.setNickName(secUserDTO.getNickName());
            onlineUserVO.setType(secUserDTO.getType().getValue());
            onlineUserVO.setStatus(secUserDTO.getStatus().getValue());

            onlineUserVO.setRoles(userDetail.getRoles());
            onlineUserVO.setResourceWebs(userDetail.getResourceWebs());
            //存储信息到redis中
            redisUtil.set(SecurityConstants.REDIS_ONLINE_TOKEN_KEY + onlineUserVO.getToken(), onlineUserVO.getUserId(), Duration.ofHours(12L));
            CookieUtil.addCookie(response, onlineUserVO.getToken());
            return onlineUserVO;
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new SliewException(ResponseCodeEnum.ERROR_CUSTOM.getCode(), I18nUtil.get("response.error.login.password"));
        }
    }

    private void authenticateForm(LoginParam param) {
        Optional<SecUserDTO> optional = secUserService.getByUserName(param.getUserName());
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException(I18nUtil.get("response.error.login.password"));
        }
        // 传递密码和 salt 值
        SecUserDTO secUserDTO = optional.get();
        String rawPassword = param.getPassword() + CarpPasswordEncoder.SPLIT + secUserDTO.getSalt();

        //检查用户名密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(param.getUserName(), rawPassword);
        //spring security框架调用userDetailsService获取用户信息并验证，验证通过后返回一个Authentication对象，存储到线程的SecurityContext中
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.clearCookieByName(request, response);
        String token = SecurityUtil.resolveToken(request);
        redisUtil.remove(SecurityConstants.REDIS_ONLINE_TOKEN_KEY + token);
        return true;
    }

    @Override
    public OnlineUserVO getOnlineUser(Long userId) {
        SecUserDTO secUserDTO = secUserService.get(userId);
        OnlineUserVO onlineUserVO = new OnlineUserVO();
        onlineUserVO.setUserId(secUserDTO.getId());
        onlineUserVO.setUserName(secUserDTO.getUserName());
        onlineUserVO.setNickName(secUserDTO.getNickName());
        onlineUserVO.setType(secUserDTO.getType().getValue());
        onlineUserVO.setStatus(secUserDTO.getStatus().getValue());

        // 查询 roles
        // 查询权限
        onlineUserVO.setRoles(Collections.emptyList());
        onlineUserVO.setResourceWebs(Collections.emptyList());
        return onlineUserVO;
    }
}
