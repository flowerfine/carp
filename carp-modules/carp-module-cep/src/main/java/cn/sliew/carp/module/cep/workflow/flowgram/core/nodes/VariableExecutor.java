package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.node.VariableNodeSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.node.VariableType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableParseResult;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableStore;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IFlowRefValue;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IValue;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowVariableType;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class VariableExecutor extends INodeExecutor {

    public VariableExecutor() {
        setType(NodeType.VARIABLE);
    }

    @Override
    public CompletableFuture<ExecutionResult> execute(ExecutionContext context) {
        CompletableFuture<ExecutionResult> future = new CompletableFuture<>();
        ExecutionResult result = new ExecutionResult();
        WorkflowOutputs outputs = new WorkflowOutputs();
//        outputs.putAll(parseVariable(context));
        result.setOutputs(outputs);
        return future;
    }

    private Map<String, Object> parseVariable(ExecutionContext context) {
        Map<String, Object> varialbeMap = new HashMap<>();

        INode node = context.getNode();
        if (Objects.isNull(node.getData())) {
            return varialbeMap;
        }
        VariableNodeSchema.VariableNodeData data = (VariableNodeSchema.VariableNodeData) node.getData();
        if (CollectionUtils.isEmpty(data.getAssign())) {
            return varialbeMap;
        }
        for (VariableNodeSchema.VariableData variableData : data.getAssign()) {
            if (Objects.nonNull(variableData.getLeft())) {
                if (variableData.getType() == VariableType.DECLARATION) {
                    String left = (String)variableData.getLeft();
                    if (Objects.nonNull(variableData.getRight())) {
                        IValue right = variableData.getRight();
                        IVariableParseResult parseResult = context.getRuntime().getState()
                            .parseFlowValue(right, WorkflowVariableType.of(right.getSchema().getType()));
                        if (Objects.nonNull(parseResult)) {
                            varialbeMap.put(left, parseResult.getValue());
                        }
                    }
                } else if (variableData.getType() == VariableType.ASSIGN) {
                    IFlowRefValue left = (IFlowRefValue)variableData.getLeft();
                    IVariableParseResult leftParseResult = context.getRuntime().getState().parseRef(left);
                    if (Objects.isNull(leftParseResult)) {
                        throw new RuntimeException("Assign varialbe left required");
                    }

                    if (Objects.nonNull(variableData.getRight())) {
                        IValue right = variableData.getRight();
                        IVariableParseResult rightParseResult = context.getRuntime().getState()
                            .parseFlowValue(right, WorkflowVariableType.of(right.getSchema().getType()));
                        if (Objects.nonNull(rightParseResult)) {
                            // todo 重新赋值
                            context.getRuntime().getState().getVariableStore().setVariable(
                                new IVariableStore.SetVariableParam()
                            );
                        }
                    }
                }
            }
        }

        return varialbeMap;

    }
}
