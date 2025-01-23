package cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics;

import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;

public class QueueEvent {

    public static final QueueEvent QueuePolled = new QueueEvent();
    public static final QueueEvent RetryPolled = new QueueEvent();

    public static final QueueEvent MessageAcknowledged = new QueueEvent();
    public static final QueueEvent MessageRetried = new QueueEvent();

    public static final QueueEvent MessageDead = new QueueEvent();
    public static final QueueEvent LockFailed = new QueueEvent();
    public static final QueueEvent NoHandlerCapacity = new QueueEvent();

    @Data
    public static class PayloadQueueEvent extends QueueEvent {
        private Message payload;
    }

    @Data
    @AllArgsConstructor
    public static class MessagePushed extends QueueEvent {
        private Message payload;
    }

    @Data
    public static class MessageProcessing extends QueueEvent {
        private Message payload;
        private Duration lag;

        public MessageProcessing(Message payload, Instant scheduledTime, Instant now) {
            this(payload, Duration.between(scheduledTime, now));
        }

        public MessageProcessing(Message payload, Duration lag) {
            this.payload = payload;
            this.lag = lag;
        }
    }

    @Data
    @AllArgsConstructor
    public static class MessageDuplicate extends QueueEvent {
        private Message payload;
    }

    @Data
    public static class MessageRescheduled extends QueueEvent {
        private Message payload;
    }

    @Data
    public static class MessageNotFound extends QueueEvent {
        private Message payload;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HandlerThrewError extends QueueEvent {
        private Message payload;
    }
}
