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

package cn.sliew.carp.example.redisson.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LockService {

    @Value("${spring.application.name}")
    private String application;

    @Autowired
    private RedissonClient client;

    /**
     * watchDog 只有设置 releaseTime 的 lock 不会生效，不会自动续期
     */
    public boolean lock(String key, long lockTimeout, long releaseTime) {
        RLock lock = getLock(key);
        try {
            // 尝试拿锁 lockTimeout 后停止重试,返回false
            // 没有Watch Dog ，releaseTime 后自动释放
            return lock.tryLock(lockTimeout, releaseTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * watchDog 自动续期时长默认为 30s，可通过 lockWatchdogTimeout 设置每次续期时长。
     * watchDog 按照 lockWatchdogTimeout / 3 的频率检测 key，进行续期
     */
    public boolean lockWithAutoRefreshTTL(String key, long lockTimeout) {
        RLock lock = getLock(key);
        try {
            // 尝试拿锁 lockTimeout 后停止重试,返回false
            // 具有Watch Dog 自动延期机制 默认续30s
            return lock.tryLock(lockTimeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public Long lockReleaseTime(String key) {
        RLock lock = getLock(key);
        if (lock.isLocked()) {
            return lock.remainTimeToLive();
        }
        return 0L;
    }

    public void unlock(String key) {
        RLock lock = getLock(key);
        try {
            if (lock.isLocked()) {
                lock.unlock();
            }
        } catch (Exception e) {
            log.error("unlock failed, lock: {}", lock.getName(), e);
        }
    }

    public void forceUnlock(String key) {
        RLock lock = getLock(key);
        try {
            if (lock.isLocked()) {
                lock.forceUnlock();
            }
        } catch (Exception e) {
            log.error("forceUnlock failed, lock: {}", lock.getName(), e);
        }
    }

    private RLock getLock(String key) {
        return client.getLock(appendLockKey(key));
    }

    private String appendLockKey(String key) {
        return String.format("%s:lock:%s", application, key);
    }
}