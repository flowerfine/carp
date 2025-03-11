package cn.sliew.carp.module.queue.api.poll;

import cn.sliew.carp.module.queue.api.BaseQueue;
import cn.sliew.carp.module.queue.api.ListenerManager;
import cn.sliew.carp.module.queue.api.Message;

public abstract class PollableQueue extends BaseQueue {

    public PollableQueue(String name, ListenerManager listenerManager) {
        super(name, listenerManager);
    }

    abstract Message poll();
}
