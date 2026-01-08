package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class StartExecutor extends INodeExecutor {

    public StartExecutor() {
        setType(NodeType.START);
    }

    @Override
    public CompletableFuture<ExecutionResult> execute(ExecutionContext context) {
        CompletableFuture<ExecutionResult> future = new CompletableFuture<>();
        ExecutionResult result = new ExecutionResult();
        if (Objects.nonNull(context.getRuntime().getIoCenter().getInputs())) {
            result.setOutputs(new WorkflowOutputs(context.getRuntime().getIoCenter().getInputs()));
        }
        future.complete(result);
        return future;
    }
}
