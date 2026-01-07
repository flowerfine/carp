package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor;

import java.util.concurrent.CompletableFuture;

public abstract class IExecutor {

    public abstract CompletableFuture<INodeExecutor.ExecutionResult> execute(INodeExecutor.ExecutionContext context);

    public abstract void register(INodeExecutor executor);
}
