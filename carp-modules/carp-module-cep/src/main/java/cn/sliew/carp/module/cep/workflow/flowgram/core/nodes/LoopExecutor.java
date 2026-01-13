package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.LoopNodeSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.context.IContext;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.engine.IEngine;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableParseResult;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableStore;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.IFlowRefValue;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowVariableType;
import cn.sliew.carp.module.cep.workflow.flowgram.core.domain.container.WorkflowRuntimeContainer;
import cn.sliew.carp.module.cep.workflow.flowgram.util.WorkflowRuntimeType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.MapUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class LoopExecutor extends INodeExecutor {

    public LoopExecutor() {
        setType(NodeType.LOOP);
    }

    @Override
    public CompletableFuture<ExecutionResult> execute(ExecutionContext context) {
        String loopNodeID = context.getNode().getId();
        IEngine engine = (IEngine) context.getContainer().get(WorkflowRuntimeContainer.ENGINE);
        IVariableParseResult<List> loopArrayVariable = getLoopArrayVariable(context);
        List loopArray = loopArrayVariable.getValue();
        List<INode> subNodes = context.getNode().getChildren();
        Optional<INode> optional = subNodes.stream().filter(node -> Objects.equals(node.getType(), NodeType.BLOCK_START)).findAny();
        if (optional.isEmpty()) {
            throw new RuntimeException("Loop block start node not found");
        }

        List<Map<String, IVariableParseResult>> loopOutputs = new ArrayList<>();
        for (int i = 0; i < loopArray.size(); i++) {
            Object loopItem = loopArray.get(i);
            IContext subContext = context.getRuntime().sub();
            IVariableStore.SetVariableParam itemSetVariableParam = new IVariableStore.SetVariableParam()
                    .setNodeID(loopNodeID + "_locals")
                    .setKey("item");
            itemSetVariableParam.setType(loopArrayVariable.getItemsType());
            itemSetVariableParam.setValue(loopItem);
            subContext.getVariableStore().setVariable(itemSetVariableParam);

            IVariableStore.SetVariableParam indexSetVariableParam = new IVariableStore.SetVariableParam()
                    .setNodeID(loopNodeID + "_locals")
                    .setKey("index");
            indexSetVariableParam.setType(WorkflowVariableType.NUMBER);
            indexSetVariableParam.setValue(i);
            subContext.getVariableStore().setVariable(indexSetVariableParam);

            try {
                engine.executeNode(subContext, optional.get());
            } catch (Exception e) {
                throw new RuntimeException("Loop block execute error", e);
            }

            if (isBreak(subContext)) {
                break;
            }
            if (isContinue(subContext)) {
                continue;
            }

            Map<String, IVariableParseResult> blockOutput = getBlockOutput(context, subContext);
            loopOutputs.add(blockOutput);
        }

        setLoopNodeOutputs(context, loopOutputs);
        Map<String, Object> outputs = combineBlockOutputs(context, loopOutputs);

        CompletableFuture<ExecutionResult> future = new CompletableFuture<>();
        ExecutionResult result = new ExecutionResult();
        if (Objects.nonNull(outputs)) {
            result.setOutputs(new WorkflowOutputs(outputs));
        }
        future.complete(result);
        return future;
    }

    private IVariableParseResult<List> getLoopArrayVariable(ExecutionContext context) {
        INode node = context.getNode();
        LoopNodeSchema.LoopNodeData data = (LoopNodeSchema.LoopNodeData) node.getData();
        IVariableParseResult loopArrayVariable = context.getRuntime().getState().parseRef(data.getLoopFor());

        checkLoopArray(loopArrayVariable);
        return loopArrayVariable;
    }

    private void checkLoopArray(IVariableParseResult loopArrayVariable) {
        Object value = loopArrayVariable.getValue();
        if (Objects.isNull(value) || !(value instanceof List<?>)) {
            throw new RuntimeException("Loop \"loopFor\" is required");
        }
        WorkflowVariableType type = loopArrayVariable.getType();
        if (!Objects.equals(type, WorkflowVariableType.ARRAY)) {
            throw new RuntimeException("Loop \"loopFor\" must be an array");
        }
        WorkflowVariableType itemsType = loopArrayVariable.getItemsType();
        if (Objects.isNull(itemsType)) {
            throw new RuntimeException("Loop \"loopFor.items\" must be array items");
        }
    }

    private Map<String, IVariableParseResult> getBlockOutput(ExecutionContext context, IContext subContext) {
        Map<String, IFlowRefValue> loopOutputsDeclare = getLoopOutputsDeclare(context);

        Map<String, IVariableParseResult> blockOutput = new HashMap<>();
        if (MapUtils.isNotEmpty(loopOutputsDeclare)) {
            loopOutputsDeclare.forEach((outputName, outputRef) -> {
                IVariableParseResult outputVariable = subContext.getState().parseRef(outputRef);
                if (Objects.nonNull(outputVariable)) {
                    blockOutput.put(outputName, outputVariable);
                }
            });
        }

        return blockOutput;
    }

    private void setLoopNodeOutputs(ExecutionContext context, List<Map<String, IVariableParseResult>> blockOutputs) {
        INode loopNode = context.getNode();
        Map<String, IFlowRefValue> loopOutputsDeclare = getLoopOutputsDeclare(context);

        if (MapUtils.isNotEmpty(loopOutputsDeclare)) {
            loopOutputsDeclare.forEach((outputName, outputRef) -> {
                List<IVariableParseResult> outputVariables = blockOutputs.stream().map(blockOutput -> blockOutput.get(outputName)).collect(Collectors.toList());
                List<WorkflowVariableType> outputTypes = outputVariables.stream().map(fieldVariable -> fieldVariable.getType()).collect(Collectors.toList());
                WorkflowVariableType itemsType = WorkflowRuntimeType.getArrayItemsType(outputTypes);
                List<Object> value = outputVariables.stream().map(IVariableParseResult::getValue).collect(Collectors.toList());
                IVariableStore.SetVariableParam setVariableParam = new IVariableStore.SetVariableParam()
                        .setNodeID(loopNode.getId())
                        .setKey(outputName)
                        .setValue(value);
                setVariableParam.setType(WorkflowVariableType.ARRAY)
                        .setItemsType(itemsType);
                context.getRuntime().getVariableStore().setVariable(setVariableParam);
            });
        }
    }

    private Map<String, Object> combineBlockOutputs(ExecutionContext context, List<Map<String, IVariableParseResult>> blockOutputs) {
        Map<String, IFlowRefValue> loopOutputsDeclare = getLoopOutputsDeclare(context);
        Map<String, Object> loopOutput = new HashMap<>();
        if (MapUtils.isNotEmpty(loopOutputsDeclare)) {
            loopOutputsDeclare.forEach((outputName, outputRef) -> {
                List<Object> value = blockOutputs.stream().map(blockOutput -> blockOutput.get(outputName).getValue()).collect(Collectors.toList());
                loopOutput.put(outputName, value);
            });
        }
        return loopOutput;
    }

    private Map<String, IFlowRefValue> getLoopOutputsDeclare(ExecutionContext context) {
        INode node = context.getNode();
        LoopNodeSchema.LoopNodeData data = (LoopNodeSchema.LoopNodeData) node.getData();
        return data.getLoopOutputs();
    }

    private boolean isBreak(IContext subContext) {
        Object value = subContext.getCache().get(BreakExecutor.CACHE_KEY);
        if (Objects.nonNull(value) && value instanceof Boolean) {
            return (Boolean) value;
        }
        return false;
    }

    private boolean isContinue(IContext subContext) {
        Object value = subContext.getCache().get(ContinueExecutor.CACHE_KEY);
        if (Objects.nonNull(value) && value instanceof Boolean) {
            return (Boolean) value;
        }
        return false;
    }

    @Data
    @Accessors(chain = true)
    public static class LoopExecutorInputs {

        private List loopFor;
    }


}
