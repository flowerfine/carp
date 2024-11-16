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

import cn.hutool.core.lang.UUID;
import cn.hutool.core.thread.ThreadUtil;
import cn.sliew.carp.example.redisson.service.LockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/api/carp/example/redisson/lock")
@Tag(name = "测试模块-Lock")
public class LockController {

    @Autowired
    private LockService lockService;

    /**
     * 测试自动续期，自动续期有 1 个要点：
     * 1.不设置锁ttl，所以会无限续期下去。如果显式指定，则不会生效。
     */
    @PostMapping("/lock-unlock")
    @Operation(summary = "加锁-释放锁")
    public String lockAndUnlock() throws Exception {
        String key = UUID.fastUUID().toString(true);
        boolean locked = lockService.lockWithAutoRefreshTTL(key, Duration.ofSeconds(3L).toMillis());
        if (locked) {
            System.out.println("lock: " + key);
            ThreadUtil.sleep(Duration.ofMinutes(3L).toMillis());
            Long releaseTime = lockService.lockReleaseTime(key);
            System.out.println("lock relase time: " + DurationFormatUtils.formatDurationHMS(releaseTime));
            lockService.unlock(key);
            return key;
        }
        return "加锁失败";
    }

    /**
     * 测试异步释放锁
     * 1.默认是只有持有锁的线程才可以释放锁，异步情况下，必须强制释放锁才可以
     */
    @PostMapping("/lock-unlock-async")
    @Operation(summary = "加锁-异步释放锁")
    public String lockAndUnlockAsync() throws Exception {
        String key = UUID.fastUUID().toString(true);
        boolean locked = lockService.lockWithAutoRefreshTTL(key, Duration.ofSeconds(3L).toMillis());
        if (locked) {
            ThreadPoolExecutor executor = ThreadUtil.newExecutor(4);
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                System.out.println("lock: " + key);
                ThreadUtil.sleep(Duration.ofMinutes(1L).toMillis());
                Long releaseTime = lockService.lockReleaseTime(key);
                System.out.println("lock relase time: " + DurationFormatUtils.formatDurationHMS(releaseTime));
            }, executor);
            future.whenCompleteAsync((unused, throwable) -> {
                lockService.forceUnlock(key);
            }, executor);
            return key;
        }
        return "加锁失败";
    }

    @PostMapping("/lock")
    @Operation(summary = "加锁")
    public String lock() throws Exception {
        String key = UUID.fastUUID().toString(true);
        boolean locked = lockService.lock(key, Duration.ofSeconds(3L).toMillis(), Duration.ofSeconds(10L).toMillis());
        if (locked) {
            return key;
        }
        return "加锁失败";
    }

    @PostMapping("/unlock")
    @Operation(summary = "释放锁")
    public void unlock(@RequestParam("key") String key) throws Exception {
        lockService.unlock(key);
    }

    @PostMapping("/forceUnlock")
    @Operation(summary = "强制释放锁")
    public void forceUnlock(@RequestParam("key") String key) throws Exception {
        lockService.forceUnlock(key);
    }

    @GetMapping("/lock-ttl")
    @Operation(summary = "锁释放时间")
    public String scheduleLambda(@RequestParam("key") String key) throws Exception {
        Long time = lockService.lockReleaseTime(key);
        return DurationFormatUtils.formatDurationHMS(time);
    }

}
