package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.handler;

import cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.ConditionValue;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import java.util.List;
import java.util.Objects;

public class BooleanConditionHandler implements ConditionHandler {

    @Override
    public boolean handle(ConditionValue condition) {
        Boolean leftValue = (Boolean)condition.getLeftValue();

        switch (condition.getOperator()) {
            case EQ:
                return Objects.equals(leftValue, condition.getRightValue());
            case NEQ:
                return !Objects.equals(leftValue, condition.getRightValue());
            case IS_TRUE:
                return BooleanUtils.isTrue(leftValue);
            case IS_FALSE:
                return BooleanUtils.isFalse(leftValue);
            case IN:
                return Objects.nonNull(condition.getRightValue()) && CollectionUtils.containsAny((List)condition.getRightValue(), leftValue);
            case NIN:
                return Objects.isNull(condition.getRightValue()) || !CollectionUtils.containsAny((List)condition.getRightValue(), leftValue);
            case IS_EMPTY:
                return Objects.isNull(condition.getLeftValue());
            case IS_NOT_EMPTY:
                return Objects.nonNull(condition.getLeftValue());
            default:
                return false;
        }
    }
}
