package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.sliew.carp.module.cep.workflow.flowgram.api.node.ConditionNodeSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.node.ConditionOperator;
import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableParseResult;
import cn.sliew.carp.module.cep.workflow.flowgram.api.schema.WorkflowVariableType;
import cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.handler.ConditionHandler;
import cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.handler.ConditionHandlers;
import cn.sliew.carp.module.cep.workflow.flowgram.util.WorkflowRuntimeType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ConditionExecutor extends INodeExecutor {

    public ConditionExecutor() {
        setType(NodeType.CONDITION);
    }

    @Override
    public CompletableFuture<ExecutionResult> execute(ExecutionContext context) {
        CompletableFuture<ExecutionResult> future = new CompletableFuture<>();
        ExecutionResult result = new ExecutionResult();

        INode node = context.getNode();
        ConditionNodeSchema.ConditionNodeData data = (ConditionNodeSchema.ConditionNodeData) node.getData();

        if (CollectionUtils.isNotEmpty(data.getConditions())) {
            List<ConditionValue> parsedConditions = data.getConditions().stream()
                    .map(item -> parseCondition(item, context))
                    .filter(item -> checkCondition(item))
                    .collect(Collectors.toList());

            Optional<ConditionValue> optional = parsedConditions.stream().filter(item -> handleCondition(item)).findAny();
            if (optional.isEmpty()) {
                result.setBranch("else");
            } else {
                result.setBranch(optional.get().getKey());
            }
        }

        future.complete(result);
        return future;
    }

    private ConditionValue parseCondition(ConditionNodeSchema.ConditionItem item, ExecutionContext context) {
        IVariableParseResult parsedLeft = context.getRuntime().getState().parseRef(item.getValue().getLeft());
        Object leftValue = null;
        WorkflowVariableType leftType = null;
        if (Objects.nonNull(parsedLeft)) {
            leftValue = parsedLeft.getValue();
            leftType = parsedLeft.getType();
        }

        WorkflowVariableType expectedRightType = getRuleType(leftType, item.getValue().getOperator());
        IVariableParseResult parsedRight = null;
        if (Objects.nonNull(item.getValue().getRight())) {
            parsedRight = context.getRuntime().getState().parseFlowValue(item.getValue().getRight(), expectedRightType);
        }
        Object rightValue = null;
        WorkflowVariableType rightType = null;
        if (Objects.nonNull(parsedRight)) {
            rightValue = parsedRight.getValue();
            rightType = parsedRight.getType();
        }
        return new ConditionValue()
                .setKey(item.getKey())
                .setLeftValue(leftValue)
                .setLeftType(leftType)
                .setRightValue(rightValue)
                .setRightType(rightType)
                .setOperator(item.getValue().getOperator());
    }

    private boolean checkCondition(ConditionValue condition) {
        Map<ConditionOperator, WorkflowVariableType> map = ConditionRules.INSTNACE.get(condition.getLeftType());
        if (MapUtils.isEmpty(map)) {
            throw new RuntimeException("Condition left type " + condition.getLeftType().getLabel() + " is not supported");
        }

        WorkflowVariableType ruleType = map.get(condition.getOperator());
        if (Objects.isNull(ruleType)) {
            throw new RuntimeException("Condition left type " + condition.getLeftType().getLabel() + " has no operator " + condition.getOperator().getLabel());
        }

        if (!WorkflowRuntimeType.isTypeEqual(ruleType, condition.getRightType())) {
            return false;
        }

        return true;
    }

    private boolean handleCondition(ConditionValue condition) {
        ConditionHandler handler = ConditionHandlers.INSTANCE.get(condition.getLeftType());

        if (Objects.isNull(handler)) {
            throw new RuntimeException("Condition left type " + condition.getLeftType().getLabel() + " is not supported");
        }

        return handler.handle(condition);
    }

    private WorkflowVariableType getRuleType(WorkflowVariableType leftType, ConditionOperator operator) {
        Map<ConditionOperator, WorkflowVariableType> map = ConditionRules.INSTNACE.get(leftType);
        if (MapUtils.isEmpty(map)) {
            return WorkflowVariableType.NULL;
        }
        WorkflowVariableType ruleType = map.get(operator);
        if (Objects.isNull(ruleType)) {
            return WorkflowVariableType.NULL;
        }

        return ruleType;
    }
}
