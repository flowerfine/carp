package cn.sliew.carp.module.queue.redis.test1;

import cn.sliew.carp.module.queue.api.Queue;

public interface MessageHander<M> {

    Class<M> getMessageType();

    Queue getQueue();

    void handle(M message);

}
