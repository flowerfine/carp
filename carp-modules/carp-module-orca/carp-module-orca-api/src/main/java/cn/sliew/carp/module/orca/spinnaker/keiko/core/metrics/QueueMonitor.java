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
