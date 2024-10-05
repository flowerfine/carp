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

package cn.sliew.carp.example.hazelcast.controller;

import cn.sliew.carp.example.hazelcast.computing.EchoRunnable;
import cn.sliew.carp.example.hazelcast.computing.IntegerCallable;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.durableexecutor.DurableExecutorService;
import com.hazelcast.scheduledexecutor.IScheduledExecutorService;
import com.hazelcast.scheduledexecutor.IScheduledFuture;
import com.hazelcast.scheduledexecutor.ScheduledTaskHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/carp/example/hazelcast/computing")
@Tag(name = "测试模块-分布式计算")
public class ComputingController {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @PostMapping("/callable")
    @Operation(summary = "执行 Callable 任务")
    public Integer submitCallable() throws Exception {
        IExecutorService executorService = hazelcastInstance.getExecutorService("executorService");
        Future<Integer> future = executorService.submit(new IntegerCallable());
        return future.get();
    }

    @PostMapping("/runnable")
    @Operation(summary = "执行 Runnable 任务")
    public void submitRunnable(@RequestParam("input") String input) throws Exception {
        IExecutorService executorService = hazelcastInstance.getExecutorService("executorService");
        Future<?> future = executorService.submit(new EchoRunnable(input));
        future.get();
    }

    @PostMapping("/durable-runnable")
    @Operation(summary = "执行 DurableRunnable 任务")
    public void submitDurationRunnable(@RequestParam("input") String input) throws Exception {
        DurableExecutorService executorService = hazelcastInstance.getDurableExecutorService("myDurableExecutorService");
        Future<?> future = executorService.submit(new EchoRunnable(input));
        future.get();
    }

    @PostMapping("/scheduled-runnable")
    @Operation(summary = "调度 Runnable 任务")
    public String scheduleRunnable(@RequestParam("input") String input) throws Exception {
        IScheduledExecutorService executorService = hazelcastInstance.getScheduledExecutorService("myScheduledExecutorService");
        IScheduledFuture<?> future = executorService.scheduleAtFixedRate(new EchoRunnable(input), 0, 30, TimeUnit.SECONDS);
//        future.get();

        String urn = future.getHandler().toUrn();
        future.dispose();
        return urn;
    }

    @DeleteMapping("/cancel-scheduled-runnable")
    @Operation(summary = "取消 Runnable 调度任务")
    public void cancelScheduledRunnable(@RequestParam("urn") String urn) throws Exception {
        IScheduledExecutorService executorService = hazelcastInstance.getScheduledExecutorService("myScheduledExecutorService");
        ScheduledTaskHandler handler = ScheduledTaskHandler.of(urn);
        IScheduledFuture<?> future = executorService.getScheduledFuture(handler);
        future.cancel(true);
        future.dispose();
    }
}
