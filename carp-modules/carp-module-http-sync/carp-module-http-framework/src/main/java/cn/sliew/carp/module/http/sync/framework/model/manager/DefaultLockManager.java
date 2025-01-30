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
package cn.sliew.carp.module.http.sync.framework.model.manager;

import cn.sliew.carp.module.http.sync.framework.model.job.JobInfo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class DefaultLockManager implements LockManager {

    private RedissonClient redissonClient;

    public DefaultLockManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public Long lockReleaseTime(JobInfo jobInfo) {
        RLock lock = getLock(getLockKey(jobInfo));
        if (lock.isLocked()) {
            return lock.remainTimeToLive();
        }
        return 0L;
    }

    /**
     * 不设置 leaseTime，redisson 会自动对锁进行续期
     * 如果任务执行 10h，那么就会锁 10h，如果任务执行 1d，那么就会锁 1d。
     * 只要任务不结束，redisson 会不停地对锁进行续期，默认每次续 30s，可通过参数调整
     * 如果设置 leaseTime，就相当于到了 leaseTime 就会释放，redisson 不会自动续期
     * 不设置 leaseTime 的风险就是必须手动释放锁，如果只加锁，不释放锁，会无限续期下去，直到应用重启
     * 应用重启后自动续期的幕后线程会挂掉，就不会自动续期了
     * 设置 leaseTime 的风险就是需有效评估任务执行时长，防止未执行完就自动释放了。
     * 当应用重启时，不会释放锁，就只能等 leaseTime 到达，锁自动释放。
     */
    @Override
    public boolean lock(JobInfo jobInfo) {
        RLock lock = getLock(getLockKey(jobInfo));
        try {
            return lock.tryLock(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean unlock(JobInfo jobInfo) {
        RLock lock = getLock(getLockKey(jobInfo));
        try {
            if (lock.isLocked()) {
                return lock.forceUnlock();
            }
            return true;
        } catch (Exception e) {
            log.error("forceUnlock failed, lock: {}", lock.getName(), e);
            return false;
        }
    }

    private String getLockKey(JobInfo jobInfo) {
        return String.format("lock:%s:%s:%s:%s:%s",
                jobInfo.getGroup(), jobInfo.getJob(), jobInfo.getSubJob().orElse("null"), jobInfo.getAccount().orElse("null"), jobInfo.getSubAccount().orElse("null"));
    }

    private RLock getLock(String key) {
        return redissonClient.getLock(key);
    }
}
