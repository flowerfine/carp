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
package cn.sliew.carp.module.alert.controller;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.log.web.annotation.WebLog;
import cn.sliew.carp.module.alert.model.webhook.WebhookAlert;
import cn.sliew.carp.module.alert.model.webhook.WebhookAlertList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebLog
@AnonymousAccess
@RestController
@RequestMapping("/api/carp/alert/webhook")
@Tag(name = "告警管理-Webhook")
public class CarpAlertWebhookController {

    @PostMapping
    @Operation(summary = "接收告警", description = "接收告警")
    public String receiveAlert(@RequestBody WebhookAlertList alertList) {
        for (WebhookAlert alert : alertList.getAlerts()) {
            String identify = alert.getLabels().get(WebhookAlert.LABEL_IDENTIFY);

        }
        return null;
    }
}
