package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class EndExecutor extends INodeExecutor {

    public EndExecutor() {
        setType(NodeType.END);
    }

    @Override
    public CompletableFuture<ExecutionResult> execute(ExecutionContext context) {
        if (Objects.nonNull(context.getInputs())) {
            context.getRuntime().getIoCenter().setOutputs(new WorkflowOutputs(context.getInputs()));
        }
        CompletableFuture<ExecutionResult> future = new CompletableFuture<>();
        ExecutionResult result = new ExecutionResult();
        if (Objects.nonNull(context.getInputs())) {
            result.setOutputs(new WorkflowOutputs(context.getInputs()));
        }
        future.complete(result);
        return future;
    }
}
