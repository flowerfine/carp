package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.handler;

import cn.hutool.core.util.NumberUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.core.nodes.ConditionValue;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class NumberConditionHandler implements ConditionHandler {

    @Override
    public boolean handle(ConditionValue condition) {
        BigDecimal leftValue = toBigDecimal(condition.getLeftValue());
        BigDecimal rightValue = toBigDecimal(condition.getRightValue());

        switch (condition.getOperator()) {
            case EQ:
                return NumberUtil.equals(leftValue, rightValue);
            case NEQ:
                return !NumberUtil.equals(leftValue, rightValue);
            case GT:
                return leftValue.compareTo(rightValue) > 0;
            case GTE:
                return leftValue.compareTo(rightValue) >= 0;
            case LT:
                return leftValue.compareTo(rightValue) < 0;
            case LTE:
                return leftValue.compareTo(rightValue) <= 0;
            case IN:
                return Objects.nonNull(condition.getRightValue()) && CollectionUtils.containsAny((List)condition.getRightValue(), condition.getLeftValue());
            case NIN:
                return Objects.isNull(condition.getRightValue()) || !CollectionUtils.containsAny((List)condition.getRightValue(), condition.getLeftValue());
            case IS_EMPTY:
                return Objects.isNull(leftValue);
            case IS_NOT_EMPTY:
                return Objects.nonNull(leftValue);
            default:
                return false;
        }
    }

    private BigDecimal toBigDecimal(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }
        return NumberUtil.toBigDecimal((Number)value);
    }
}
