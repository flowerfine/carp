package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;

import java.util.concurrent.CompletableFuture;

public class BreakExecutor extends INodeExecutor {

    public BreakExecutor() {
        setType(NodeType.BREAK);
    }

    @Override
    public CompletableFuture<ExecutionResult> execute(ExecutionContext context) {
        context.getRuntime().getCache().set("loop-break", true);
        CompletableFuture<ExecutionResult> future = new CompletableFuture<>();
        ExecutionResult result = new ExecutionResult();
        future.complete(result);
        return future;
    }
}
