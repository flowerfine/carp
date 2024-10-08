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

package cn.sliew.carp.example.camellia.controller;

import cn.sliew.carp.example.camellia.listener.ConsumerService1;
import com.netease.nim.camellia.delayqueue.common.domain.CamelliaDelayMsg;
import com.netease.nim.camellia.delayqueue.sdk.CamelliaDelayQueueSdk;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/api/carp/example/camellia")
@Tag(name = "测试模块-Camellia延迟消息")
public class DelayMsgController {

    @Autowired
    private CamelliaDelayQueueSdk delayQueueSdk;

    @PostMapping("/put")
    public CamelliaDelayMsg sendDelayMsg(@RequestParam("msg") String msg,
                                         @RequestParam("delaySeconds") long delaySeconds,
                                         @RequestParam(value = "ttlSeconds", required = false, defaultValue = "30") long ttlSeconds,
                                         @RequestParam(value = "maxRetry", required = false, defaultValue = "3") int maxRetry) {
        String topic = ConsumerService1.TOPIC;
        log.info("sendDelayMsg, topic = {}, msg = {}, delaySeconds = {}, ttlSeconds = {}, maxRetry = {}", topic, msg, delaySeconds, ttlSeconds, maxRetry);
        return delayQueueSdk.sendMsg(topic, msg, delaySeconds, TimeUnit.SECONDS, ttlSeconds, TimeUnit.SECONDS, maxRetry);
    }
}