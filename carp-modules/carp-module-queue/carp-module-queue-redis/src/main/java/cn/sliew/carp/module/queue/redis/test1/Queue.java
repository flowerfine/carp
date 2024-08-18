package cn.sliew.carp.module.queue.redis.test1;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Queue {

    void pull(Consumer callback);

    void pull(Integer maxMessages, Consumer<TestMessage> callback);

    void push(TestMessage message);

    void push(TestMessage message, TemporalAmount delay);

    default void reschedule(TestMessage message) {
        reschedule(message, Duration.ZERO);
    }

    void reschedule(TestMessage message, TemporalAmount delay);

    void ensure(TestMessage message, TemporalAmount delay);

    default void retry() {

    }

    default void clear() {

    }

    TemporalAmount getAckTimeout();

    List<BiConsumer<TestMessage, Queue>> getDeadMessageCallbacks();

}
