package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class HTTPExecutor extends INodeExecutor {

    public HTTPExecutor() {
        setType(NodeType.HTTP);
    }

    @Override
    public CompletableFuture<ExecutionResult> execute(ExecutionContext context) {
        CompletableFuture<ExecutionResult> future = new CompletableFuture<>();
        ExecutionResult result = new ExecutionResult();

        HTTPExecutorInputs inputs = parseInputs(context);


        future.complete(result);
        return future;
    }

    private HTTPExecutorInputs parseInputs(ExecutionContext context) {
        Object data = context.getNode().getData();
        return null;
    }

    @Data
    public static class HTTPExecutorInputs {
        private String method;
        private String url;
        private Map<String, String> headers;
        private Map<String, String> params;
        private String bodyType;
        private String body;
        private Long retryTimes;
        private Long timeout;
    }


}
