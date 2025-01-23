package cn.sliew.carp.module.orca.spinnaker.orca.queue;

import cn.sliew.carp.module.orca.spinnaker.keiko.core.QueueExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadPoolQueueExecutor extends QueueExecutor<ThreadPoolTaskExecutor> {

    public ThreadPoolQueueExecutor(ThreadPoolTaskExecutor executor) {
        super(executor);
    }

    @Override
    public boolean hasCapacity() {
        int activeCount = executor.getThreadPoolExecutor().getActiveCount();
        int maximumPoolSize = executor.getThreadPoolExecutor().getMaximumPoolSize();
        return activeCount < maximumPoolSize;
    }

    @Override
    public Integer availableCapacity() {
        int activeCount = executor.getThreadPoolExecutor().getActiveCount();
        int maximumPoolSize = executor.getThreadPoolExecutor().getMaximumPoolSize();
        return maximumPoolSize - activeCount;
    }
}
