package cn.sliew.carp.module.orca.config;

import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.*;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics.EventPublisher;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics.QueueMetricsPublisher;
import cn.sliew.carp.module.orca.spinnaker.keiko.mem.InMemoryQueue;
import cn.sliew.carp.module.orca.spinnaker.orca.queue.ThreadPoolQueueExecutor;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Clock;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
@Configuration
public class SpinnakerKeikoConfig {

    public static final String IN_MEMORY_QUEUE = "in-memory-queue";
    public static final String DEFAULT_QUEUE_PROCESSOR = "default-queue-processor";

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("scheduler-");
        scheduler.setPoolSize(10);
        return scheduler;
    }

    @Bean
    public QueueExecutor<ThreadPoolTaskExecutor> queueExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("keiko-queue-processor-");
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        executor.setQueueCapacity(1024);
        executor.setKeepAliveSeconds((int) Duration.ofMinutes(1L).toSeconds());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return new ThreadPoolQueueExecutor(executor);
    }

    @Bean
    public EventPublisher eventPublisher(MeterRegistry registry, Clock clock) {
        return new QueueMetricsPublisher(registry, clock);
    }

    @Bean(IN_MEMORY_QUEUE)
    public Queue inMemoryQueue(Clock clock, EventPublisher eventPublisher) {
        BiConsumer<Queue, Message> deadMessageHandler = (queue, message) -> {
            log.error("queue ({}) received a dead message: {}", queue, JacksonUtil.toJsonString(message));
        };
        return new InMemoryQueue(clock, Duration.ofSeconds(5L), Collections.singletonList(deadMessageHandler), false, eventPublisher);
    }

    @Bean(DEFAULT_QUEUE_PROCESSOR)
    public QueueProcessor defaultQueueProcessor(
            Queue inMemoryQueue,
            QueueExecutor queueExecutor,
            List<MessageHandler<?>> messageHandlers, EventPublisher eventPublisher) {
        BiConsumer<Queue, Message> deadMessageHandler = (queue, message) -> {
            log.error("queue ({}) received a dead message: {}", queue, JacksonUtil.toJsonString(message));
        };
        return new QueueProcessor(inMemoryQueue, queueExecutor, messageHandlers, eventPublisher, deadMessageHandler, true, Duration.ZERO, Duration.ZERO);
    }
}
