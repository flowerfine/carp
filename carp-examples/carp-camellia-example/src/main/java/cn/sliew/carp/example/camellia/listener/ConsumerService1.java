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

package cn.sliew.carp.example.camellia.listener;

import cn.sliew.milky.common.util.JacksonUtil;
import com.netease.nim.camellia.delayqueue.common.domain.CamelliaDelayMsg;
import com.netease.nim.camellia.delayqueue.sdk.CamelliaDelayMsgListener;
import com.netease.nim.camellia.delayqueue.sdk.springboot.CamelliaDelayMsgListenerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 使用spring托管
 * 此时需要service实现CamelliaDelayMsgListener接口，并且添加@CamelliaDelayMsgListenerConfig注解设置topic以及其他参数
 * Created by caojiajun on 2022/7/21
 */
@Slf4j
@Component
@CamelliaDelayMsgListenerConfig(topic = ConsumerService1.TOPIC, pullThreads = 1, consumeThreads = 3)
public class ConsumerService1 implements CamelliaDelayMsgListener {

    public static final String TOPIC = "topic1";

    @Override
    public boolean onMsg(CamelliaDelayMsg delayMsg) {
        String produceTime = DateFormatUtils.format(delayMsg.getProduceTime(), "yyyy-MM-dd HH:mm:ss");
        String triggerTime = DateFormatUtils.format(delayMsg.getTriggerTime(), "yyyy-MM-dd HH:mm:ss");
        String delay = DurationFormatUtils.formatDurationHMS(delayMsg.getTriggerTime() - delayMsg.getProduceTime());
        try {
            log.info("onMsg, produceTime = {}, triggerTime = {}, delay = {}, time-gap = {}, delayMsg = {}",
                    produceTime, triggerTime, delay, System.currentTimeMillis() - delayMsg.getTriggerTime(), JacksonUtil.toJsonString(delayMsg));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;//返回false，则delay-queue-server会重试
        }
    }
}