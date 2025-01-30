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
package cn.sliew.carp.module.orca.spinnaker.keiko.core;

import cn.hutool.core.thread.ThreadUtil;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics.EventPublisher;
import cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics.QueueEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import static cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics.QueueEvent.MessageDead;
import static cn.sliew.carp.module.orca.spinnaker.keiko.core.metrics.QueueEvent.NoHandlerCapacity;

@Slf4j
public class QueueProcessor implements InitializingBean, DisposableBean {

    private Random random = new Random();
    private Map<Class, MessageHandler> handlerCache = new HashMap<>();
    private ScheduledThreadPoolExecutor scheduledExecutor;

    private Queue queue;
    private QueueExecutor<?> executor;
    private Collection<MessageHandler<?>> handlers;
    private EventPublisher publisher;
    private BiConsumer<Queue, Message> deadMessageHandler;
    private Boolean fillExecutorEachCycle = true;
    private Duration requeueDelay = Duration.ofSeconds(0);
    private Duration requeueMaxJitter = Duration.ofSeconds(0);


    public QueueProcessor(Queue queue, QueueExecutor<?> executor, Collection<MessageHandler<?>> handlers, EventPublisher publisher, BiConsumer<Queue, Message> deadMessageHandler, Boolean fillExecutorEachCycle, Duration requeueDelay, Duration requeueMaxJitter) {
        this.queue = queue;
        this.executor = executor;
        this.handlers = handlers;
        this.publisher = publisher;
        this.deadMessageHandler = deadMessageHandler;
        // builder 更优雅一些
        if (Objects.nonNull(fillExecutorEachCycle)) {
            this.fillExecutorEachCycle = fillExecutorEachCycle;
        }
        if (Objects.nonNull(requeueDelay)) {
            this.requeueDelay = requeueDelay;
        }
        if (Objects.nonNull(requeueMaxJitter)) {
            this.requeueMaxJitter = requeueMaxJitter;
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        scheduledExecutor = ThreadUtil.createScheduledExecutor(Runtime.getRuntime().availableProcessors());
        ThreadUtil.schedule(scheduledExecutor, () -> pull(), 0, 50L, false);
        log.info("start process queue: {}", queue.getClass().getSimpleName());
    }

    @Override
    public void destroy() throws Exception {
        scheduledExecutor.shutdown();
        log.info("stop process queue: {}", queue.getClass().getSimpleName());
    }

    // 如果是用 @Bean 注册，不会触发执行
//    @Scheduled(fixedDelayString = "${queue.poll.frequency.ms: 50}")
    public void pull() {
        try {
            if (executor.hasCapacity()) {
                if (fillExecutorEachCycle) {
                    if (queue.getCanPollMany()) {
                        queue.poll(executor.availableCapacity(), callback);
                    } else {
                        IntStream.range(1, executor.availableCapacity()).forEach(number -> {
                            pollOnce();
                        });
                    }
                } else {
                    pollOnce();
                }
            } else {
                publisher.publishEvent(NoHandlerCapacity);
            }
        } catch (Exception e) {
            log.error("pull message error, queue: {}", queue, e);
        }
    }

    private void pollOnce() {
        queue.poll(callback);
    }

    private MessageHandler handlerFor(Message message) {
        return handlerCache.computeIfAbsent(
                message.getClass(),
                messageType -> handlers.stream()
                        .filter(h -> h.getMessageType().isAssignableFrom(messageType))
                        .findFirst()
                        .orElse(null)
        );
    }

    private BiConsumer<Message, Runnable> callback = (message, ack) -> {
        MessageHandler handler = handlerFor(message);
        if (Objects.isNull(handler)) {
            log.error("Unsupported message type {}: {}", message.getClass().getSimpleName(), message);
            deadMessageHandler.accept(queue, message);
            publisher.publishEvent(MessageDead);
            return;
        }

        try {
            executor.execute(() -> {
                {
                    try {
                        QueueContextHolder.set(message);
                        handler.invoke(message);
                        ack.run();
                    } catch (Throwable e) {
                        // Something very bad is happening
                        log.error("Unhandled throwable from {}", message, e);
                        publisher.publishEvent(new QueueEvent.HandlerThrewError(message));
                    } finally {
                        QueueContextHolder.clear();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            var requeueDelaySeconds = requeueDelay.toSeconds();
            if (requeueMaxJitter.toSeconds() > 0) {
                requeueDelaySeconds += random.nextInt((int) requeueMaxJitter.toSeconds());
            }

            Duration requeueDelay = Duration.ofSeconds(requeueDelaySeconds);
            Integer attempts = (Integer) message.getAttribute(Message.ATTRIBUTE_ATTEMPTS, Message.ATTRIBUTE_ATTEMPTS_VALUE);

            log.warn("Executor at capacity, re-queuing message {} (delay: {}, attempts: {})",
                    message, requeueDelay, attempts, e
            );
            queue.push(message, requeueDelay);
        }
    };


}
