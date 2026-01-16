package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.CodeNodeSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CodeExecutor extends INodeExecutor {

    private static final String SCRIPT_TEMPLATE = """
        'use strict';
        
        %s
        
        // Ensure main function exists
        if (typeof main !== 'function') {
          throw new Error('main function is required in the script');
        }
        
        // Execute main function with params
        main({ params })
            .then(result => _resolveJava(result))
            .catch(error => _rejectJava(error));
        """;

    private final Context context;

    public CodeExecutor() {
        setType(NodeType.CODE);
        context = Context.newBuilder("js").allowAllAccess(true).allowHostAccess(HostAccess.ALL)
            .allowHostClassLookup(className -> true).allowExperimentalOptions(true).build();
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
        CodeNodeSchema.CodeNodeData data = (CodeNodeSchema.CodeNodeData)node.getData();
        if (Objects.isNull(data.getScript()) || StringUtils.isEmpty(data.getScript().getContent())) {
            throw new RuntimeException("Code content is required");
        }

        return new CodeExecutorInputs().setParams(context.getInputs()).setScript(
            new Script().setLanguage(data.getScript().getLanguage()).setContent(data.getScript().getContent()));
    }

    private CompletableFuture<ExecutionResult> javascript(CodeExecutorInputs inputs) {
        String script = String.format(SCRIPT_TEMPLATE, inputs.getScript().getContent());
        Map<String, Object> params = MapUtils.isEmpty(inputs.getParams()) ? Collections.emptyMap() : inputs.getParams();

        try {
            CompletableFuture<Object> resultFuture = new CompletableFuture<>();
            Value bindings = context.getBindings("js");
            bindings.putMember("params", params);
            bindings.putMember("_resolveJava", (Consumer)resultFuture::complete);
            bindings.putMember("_rejectJava", (Consumer<Throwable>)resultFuture::completeExceptionally);

            context.eval("js", script);

            return resultFuture.thenApply(object -> {
                ExecutionResult result = new ExecutionResult();
                if (Objects.nonNull(object) && object instanceof Map) {
                    WorkflowOutputs outputs = new WorkflowOutputs((Map)object);
                    result.setOutputs(outputs);
                }
                return result;
            });
        } catch (Exception e) {
            throw new RuntimeException("执行失败", e);
        }
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
