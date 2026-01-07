package cn.sliew.carp.module.cep.workflow.flowgram.core.domain.executor;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.IExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutorFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class WorkflowRuntimeExecutor extends IExecutor {

    private Map<NodeType, INodeExecutor> nodeExecutors = new HashMap<>();

    public WorkflowRuntimeExecutor(List<INodeExecutorFactory> nodeExecutors) {
        nodeExecutors.forEach(factory -> register(factory.newExecutor()));
    }

    @Override
    public CompletableFuture<INodeExecutor.ExecutionResult> execute(INodeExecutor.ExecutionContext context) {
        NodeType nodeType = context.getNode().getType();
        INodeExecutor nodeExecutor = nodeExecutors.get(nodeType);
        if (Objects.isNull(nodeExecutor)) {
            throw new RuntimeException("No executor found for node type: " + nodeType.getValue());
        }
        return nodeExecutor.execute(context);
    }

    @Override
    public void register(INodeExecutor executor) {
        nodeExecutors.put(executor.getType(), executor);
    }
}
