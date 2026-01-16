package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.handler;

import cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.ConditionValue;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class StringConditionHandler implements ConditionHandler {

    @Override
    public boolean handle(ConditionValue condition) {
        String leftValue = (String)condition.getLeftValue();

        switch (condition.getOperator()) {
            case EQ:
                return StringUtils.equals(leftValue, (String)condition.getRightValue());
            case NEQ:
                return !StringUtils.equals(leftValue, (String)condition.getRightValue());
            case CONTAINS:
                return StringUtils.contains(leftValue, (String)condition.getRightValue());
            case NOT_CONTAINS:
                return !StringUtils.contains(leftValue, (String)condition.getRightValue());
            case IN:
                return Objects.nonNull(condition.getRightValue()) && CollectionUtils.containsAny((List)condition.getRightValue(), leftValue);
            case NIN:
                return Objects.isNull(condition.getRightValue()) || !CollectionUtils.containsAny((List)condition.getRightValue(), leftValue);
            case IS_EMPTY:
                return StringUtils.isBlank(leftValue);
            case IS_NOT_EMPTY:
                return StringUtils.isNotBlank(leftValue);
            default:
                return false;
        }
    }
}
