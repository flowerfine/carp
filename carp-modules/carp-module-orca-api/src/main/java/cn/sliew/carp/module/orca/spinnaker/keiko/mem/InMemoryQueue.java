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
package cn.sliew.carp.module.orca.spinnaker.keiko.mem;

import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics.EventPublisher;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics.MonitorableQueue;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics.QueueEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@Slf4j
public class InMemoryQueue implements MonitorableQueue {

    private Clock clock;
    private TemporalAmount ackTimeout = Duration.ofMinutes(1);
    private List<BiConsumer<Queue, Message>> deadMessageHandlers = new ArrayList<>();
    private Boolean canPollMany = false;
    private EventPublisher publisher;

    private DelayQueue<Envelope> queue = new DelayQueue<>();
    private DelayQueue<Envelope> unacked = new DelayQueue<>();

    public InMemoryQueue(
            Clock clock,
            TemporalAmount ackTimeout,
            List<BiConsumer<Queue, Message>> deadMessageHandlers,
            boolean canPollMany,
            EventPublisher publisher) {
        this.clock = clock;
        if (Objects.nonNull(ackTimeout)) {
            this.ackTimeout = ackTimeout;
        }
        if (Objects.nonNull(deadMessageHandlers)) {
            this.deadMessageHandlers = deadMessageHandlers;
        }
        if (Objects.nonNull(canPollMany)) {
            this.canPollMany = canPollMany;
        }
        this.publisher = publisher;
    }

    @Override
    public TemporalAmount getAckTimeout() {
        return ackTimeout;
    }

    @Override
    public List<BiConsumer<Queue, Message>> getDeadMessageHandlers() {
        return deadMessageHandlers;
    }

    @Override
    public Boolean getCanPollMany() {
        return canPollMany;
    }

    @Override
    public void poll(BiConsumer<Message, Runnable> callback) {
        fire(QueueEvent.QueuePolled);

        Envelope envelope = queue.poll();
        if (Objects.nonNull(envelope)) {
            TemporalAmount messageAckTimeout = envelope.payload.getAckTimeoutMs() != null
                    ? Duration.ofMillis(envelope.payload.getAckTimeoutMs())
                    : ackTimeout;

            if (unacked.contains(envelope)) {
                queue.put(envelope);
            } else {
                Envelope copy = envelope.copy(clock.instant().plus(messageAckTimeout));
                ;
                unacked.put(copy);
                fire(new QueueEvent.MessageProcessing(envelope.payload, envelope.scheduledTime, clock.instant()));
                callback.accept(envelope.getPayload(), () -> {
                    ack(envelope.getId());
                    fire(QueueEvent.MessageAcknowledged);
                });
            }
        }
    }

    @Override
    public void poll(Integer maxMessages, BiConsumer<Message, Runnable> callback) {
        poll(callback);
    }

    @Override
    public void push(Message message, TemporalAmount delay) {
        boolean existed = queue.removeIf(item -> item.getPayload() == message);
        queue.put(new Envelope(message, clock.instant().plus(delay), clock));
        if (existed) {
            fire(new QueueEvent.MessageDuplicate(message));
        } else {
            fire(new QueueEvent.MessagePushed(message));
        }
    }

    @Override
    public void reschedule(Message message, TemporalAmount delay) {
        boolean existed = queue.removeIf(item -> item.getPayload() == message);
        if (existed) {
            queue.put(new Envelope(message, clock.instant().plus(delay), clock));
        }
    }

    @Override
    public void ensure(Message message, TemporalAmount delay) {
        if (queue.stream().noneMatch(item -> item.getPayload() == message)
                && unacked.stream().noneMatch(item -> item.getPayload() == message)) {
            push(message, delay);
        }
    }

    @Scheduled(fixedDelayString = "${queue.retry.frequency.ms:10000}")
    @Override
    public void retry() {
        Instant now = clock.instant();
        fire(QueueEvent.RetryPolled);

        while (true) {
            Envelope message = unacked.poll();
            if (Objects.isNull(message)) {
                break;
            }

            if (message.count >= Queue.maxRetries) {
                deadMessageHandlers.forEach(it -> it.accept(this, message.payload));
                fire(QueueEvent.MessageDead);
            } else {
                boolean existed = queue.removeIf(it -> it.payload.equals(message.payload));
                log.warn("redelivering unacked message {}", message.payload);
                queue.put(message.copy(now, message.count + 1));
                if (existed) {
                    fire(new QueueEvent.MessageDuplicate(message.payload));
                } else {
                    fire(QueueEvent.MessageRetried);
                }
            }
        }
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public QueueState readState() {
        return new QueueState(
                queue.size(),
                (int) queue.stream().filter(it -> it.getDelay(TimeUnit.NANOSECONDS) <= 0).count(),
                unacked.size()
        );
    }

    @Override
    public boolean containsMessage(Predicate<Message> predicate) {
        return queue.stream().map(it -> it.payload).anyMatch(predicate);
    }

    @Override
    public EventPublisher getPublisher() {
        return publisher;
    }

    private void ack(UUID messageId) {
        unacked.removeIf(item -> item.id == messageId);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Envelope implements Delayed {
        private UUID id;
        private Message payload;
        private Instant scheduledTime;
        private Clock clock;
        private Integer count = 1;

        public Envelope(Message payload, Instant scheduledTime, Clock clock) {
            this(UUID.randomUUID(), payload, scheduledTime, clock, 1);
        }

        public Envelope copy(Instant scheduledTime) {
            return new Envelope(id, payload, scheduledTime, clock, count);
        }

        public Envelope copy(Instant scheduledTime, int count) {
            return new Envelope(id, payload, scheduledTime, clock, count);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return clock.instant().until(scheduledTime, unit.toChronoUnit());
        }

        @Override
        public int compareTo(Delayed other) {
            return Long.compare(
                    getDelay(TimeUnit.MILLISECONDS),
                    other.getDelay(TimeUnit.MILLISECONDS)
            );
        }
    }
}
