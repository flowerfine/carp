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
package cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Clock;
import java.util.concurrent.atomic.AtomicReference;

public class QueueMonitor {

    private final MeterRegistry registry;
    private final Clock clock;
    private final MonitorableQueue queue;
    private final AtomicReference<MonitorableQueue.QueueState> _lastState;

    public QueueMonitor(MeterRegistry registry, Clock clock, MonitorableQueue queue) {
        this.registry = registry;
        this.clock = clock;
        this.queue = queue;
        this._lastState = new AtomicReference<>(new MonitorableQueue.QueueState(0, 0, 0));

        // 设置各种监控指标
        registry.gauge("queue.depth", this, monitor -> monitor.getLastState().getDepth());
        registry.gauge("queue.unacked.depth", this, monitor -> monitor.getLastState().getUnacked());
        registry.gauge("queue.ready.depth", this, monitor -> monitor.getLastState().getReady());
        registry.gauge("queue.orphaned.messages", this, monitor -> monitor.getLastState().getOrphaned());
    }

    public MonitorableQueue.QueueState getLastState() {
        return _lastState.get();
    }

    @Scheduled(fixedDelayString = "${queue.depth.metric.frequency:30000}")
    public void pollQueueState() {
        _lastState.set(queue.readState());
    }
}
