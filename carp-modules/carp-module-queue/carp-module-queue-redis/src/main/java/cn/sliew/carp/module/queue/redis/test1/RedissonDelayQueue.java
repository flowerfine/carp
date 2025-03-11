package cn.sliew.carp.module.queue.redis.test1;

import cn.hutool.core.thread.ThreadUtil;
import cn.sliew.carp.module.queue.api.BaseQueue;
import cn.sliew.carp.module.queue.api.ListenerManager;
import cn.sliew.carp.module.queue.api.Message;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.context.SmartLifecycle;

import java.time.Duration;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class RedissonDelayQueue extends BaseQueue implements SmartLifecycle {

    private RedissonClient redissonClient;
    private AtomicBoolean started = new AtomicBoolean(false);
    private ScheduledThreadPoolExecutor scheduledExecutor;

    public RedissonDelayQueue(String name, ListenerManager listenerManager, RedissonClient redissonClient) {
        super(name, listenerManager);
    }

    @Override
    public void start() {
        if (started.compareAndSet(false, true)) {
            scheduledExecutor = ThreadUtil.createScheduledExecutor(Runtime.getRuntime().availableProcessors());
            ThreadUtil.schedule(scheduledExecutor, () -> poll(), 0, 50L, false);
            log.debug("Start process queue: {}", getName());
        }
    }

    @Override
    public void stop() {
        if (started.compareAndSet(true, false)) {
            if (scheduledExecutor != null) {
                scheduledExecutor.shutdown();
            }
        }
    }

    @Override
    public boolean isRunning() {
        return started.get();
    }

    private void poll() {
        RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(getName());
        blockingDeque.poll();

    }

    @Override
    public void push(Message message, Duration delay) {
        RBlockingDeque<Message> blockingDeque = redissonClient.getBlockingDeque(getName());
        RDelayedQueue<Message> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        delayedQueue.offer(message, delay.toMillis(), TimeUnit.MILLISECONDS);
    }

}
