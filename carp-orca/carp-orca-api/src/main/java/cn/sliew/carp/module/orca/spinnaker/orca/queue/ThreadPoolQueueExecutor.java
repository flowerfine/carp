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
package cn.sliew.carp.module.orca.spinnaker.orca.queue;

import cn.sliew.carp.module.orca.spinnaker.keiko.core.QueueExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadPoolQueueExecutor extends QueueExecutor<ThreadPoolTaskExecutor> {

    public ThreadPoolQueueExecutor(ThreadPoolTaskExecutor executor) {
        super(executor);
    }

    @Override
    public boolean hasCapacity() {
        int activeCount = executor.getThreadPoolExecutor().getActiveCount();
        int maximumPoolSize = executor.getThreadPoolExecutor().getMaximumPoolSize();
        return activeCount < maximumPoolSize;
    }

    @Override
    public Integer availableCapacity() {
        int activeCount = executor.getThreadPoolExecutor().getActiveCount();
        int maximumPoolSize = executor.getThreadPoolExecutor().getMaximumPoolSize();
        return maximumPoolSize - activeCount;
    }
}
