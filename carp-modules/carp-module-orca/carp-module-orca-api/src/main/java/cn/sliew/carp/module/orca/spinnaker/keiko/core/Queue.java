package cn.sliew.carp.module.orca.spinnaker.keiko.core;

import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.function.BiConsumer;

public interface Queue {

    int maxRetries = 5;

    TemporalAmount getAckTimeout();

    List<BiConsumer<Queue, Message>> getDeadMessageHandlers();

    Boolean getCanPollMany();

    void poll(BiConsumer<Message, Runnable> callback);

    void poll(Integer maxMessages, BiConsumer<Message, Runnable> callback);

    default void push(Message message) {
        push(message, Duration.ZERO);
    }

    void push(Message message, TemporalAmount delay);

    default void reschedule(Message message) {
        reschedule(message, Duration.ZERO);
    }

    void reschedule(Message message, TemporalAmount delay);

    void ensure(Message message, TemporalAmount delay);

    void retry();

    void clear();

}
