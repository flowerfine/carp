package cn.sliew.carp.module.queue.redis.test1;

import java.util.concurrent.Executor;

public abstract class QueueExecutor implements Executor {

    private Executor executor;

    @Override
    public void execute(Runnable command) {
        executor.execute(command);
    }

    public abstract boolean hasCapacity();

    public abstract Integer availableCapacity();
}
