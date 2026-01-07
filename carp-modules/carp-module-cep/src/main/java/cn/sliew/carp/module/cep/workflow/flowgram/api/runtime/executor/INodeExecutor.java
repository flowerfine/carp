package cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.container.IContainer;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context.IContext;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.ISnapshot;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;

@Data
public abstract class INodeExecutor {

    private NodeType type;

    public abstract CompletableFuture<ExecutionResult> execute(ExecutionContext context);

    @Data
    @Accessors(chain = true)
    public static class ExecutionContext {

        private INode node;
        private WorkflowInputs inputs;
        private IContainer container;
        private IContext runtime;
        private ISnapshot snapshot;
    }

    @Data
    public static class ExecutionResult {
        private WorkflowOutputs outputs;
        private String branch;
    }
}
