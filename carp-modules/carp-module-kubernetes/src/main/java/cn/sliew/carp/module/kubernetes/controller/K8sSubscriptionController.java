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

package cn.sliew.carp.module.kubernetes.controller;

import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.framework.web.response.ApiResponseWrapper;
import cn.sliew.carp.module.kubernetes.watch.source.K8sSources;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.pekko.Done;
import org.apache.pekko.actor.typed.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletionStage;

@RestController
@ApiResponseWrapper
@RequestMapping("/api/carp/kubernetes/subscription")
@Tag(name = "Kubernetes模块-订阅管理")
public class K8sSubscriptionController {

    @Autowired
    private ActorSystem actorSystem;

    @AnonymousAccess
    @GetMapping("start")
    @Operation(summary = "启动", description = "启动")
    public boolean start() {
        CompletionStage<Done> completionStage = K8sSources.source().runForeach(object -> System.out.println(object), actorSystem);
        return true;
    }

    @GetMapping("stop")
    @Operation(summary = "停止", description = "停止")
    public boolean stop() {
        return true;
    }

}
