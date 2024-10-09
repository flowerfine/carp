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

package cn.sliew.carp.example.redisson.controller;

import cn.sliew.carp.example.redisson.service.DistributedExecutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carp/example/redisson/distributed")
@Tag(name = "测试模块-分布式计算")
public class DistributedController {

    @Autowired
    private DistributedExecutorService distributedExecutorService;

    @PostMapping("/submitCallable")
    @Operation(summary = "执行 Callable 任务")
    public String submitCallable(@RequestParam("input") String input) throws Exception {
        return distributedExecutorService.submitCallable(input);
    }

    @PostMapping("/scheduleRunnable")
    @Operation(summary = "调度 Runnable 任务")
    public void scheduleRunnable(@RequestParam("input") String input) throws Exception {
        distributedExecutorService.scheduleRunnable(input);
    }

    @PostMapping("/scheduleLambda")
    @Operation(summary = "调度 Lambda 任务")
    public void scheduleLambda(@RequestParam("input") String input) throws Exception {
        distributedExecutorService.scheduleLambda(input);
    }

    @PostMapping("/scheduleRunnableWithCron")
    @Operation(summary = "使用 CRON 调度 Runnable 任务")
    public void scheduleRunnableWithCron(@RequestParam("input") String input) throws Exception {
        distributedExecutorService.scheduleRunnableWithCron(input);
    }

    @DeleteMapping("/cancel")
    @Operation(summary = "取消任务")
    public Boolean cancel(@RequestParam("taskId") String taskId) throws Exception {
        return distributedExecutorService.cancel(taskId);
    }

}
