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
import cn.sliew.milky.common.util.JacksonUtil;
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
        outputs.putAll(parseVariable(context));
        result.setOutputs(outputs);
        future.complete(result);
        return future;
    }

    private Map<String, Object> parseVariable(ExecutionContext context) {
        Map<String, Object> varialbeMap = new HashMap<>();

        INode node = context.getNode();
        if (Objects.isNull(node.getData())) {
            return varialbeMap;
        }
        VariableNodeSchema.VariableNodeData data = (VariableNodeSchema.VariableNodeData)node.getData();
        if (CollectionUtils.isEmpty(data.getAssign())) {
            return varialbeMap;
        }
        for (VariableNodeSchema.VariableData variableData : data.getAssign()) {
            if (Objects.isNull(variableData.getOperator())) {
                throw new RuntimeException("Variable operator required");
            }
            if (Objects.isNull(variableData.getLeft())) {
                throw new RuntimeException("Variable left required");
            }
            if (variableData.getOperator() == VariableType.DECLARATION) {
                String left = variableData.getLeft().asText();
                if (Objects.nonNull(variableData.getRight())) {
                    IValue right = JacksonUtil.toObject(variableData.getRight(), IValue.class);
                    IVariableParseResult parseResult = context.getRuntime().getState()
                        .parseFlowValue(right, WorkflowVariableType.of(right.getSchema().getType()));
                    if (Objects.nonNull(parseResult)) {
                        varialbeMap.put(left, parseResult.getValue());
                    }
                }
            } else if (variableData.getOperator() == VariableType.ASSIGN) {
                IFlowRefValue left = JacksonUtil.toObject(variableData.getLeft(), IFlowRefValue.class);
                IVariableParseResult leftParseResult = context.getRuntime().getState().parseRef(left);
                if (Objects.isNull(leftParseResult)) {
                    throw new RuntimeException("Assign varialbe left required");
                }
                if (Objects.nonNull(variableData.getRight())) {
                    IValue right = JacksonUtil.toObject(variableData.getRight(), IValue.class);
                    IVariableParseResult rightParseResult = context.getRuntime().getState()
                        .parseFlowValue(right, WorkflowVariableType.of(right.getSchema().getType()));
                    if (Objects.nonNull(rightParseResult)) {
                        // todo 可以对值进行重新赋值，是不是太危险了？
                        if (!Objects.equals(leftParseResult.getType(), rightParseResult.getType()) || (Objects.nonNull(
                            leftParseResult.getItemsType()) && Objects.nonNull(
                            rightParseResult.getItemsType()) && Objects.equals(leftParseResult.getItemsType(),
                            rightParseResult.getItemsType()))) {
                            throw new RuntimeException(
                                "Assign left's type or itemsType must equals with right's type or itemsType");
                        }
                        IVariableStore.SetVariableParam setVariableParam =
                            new IVariableStore.SetVariableParam().setNodeID(leftParseResult.getNodeId())
                                .setKey(leftParseResult.getKey()).setValue(rightParseResult.getValue());
                        setVariableParam.setType(leftParseResult.getType());
                        setVariableParam.setItemsType(leftParseResult.getItemsType());
                        context.getRuntime().getState().getVariableStore().setVariable(setVariableParam);
                        varialbeMap.put(leftParseResult.getKey(), rightParseResult.getValue());
                    }
                }
            }
        }

        return varialbeMap;

    }
}
