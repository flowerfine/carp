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

@RestController
@RequestMapping("/api/carp/example/redisson/lock")
@Tag(name = "测试模块-Lock")
public class LockController {

    @Autowired
    private LockService lockService;

    @PostMapping("/lock-unlock")
    @Operation(summary = "加锁-释放锁")
    public String lockAndUnlock() throws Exception {
        String key = UUID.fastUUID().toString(true);
        boolean locked = lockService.lock(key, Duration.ofSeconds(3L).toMillis(), Duration.ofSeconds(10L).toMillis());
        if (locked) {
            ThreadUtil.sleep(Duration.ofMinutes(1L).toMillis());
            lockService.unlock(key);
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
