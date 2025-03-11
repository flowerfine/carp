package cn.sliew.carp.module.queue.api.poll;

import cn.hutool.core.thread.ThreadUtil;
import cn.sliew.carp.module.queue.api.ListenerManager;
import cn.sliew.carp.module.queue.api.Message;
import cn.sliew.carp.module.queue.api.MessageHandler;
import cn.sliew.milky.common.concurrent.CapacityExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PollProcessor implements InitializingBean, DisposableBean {

    private PollableQueue queue;
    private CapacityExecutor executor;
    private ListenerManager listenerManager;
    private Boolean fillExecutorEachCycle;
    private Duration requeueDelay;
    private Duration requeueMaxJitter;

    private final Random random = new Random();
    private ScheduledThreadPoolExecutor scheduledExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        scheduledExecutor = ThreadUtil.createScheduledExecutor(Runtime.getRuntime().availableProcessors());
        ThreadUtil.schedule(scheduledExecutor, () -> poll(), 0, 50L, false);
        log.debug("Start process queue: {}", queue.getName());
    }

    @Override
    public void destroy() throws Exception {
        if (Objects.nonNull(scheduledExecutor)) {
            scheduledExecutor.shutdown();
            log.info("Stop process queue: {}", queue.getClass().getSimpleName());
        }
    }

    private void poll() {
        if (executor.hasCapacity()) {
            if (fillExecutorEachCycle) {
                for (int i = executor.availableCapacity(); i > 0; i--) {
                    pollOnce();
                }
            } else {
                pollOnce();
            }
        }
    }

    private void pollOnce() {
        Message message = queue.poll();
        if (Objects.nonNull(message)) {
            handleMessage(message);
        }
    }

    private void handleMessage(Message message) {
        log.info("Received message {}", message);
        Map<String, List<MessageHandler>> listeners = listenerManager.get(queue.getName());
        if (CollectionUtils.isEmpty(listeners) == false) {
            for (Map.Entry<String, List<MessageHandler>> entry : listeners.entrySet()) {
                String consumerGroup = entry.getKey();
                List<MessageHandler> handlers = entry.getValue();
                if (CollectionUtils.isEmpty(handlers) == false) {
                    MessageHandler handler = handlers.get(0);
                    doHandleMessage(consumerGroup, handler, message);
                }
            }
        }
    }

    private void doHandleMessage(String consumerGroup, MessageHandler handler, Message message) {
        try {
            executor.execute(() -> {
                try {
                    handler.handler(message);
                } catch (Throwable e) {
                    log.error("message deliver error, msgId: {}, topic: {}, consumerGroup: {}, retry: {}, maxRetry: {}",
                            message.getId(), message.getTopic(), consumerGroup, message.getRetry(), message.getMaxRetry(), e);
                    if (message.getRetry() < message.getMaxRetry()) {
                        message.setRetry(message.getRetry() + 1);
                        queue.push(message);
                    } else {
                        log.error("message retry reach max retry, give up deliver! msgId: {}, topic: {}, consumerGroup: {}",
                                message.getId(), message.getTopic(), consumerGroup, e);
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            long requeueDelaySeconds = requeueDelay.getSeconds();
            if (requeueMaxJitter.getSeconds() > 0) {
                requeueDelaySeconds += random.nextInt((int) requeueMaxJitter.getSeconds());
            }

            Duration requeueDelay = Duration.ofSeconds(requeueDelaySeconds);
            log.warn("Executor at capacity, re-queuing message {} (delay: {}, attempts: {})", message, requeueDelay, message.getRetry(), e);
            queue.push(message, requeueDelay);
        }
    }

}
