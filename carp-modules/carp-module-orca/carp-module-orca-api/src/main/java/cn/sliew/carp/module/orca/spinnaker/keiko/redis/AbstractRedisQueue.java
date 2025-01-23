package cn.sliew.carp.module.orca.spinnaker.keiko.redis;

import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.Queue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;

import java.time.Clock;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

abstract class AbstractRedisQueue implements Queue {

    private Clock clock;
    private TemporalAmount ackTimeout = Duration.ofMinutes(1);
    private List<BiConsumer<Queue, Message>> deadMessageHandlers = new ArrayList<>();
    private Boolean canPollMany = false;

    private ObjectMapper mapper;
    private Integer lockTtlSeconds = 10;

    protected abstract String getQueueKey();
    protected abstract String getUnackedKey();
    protected abstract String getMessagesKey();
    protected abstract String getLocksKey();
    protected abstract String getAttemptsKey();

    protected abstract String getCacheScript();
    protected abstract String getReadMessageWithLockScriptSha();

    protected void handleDeadMessage(Message message) {
        if (CollectionUtils.isNotEmpty(deadMessageHandlers)) {
            deadMessageHandlers.forEach(handler -> handler.accept(this, message));
        }
    }

    protected Double score(TemporalAmount delay) {
        if (Objects.isNull(delay)) {
            delay = Duration.ZERO;
        }
        return Double.valueOf(clock.instant().plus(delay).toEpochMilli());
    }


}
