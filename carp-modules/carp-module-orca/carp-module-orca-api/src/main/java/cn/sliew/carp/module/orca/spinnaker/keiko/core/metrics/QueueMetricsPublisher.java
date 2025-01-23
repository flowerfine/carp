package cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class QueueMetricsPublisher implements EventPublisher {

    private final MeterRegistry registry;
    private final Clock clock;

    private final AtomicReference<Instant> _lastQueuePoll;
    private final AtomicReference<Instant> _lastRetryPoll;

    public QueueMetricsPublisher(MeterRegistry registry, Clock clock) {
        this.registry = registry;
        this.clock = clock;
        this._lastQueuePoll = new AtomicReference<>(clock.instant());
        this._lastRetryPoll = new AtomicReference<>(clock.instant());

        registry.gauge("queue.last.poll.age",
                this,
                self -> Duration.between(self.getLastQueuePoll(), clock.instant()).toMillis());

        registry.gauge("queue.last.retry.check.age",
                this,
                self -> Duration.between(self.getLastRetryPoll(), clock.instant()).toMillis());
    }

    @Override
    public void publishEvent(QueueEvent event) {
        if (event == QueueEvent.QueuePolled) {
            _lastQueuePoll.set(clock.instant());
        } else if (event instanceof QueueEvent.MessageProcessing) {
            QueueEvent.MessageProcessing mp = (QueueEvent.MessageProcessing) event;
            registry.timer("queue.message.lag")
                    .record(mp.getLag().toMillis(), TimeUnit.MILLISECONDS);
        } else if (event == QueueEvent.RetryPolled) {
            _lastRetryPoll.set(clock.instant());
        } else if (event instanceof QueueEvent.MessagePushed) {
            getMessagePushedCounter().increment();
        } else if (event == QueueEvent.MessageAcknowledged) {
            getMessageAcknowledgedCounter().increment();
        } else if (event == QueueEvent.MessageRetried) {
            getMessageRetriedCounter().increment();
        } else if (event == QueueEvent.MessageDead) {
            getMessageDeadCounter().increment();
        } else if (event instanceof QueueEvent.MessageDuplicate) {
            getMessageDuplicateCounter((QueueEvent.MessageDuplicate) event).increment();
        } else if (event == QueueEvent.LockFailed) {
            getLockFailedCounter().increment();
        } else if (event instanceof QueueEvent.MessageRescheduled) {
            getMessageRescheduledCounter().increment();
        } else if (event instanceof QueueEvent.MessageNotFound) {
            getMessageNotFoundCounter().increment();
        }
    }


    private Counter getMessagePushedCounter() {
        return registry.counter("queue.pushed.messages");
    }

    private Counter getMessageAcknowledgedCounter() {
        return registry.counter("queue.acknowledged.messages");
    }

    private Counter getMessageRetriedCounter() {
        return registry.counter("queue.retried.messages");
    }

    private Counter getMessageDeadCounter() {
        return registry.counter("queue.dead.messages");
    }

    private Counter getMessageDuplicateCounter(QueueEvent.MessageDuplicate event) {
        return registry.counter("queue.duplicate.messages",
                "messageType", event.getPayload().getClass().getSimpleName());
    }

    private Counter getLockFailedCounter() {
        return registry.counter("queue.lock.failed");
    }

    private Counter getMessageRescheduledCounter() {
        return registry.counter("queue.reschedule.succeeded");
    }

    private Counter getMessageNotFoundCounter() {
        return registry.counter("queue.message.notfound");
    }

    public Instant getLastQueuePoll() {
        return _lastQueuePoll.get();
    }

    public Instant getLastRetryPoll() {
        return _lastRetryPoll.get();
    }
}
