package cn.sliew.carp.module.orca.spinnaker.keiko.core;

import java.util.concurrent.Executor;

public abstract class QueueExecutor<T extends Executor> implements Executor {

    protected T executor;

    public QueueExecutor(T executor) {
        this.executor = executor;
    }

    @Override
    public void execute(Runnable command) {
        executor.execute(command);
    }

    public abstract boolean hasCapacity();

    public abstract Integer availableCapacity();
}
