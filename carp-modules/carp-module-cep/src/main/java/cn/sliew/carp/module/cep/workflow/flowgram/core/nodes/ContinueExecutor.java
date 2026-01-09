package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;

import java.util.concurrent.CompletableFuture;

public class ContinueExecutor extends INodeExecutor {

    public ContinueExecutor() {
        setType(NodeType.CONTINUE);
    }

    @Override
    public CompletableFuture<ExecutionResult> execute(ExecutionContext context) {
        context.getRuntime().getCache().set("loop-continue", true);
        CompletableFuture<ExecutionResult> future = new CompletableFuture<>();
        ExecutionResult result = new ExecutionResult();
        future.complete(result);
        return future;
    }
}
