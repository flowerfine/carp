package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.CodeNodeSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class CodeExecutor extends INodeExecutor {

    public CodeExecutor() {
        setType(NodeType.CODE);
    }

    @Override
    public CompletableFuture<ExecutionResult> execute(ExecutionContext context) {
        CodeExecutorInputs inputs = parseInputs(context);
        if (StringUtils.equals(inputs.getScript().getLanguage(), "javascript")) {
            return javascript(inputs);
        }
        throw new RuntimeException("Unsupported code language: " + inputs.getScript().getLanguage());
    }

    private CodeExecutorInputs parseInputs(ExecutionContext context) {
        INode node = context.getNode();
        CodeNodeSchema.CodeNodeData data = (CodeNodeSchema.CodeNodeData) node.getData();
        if (Objects.isNull(data.getScript()) || StringUtils.isEmpty(data.getScript().getContent())) {
            throw new RuntimeException("Code content is required");
        }

        return new CodeExecutorInputs()
                .setParams(context.getInputs())
                .setScript(new Script()
                        .setLanguage(data.getScript().getLanguage())
                        .setContent(data.getScript().getContent())
                );
    }

    private CompletableFuture<ExecutionResult> javascript(CodeExecutorInputs inputs) {
        CompletableFuture<ExecutionResult> future = new CompletableFuture<>();
        ExecutionResult result = new ExecutionResult();
        future.complete(result);
        return future;
    }

    @Data
    @Accessors(chain = true)
    public static class CodeExecutorInputs {

        private Map<String, Object> params;
        private Script script;
    }

    @Data
    @Accessors(chain = true)
    public static class Script {

        private String language;
        private String content;
    }
}
