package cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics;

import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Predicate;

public interface MonitorableQueue extends Queue {

    EventPublisher getPublisher();

    QueueState readState();

    boolean containsMessage(Predicate<Message> predicate);

    default void fire(QueueEvent event) {
        getPublisher().publishEvent(event);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QueueState {

        public QueueState(int depth, int ready, int unacked) {
            this(depth, ready, unacked, 0, 0);
        }

        /**
         * Number of messages currently queued for delivery including any not yet due.
         */
        private Integer depth;
        /**
         * Number of messages ready for delivery.
         */
        private Integer ready;
        /**
         * Number of messages currently being processed but not yet acknowledged.
         */
        private Integer unacked;
        /**
         * Number of messages neither queued or in-process.
         *
         * Some implementations may not have any way to implement this metric. It is
         * only intended for alerting leaks.
         */
        private Integer orphaned = 0;
        /**
         * Difference between number of known message hashes and number of de-dupe
         * hashes plus in-process messages.
         *
         * Some implementations may not have any way to implement this metric. It is
         * only intended for alerting leaks.
         */
        private Integer hashDrift = 0;

    }
}
