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
package cn.sliew.carp.module.orca.controller;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.module.orca.service.QueueService;
import cn.sliew.carp.module.orca.service.message.TestMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AnonymousAccess
@RestController
@RequestMapping("/api/carp/orca/keiko")
@Tag(name = "队列模块-Keiko测试")
public class KeikoController {

    @Autowired
    private QueueService queueService;

    @PutMapping("/push")
    @Operation(summary = "推送消息", description = "推送消息")
    public void push() {
        TestMessage message = new TestMessage();
        message.setName("test message push");
        queueService.push(message);
    }
}
