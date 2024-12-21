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
package cn.sliew.carp.module.security.core.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.RandomUtil;
import cn.sliew.carp.framework.common.util.UUIDUtil;
import cn.sliew.carp.framework.redis.RedissonUtil;
import cn.sliew.carp.module.security.core.service.SecCaptchaService;
import cn.sliew.carp.module.security.core.service.dto.SecCaptchaDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.time.Duration;

@Slf4j
@Service
public class SecCaptchaServiceImpl implements SecCaptchaService {

    public static final String AUTH_CAPTCHA_KEY = "auth-captcha-key:";

    @Autowired
    private RedissonUtil redisUtil;

    @Override
    public SecCaptchaDTO getCaptcha() {
        LineCaptcha lineCaptcha =
                CaptchaUtil.createLineCaptcha(150, 32, 5, RandomUtil.randomInt(6, 10));
        Font font = new Font("Stencil", Font.BOLD + Font.ITALIC, 20);
        lineCaptcha.setFont(font);
        lineCaptcha.setBackground(new Color(246, 250, 254));
        lineCaptcha.createCode();
        String uuid = AUTH_CAPTCHA_KEY + UUIDUtil.randomUUId();
        // 过期时间 10min
        redisUtil.set(uuid, lineCaptcha.getCode(), Duration.ofMinutes(10L));
        SecCaptchaDTO dto = new SecCaptchaDTO();
        dto.setUuid(uuid);
        dto.setImg(lineCaptcha.getImageBase64Data());
        return dto;
    }

    @Override
    public boolean verityCaptcha(String uuid, String authCode) {
        String redisAuthCode = (String) redisUtil.remove(uuid);
        return StringUtils.hasText(authCode)
                && StringUtils.hasText(redisAuthCode)
                && redisAuthCode.equalsIgnoreCase(authCode);
    }
}
